package com.aeroshide.debugguiscale;

import com.aeroshide.rose_bush.config.Config;
import net.fabricmc.api.ClientModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DebugguiscaleClient implements ClientModInitializer {
    public static final Logger LOG = LogManager.getLogger("DebugGUIScale");
    public static Double scale = 1d;
    public static Config config = new Config("config/DebugGUIScale.json");
    @Override
    public void onInitializeClient() {
        if (config.getOption("scale") == null)
        {
            config.setOption("scale", 1d);
        }

        scale = Double.parseDouble(config.getOption("scale").toString());
    }
}
