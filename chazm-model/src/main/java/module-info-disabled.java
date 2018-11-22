//module runtimemodels.chazm.model {
//    requires transitive runtimemodels.chazm.api;
//    requires aopalliance;
//    requires com.google.guice;
//    requires com.google.guice.extensions.assistedinject;
//    requires io.reactivex.rxjava2;
//    requires java.desktop;
//    requires java.validation;
//    requires java.xml;
//    requires javax.inject;
//    requires kotlin.stdlib;
//    requires org.slf4j;
//
//    exports runtimemodels.chazm.model.id to com.google.guice;
//
//    opens runtimemodels.chazm.model to com.google.guice;
//    opens runtimemodels.chazm.model.entity to com.google.guice;
//    opens runtimemodels.chazm.model.event to com.google.guice;
//    opens runtimemodels.chazm.model.function to com.google.guice;
//    opens runtimemodels.chazm.model.notification to com.google.guice;
//    opens runtimemodels.chazm.model.parsers to com.google.guice;
//    opens runtimemodels.chazm.model.relation to com.google.guice;
//}
