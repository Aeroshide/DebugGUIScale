package com.aeroshide.debugguiscale.mixins;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.debug.DebugScreenEntryList;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

import java.util.List;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.DebugScreenOverlay;

import static com.aeroshide.debugguiscale.DebugguiscaleClient.ALTscale;
import static com.aeroshide.debugguiscale.DebugguiscaleClient.scale;

@Mixin(DebugScreenOverlay.class)
abstract class DebugHudMixin {
    @Shadow @Final
    private Minecraft minecraft;

    @WrapMethod(method = "extractLines")
    private void wrapTextRendering(GuiGraphicsExtractor graphics, List<String> text, boolean left, Operation<Void> original) {
        graphics.pose().pushMatrix();

        if (isOverlayVisible()) {
            graphics.pose().scale(scale.floatValue(), scale.floatValue());
        } else {
            graphics.pose().scale(ALTscale.floatValue(), ALTscale.floatValue());
        }

        original.call(graphics, text, left);
        graphics.pose().popMatrix();
    }

    @ModifyExpressionValue(method = "extractLines", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphicsExtractor;guiWidth()I"))
    private int getReadWidth(int original) {
        float currentScale = isOverlayVisible() ? scale.floatValue() : ALTscale.floatValue();
        return (int) (original / currentScale);
    }

    @Unique
    private boolean isOverlayVisible() {
        DebugScreenEntryList debugScreenEntryList = this.minecraft.debugEntries;
        return debugScreenEntryList.isOverlayVisible();
    }
}