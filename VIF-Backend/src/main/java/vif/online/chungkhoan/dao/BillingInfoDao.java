package vif.online.chungkhoan.dao;

import java.util.List;

import vif.online.chungkhoan.entities.BillingInfo;



public interface BillingInfoDao {

	List<BillingInfo> getAlls();

	BillingInfo getById(int id);

	void deleteById(Integer id);

	void updateBillingInfo(BillingInfo bInfo);

	boolean addBillingInfo(BillingInfo bInfo);

	List<BillingInfo> SearchByCondition(int page, int pageSize, Boolean asc, String accountName, String bankName,
			String bankBranch, String bankAccount, Integer activeFlg);

	int getRowCount(String accountName, String bankName, String bankBranch, String bankAccount, Integer activeFlg);

}
