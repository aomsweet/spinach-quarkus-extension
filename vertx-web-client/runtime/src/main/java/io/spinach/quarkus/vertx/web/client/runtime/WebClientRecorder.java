package io.spinach.quarkus.vertx.web.client.runtime;

import io.quarkus.arc.Arc;
import io.quarkus.runtime.annotations.Recorder;
import io.spinach.quarkus.vertx.web.client.runtime.config.ConfigurationConvertor;
import io.spinach.quarkus.vertx.web.client.runtime.config.RootWebClientConfiguration;
import io.spinach.quarkus.vertx.web.client.runtime.config.WebClientConfiguration;
import io.vertx.core.Vertx;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.client.WebClientOptions;

import java.util.function.Supplier;

/**
 * @author aomsweet
 */
@Recorder
public class WebClientRecorder {

    public Supplier<WebClient> configureWebClient(String clientName) {
        return () -> createWebClient(clientName);
    }

    private WebClient createWebClient(String clientName) {
        Vertx vertx = Arc.container().instance(Vertx.class).get();
        RootWebClientConfiguration rootConfiguration = Arc.container().instance(RootWebClientConfiguration.class).get();
        WebClientConfiguration configuration = rootConfiguration.getWebClientConfiguration(clientName);
        if (configuration == null) {
            throw new IllegalArgumentException("Configuration named [" + clientName + "] could not be found.");
        } else {
            ConfigurationConvertor convertor = new ConfigurationConvertor();
            WebClientOptions options = convertor.convertWebClientOptions(configuration);
            return WebClient.create(vertx, options);
        }
    }
}
