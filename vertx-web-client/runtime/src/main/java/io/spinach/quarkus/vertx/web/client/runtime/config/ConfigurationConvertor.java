package io.spinach.quarkus.vertx.web.client.runtime.config;

import io.vertx.core.http.HttpVersion;
import io.vertx.core.net.ProxyOptions;
import io.vertx.ext.web.client.WebClientOptions;

import java.util.Optional;

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

//        ProxyConfiguration proxyConfiguration = configuration.proxy;
//        ProxyOptions proxyOptions = buildProxyOptions(proxyConfiguration);
//        options.setProxyOptions(proxyOptions);

        Optional<ProxyConfiguration> proxyConfiguration = configuration.proxy;
        if (proxyConfiguration.isPresent()) {
            ProxyOptions proxyOptions = buildProxyOptions(proxyConfiguration.get());
            options.setProxyOptions(proxyOptions);
        }

        return options;
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
