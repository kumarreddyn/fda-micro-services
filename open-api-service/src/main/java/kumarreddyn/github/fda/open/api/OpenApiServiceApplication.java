package kumarreddyn.github.fda.open.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class OpenApiServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(OpenApiServiceApplication.class, args);
	}

}
