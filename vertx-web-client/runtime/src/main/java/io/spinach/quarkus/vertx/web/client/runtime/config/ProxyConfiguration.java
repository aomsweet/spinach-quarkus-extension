package io.spinach.quarkus.vertx.web.client.runtime.config;

import io.quarkus.runtime.annotations.ConfigGroup;
import io.quarkus.runtime.annotations.ConfigItem;
import io.quarkus.runtime.annotations.ConvertWith;
import io.vertx.core.net.ProxyType;
import org.eclipse.microprofile.config.spi.Converter;

import java.util.Optional;

/**
 * @author aomsweet
 */
@ConfigGroup
public class ProxyConfiguration {

    /**
     * type
     */
//    @DefaultConverter
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

    public static class ProxyTypeConverter implements Converter<ProxyType> {

        @Override
        public ProxyType convert(String value) {
            if (value == null || value.isEmpty()) {
                throw new IllegalArgumentException("Proxy type is empty.");
            }
            value = value.trim().toUpperCase();
            switch (value) {
                case "HTTP":
                    return ProxyType.HTTP;
                case "SOCKS5":
                    return ProxyType.SOCKS5;
                case "SOCKS4":
                    return ProxyType.SOCKS4;
                default:
                    throw new IllegalArgumentException("Unsupported value [" + value + "] given.");
            }
        }
    }

}
