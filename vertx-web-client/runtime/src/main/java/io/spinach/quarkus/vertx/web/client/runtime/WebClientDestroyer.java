package io.spinach.quarkus.vertx.web.client.runtime;

import io.quarkus.arc.BeanDestroyer;
import io.vertx.ext.web.client.WebClient;

import javax.enterprise.context.spi.CreationalContext;
import java.util.Map;

/**
 * @author aomsweet
 */
public class WebClientDestroyer implements BeanDestroyer<WebClient> {

    @Override
    public void destroy(WebClient instance, CreationalContext<WebClient> creationalContext, Map<String, Object> params) {
        instance.close();
    }
}
