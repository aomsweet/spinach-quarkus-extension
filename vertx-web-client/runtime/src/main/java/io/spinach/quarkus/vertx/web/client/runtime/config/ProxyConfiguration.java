package io.spinach.quarkus.vertx.web.client.runtime.config;

import io.quarkus.runtime.annotations.ConfigGroup;
import io.quarkus.runtime.annotations.ConfigItem;
import io.quarkus.runtime.annotations.ConvertWith;
import io.vertx.core.net.ProxyType;

import java.util.Optional;

/**
 * @author aomsweet
 */
@ConfigGroup
public class ProxyConfiguration {

    /**
     * type
     */
    @ConvertWith(ProxyTypeConverter.class)
    @ConfigItem
    ProxyType type;

    /**
     * host
     */
    @ConfigItem
    String host;

    /**
     * port
     */
    @ConfigItem
    int port;

    /**
     * username
     */
    @ConfigItem
    Optional<String> username;

    /**
     * password
     */
    @ConfigItem
    Optional<String> password;

}
