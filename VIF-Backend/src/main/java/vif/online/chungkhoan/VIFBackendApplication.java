package vif.online.chungkhoan;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class VIFBackendApplication {

	public static void main(String[] args) throws IOException {
		SpringApplication.run(VIFBackendApplication.class, args);
		/*
		 * Document doc =
		 * Jsoup.connect("https://www.stockbiz.vn/MarketMovers.aspx").maxBodySize(0).
		 * timeout(20000).get(); Elements elements = doc.select("table");
		 * System.out.println(elements);
		 */
	}
}
