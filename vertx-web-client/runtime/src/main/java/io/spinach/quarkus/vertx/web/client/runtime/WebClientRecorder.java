package io.spinach.quarkus.vertx.web.client.runtime;

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

    ConfigurationConvertor convertor = new ConfigurationConvertor();

    public Supplier<WebClient> createWebClient(String name,
                                               Supplier<Vertx> vertx,
                                               RootWebClientConfiguration configuration) {
        return () -> {
            WebClientOptions options;
            if (name == null) {
                options = convertor.convertWebClientOptions(configuration.defaultConfiguration);
            } else {
                WebClientConfiguration namedConfiguration = configuration.additionalConfiguration.get(name);
                if (namedConfiguration == null) {
                    throw new IllegalArgumentException("Configuration named [" + name + "] could not be found.");
                }
                options = convertor.convertWebClientOptions(namedConfiguration);
            }
            return WebClient.create(vertx.get(), options);
        };
    }


}
