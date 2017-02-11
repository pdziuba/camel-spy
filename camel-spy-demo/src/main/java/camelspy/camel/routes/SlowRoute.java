package camelspy.camel.routes;

import lombok.extern.log4j.Log4j;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

/**
 * Created by przemekd on 11.02.17.
 */
@Component
@Log4j
public class SlowRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        rest().post("/slow").to("direct:slow");

        from("direct:slow")
                .process(exchange -> {
                    String body = exchange.getIn().getBody(String.class);
                    if (null != body && body.length() > 0){
                        log.info("Yay, we get some body");
                        exchange.getIn().setBody("We get some message. It was: " + body);
                    }else{
                        exchange.getIn().setBody("Empty message :(");
                    }
                    //let's pretend we are doing some serious calculations here
                    Thread.sleep(2000);
                });
    }
}
