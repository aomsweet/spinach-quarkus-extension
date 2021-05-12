package io.spinach.web.client;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.spinach.quarkus.vertx.web.client.WebClientName;
import io.spinach.quarkus.vertx.web.client.runtime.config.RootWebClientConfiguration;
import io.vertx.core.buffer.Buffer;
import io.vertx.ext.web.client.HttpRequest;
import io.vertx.ext.web.client.WebClient;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

@Path("/client")
public class WebClientResource {

    @Inject
    WebClient webClient;

    @Inject
    @WebClientName("c1")
    WebClient webClientC1;

    @Inject
    RootWebClientConfiguration configuration;

    @GET
    @Path("/config")
    @Produces(MediaType.APPLICATION_JSON)
    public String config() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper.writeValueAsString(configuration);
    }

    @GET
    public CompletionStage<String> defaultClient() {
        CompletableFuture<String> future = new CompletableFuture<>();
        HttpRequest<Buffer> request = webClient.getAbs("https://github.com/");
        request.send()
            .onSuccess(response -> future.complete(response.bodyAsString()))
            .onFailure(future::completeExceptionally);
        return future;
    }

    @GET
    @Path("/c1")
    public CompletionStage<String> c1Client() {
        CompletableFuture<String> future = new CompletableFuture<>();
        HttpRequest<Buffer> request = webClientC1.getAbs("https://github.com/");
        request.send()
            .onSuccess(response -> future.complete(response.bodyAsString()))
            .onFailure(future::completeExceptionally);
        return future;
    }
}
