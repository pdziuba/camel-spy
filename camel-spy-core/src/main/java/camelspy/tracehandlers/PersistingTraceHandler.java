package camelspy.tracehandlers;

import camelspy.domain.InterceptedEvent;
import camelspy.service.InterceptedEventsService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.log4j.Log4j;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.RouteNode;
import org.apache.camel.model.ProcessorDefinition;
import org.apache.camel.model.ProcessorDefinitionHelper;
import org.apache.camel.model.RouteDefinition;
import org.apache.camel.processor.interceptor.TraceEventHandler;
import org.apache.camel.processor.interceptor.TraceInterceptor;
import org.apache.camel.spi.TracedRouteNodes;

import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by przemekd on 29.01.17.
 */
@Log4j
@AllArgsConstructor
public class PersistingTraceHandler implements TraceEventHandler {

    @Getter
    private InterceptedEventsService service;

    private String getNodeMessage(RouteNode entry, Exchange exchange) {
        String message = entry.getLabel(exchange);
        int nodeLength = 45;
        return nodeLength > 0?String.format("%1$-" + nodeLength + "." + nodeLength + "s", new Object[]{message}):message;
    }

    @Override
    public void traceExchange(ProcessorDefinition<?> processorDefinition, Processor processor, TraceInterceptor traceInterceptor, Exchange exchange) throws Exception {

        String from = "";
        String to = "";
        String route = "";

        if(exchange.getUnitOfWork() != null) {
            TracedRouteNodes traced = exchange.getUnitOfWork().getTracedRouteNodes();
            RouteNode traceFrom = traced.getSecondLastNode();
            if(traceFrom != null) {
                from = this.getNodeMessage(traceFrom, exchange);
            } else if(exchange.getFromEndpoint() != null) {
                from = "from(" + exchange.getFromEndpoint().getEndpointUri() + ")";
            }

            RouteNode traceTo = traced.getLastNode();
            if(traceTo != null) {
                to = this.getNodeMessage(traceTo, exchange);
                if(traceTo.isAbstract() && traceTo.getProcessorDefinition() == null) {
                    traceTo = traced.getSecondLastNode();
                }

                if(traceTo != null) {
                    RouteDefinition routeDefinition = ProcessorDefinitionHelper.getRoute(processorDefinition);
                    route = routeDefinition!=null?routeDefinition.getId():"";
                }
            }
        }

        InterceptedEvent.InterceptedEventBuilder builder = InterceptedEvent.builder();
        builder.exchangeId(exchange.getExchangeId());
        builder.routeId(route);
        InputStream body = exchange.getIn().getBody(InputStream.class);
        if (body != null) {
            builder.body(org.apache.commons.io.IOUtils.toString(body));
            body.reset();
        }
        Date createdTimestamp = exchange.getProperty(Exchange.CREATED_TIMESTAMP, Date.class);
        builder.date(createdTimestamp);
        builder.from(from.trim()).to(to.trim());
        builder.properties(extractProperties(exchange));
        builder.headers(extractHeaders(exchange));
        service.saveEvent(builder.build());

        log.info("Persisted intercepted event");
    }

    private Map<String, String> extractProperties(Exchange exchange){
        Map<String, String> result = new HashMap<>();

        for (Map.Entry<String, Object> entry : exchange.getProperties().entrySet()) {
            result.put(entry.getKey(), entry.getValue().toString());
        }
        return result;
    }

    private Map<String, String> extractHeaders(Exchange exchange){
        Map<String, String> result = new HashMap<>();

        for (Map.Entry<String, Object> entry : exchange.getIn().getHeaders().entrySet()) {
            String value = entry.getValue() != null ? entry.getValue().toString() : "null";
            result.put(entry.getKey(), value);
        }
        return result;
    }

    @Override
    public Object traceExchangeIn(ProcessorDefinition<?> processorDefinition, Processor processor, TraceInterceptor traceInterceptor, Exchange exchange) throws Exception {
        return null;
    }

    @Override
    public void traceExchangeOut(ProcessorDefinition<?> processorDefinition, Processor processor, TraceInterceptor traceInterceptor, Exchange exchange, Object o) throws Exception {

    }
}
