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
		/*
		 * Document doc =
		 * Jsoup.connect("https://www.stockbiz.vn/MarketMovers.aspx").maxBodySize(0).
		 * timeout(20000).get(); Elements elements = doc.select("table");
		 * System.out.println(elements);
		 */
	}
	
	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(VIFBackendApplication.class);
    }
}
