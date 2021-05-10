package io.spinach.quarkus.vertx.web.client.runtime.config;

import io.vertx.core.net.ProxyType;
import org.eclipse.microprofile.config.spi.Converter;

/**
 * @author aomsweet
 */
public class ProxyTypeConverter implements Converter<ProxyType> {

    @Override
    public ProxyType convert(String value) {
        if (value == null || value.isEmpty()) {
            throw new IllegalArgumentException("Proxy type is empty.");
        }
        value = value.trim().toUpperCase();
        switch (value) {
            case "HTTP":
            case "HTTPS":
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
