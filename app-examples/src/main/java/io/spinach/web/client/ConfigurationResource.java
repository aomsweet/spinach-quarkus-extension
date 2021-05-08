package io.spinach.web.client;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.spinach.quarkus.vertx.web.client.runtime.WebClientProducer;
import io.spinach.quarkus.vertx.web.client.runtime.config.WebClientConfiguration;
import io.vertx.ext.web.client.WebClient;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/config")
public class ConfigurationResource {

    @Inject
    WebClient webClient;

    @Inject
    WebClientProducer webClientProducer;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String config() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        objectMapper.registerModule(new JavaTimeModule());
        WebClientConfiguration configuration = webClientProducer.getConfiguration().defaultConfiguration;
        return objectMapper.writeValueAsString(configuration);
    }
}
