package home.vertx;

import io.advantageous.qbit.server.ServiceEndpointServer;
import io.vertx.core.http.HttpServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import javax.annotation.PostConstruct;

@Configuration
@Import({DatabaseConfig.class, VertxConfig.class})
@ComponentScan("home.vertx.contact")
@PropertySource("classpath:/application.properties")
public class Starter {

    @Autowired
    private ServiceEndpointServer serviceEndpointServer;

    @Autowired
    private HttpServer server;

    @Bean
    public static PropertySourcesPlaceholderConfigurer placeholderConfigurer(){
        return new PropertySourcesPlaceholderConfigurer();
    }

    public static void main(String[] args) throws Exception {
       SpringApplication.run(Starter.class);
    }

    @PostConstruct
    public void gameOn(){
        serviceEndpointServer.start();
        server.listen(8080);
    }
}
