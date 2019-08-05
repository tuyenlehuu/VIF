package vif.online.chungkhoan.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import vif.online.chungkhoan.entities.TransactionHistory;
import vif.online.chungkhoan.helper.ApiResponse;
import vif.online.chungkhoan.helper.IContaints;
import vif.online.chungkhoan.helper.WriteDataToCSV;
import vif.online.chungkhoan.services.TransactionService;

@Controller
@RequestMapping("transaction")
@CrossOrigin(origins= {"*"})
public class TransactionController {

	@Autowired
	TransactionService transactionService;
	@GetMapping("getAlls")
	public ResponseEntity<ApiResponse> getAllsTransaction() {
		ApiResponse object = new ApiResponse();
		List<TransactionHistory> list = transactionService.getAll();
		object.setCode(200);
		object.setErrors(null);
		object.setStatus(true);
		object.setData(list);
		return new ResponseEntity<ApiResponse>(object, HttpStatus.OK);
	}
	@GetMapping("getTransactionsByCondition")
	public ResponseEntity<ApiResponse> SearchInvestorHistoryByCondition(@RequestParam(value = "page", required = true) int page,
			@RequestParam(value = "pageSize", required = true) int pageSize,
			@RequestParam(value = "columnSortName", required = false) String columnSortName,
			@RequestParam(value = "asc", required = false) Boolean asc,
			@RequestParam(value = "fromDate", required = false) String fromDate,
			@RequestParam(value = "toDate", required = false) String toDate,
			@RequestParam(value = "typeOfTransaction", required = false) String typeOfTransaction,
			@RequestParam(value = "assetId", required = false) Integer assetId){
		ApiResponse object = new ApiResponse();
		List<TransactionHistory> list = transactionService.SearchTransactionByCondition(page, pageSize, columnSortName, asc, fromDate,toDate, typeOfTransaction, assetId);
		int rowCount = transactionService.getRowCount(fromDate,toDate, typeOfTransaction, assetId);
		object.setCode(200);
		object.setErrors(null);
		object.setStatus(true);
		object.setData(list);
		object.setPage(page);
		object.setPageSize(pageSize);
		object.setTotalRow(rowCount);
		return new ResponseEntity<ApiResponse>(object, HttpStatus.OK);
	}
	@GetMapping("exportCSV/transaction-history.csv")
	public ResponseEntity<Void> exportCSV(@RequestParam(value = "fromDate", required = false) String fromDate,
			@RequestParam(value = "toDate", required = false) String toDate,
			@RequestParam(value = "typeOfTransaction", required = false) String typeOfTransaction,
			@RequestParam(value = "assetId", required = false) Integer assetId,
			HttpServletResponse response) throws IOException{
		response.setContentType("text/csv;charset=ISO-8859-1");
	    response.setHeader("Content-Disposition", "attachment; filename=transaction-history.csv");
	    List<TransactionHistory> list = transactionService.SearchTransactionByCondition(1, IContaints.PAGER.MAX_PAGE_SIZE, null, null,fromDate,toDate , typeOfTransaction, assetId);
	    if(list.size()>0) {
	    	WriteDataToCSV.exportTransactionHistoryToCsv(response.getWriter(),list);
	    }
	    
	    return new ResponseEntity<Void>(HttpStatus.OK);
	}

}
