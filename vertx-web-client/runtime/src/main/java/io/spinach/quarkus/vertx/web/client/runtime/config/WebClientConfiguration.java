package io.spinach.quarkus.vertx.web.client.runtime.config;

import io.quarkus.runtime.annotations.ConfigGroup;
import io.quarkus.runtime.annotations.ConfigItem;
import io.vertx.core.http.HttpVersion;

import java.time.Duration;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * @author aomsweet
 */
@ConfigGroup
public class WebClientConfiguration {

    /**
     * userAgentEnabled
     */
    @ConfigItem(defaultValue = "true")
    boolean userAgentEnabled;

    /**
     * userAgent
     */
    @ConfigItem
    Optional<String> userAgent;

    /**
     * maxPoolSize
     */
    @ConfigItem(defaultValue = "8")
    int maxPoolSize;

    /**
     * tryUseCompression
     */
    @ConfigItem(defaultValue = "true")
    boolean tryUseCompression;

    /**
     * idleTimeout
     */
    @ConfigItem
    Optional<Integer> idleTimeout;

    /**
     * idleTimeoutUnit
     */
    @ConfigItem
    Optional<TimeUnit> idleTimeoutUnit;

    /**
     * ssl
     */
    @ConfigItem
    Optional<Boolean> ssl;

    /**
     * sslHandshakeTimeout
     */
    @ConfigItem
    Optional<Integer> sslHandshakeTimeout;

    /**
     * sslHandshakeTimeoutUnit
     */
    @ConfigItem
    Optional<TimeUnit> sslHandshakeTimeoutUnit;

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
     * <p>
     * If `proxyUrl` is configured, it will be ignored
     *
     * @see #proxyUrl
     */
    @ConfigItem
    Optional<ProxyConfiguration> proxy;

    /**
     * <scheme>://<username>:<password>@<hostname>:<port>
     * <p>
     * proxyUrl
     */
    @ConfigItem
    Optional<String> proxyUrl;

}
