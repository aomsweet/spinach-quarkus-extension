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

}
