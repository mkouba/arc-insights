package org.acme.extension;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.Dependent;
import jakarta.enterprise.inject.Instance;
import jakarta.enterprise.inject.build.compatible.spi.AnnotationBuilder;
import jakarta.enterprise.inject.build.compatible.spi.BeanInfo;
import jakarta.enterprise.inject.build.compatible.spi.BuildCompatibleExtension;
import jakarta.enterprise.inject.build.compatible.spi.ClassConfig;
import jakarta.enterprise.inject.build.compatible.spi.Discovery;
import jakarta.enterprise.inject.build.compatible.spi.Enhancement;
import jakarta.enterprise.inject.build.compatible.spi.Messages;
import jakarta.enterprise.inject.build.compatible.spi.Parameters;
import jakarta.enterprise.inject.build.compatible.spi.Registration;
import jakarta.enterprise.inject.build.compatible.spi.ScannedClasses;
import jakarta.enterprise.inject.build.compatible.spi.Synthesis;
import jakarta.enterprise.inject.build.compatible.spi.SyntheticBeanCreator;
import jakarta.enterprise.inject.build.compatible.spi.SyntheticComponents;
import jakarta.enterprise.inject.build.compatible.spi.Validation;

public class CdiExtension implements BuildCompatibleExtension {

    @Discovery
    public void discoveryPhase(ScannedClasses scannedClasses) {
        // register otherwise undiscovered bean
        scannedClasses.add(UndiscoveredBean.class.getName());
    }

    @Enhancement(types = UndiscoveredBean.class)
    public void enhancementPhase(ClassConfig classConfig) {
        // add @ApplicationScoped
        classConfig.addAnnotation(ApplicationScoped.class)
                // add @MyQualifier(name = "fromExtension")
                .addAnnotation(AnnotationBuilder.of(MyQualifier.class).member("name", "fromExtension").build());
    }

    @Registration(types = UndiscoveredBean.class)
    public void registrationPhase(BeanInfo beanInfo, Messages messages){
        // browse discovered beans, inspect metadata
        messages.info("UndiscoveredBean has scope @" + beanInfo.scope().name() + " and its qualifiers are " + beanInfo.qualifiers(), beanInfo);
    }

    @Synthesis
    public void synthesisPhase(SyntheticComponents syntheticComponents) {
        // allows to create synthetic beans and observers; below is a very primitive example of a String typed bean
        syntheticComponents.addBean(String.class)
                .type(String.class)
                .qualifier(AnnotationBuilder.of(MyQualifier.class).member("name", "synthetic").build())
                .scope(Dependent.class)
                .createWith(StringCreator.class);
    }

    @Validation
    public void validationPhase(Messages messages) {
        // can perform validation, throw exceptions
        // in this case, we just log that all works as expected
        messages.info("CdiExtension bootstrapped successfully!");
    }

    /**
     * A helper class hosting the logic needed to create a synthetic String bean
     */
    public static class StringCreator implements SyntheticBeanCreator<String> {

        @Override
        public String create(Instance<Object> lookup, Parameters params) {
            return "Synthetic Bean";
        }
    }
}
