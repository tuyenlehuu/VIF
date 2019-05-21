package vif.online.chungkhoan.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import vif.online.chungkhoan.entities.InvestorHistory;
import vif.online.chungkhoan.helper.ApiResponse;
import vif.online.chungkhoan.helper.BuySellDTO;
import vif.online.chungkhoan.helper.CommissionObj;
import vif.online.chungkhoan.helper.IContaints;
import vif.online.chungkhoan.helper.WriteDataToCSV;
import vif.online.chungkhoan.services.InvestorTransService;

@Controller
@RequestMapping("investor-transaction")
@CrossOrigin(origins = { "http://localhost:8080", "http://18.136.211.82:8080" })
public class InvestorTransController {
	@Autowired
	private InvestorTransService investorTransService;

	// @PostMapping("buyCCQ")
	@RequestMapping(value = "/buyCCQ", method = RequestMethod.POST, headers = "Accept=application/json")
	public @ResponseBody ResponseEntity<ApiResponse> buyCCQ(@RequestBody BuySellDTO buyObject) {
		ApiResponse result = new ApiResponse();
		if (buyObject == null || buyObject.getCustomerId() == null || buyObject.getMoney() == null
				|| buyObject.getPriceCCQ() == null) {
			result.setCode(500);
			result.setStatus(false);
			result.setErrors("missing parameters!");
			return new ResponseEntity<ApiResponse>(result, HttpStatus.OK);
		}

		result = investorTransService.buyCCQ(buyObject.getCustomerId(), buyObject.getMoney(), buyObject.getPriceCCQ());
		/*
		 * if(isBuySuccess) { result.setCode(200); result.setStatus(true);
		 * result.setData("Buy CCQ success!"); }else { result.setCode(500);
		 * result.setStatus(false); result.setErrors("Buy CCQ failed!"); }
		 */
		return new ResponseEntity<ApiResponse>(result, HttpStatus.OK);
	}

	// @PostMapping("sellCCQ")
	@RequestMapping(value = "/sellCCQ", method = RequestMethod.POST, headers = "Accept=application/json")
	public @ResponseBody ResponseEntity<ApiResponse> sellCCQ(@RequestBody BuySellDTO sellObject) {
		ApiResponse result = new ApiResponse();

		if (sellObject == null || sellObject.getCustomerId() == null || sellObject.getAmountCCQ() == null
				|| (sellObject.getAmountCCQ() != null && sellObject.getAmountCCQ().compareTo(new BigDecimal(0)) <= 0)
				|| sellObject.getPriceCCQ() == null) {
			result.setCode(500);
			result.setStatus(false);
			result.setErrors("wrong parameters!");
			return new ResponseEntity<ApiResponse>(result, HttpStatus.OK);
		}

		result = investorTransService.sellCCQ(sellObject.getCustomerId(), sellObject.getAmountCCQ(),
				sellObject.getPriceCCQ());
		return new ResponseEntity<ApiResponse>(result, HttpStatus.OK);
	}

	@GetMapping("getAllsHistory")
	public ResponseEntity<List<InvestorHistory>> getAllsInvestorHistory() {
		List<InvestorHistory> list = investorTransService.getAllInvestorHistory();
		return new ResponseEntity<List<InvestorHistory>>(list, HttpStatus.OK);
	}

	@GetMapping("getInvestorHistoryByCondition")
	public ResponseEntity<ApiResponse> SearchInvestorHistoryByCondition(
			@RequestParam(value = "page", required = true) int page,
			@RequestParam(value = "pageSize", required = true) int pageSize,
			@RequestParam(value = "columnSortName", required = false) String columnSortName,
			@RequestParam(value = "asc", required = false) Boolean asc,
			@RequestParam(value = "customerId", required = false) Integer customerId,
			@RequestParam(value = "fromDate", required = false) String fromDate,
			@RequestParam(value = "toDate", required = false) String toDate) {
		ApiResponse object = new ApiResponse();
		List<InvestorHistory> list = investorTransService.searchInvestorHistoryByCondition(page, pageSize,
				columnSortName, asc, customerId, fromDate, toDate);
		int rowCount = investorTransService.getRowCount(customerId, fromDate, toDate);
		object.setCode(200);
		object.setErrors(null);
		object.setStatus(true);
		object.setData(list);
		object.setPage(page);
		object.setPageSize(pageSize);
		object.setTotalRow(rowCount);
		return new ResponseEntity<ApiResponse>(object, HttpStatus.OK);
	}

	@GetMapping("exportCSV/invest-history.csv")
	public ResponseEntity<Void> exportCSV(@RequestParam(value = "customerId", required = false) Integer customerId,
			@RequestParam(value = "fromDate", required = false) String fromDate,
			@RequestParam(value = "toDate", required = false) String toDate, HttpServletResponse response)
			throws IOException {
		response.setContentType("text/csv;charset=ISO-8859-1");
		response.setHeader("Content-Disposition", "attachment; filename=invest-history.csv");
		List<InvestorHistory> list = investorTransService.searchInvestorHistoryByCondition(1,
				IContaints.PAGER.MAX_PAGE_SIZE, null, null, customerId, fromDate, toDate);
		if (list.size() > 0) {
			WriteDataToCSV.exportInvestHistoryToCsv(response.getWriter(), list);
		}

		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	@RequestMapping(value = "/cCommissionDivide", method = RequestMethod.POST, headers = "Accept=application/json")
	public @ResponseBody ResponseEntity<ApiResponse> cCommissionDivide(@RequestBody CommissionObj commissionObj) {
		ApiResponse result = new ApiResponse();
		if (commissionObj == null || commissionObj.getCustomerID() == null || commissionObj.getAmount() == null
				|| commissionObj.getcType() == null) {
			result.setCode(500);
			result.setStatus(false);
			result.setErrors("missing parameters!");
			return new ResponseEntity<ApiResponse>(result, HttpStatus.OK);
		}

		result = investorTransService.cCommissionDivide(commissionObj.getCustomerID(), commissionObj.getAmount(),
				commissionObj.getcType());
		return new ResponseEntity<ApiResponse>(result, HttpStatus.OK);
	}

	@RequestMapping(value = "/buyEnsureCCQ", method = RequestMethod.POST, headers = "Accept=application/json")
	public @ResponseBody ResponseEntity<ApiResponse> buyEnsureCCQ(@RequestBody BuySellDTO buyObject) {
		ApiResponse result = new ApiResponse();
		if (buyObject == null || buyObject.getCustomerId() == null || buyObject.getMoney() == null
				|| buyObject.getPriceCCQ() == null || buyObject.getEnsureCCQCode() == null) {
			result.setCode(500);
			result.setStatus(false);
			result.setErrors("missing parameters!");
			return new ResponseEntity<ApiResponse>(result, HttpStatus.OK);
		}

		result = investorTransService.buyEnsureCCQ(buyObject);
		return new ResponseEntity<ApiResponse>(result, HttpStatus.OK);
	}

	@RequestMapping(value = "/sellEnsureCCQ", method = RequestMethod.POST, headers = "Accept=application/json")
	public @ResponseBody ResponseEntity<ApiResponse> sellEnsureCCQ(@RequestBody BuySellDTO sellObject) {
		ApiResponse result = new ApiResponse();

		if (sellObject == null || sellObject.getCustomerId() == null || sellObject.getAmountCCQ() == null
				|| (sellObject.getAmountCCQ() != null && sellObject.getAmountCCQ().compareTo(new BigDecimal(0)) <= 0)
				|| sellObject.getPriceCCQ() == null || sellObject.getEnsureCCQCode() == null) {
			result.setCode(500);
			result.setStatus(false);
			result.setErrors("wrong parameters!");
			return new ResponseEntity<ApiResponse>(result, HttpStatus.OK);
		}

		result = investorTransService.sellEnsureCCQ(sellObject);
		return new ResponseEntity<ApiResponse>(result, HttpStatus.OK);
	}

	@GetMapping("getEnsureCCQByCusAsset/{customerId}/{assetCode}")
	public ResponseEntity<ApiResponse> getEnsureCCQByCusAsset(@PathVariable("customerId") Integer customerId,
			@PathVariable("assetCode") String assetCode) {
		ApiResponse result = new ApiResponse();

		if (customerId == null || assetCode == null) {
			result.setCode(500);
			result.setStatus(false);
			result.setErrors("wrong parameters!");
			return new ResponseEntity<ApiResponse>(result, HttpStatus.OK);
		}
		result = investorTransService.getEnsureCCQByCusAsset(customerId, assetCode);
		return new ResponseEntity<ApiResponse>(result, HttpStatus.OK);
	}
}
