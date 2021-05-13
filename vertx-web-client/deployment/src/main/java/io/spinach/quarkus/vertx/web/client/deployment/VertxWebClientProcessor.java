package io.spinach.quarkus.vertx.web.client.deployment;

import io.quarkus.arc.deployment.AdditionalBeanBuildItem;
import io.quarkus.arc.deployment.BeanArchiveIndexBuildItem;
import io.quarkus.arc.deployment.SyntheticBeanBuildItem;
import io.quarkus.deployment.annotations.BuildProducer;
import io.quarkus.deployment.annotations.BuildStep;
import io.quarkus.deployment.annotations.ExecutionTime;
import io.quarkus.deployment.annotations.Record;
import io.quarkus.deployment.builditem.ExtensionSslNativeSupportBuildItem;
import io.quarkus.deployment.builditem.FeatureBuildItem;
import io.quarkus.vertx.core.deployment.CoreVertxBuildItem;
import io.spinach.quarkus.vertx.web.client.WebClientName;
import io.spinach.quarkus.vertx.web.client.runtime.WebClientDestroyer;
import io.spinach.quarkus.vertx.web.client.runtime.WebClientRecorder;
import io.spinach.quarkus.vertx.web.client.runtime.config.RootWebClientConfiguration;
import io.vertx.core.Vertx;
import io.vertx.ext.web.client.WebClient;
import org.jboss.jandex.AnnotationInstance;
import org.jboss.jandex.AnnotationValue;
import org.jboss.jandex.DotName;
import org.jboss.jandex.IndexView;

import javax.enterprise.inject.Default;
import javax.inject.Singleton;
import java.util.Collection;
import java.util.function.Supplier;

class VertxWebClientProcessor {

    private static final DotName WEB_CLIENT_ANNOTATION = DotName.createSimple(WebClientName.class.getName());

    private static final String FEATURE = "vertx-web-client";

    @BuildStep
    FeatureBuildItem feature() {
        return new FeatureBuildItem(FEATURE);
    }

    @BuildStep
    ExtensionSslNativeSupportBuildItem activateSslNativeSupport() {
        return new ExtensionSslNativeSupportBuildItem(FEATURE);
    }

    @BuildStep
    AdditionalBeanBuildItem registerWebClientNameBean() {
        return AdditionalBeanBuildItem
            .builder()
            .addBeanClass(WebClientName.class)
            .build();
    }

    @BuildStep
    @Record(ExecutionTime.RUNTIME_INIT)
    public void produceWebClient(WebClientRecorder recorder,
                                 CoreVertxBuildItem vertxBuildItem,
                                 BeanArchiveIndexBuildItem indexBuildItem,
                                 RootWebClientConfiguration configuration,
                                 BuildProducer<SyntheticBeanBuildItem> syntheticBeans) {
        Supplier<Vertx> vertx = vertxBuildItem.getVertx();

        // default web client
        syntheticBeans.produce(createWebClientSyntheticBean(null, vertx, configuration, recorder));

        // named web client
        IndexView indexView = indexBuildItem.getIndex();
        Collection<AnnotationInstance> clientAnnotations = indexView.getAnnotations(WEB_CLIENT_ANNOTATION);
        for (AnnotationInstance annotation : clientAnnotations) {
            AnnotationValue value = annotation.value();
            if (value != null) {
                String name = annotation.value().asString();
                syntheticBeans.produce(createWebClientSyntheticBean(name, vertx, configuration, recorder));
            }
        }
    }

    private SyntheticBeanBuildItem createWebClientSyntheticBean(String name,
                                                                Supplier<Vertx> vertx,
                                                                RootWebClientConfiguration configuration,
                                                                WebClientRecorder recorder) {
        Supplier<WebClient> webClient = recorder.createWebClient(name, vertx, configuration);
        SyntheticBeanBuildItem.ExtendedBeanConfigurator configurator = SyntheticBeanBuildItem
            .configure(WebClient.class)
            .unremovable()
            .setRuntimeInit()
            .supplier(webClient)
            .scope(Singleton.class)
            .destroyer(WebClientDestroyer.class);

        if (name == null) {
            configurator.addQualifier(Default.class).done();
        } else {
            configurator.addQualifier().annotation(WEB_CLIENT_ANNOTATION).addValue("value", name).done();
        }

        return configurator.done();
    }
}
