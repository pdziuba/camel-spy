package camelspy.camel.routes;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

/**
 * Created by przemekd on 29.01.17.
 */
@Component
public class HelloRoute extends RouteBuilder{

    @Override
    public void configure() throws Exception {
        rest().get("/hello").to("direct:hello");
        from("direct:hello").setBody(constant("Hello world!"));
    }
}
