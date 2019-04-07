package vif.online.chungkhoan.helper;

import java.io.PrintWriter;
import java.util.List;

import com.opencsv.CSVWriter;
import vif.online.chungkhoan.entities.InvestorHistory;

public class WriteDataToCSV {
	public static void exportInvestHistoryToCsv(PrintWriter writer,List<InvestorHistory> investorHistoryLst) {
	    String[] CSV_HEADER = { "Id", "Mã GD", "Nhà đầu tư", "Số CCQ", "Giá CCQ", "Loại GD", "Ngày GD", "Số CCQ trước", "Giá CCQ trước"};
	    try (
	      CSVWriter csvWriter = new CSVWriter(writer,
	                    CSVWriter.DEFAULT_SEPARATOR,
	                    CSVWriter.NO_QUOTE_CHARACTER,
	                    CSVWriter.DEFAULT_ESCAPE_CHARACTER,
	                    CSVWriter.DEFAULT_LINE_END);
	    ){
	      csvWriter.writeNext(CSV_HEADER);
	 
	      for (InvestorHistory item : investorHistoryLst) {
	        String[] data = {
	            item.getId().toString(),
	            item.getCode(),
	            item.getCustomer().getFullName(),
	            item.getAmountCCQ().toString(),
	            item.getPriceOfCCQ().toString(),
	            item.getTypeOfTransaction(),
	            item.getCreateDate().toGMTString(),
	            item.getAmountCCQBefore().toString(),
	            item.getPriceOfCCQBefore().toString()
	        };
	        
	        csvWriter.writeNext(data);
	      }
	      
	      System.out.println("Write CSV using CSVWriter successfully!");
	    }catch (Exception e) {
	      System.out.println("Writing CSV error!");
	      e.printStackTrace();
	    }
	  }
}
