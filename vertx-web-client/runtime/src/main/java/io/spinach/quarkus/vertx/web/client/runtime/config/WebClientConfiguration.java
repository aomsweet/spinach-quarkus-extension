package io.spinach.quarkus.vertx.web.client.runtime.config;

import io.quarkus.runtime.annotations.ConfigGroup;
import io.quarkus.runtime.annotations.ConfigItem;
import io.quarkus.runtime.annotations.ConvertWith;
import io.vertx.core.http.HttpVersion;
import org.eclipse.microprofile.config.spi.Converter;

import java.time.Duration;
import java.util.Optional;

/**
 * @author aomsweet
 */
@ConfigGroup
public class WebClientConfiguration {

    /**
     * userAgentEnabled
     */
    @ConfigItem(defaultValue = "false")
    boolean userAgentEnabled;

    /**
     * userAgent
     */
    @ConfigItem
    Optional<String> userAgent;

    /**
     * maxPoolSize
     */
    @ConfigItem(defaultValue = "16")
    int maxPoolSize;

    /**
     * tryUseCompression
     */
    @ConfigItem(defaultValue = "true")
    boolean tryUseCompression;

    /**
     * keepAlive
     */
    @ConfigItem(defaultValue = "true")
    boolean keepAlive;

    /**
     * keepAliveTimeout
     */
    @ConfigItem(defaultValue = "60s")
    Duration keepAliveTimeout;

    /**
     * connectTimeout
     */
    @ConfigItem(defaultValue = "60s")
    Duration connectTimeout;

    /**
     * protocolVersion
     */
    @ConfigItem
    @ConvertWith(HttpVersionConverter.class)
    Optional<HttpVersion> protocolVersion;

    /**
     * maxWaitQueueSize
     */
    @ConfigItem(defaultValue = "-1")
    int maxWaitQueueSize;

    /**
     * followRedirects
     */
    @ConfigItem(defaultValue = "true")
    boolean followRedirects;

    /**
     * tcpNoDelay
     */
    @ConfigItem(defaultValue = "true")
    boolean tcpNoDelay;

    /**
     * tcpFastOpen
     */
    @ConfigItem(defaultValue = "false")
    boolean tcpFastOpen;

    /**
     * tcpKeepAlive
     */
    @ConfigItem(defaultValue = "true")
    boolean tcpKeepAlive;

    /**
     * tcpCork
     */
    @ConfigItem(defaultValue = "false")
    boolean tcpCork;

    /**
     * tcpQuickAck
     */
    @ConfigItem(defaultValue = "false")
    boolean tcpQuickAck;

    /**
     * http2MaxPoolSize
     */
    @ConfigItem(defaultValue = "8")
    int http2MaxPoolSize;

    /**
     * http2KeepAliveTimeout
     */
    @ConfigItem(defaultValue = "60s")
    Duration http2KeepAliveTimeout;

    /**
     * trustAll
     */
    @ConfigItem
    boolean trustAll;

    /**
     * verifyHost
     */
    @ConfigItem
    boolean verifyHost;

    /**
     * proxyConfiguration
     */
    @ConfigItem
    Optional<ProxyConfiguration> proxy;

    public static class HttpVersionConverter implements Converter<HttpVersion> {

        @Override
        public HttpVersion convert(String value) {
            value = value.trim().toUpperCase();
            switch (value) {
                case "HTTP_1_1":
                    return HttpVersion.HTTP_1_1;
                case "HTTP_1_0":
                    return HttpVersion.HTTP_1_0;
                case "HTTP_2":
                    return HttpVersion.HTTP_2;
                default:
                    throw new IllegalArgumentException("Unsupported value [" + value + "] given.");
            }
        }
    }
}
