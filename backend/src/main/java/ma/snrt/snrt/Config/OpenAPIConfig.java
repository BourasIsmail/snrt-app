package ma.snrt.snrt.Config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class OpenAPIConfig {



  @Bean
  public OpenAPI myOpenAPI() {
    Server devServer = new Server();
    devServer.setUrl("http://92.222.25.97:8080/api/");
    devServer.setDescription("Server URL in Development environment");



    Server prodServer = new Server();
    prodServer.setUrl("http://92.222.25.97:8080/api/");
    prodServer.setDescription("Server URL in Production environment");





    Info info = new Info()
        .title("Enterprise Resource Management API")
        .version("1.0")
        .description("This API exposes endpoints to manage Enterprise Resource Management.");




    return new OpenAPI().info(info).servers(List.of(devServer, prodServer));
  }
}