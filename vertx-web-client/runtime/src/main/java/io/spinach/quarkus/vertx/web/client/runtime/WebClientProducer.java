package io.spinach.quarkus.vertx.web.client.runtime;

import io.spinach.quarkus.vertx.web.client.runtime.config.ConfigurationConvertor;
import io.spinach.quarkus.vertx.web.client.runtime.config.RootWebClientConfiguration;
import io.spinach.quarkus.vertx.web.client.runtime.config.WebClientConfiguration;
import io.vertx.core.Vertx;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.client.WebClientOptions;

import javax.annotation.PreDestroy;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author aomsweet
 */
public class WebClientProducer {

    private final Map<String, WebClient> webClients = new ConcurrentHashMap<>();

    private final Vertx vertx;
    private final RootWebClientConfiguration configuration;

    public RootWebClientConfiguration getConfiguration() {
        return configuration;
    }

    public WebClientProducer(Vertx vertx, RootWebClientConfiguration configuration) {
        this.vertx = vertx;
        this.configuration = configuration;
    }

    public WebClient getWebClient(String name) {
        return webClients.computeIfAbsent(name, key -> {
            WebClientConfiguration configuration = this.configuration.getWebClientConfiguration(key);
            if (configuration == null) {
                throw new IllegalArgumentException("Configuration named [" + key + "] could not be found.");
            } else {
                ConfigurationConvertor convertor = new ConfigurationConvertor();
                WebClientOptions options = convertor.convertWebClientOptions(configuration);
                return WebClient.create(vertx, options);
            }
        });
    }

    @PreDestroy
    public void close() {
        for (WebClient webClient : webClients.values()) {
            webClient.close();
        }

        webClients.clear();
    }
}
