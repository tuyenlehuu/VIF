//package vif.online.chungkhoan.services.impl;
//
//import java.util.Date;
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import vif.online.chungkhoan.dao.BillingInfoDao;
//import vif.online.chungkhoan.entities.BillingInfo;
//import vif.online.chungkhoan.services.BillingInfoService;
//
//@Service
//public class BillingInfoServiceImpl implements BillingInfoService {
//
//	@Autowired
//	private BillingInfoDao billingInfoDao;
//
//	@Override
//	public List<BillingInfo> getAlls() {
//		// TODO Auto-generated method stub
//		return billingInfoDao.getAlls();
//	}
//
//	@Override
//	public BillingInfo getById(int id) {
//		// TODO Auto-generated method stub
//		return billingInfoDao.getById(id);
//	}
//
//	@Override
//	public void deleteById(Integer id) {
//		// TODO Auto-generated method stub
//		BillingInfo bi = getById(id);
//		bi.setUpdateDate(new Date());
//		billingInfoDao.deleteById(id);
//	}
//
//	@Override
//	public void updateBillingInfo(BillingInfo bInfo) {
//		// TODO Auto-generated method stub
//
//		billingInfoDao.updateBillingInfo(bInfo);
//	}
//
//	@Override
//	public boolean addBillingInfo(BillingInfo bInfo) {
//		// TODO Auto-generated method stub
//		return billingInfoDao.addBillingInfo(bInfo);
//	}
//
//	@Override
//	public List<BillingInfo> SearchByCondition(int page, int pageSize, Boolean asc, String accountName, String bankName,
//			String bankBranch, String bankAccount, Integer activeFlg) {
//		// TODO Auto-generated method stub
//		return billingInfoDao.searchByCondition(page, pageSize, asc, accountName, bankName, bankBranch, bankAccount, activeFlg);
//	}
//
//	@Override
//	public int getRowCount(String accountName, String bankName, String bankBranch, String bankAccount,
//			Integer activeFlg) {
//		// TODO Auto-generated method stub
//		return billingInfoDao.getRowCount(accountName, bankName, bankBranch, bankAccount, activeFlg);
//	}
//
//}
