package vif.online.chungkhoan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import vif.online.chungkhoan.entities.DashBoard;
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
}
