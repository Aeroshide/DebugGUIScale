package com.aeroshide.debugguiscale.config;

import com.aeroshide.debugguiscale.config.SettingsGUI;
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import net.minecraft.client.gui.screen.Screen;

public class ModMenuImpl implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return (ConfigScreenFactory<Screen>) SettingsGUI::new;
    }
}