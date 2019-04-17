package vif.online.chungkhoan.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import vif.online.chungkhoan.entities.Asset;
import vif.online.chungkhoan.entities.GroupAsset;
import vif.online.chungkhoan.helper.ApiResponse;
import vif.online.chungkhoan.helper.BuySellAssetObj;
import vif.online.chungkhoan.services.AssetService;
import vif.online.chungkhoan.services.GroupAssetService;

@Controller
@RequestMapping("asset")
@CrossOrigin(origins = { "*" })
public class AssetServiceController {
	@Autowired
	private AssetService assetService;
	
	@Autowired
	private GroupAssetService groupAssetService;

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
	
	@GetMapping("getAssetsByCondition")
	public ResponseEntity<ApiResponse> SearchAssetsByCondition(@RequestParam(value = "page", required = true) int page,
			@RequestParam(value = "pageSize", required = true) int pageSize,
			@RequestParam(value = "columnSortName", required = false) String columnSortName,
			@RequestParam(value = "asc", required = false) Boolean asc,
			@RequestParam(value = "assetCode", required = false) String assetCode,
			@RequestParam(value = "groupAssetId", required = false) Integer groupAssetId,
			@RequestParam(value = "assetName", required = false) String assetName) {
		ApiResponse object = new ApiResponse();
		List<Asset> list = assetService.SearchAssetsByCondition(page, pageSize, columnSortName, asc, assetCode, groupAssetId, assetName);
		int rowCount = assetService.getRowCount(assetCode, groupAssetId, assetName);
		object.setCode(200);
		object.setErrors(null);
		object.setStatus(true);
		object.setData(list);
		object.setPage(page);
		object.setPageSize(pageSize);
		object.setTotalRow(rowCount);
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
	public ResponseEntity<ApiResponse> addAsset(@RequestBody Asset asset) {
		ApiResponse object = new ApiResponse();
		GroupAsset group = groupAssetService.getByGroupById(asset.getGroupAsset()!=null?asset.getGroupAsset().getId():0);
		if(group == null) {
			object.setCode(500);
			object.setErrors("Group asset can be not null!");
			object.setStatus(true);
			return new ResponseEntity<ApiResponse>(object, HttpStatus.OK);
		}
		
		asset.setGroupAsset(group);
		
		boolean flag = assetService.addAsset(asset);
		if (flag == false) {
			object.setCode(409);
			object.setErrors("User is exists!");
			object.setStatus(false);
			return new ResponseEntity<ApiResponse>(object, HttpStatus.OK);
		}
		
		object.setCode(200);
		object.setErrors(null);
		object.setStatus(true);
		return new ResponseEntity<ApiResponse>(object, HttpStatus.OK);
	}

	@RequestMapping(value = "/buySercurities", method = RequestMethod.POST, headers = "Accept=application/json")
	public @ResponseBody ResponseEntity<ApiResponse> buySercurities(@RequestBody BuySellAssetObj buyObject) {
		ApiResponse result = new ApiResponse();
		
		if(buyObject==null || buyObject.getAssetId() == null || buyObject.getAmount() ==null || buyObject.getPrice() == null) {
			result.setCode(500);
			result.setStatus(false);
			result.setErrors("missing parameters!");
			return new ResponseEntity<ApiResponse>(result, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		result = assetService.buySercurities(buyObject.getAssetId(),buyObject.getAmount() ,buyObject.getPrice());
		return new ResponseEntity<ApiResponse>(result, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/sellSercurities", method = RequestMethod.POST, headers = "Accept=application/json")
	public @ResponseBody ResponseEntity<ApiResponse> sellSercurities(@RequestBody BuySellAssetObj sellObject) {
		ApiResponse result = new ApiResponse();
		
		if(sellObject==null || sellObject.getAssetId() == null || sellObject.getAmount() ==null || sellObject.getPrice() == null) {
			result.setCode(500);
			result.setStatus(false);
			result.setErrors("missing parameters!");
			return new ResponseEntity<ApiResponse>(result, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		result = assetService.sellSercurities(sellObject.getAssetId(),sellObject.getAmount() ,sellObject.getPrice());
		return new ResponseEntity<ApiResponse>(result, HttpStatus.OK);
	}

	
	@GetMapping("/getAllShares")
	public ResponseEntity<ApiResponse> getAllShares() {
		ApiResponse object = new ApiResponse();
		List<Asset> list = assetService.getAllShares();
		object.setCode(200);
		object.setErrors(null);
		object.setStatus(true);
		object.setData(list);
		return new ResponseEntity<ApiResponse>(object, HttpStatus.OK);
	}
	
	@GetMapping("/getAllSharesForBuy")
	public ResponseEntity<ApiResponse> getAllSharesForBuy() {
		ApiResponse object = new ApiResponse();
		List<Asset> list = assetService.getAllSharesForBuy();
		object.setCode(200);
		object.setErrors(null);
		object.setStatus(true);
		object.setData(list);
		return new ResponseEntity<ApiResponse>(object, HttpStatus.OK);
	}
	
	@GetMapping("getOtherAssetNotShares")
	public ResponseEntity<ApiResponse> getOtherAssetNotShares() {
		ApiResponse object = new ApiResponse();
		List<Asset> assets = assetService.getOtherAssetNotShares();
		object.setCode(200);
		object.setErrors(null);
		object.setStatus(true);
		object.setData(assets);
		return new ResponseEntity<ApiResponse>(object, HttpStatus.OK);
	}
	
	@PutMapping("update")
	public ResponseEntity<Asset> updateAsset(@RequestBody Asset asset) {
		assetService.updateAsset(asset);
		return new ResponseEntity<Asset>(asset, HttpStatus.OK);
	}


	@DeleteMapping("deleteAssetByCode/{assetCode}")
	public ResponseEntity<Void> deleteAssetByCode(@PathVariable("assetCode") String assetCode) {
		assetService.deleteAssetByCode(assetCode);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	

	@DeleteMapping("deleteAssetById/{id}")
	public ResponseEntity<Void> deleteAssetByCode(@PathVariable("id") Integer id) {
		assetService.deleteAssetById(id);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	@GetMapping("getAssetById/{id}")
	public ResponseEntity<ApiResponse> getAssetByCode(@PathVariable("id") Integer id) {
		ApiResponse object = new ApiResponse();
		Asset asset = assetService.getAssetById(id);
		object.setCode(200);
		object.setErrors(null);
		object.setStatus(true);
		object.setData(asset);
		return new ResponseEntity<ApiResponse>(object, HttpStatus.OK);
	}
	
	@GetMapping("group/getAlls")
	public ResponseEntity<ApiResponse> getAllsGroup() {
		ApiResponse object = new ApiResponse();
		List<GroupAsset> list = groupAssetService.getAlls();
		object.setCode(200);
		object.setErrors(null);
		object.setStatus(true);
		object.setData(list);
		return new ResponseEntity<ApiResponse>(object, HttpStatus.OK);
	}
}
