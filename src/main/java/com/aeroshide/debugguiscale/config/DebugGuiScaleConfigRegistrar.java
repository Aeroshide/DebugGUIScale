package com.aeroshide.debugguiscale.config;

import com.aeroshide.debugguiscale.DebugguiscaleClient;
import com.aeroshide.rose_bush.api.RoselibVanillaSettings;
import com.mojang.serialization.Codec;
import net.minecraft.client.OptionInstance;
import net.minecraft.network.chat.Component;

import java.io.IOException;
import java.util.Locale;

public class DebugGuiScaleConfigRegistrar {

    public static void register() {
        OptionInstance<Double> scaleOption = new OptionInstance<>(
                "debugguiscale.scaleVanilla",
                OptionInstance.cachedConstantTooltip(Component.translatable("debugguiscale.scaleVanillaDESC")),
                (caption, value) -> Component.translatable("options.generic_value", caption, Component.literal(String.format(Locale.ROOT, "%.2f", value))),
                (new OptionInstance.IntRange(2, 100)).xmap(
                        step -> (double)step / 20.0,
                        value -> (int)(value * 20.0),
                        false
                ),
                Codec.doubleRange(0.1, 5.0),
                getSafeDouble("scale", DebugguiscaleClient.scale, 1.0),
                (newValue) -> {
                    DebugguiscaleClient.scale = newValue;
                    saveConfig("scale", newValue);
                }
        );

        OptionInstance<Double> altScaleOption = new OptionInstance<>(
                "debugguiscale.altScaleVanilla",
                OptionInstance.cachedConstantTooltip(Component.translatable("debugguiscale.altScaleVanillaDESC")),
                (caption, value) -> Component.translatable("options.generic_value", caption, Component.literal(String.format(Locale.ROOT, "%.2f", value))),
                (new OptionInstance.IntRange(2, 100)).xmap(
                        step -> (double)step / 20.0,
                        value -> (int)(value * 20.0),
                        false
                ),
                Codec.doubleRange(0.1, 5.0),
                getSafeDouble("ALTscale", DebugguiscaleClient.ALTscale, 0.7),
                (newValue) -> {
                    DebugguiscaleClient.ALTscale = newValue;
                    saveConfig("ALTscale", newValue);
                }
        );

        RoselibVanillaSettings.addAccessibilityOption(scaleOption);
        RoselibVanillaSettings.addAccessibilityOption(altScaleOption);
    }

    private static Double getSafeDouble(String key, double fallbackStatic, double hardFallback) {
        try {
            Object rawVal = DebugguiscaleClient.config.getOption(key);
            if (rawVal instanceof Number) return ((Number) rawVal).doubleValue();
            if (rawVal instanceof String) return Double.parseDouble((String) rawVal);
            return fallbackStatic;
        } catch (Exception e) {
            return hardFallback;
        }
    }

    private static void saveConfig(String key, Double value) {
        try {
            DebugguiscaleClient.config.setOption(key, value);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}