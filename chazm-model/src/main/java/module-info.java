module runtimemodels.chazm.model {
    requires transitive runtimemodels.chazm.api;
    requires aopalliance; // automatic module
    requires com.google.guice; // automatic module
    requires com.google.guice.extensions.assistedinject; // automatic module
    requires io.reactivex.rxjava2; // automatic module
    requires java.desktop;
    requires java.validation; // automatic module
    requires java.xml;
    requires javax.inject; // automatic module
    requires org.slf4j;

    exports runtimemodels.chazm.model.id to com.google.guice;

    opens runtimemodels.chazm.model to com.google.guice;
    opens runtimemodels.chazm.model.entity to com.google.guice;
    opens runtimemodels.chazm.model.event to com.google.guice;
    opens runtimemodels.chazm.model.function to com.google.guice;
    opens runtimemodels.chazm.model.notification to com.google.guice;
    opens runtimemodels.chazm.model.parsers to com.google.guice;
    opens runtimemodels.chazm.model.relation to com.google.guice;
}
