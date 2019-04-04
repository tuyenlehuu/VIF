package vif.online.chungkhoan.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import vif.online.chungkhoan.entities.DashBoard;
import vif.online.chungkhoan.helper.KeyNameValueDTO;
import vif.online.chungkhoan.helper.NAVDTO;
import vif.online.chungkhoan.services.DashboardService;

@Controller
@RequestMapping("dashboard")
@CrossOrigin(origins= {"*"})
public class DashboardController {
	@Autowired
	private DashboardService dashboardService;
	
	@GetMapping("/getData")
	public ResponseEntity<DashBoard> getData() {
		DashBoard dashboad = dashboardService.getData();
		return new ResponseEntity<DashBoard>(dashboad, HttpStatus.OK);
	}
	
	@GetMapping("/get-asset-report")
	public ResponseEntity<List<KeyNameValueDTO>> getAssetReport() {
		List<KeyNameValueDTO> assetList = dashboardService.getDataTotalAsset();
		return new ResponseEntity<List<KeyNameValueDTO>>(assetList, HttpStatus.OK);
	}
	
	@GetMapping("/get-nav-report")
	public ResponseEntity<List<NAVDTO>> getNAVReport(@RequestParam(value = "customerId", required = false) Integer customerId, 
			@RequestParam(value = "fromDate", required = false) String fromDate,
			@RequestParam(value = "toDate", required = false) String toDate) {
		List<NAVDTO> NAVList = dashboardService.getNAVReport(customerId, fromDate, toDate);
		return new ResponseEntity<List<NAVDTO>>(NAVList, HttpStatus.OK);
	}
	
	@GetMapping("/get-nav-chart-report")
	public ResponseEntity<List<KeyNameValueDTO>> getNavChartData(@RequestParam(value = "isByMonth", required = true) boolean isByMonth) {
		List<KeyNameValueDTO> navList = dashboardService.getNavChartData(isByMonth);
		return new ResponseEntity<List<KeyNameValueDTO>>(navList, HttpStatus.OK);
	}
	
	
}
