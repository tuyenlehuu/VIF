package vif.online.chungkhoan.helper;

public interface IContaints {
	
	public interface ASSET_CODE {
		public String ITRT_INVEST = "IT_RT"; // Tien dau tu quy uy thac
		public String CASH_IN_BANK = "CS";  // Tien gui ngan hang
		public String CASH = "CASH"; // Tien mat
		public String VIF_CCQ = "VIF_CCQ"; // CCQ phat hanh cua VIF
		public String SHARES = "SHARES"; // Co phieu
		public String DEBT_LONG = "DEBT_LONG"; // No dai han
		public String DEBT_SHORT = "DEBT_SHORT"; // No ngan han
		public int ACTIVE = 1; // ACTIVE FLAG
		public int DEACTIVE_FLAG = 0; // DEACTIVE FLAG
		public String GROUP_ASSET_SHARE_CODE = "SECURITIES";
	
	}
	
	public interface PAGER{
		public int MAX_PAGE_SIZE = 65000;
	}
}
