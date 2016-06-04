package home.vertx;

import home.vertx.contact.ContactEndpoint;
import io.advantageous.qbit.server.EndpointServerBuilder;
import io.advantageous.qbit.server.ServiceEndpointServer;
import io.advantageous.qbit.vertx.http.VertxHttpServerBuilder;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.jdbc.JDBCClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import javax.sql.DataSource;

@Configuration
public class VertxConfig {

    @Bean(name = "vertx")
    public Vertx vertx(){
        VertxOptions options = new VertxOptions();
        Vertx vertx = Vertx.vertx(options);
        return vertx;
    }

    @Bean
    @DependsOn(value = "vertx")
    public JDBCClient jdbcClient(Vertx vertx, DataSource dataSource){
        JDBCClient client = JDBCClient.create(vertx, dataSource);
        return client;
    }

    @Bean
    public HttpServer server(Vertx vertx){
        HttpServer server = vertx.createHttpServer();
        return server;
    }

    @Bean
    public io.advantageous.qbit.http.server.HttpServer qbitServer(HttpServer server, Vertx vertx){
        io.advantageous.qbit.http.server.HttpServer qbitServer = VertxHttpServerBuilder.vertxHttpServerBuilder()
                .setHttpServer(server)
                .setVertx(vertx)
                .build();
        return qbitServer;
    }

    @Bean
    public ServiceEndpointServer serviceEndpointServer(io.advantageous.qbit.http.server.HttpServer qbitServer,
                                                       ContactEndpoint endpoint){
        return EndpointServerBuilder.endpointServerBuilder()
                       .setHttpServer(qbitServer)
                       .setUri("/api/")
                       .addServices(endpoint)
                       .build();
    }
}

