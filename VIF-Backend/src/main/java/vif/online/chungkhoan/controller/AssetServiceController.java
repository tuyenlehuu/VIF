package vif.online.chungkhoan.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.UriComponentsBuilder;

import vif.online.chungkhoan.entities.Asset;
import vif.online.chungkhoan.helper.ApiResponse;
import vif.online.chungkhoan.helper.BuySellAssetObj;
import vif.online.chungkhoan.helper.BuySellDTO;
import vif.online.chungkhoan.services.AssetService;

@Controller
@RequestMapping("asset")
@CrossOrigin(origins= {"*"})
public class AssetServiceController {
	@Autowired
	private AssetService assetService;
	
	@GetMapping("/getAlls")
	public ResponseEntity<ApiResponse> getAllsAsset() {
		ApiResponse object = new ApiResponse();
		List<Asset> list = assetService.getAlls();
		object.setCode(200);
		object.setErrors(null);
		object.setStatus(true);
		object.setData(list);
		return new ResponseEntity<ApiResponse>(object, HttpStatus.OK);
	}
	
	@GetMapping("getAssetByCode/{assetCode}")
	public ResponseEntity<ApiResponse> getAssetByCode(@PathVariable("assetCode") String assetCode) {
		ApiResponse object = new ApiResponse();
		Asset asset = assetService.getAssetByCode(assetCode);
		object.setCode(200);
		object.setErrors(null);
		object.setStatus(true);
		object.setData(asset);
		return new ResponseEntity<ApiResponse>(object, HttpStatus.OK);
	}
	
	@PostMapping("add")
	public ResponseEntity<Void> addAsset(@RequestBody Asset asset, UriComponentsBuilder builder) {
		boolean flag = assetService.addAsset(asset);
		if (flag == false) {
			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		}
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(builder.path("/getAssetByCode/{assetCode}").buildAndExpand(asset.getAssetCode()).toUri());
		return new ResponseEntity<Void>(headers, HttpStatus.OK);
	}
	

	@RequestMapping(value = "/buySercurities", method = RequestMethod.POST, headers = "Accept=application/json")
	public @ResponseBody ResponseEntity<ApiResponse> buySercurities(@RequestBody BuySellAssetObj buyObject) {
		ApiResponse result = new ApiResponse();
		result.setCode(200);
		result.setStatus(true);
		result.setData(buyObject);
		
		
		
//		if(buyObject==null || buyObject.getCustomerId() == null || buyObject.getMoney()==null || buyObject.getPriceCCQ() == null) {
//			result.setCode(500);
//			result.setStatus(false);
//			result.setErrors("missing parameters!");
//			return new ResponseEntity<ApiResponse>(result, HttpStatus.OK);
//		}
//		result = investorTransService.buyCCQ(buyObject.getCustomerId(), buyObject.getMoney(), buyObject.getPriceCCQ());
	
		
		 
		return new ResponseEntity<ApiResponse>(result, HttpStatus.OK);
	}
	
}
