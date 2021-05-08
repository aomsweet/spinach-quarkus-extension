package io.spinach.quarkus.vertx.web.client;

import javax.inject.Qualifier;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author aomsweet
 */
@Target({ElementType.TYPE, ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
@Retention(RUNTIME)
@Documented
@Qualifier
public @interface WebClientName {

    /**
     * The Web client name.
     */
    String value() default "";

}
