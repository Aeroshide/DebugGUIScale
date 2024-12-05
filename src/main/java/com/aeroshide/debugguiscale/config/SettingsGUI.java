package com.aeroshide.debugguiscale.config;

import com.aeroshide.debugguiscale.DebugguiscaleClient;
import com.aeroshide.rose_bush.gui.DoubleFieldWidget;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import com.aeroshide.rose_bush.gui.IntFieldWidget;

public class SettingsGUI extends Screen {

    private final Screen parent;
    private DoubleFieldWidget scaleField;
    private ButtonWidget resetScaleField;

    private static final Text TEXT_MULTIPLIER_TEXT = Text.translatable("debugguiscale.scale");

    public SettingsGUI(Screen parent) {
        super(Text.translatable("debugguiscale.configScreen"));
        this.parent = parent;
    }

    @Override
    public void init() {
        this.scaleField = new DoubleFieldWidget(this.textRenderer, this.width / 2 - 100, 44, 200, 20, Text.translatable("debugguiscale.scale"));
        this.scaleField.setMaxLength(4);
        this.scaleField.setText(String.valueOf(DebugguiscaleClient.config.getOption("scale"))); // Default value, adjust as needed

        this.resetScaleField = this.addDrawableChild(ButtonWidget.builder(Text.literal("R"), (button) -> {
            scaleField.setText("1"); // Reset to default value
        }).dimensions(this.scaleField.getX() + 205, this.scaleField.getY(), 20, 20).build());

        ButtonWidget discardButton = this.addDrawableChild(ButtonWidget.builder(Text.translatable("debugguiscale.discard"), (button) -> {
            client.setScreen(this.parent);
        }).dimensions(this.width / 2 - 110, this.height / 2 + 90, 100, 20).build());

        ButtonWidget acceptButton = this.addDrawableChild(ButtonWidget.builder(Text.translatable("debugguiscale.save"), (button) -> {
            DebugguiscaleClient.scale = this.scaleField.getDouble();
            DebugguiscaleClient.config.setOption("scale", this.scaleField.getDouble());
            client.setScreen(this.parent);
        }).dimensions(this.width / 2 + 20, this.height / 2 + 90, 100, 20).build());

        this.setInitialFocus(this.scaleField);
        this.addSelectableChild(this.scaleField);
    }

    @Override
    public void render(final DrawContext context, final int mouseX, final int mouseY, final float delta) {
        super.render(context, mouseX, mouseY, delta);
        context.drawCenteredTextWithShadow(this.textRenderer, this.title, this.width / 2, 20, 16777215);
        context.drawTextWithShadow(this.textRenderer, TEXT_MULTIPLIER_TEXT, this.width / 2 - 100, this.scaleField.getY() - 10, 10526880);

        this.scaleField.render(context, mouseX, mouseY, delta);
    }
}