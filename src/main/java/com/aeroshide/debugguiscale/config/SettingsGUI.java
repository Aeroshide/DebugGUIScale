package com.aeroshide.debugguiscale.config;

import com.aeroshide.debugguiscale.DebugguiscaleClient;
import com.aeroshide.rose_bush.gui.DoubleFieldWidget;

import java.io.IOException;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.jspecify.annotations.NonNull;

public class SettingsGUI extends Screen {

    private final Screen parent;

    private DoubleFieldWidget scaleField;
    private Button resetScaleField;

    private DoubleFieldWidget altScaleField;
    private Button resetAltScaleField;

    private static final Component TEXT_MULTIPLIER_TEXT = Component.translatable("debugguiscale.scale");
    private static final Component ALT_MULTIPLIER_TEXT = Component.translatable("debugguiscale.altScale");

    public SettingsGUI(Screen parent) {
        super(Component.translatable("debugguiscale.configScreen"));
        this.parent = parent;
    }

    @Override
    public void init() {
        this.scaleField = new DoubleFieldWidget(this.font, this.width / 2 - 100, 44, 200, 20, Component.translatable("debugguiscale.scale"));
        this.scaleField.setMaxLength(4);
        this.scaleField.setValue(String.valueOf(DebugguiscaleClient.config.getOption("scale")));

        this.resetScaleField = this.addRenderableWidget(Button.builder(Component.literal("R"), (button) -> {
            scaleField.setValue("1.0");
        }).bounds(this.scaleField.getX() + 205, this.scaleField.getY(), 20, 20).build());

        this.altScaleField = new DoubleFieldWidget(this.font, this.width / 2 - 100, 88, 200, 20, Component.translatable("debugguiscale.altScale"));
        this.altScaleField.setMaxLength(4);
        this.altScaleField.setValue(String.valueOf(DebugguiscaleClient.config.getOption("ALTscale")));

        this.resetAltScaleField = this.addRenderableWidget(Button.builder(Component.literal("R"), (button) -> {
            altScaleField.setValue("0.7");
        }).bounds(this.altScaleField.getX() + 205, this.altScaleField.getY(), 20, 20).build());

        Button discardButton = this.addRenderableWidget(Button.builder(Component.translatable("debugguiscale.discard"), (button) -> {
            minecraft.gui.setScreen(this.parent);
        }).bounds(this.width / 2 - 110, this.height / 2 + 90, 100, 20).build());

        Button acceptButton = this.addRenderableWidget(Button.builder(Component.translatable("debugguiscale.save"), (button) -> {
            DebugguiscaleClient.scale = this.scaleField.getDouble();
            DebugguiscaleClient.ALTscale = this.altScaleField.getDouble();

            try {
                DebugguiscaleClient.config.setOption("scale", this.scaleField.getDouble());
                DebugguiscaleClient.config.setOption("ALTscale", this.altScaleField.getDouble());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            minecraft.gui.setScreen(this.parent);
        }).bounds(this.width / 2 + 20, this.height / 2 + 90, 100, 20).build());

        this.setInitialFocus(this.scaleField);

        this.addRenderableWidget(this.scaleField);
        this.addRenderableWidget(this.altScaleField);
    }

    @Override
    public void extractRenderState(final @NonNull GuiGraphicsExtractor graphics, final int mouseX, final int mouseY, final float delta) {
        super.extractRenderState(graphics, mouseX, mouseY, delta);

        graphics.centeredText(this.font, this.title, this.width / 2, 20, 0xFFFFFFFF);

        graphics.text(this.font, TEXT_MULTIPLIER_TEXT, this.width / 2 - 100, this.scaleField.getY() - 10, 0xFFA0A0A0);
        graphics.text(this.font, ALT_MULTIPLIER_TEXT, this.width / 2 - 100, this.altScaleField.getY() - 10, 0xFFA0A0A0);
    }
}