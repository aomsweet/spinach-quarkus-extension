package io.spinach.quarkus.vertx.web.client.runtime.config;

import io.quarkus.runtime.annotations.ConfigItem;
import io.quarkus.runtime.annotations.ConfigPhase;
import io.quarkus.runtime.annotations.ConfigRoot;

import java.util.Map;

/**
 * @author aomsweet
 */
@ConfigRoot(name = "spinach.web.client", phase = ConfigPhase.RUN_TIME)
public class RootWebClientConfiguration {

    public static final String DEFAULT_CLIENT = "<default>";

    public static boolean isDefault(String name) {
        return DEFAULT_CLIENT.equals(name);
    }

    /**
     * defaultConfiguration
     */
    @ConfigItem(name = ConfigItem.PARENT)
    public WebClientConfiguration defaultConfiguration;

    /**
     * additionalConfiguration
     */
    @ConfigItem(name = ConfigItem.PARENT)
    public Map<String, WebClientConfiguration> additionalConfiguration;

    public WebClientConfiguration getWebClientConfiguration(String name) {
        return isDefault(name) ? defaultConfiguration : additionalConfiguration.get(name);
    }
}
