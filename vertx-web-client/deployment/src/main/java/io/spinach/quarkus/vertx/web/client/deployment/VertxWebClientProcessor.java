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
import io.spinach.quarkus.vertx.web.client.WebClientName;
import io.spinach.quarkus.vertx.web.client.runtime.WebClientProducer;
import io.spinach.quarkus.vertx.web.client.runtime.WebClientRecorder;
import io.spinach.quarkus.vertx.web.client.runtime.config.RootWebClientConfiguration;
import io.vertx.ext.web.client.WebClient;
import org.jboss.jandex.AnnotationInstance;
import org.jboss.jandex.AnnotationValue;
import org.jboss.jandex.DotName;
import org.jboss.jandex.IndexView;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Default;
import java.util.*;

import static io.quarkus.arc.processor.BuiltinScope.SINGLETON;

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
    List<AdditionalBeanBuildItem> registerWebClientProducerBeans() {
        return Arrays.asList(
            AdditionalBeanBuildItem
                .builder()
                .addBeanClass(WebClientProducer.class)
                .setDefaultScope(SINGLETON.getName())
                .setUnremovable()
                .build(),
            AdditionalBeanBuildItem
                .builder()
                .addBeanClass(WebClientName.class)
                .build());
    }

    @BuildStep
    @Record(ExecutionTime.RUNTIME_INIT)
    public void produceWebClient(WebClientRecorder recorder, BeanArchiveIndexBuildItem indexBuildItem,
                                 BuildProducer<SyntheticBeanBuildItem> syntheticBeans) {
        Set<String> clientNames = new HashSet<>();
        clientNames.add(RootWebClientConfiguration.DEFAULT_CLIENT);

        IndexView indexView = indexBuildItem.getIndex();
        Collection<AnnotationInstance> clientAnnotations = indexView.getAnnotations(WEB_CLIENT_ANNOTATION);
        for (AnnotationInstance annotation : clientAnnotations) {
            AnnotationValue value = annotation.value();
            if (value != null) {
                clientNames.add(annotation.value().asString());
            }
        }

        for (String clientName : clientNames) {
            syntheticBeans.produce(createWebClientSyntheticBean(recorder, clientName));
        }
    }

    private SyntheticBeanBuildItem createWebClientSyntheticBean(WebClientRecorder recorder, String clientName) {
        SyntheticBeanBuildItem.ExtendedBeanConfigurator configurator = SyntheticBeanBuildItem
            .configure(WebClient.class)
            .scope(ApplicationScoped.class)
            .supplier(recorder.webClientSupplier(clientName))
            .setRuntimeInit();

        return applyCommonBeanConfig(clientName, configurator);
    }

    private SyntheticBeanBuildItem applyCommonBeanConfig(String clientName,
                                                         SyntheticBeanBuildItem.ExtendedBeanConfigurator configurator) {
        configurator.unremovable();
        if (RootWebClientConfiguration.isDefault(clientName)) {
            configurator.addQualifier(Default.class);
        } else {
            configurator.addQualifier().annotation(WEB_CLIENT_ANNOTATION).addValue("value", clientName).done();
        }
        return configurator.done();
    }
}
