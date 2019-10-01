package vif.online.chungkhoan;

import java.io.IOException;
import java.math.BigDecimal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class VIFBackendApplication extends SpringBootServletInitializer {

	public static void main(String[] args) throws IOException {
		SpringApplication.run(VIFBackendApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(VIFBackendApplication.class);
	}
	
	/*private static void scheduleUpdateStock() {
		ScheduledExecutorService executor =
			    Executors.newSingleThreadScheduledExecutor();
			Runnable periodicTask = new Runnable() {
			    public void run() {
			        // Invoke method(s) to do the work
			        doPeriodicWork();
			    }
			};

			executor.scheduleAtFixedRate(periodicTask, 0, 1, TimeUnit.MINUTES);
	}
	
	private static void doPeriodicWork() {
		StockTrackingController mController = new StockTrackingController();
		mController.updateStockTrackingFromMarket();
		System.out.println("Thread update stock price is successfully!");
	}
	
	@PostConstruct
	private void init() {
		scheduleUpdateStock();
	}*/

}
