package vif.online.chungkhoan.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import vif.online.chungkhoan.entities.Asset;
import vif.online.chungkhoan.services.AssetService;


@Controller
@RequestMapping("asset")
@CrossOrigin(origins= {"http://localhost:8080", "http://18.136.211.82:8080"})
public class AssetController {
	
	@Autowired
	private AssetService assetService;
	
	@GetMapping("getAlls")
	public ResponseEntity<List<Asset>> getAllAsset() {
		List<Asset> list = assetService.getAlls();
		return new ResponseEntity<List<Asset>>(list, HttpStatus.OK);
	}
}
