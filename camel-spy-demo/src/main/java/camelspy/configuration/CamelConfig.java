package camelspy.configuration;

import camelspy.service.InterceptedEventsService;
import camelspy.tracehandlers.PersistingTraceHandler;
import org.apache.camel.CamelContext;
import org.apache.camel.component.properties.PropertiesComponent;
import org.apache.camel.processor.interceptor.Tracer;
import org.apache.camel.spi.RestConfiguration;
import org.apache.camel.spring.boot.CamelContextConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * Created by przemekd on 29.01.17.
 */
@Configuration
public class CamelConfig implements CamelContextConfiguration{

    @Autowired
    private InterceptedEventsService interceptedEventsService;

    @Override
    public void beforeApplicationStart(CamelContext camelContext) {
        RestConfiguration restConfiguration = new RestConfiguration();
        restConfiguration.setComponent("servlet");
        restConfiguration.setEnableCORS(true);
        restConfiguration.setApiContextPath("/camel");
        camelContext.setRestConfiguration(restConfiguration);

        camelContext.disableJMX();
        Tracer tracer = Tracer.createTracer(camelContext);
        tracer.addTraceHandler(new PersistingTraceHandler(interceptedEventsService));
        camelContext.addInterceptStrategy(tracer);
        camelContext.setStreamCaching(true);
        camelContext.addComponent("properties", new PropertiesComponent("classpath:application.properties"));

    }

    @Override
    public void afterApplicationStart(CamelContext camelContext) {

    }
}
