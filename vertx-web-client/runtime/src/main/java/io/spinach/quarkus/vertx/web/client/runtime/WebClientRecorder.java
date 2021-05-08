package io.spinach.quarkus.vertx.web.client.runtime;

import io.quarkus.arc.Arc;
import io.quarkus.runtime.annotations.Recorder;
import io.vertx.ext.web.client.WebClient;

import java.util.function.Supplier;

/**
 * @author aomsweet
 */
@Recorder
public class WebClientRecorder {

    public Supplier<WebClient> webClientSupplier(String clientName) {
        return () -> getWebClient(clientName);
    }

    private WebClient getWebClient(String clientName) {
        WebClientProducer webClientProducer = Arc.container().instance(WebClientProducer.class).get();
        return webClientProducer.getWebClient(clientName);
    }
}
