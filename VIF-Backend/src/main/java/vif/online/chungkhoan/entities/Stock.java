package vif.online.chungkhoan.entities;

public class Stock {
	String StockCode;
	String HighestPrice;
	String LowestPrice;
	String OpenPrice;
	String LastPrice;
	String TotalVol;
	String EPS;
	String PE;
	
	public String getHighestPrice() {
		return HighestPrice;
	}
	public void setHighestPrice(String highestPrice) {
		HighestPrice = highestPrice;
	}
	public String getLowestPrice() {
		return LowestPrice;
	}
	public void setLowestPrice(String lowestPrice) {
		LowestPrice = lowestPrice;
	}
	public String getOpenPrice() {
		return OpenPrice;
	}
	public void setOpenPrice(String openPrice) {
		OpenPrice = openPrice;
	}
	public String getLastPrice() {
		return LastPrice;
	}
	public void setLastPrice(String lastPrice) {
		LastPrice = lastPrice;
	}
	public String getStockCode() {
		return StockCode;
	}
	public void setStockCode(String stockCode) {
		StockCode = stockCode;
	}
	public String getTotalVol() {
		return TotalVol;
	}
	public void setTotalVol(String totalVol) {
		TotalVol = totalVol;
	}
	public String getEPS() {
		return EPS;
	}
	public void setEPS(String ePS) {
		EPS = ePS;
	}
	public String getPE() {
		return PE;
	}
	public void setPE(String pE) {
		PE = pE;
	}
	
	
}
