package camelspy;

import camelspy.service.InterceptedEventsInMemoryService;
import camelspy.service.InterceptedEventsService;
import org.apache.camel.component.servlet.CamelHttpTransportServlet;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"camelspy"})
public class CamelSpyDemoApplication extends SpringBootServletInitializer{

    @Bean
    public ServletRegistrationBean servletRegistrationBean() {
        ServletRegistrationBean servlet = new ServletRegistrationBean(
                new CamelHttpTransportServlet(), "/camel/*");
        servlet.setName("CamelServlet");
        return servlet;
    }

    @Bean
    public InterceptedEventsService getInterceptedEventsService(){
        return new InterceptedEventsInMemoryService();
    }

	public static void main(String[] args) {
		SpringApplication.run(CamelSpyDemoApplication.class, args);
	}
}
