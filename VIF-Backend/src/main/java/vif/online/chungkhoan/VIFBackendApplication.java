package vif.online.chungkhoan;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class VIFBackendApplication extends SpringBootServletInitializer{
	

	public static void main(String[] args) throws IOException {
		SpringApplication.run(VIFBackendApplication.class, args);
	}
	
	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(VIFBackendApplication.class);
    }
	
}
