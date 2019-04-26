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
	
	public interface INVEST {
		public int TYPE_CASH_DIVIDEND = 1;
		public String CASH_DIVIDEND_FEE = "DEVIDEN_CASH_FEE";
		public String BUY_FEE = "BUY_FEE";
		public String SELL_FEE = "SELL_FEE";
		public int PENDING = 1;
		public int APPROVED = 2;
		public int REJECTED = 3;
		public int TRANS_ACTIVE = 1;
		public int TRANS_DEACTIVE = 0;
	}

	public interface PAGER{
		public int MAX_PAGE_SIZE = 65000;
	}
	
	public interface FILE_UPLOAD {
		public int MAX_SIZE_FILE = 1024 * 1024 * 3;
		
		// dev env
		public String AVATAR_UPLOAD_DIRECTORY = "D:\\upload_file\\avatar\\";
		public String DOC_FRONT_UPLOAD_DIRECTORY = "D:\\upload_file\\doc_front\\";
		public String DOC_BACK_UPLOAD_DIRECTORY = "D:\\upload_file\\doc_back\\";
		
		// prod env
//		public String AVATAR_UPLOAD_DIRECTORY = "/opt/upload_file/avatar";
//		public String DOC_FRONT_UPLOAD_DIRECTORY = "/opt/upload_file/doc_front";
//		public String DOC_BACK_UPLOAD_DIRECTORY = "/opt/upload_file/doc_back";
	}
	
	public interface RESPONSE_CODE{
		public String SUCCESS = "success";
		public String ERROR = "error";
		public String INPUT = "input";
	}
}
