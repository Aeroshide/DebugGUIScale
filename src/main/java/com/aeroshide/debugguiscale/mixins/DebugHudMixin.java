package com.aeroshide.debugguiscale.mixins;

import com.aeroshide.debugguiscale.DebugguiscaleClient;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.DebugHud;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

import static com.aeroshide.debugguiscale.DebugguiscaleClient.scale;

@Mixin(DebugHud.class)
abstract class DebugHudMixin {

    @Inject(method = "drawText", at = @At("HEAD"))
    private void beforeTextRendering(DrawContext context, List<String> text, boolean left, CallbackInfo ci) {
        context.getMatrices().pushMatrix();
        context.getMatrices().scale(scale.floatValue(), scale.floatValue());
    }

    @ModifyExpressionValue(method = "drawText", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;getScaledWindowWidth()I"))
    private int getReadWidth(int original) {
        return (int) (original / scale);
    }

    @Inject(method = "drawText", at = @At("TAIL"))
    private void afterTextRendering(DrawContext context, List<String> text, boolean left, CallbackInfo ci) {
        context.getMatrices().popMatrix();
    }
}