package com.aeroshide.debugguiscale.mixins;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.DebugHud;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.List;

import static com.aeroshide.debugguiscale.DebugguiscaleClient.scale;

@Mixin(DebugHud.class)
abstract class DebugHudMixin {
    @WrapMethod(method = "drawText")
    private void wrapTextRendering(DrawContext context, List<String> text, boolean left, Operation<Void> original) {
        context.getMatrices().pushMatrix();
        context.getMatrices().scale(scale.floatValue(), scale.floatValue());
        original.call(context, text, left);
        context.getMatrices().popMatrix();
    }

    @ModifyExpressionValue(method = "drawText", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;getScaledWindowWidth()I"))
    private int getReadWidth(int original) {
        return (int) (original / scale);
    }
}
