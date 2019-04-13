package vif.online.chungkhoan.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import vif.online.chungkhoan.entities.Asset;
import vif.online.chungkhoan.entities.TransactionHistory;
import vif.online.chungkhoan.entities.User;
import vif.online.chungkhoan.helper.ApiResponse;
import vif.online.chungkhoan.repositories.TransactionRepository;
import vif.online.chungkhoan.services.TransactionService;

@Controller
@RequestMapping("transaction")
@CrossOrigin(origins = { "http://localhost:8080", "http://18.136.211.82:8080" })
public class TransactionController {

	@Autowired
	TransactionService transactionService;
	@Autowired
	TransactionRepository transactionRepository;

	@GetMapping("getTransactionsByCondition")
	public ResponseEntity<ApiResponse> SearchUserByCondition(@RequestParam(value = "page", required = true) int page,
			@RequestParam(value = "pageSize", required = true) int pageSize,
			@RequestParam(value = "columnSortName", required = false) String columnSortName,
			@RequestParam(value = "asc", required = false) Boolean asc,
			@RequestParam(value = "creatDate", required = false) Date creatDate,
			@RequestParam(value = "typeOfTransaction", required = false) String typeOfTransaction) {
		ApiResponse object = new ApiResponse();
		List<TransactionHistory> list = transactionService.SearchTransactionByCondition(page, pageSize, columnSortName,
				asc, creatDate , typeOfTransaction);
		int rowCount = transactionService.getRowCount( creatDate, typeOfTransaction);
		object.setCode(200);
		object.setErrors(null);
		object.setStatus(true);
		object.setData(list);
		object.setPage(page);
		object.setPageSize(pageSize);
		object.setTotalRow(rowCount);
		return new ResponseEntity<ApiResponse>(object, HttpStatus.OK);
	}
	
}
