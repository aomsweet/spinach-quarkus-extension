package io.spinach.quarkus.vertx.web.client.runtime.config;

import io.vertx.core.http.HttpVersion;
import io.vertx.core.net.ProxyOptions;
import io.vertx.core.net.ProxyType;
import io.vertx.ext.web.client.WebClientOptions;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLDecoder;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * @author aomsweet
 */
public class ConfigurationConvertor {

    public WebClientOptions convertWebClientOptions(WebClientConfiguration configuration) {
        WebClientOptions options = new WebClientOptions();
        options.setUserAgentEnabled(configuration.userAgentEnabled);
        Optional<String> userAgent = configuration.userAgent;
        if (userAgent.isPresent()) {
            options.setUserAgent(userAgent.get());
        }
        options.setMaxPoolSize(configuration.maxPoolSize);
        options.setTryUseCompression(configuration.tryUseCompression);
        Optional<Integer> idleTimeout = configuration.idleTimeout;
        if (idleTimeout.isPresent()) {
            options.setIdleTimeout(idleTimeout.get());
        }
        Optional<TimeUnit> idleTimeoutUnit = configuration.idleTimeoutUnit;
        if (idleTimeoutUnit.isPresent()) {
            options.setIdleTimeoutUnit(idleTimeoutUnit.get());
        }
        Optional<Boolean> ssl = configuration.ssl;
        if (ssl.isPresent()) {
            options.setSsl(ssl.get());
        }
        Optional<Integer> sslHandshakeTimeout = configuration.sslHandshakeTimeout;
        if (sslHandshakeTimeout.isPresent()) {
            options.setSslHandshakeTimeout(sslHandshakeTimeout.get());
        }
        Optional<TimeUnit> sslHandshakeTimeoutUnit = configuration.sslHandshakeTimeoutUnit;
        if (sslHandshakeTimeoutUnit.isPresent()) {
            options.setSslHandshakeTimeoutUnit(sslHandshakeTimeoutUnit.get());
        }
        options.setKeepAlive(configuration.keepAlive);
        options.setKeepAliveTimeout((int) configuration.keepAliveTimeout.getSeconds());
        options.setConnectTimeout((int) configuration.connectTimeout.getSeconds());
        Optional<HttpVersion> protocolVersion = configuration.protocolVersion;
        if (protocolVersion.isPresent()) {
            options.setProtocolVersion(protocolVersion.get());
        }
        options.setMaxWaitQueueSize(configuration.maxWaitQueueSize);
        options.setFollowRedirects(configuration.followRedirects);
        options.setTcpNoDelay(configuration.tcpNoDelay);
        options.setTcpFastOpen(configuration.tcpFastOpen);
        options.setTcpKeepAlive(configuration.tcpKeepAlive);
        options.setTcpCork(configuration.tcpCork);
        options.setTcpQuickAck(configuration.tcpQuickAck);
        options.setHttp2MaxPoolSize(configuration.http2MaxPoolSize);
        options.setHttp2KeepAliveTimeout((int) configuration.http2KeepAliveTimeout.getSeconds());
        options.setTrustAll(configuration.trustAll);
        options.setVerifyHost(configuration.verifyHost);

        Optional<ProxyConfiguration> proxyConfiguration = configuration.proxy;
        if (proxyConfiguration.isPresent()) {
            ProxyOptions proxyOptions = buildProxyOptions(proxyConfiguration.get());
            options.setProxyOptions(proxyOptions);
        } else {
            Optional<String> proxyServerUrl = configuration.proxyUrl;
            if (proxyServerUrl.isPresent()) {
                ProxyOptions proxyOptions = buildProxyOptions(proxyServerUrl.get());
                options.setProxyOptions(proxyOptions);
            }
        }

        return options;
    }

    private ProxyOptions buildProxyOptions(String proxyServerUrl) {
        try {
            URI uri = URI.create(proxyServerUrl);
            String scheme = uri.getScheme();
            String userInfo = uri.getUserInfo();
            String host = uri.getHost();
            int port = uri.getPort();

            ProxyType proxyType = ProxyType.valueOf(scheme.toUpperCase());

            if (port == -1) {
                if (proxyType == ProxyType.HTTP) {
                    port = 3128;
                } else {
                    port = 1080;
                }
            }

            ProxyOptions proxyOptions = new ProxyOptions();
            proxyOptions.setType(proxyType)
                .setHost(host)
                .setPort(port);

            if (userInfo != null) {
                int i = userInfo.indexOf(':');
                if (i == -1) {
                    proxyOptions.setPassword(URLDecoder.decode(userInfo, "utf8"));
                } else {
                    proxyOptions.setUsername(URLDecoder.decode(userInfo.substring(0, i), "utf8"));
                    proxyOptions.setPassword(URLDecoder.decode(userInfo.substring(i + 1), "utf-8"));
                }
            }
            return proxyOptions;
        } catch (UnsupportedEncodingException e) {
            throw new IllegalArgumentException("Username or password contains unsupported characters. ", e);
        }
    }

    private ProxyOptions buildProxyOptions(ProxyConfiguration proxyConfiguration) {
        ProxyOptions proxyOptions = new ProxyOptions();
        proxyOptions.setType(proxyConfiguration.type)
            .setHost(proxyConfiguration.host)
            .setPort(proxyConfiguration.port);

        Optional<String> username = proxyConfiguration.username;
        if (username.isPresent()) {
            proxyOptions.setUsername(username.get());
        }

        Optional<String> password = proxyConfiguration.password;
        if (password.isPresent()) {
            proxyOptions.setPassword(password.get());
        }

        return proxyOptions;
    }

}
