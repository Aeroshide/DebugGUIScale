package com.aeroshide.debugguiscale;

import com.aeroshide.debugguiscale.config.DebugGuiScaleConfigRegistrar;
import com.aeroshide.rose_bush.config.Config;
import net.fabricmc.api.ClientModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Path;

public class DebugguiscaleClient implements ClientModInitializer {
    public static final Logger LOG = LogManager.getLogger("DebugGUIScale");
    public static Double scale = 1d;
    public static Double ALTscale = 0.7d;
    public static Config config;

    static {
        try {
            config = new Config(Path.of("config/DebugGUIScale.json"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onInitializeClient() {
        if (config.getOption("scale") == null)
        {
            try {
                config.setOption("scale", 1d);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        if (config.getOption("ALTscale") == null)
        {
            try {
                config.setOption("ALTscale", 0.7d);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        scale = Double.parseDouble(config.getOption("scale").toString());
        ALTscale = Double.parseDouble(config.getOption("ALTscale").toString());

        DebugGuiScaleConfigRegistrar.register();
    }
}
