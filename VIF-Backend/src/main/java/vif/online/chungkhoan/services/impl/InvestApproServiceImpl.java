package vif.online.chungkhoan.services.impl;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import vif.online.chungkhoan.dao.CustomerDao;
import vif.online.chungkhoan.dao.InvestApproDao;
import vif.online.chungkhoan.dao.InvestorHistoryDao;
import vif.online.chungkhoan.dao.TransactionHistoryDao;
import vif.online.chungkhoan.entities.Asset;
import vif.online.chungkhoan.entities.Customer;
import vif.online.chungkhoan.entities.GroupAsset;
import vif.online.chungkhoan.entities.InvestRequest;
import vif.online.chungkhoan.entities.InvestorHistory;
import vif.online.chungkhoan.entities.TransactionHistory;
import vif.online.chungkhoan.helper.ApiResponse;
import vif.online.chungkhoan.helper.IContaints;
import vif.online.chungkhoan.services.AssetService;
import vif.online.chungkhoan.services.InvestApproService;

@Service(value = "investApproService")

public class InvestApproServiceImpl implements InvestApproService {

	@Autowired
	private InvestApproDao investApproDao;
	@Autowired
	private CustomerDao customerDao;

	@Autowired
	private InvestorHistoryDao investorHistoryDao;

	@Autowired
	private AssetService assetService;

	@Autowired
	private TransactionHistoryDao transHistoryDao;
	
	@Override
	public List<InvestRequest> getAllInvestRequest() {
		// TODO Auto-generated method stub
		return investApproDao.getAllInvestRequest();
	}

	@Override
	public List<InvestRequest> SearchInvestRequestByCondition(int page, int pageSize, Boolean asc,
			Integer typeOfRequest, String fromDate, String toDate) {
		// TODO Auto-generated method stub
		return investApproDao.SearchInvestRequestByCondition(page, pageSize, asc, typeOfRequest, fromDate, toDate);
	}

	@Override
	public int getRowCount(Integer typeOfRequest, String fromDate, String toDate) {
		// TODO Auto-generated method stub
		return investApproDao.getRowCount(typeOfRequest, fromDate, toDate);
	}

	@Override
	public ApiResponse buyCCQ(Integer customerId, BigDecimal money, BigDecimal priceCCQ) {
		// TODO Auto-generated method stub
		ApiResponse resultResponse = new ApiResponse();
		// 1. Calculate CCQ = money/price CCQ
		BigDecimal amoutnCCQ = money.divide(priceCCQ, 2, RoundingMode.HALF_UP);
		Customer customer = customerDao.getCustomerById(customerId);
		if (customer != null) {
			// 2. Insert into table Investor History
			InvestorHistory investorHistory = new InvestorHistory();
			String newCodeTrans = customer.getCode() + System.currentTimeMillis();
			investorHistory.setCode(newCodeTrans);
			investorHistory.setAmountCCQ(amoutnCCQ);
			BigDecimal amountCCQBefore = customer.getTotalCcq() != null ? customer.getTotalCcq() : new BigDecimal(0);
			investorHistory.setAmountCCQBefore(amountCCQBefore);
			investorHistory.setCreateDate(new Date());
			investorHistory.setCustomer(customer);
			investorHistory.setLastUpdate(new Date());
			investorHistory.setPriceOfCCQ(priceCCQ);
			investorHistory.setPriceOfCCQBefore(
					customer.getOrginalCCQPrice() != null ? customer.getOrginalCCQPrice() : new BigDecimal(0));
			investorHistory.setTypeOfTransaction("M");
			investorHistoryDao.addInvestorHistory(investorHistory);

			// 3. Add money into table Asset and insert into table Transaction_History
			Asset asset = assetService.getAssetByCode(IContaints.ASSET_CODE.CASH);
			if (asset != null) {
				BigDecimal oldMoney = asset.getCurrentPrice();
				asset.setCurrentPrice(oldMoney.add(money));
				assetService.updateAsset(asset);
			} else {
				Asset newAsset = new Asset();
				newAsset.setAssetCode(IContaints.ASSET_CODE.CASH);
				newAsset.setActiveFlg(1);
				newAsset.setAssetName("Tien mat");
				newAsset.setAmount(new BigDecimal(0));
				newAsset.setBranchCode(null);
				newAsset.setCurrentPrice(money);
				newAsset.setDescription("Chung chi quy VIF");
				newAsset.setOrginalPrice(new BigDecimal(0));

				GroupAsset groupAsset = new GroupAsset();
				groupAsset.setId(1); // Tien mat
				newAsset.setGroupAsset(groupAsset);
				assetService.addAsset(newAsset);
			}

			// Insert into table Transaction_History
			TransactionHistory transHistory = new TransactionHistory();
			transHistory.setActiveFlg(1);
			transHistory.setAmount(money);
			transHistory.setAsset(asset);
			transHistory.setFeeType(null);
			transHistory.setCreateDate(new Date());
			transHistory.setDescription(customer.getFullName() + " invest money to VIF");
			transHistory.setLastUpdate(new Date());
			transHistory.setPrice(new BigDecimal(0));
			transHistory.setStatus(2); // 1 – Pending; 2 – Approved; 3 – Rejected
			transHistory.setTypeOfTransaction("M"); // M: Thêm B: Bớt C: cổ tức tiền S: Cổ tức cổ phiếu
			transHistoryDao.addTransactionHistory(transHistory);

			// 4. Add amount of CCQ in table Asset. If not exist, insert new recored
			Asset assetCCQ = assetService.getAssetByCode(IContaints.ASSET_CODE.VIF_CCQ);
			if (assetCCQ != null) {
				assetCCQ.setAmount(assetCCQ.getAmount().add(amoutnCCQ));
				assetCCQ.setOrginalPrice(getOrignalPriceOfCCQVif(assetCCQ.getAmount(), money, true));
				assetCCQ.setCurrentPrice(assetCCQ.getOrginalPrice());
				assetService.updateAsset(assetCCQ);
			} else {
				Asset newAsset = new Asset();
				newAsset.setAssetCode(IContaints.ASSET_CODE.VIF_CCQ);
				newAsset.setActiveFlg(1);
				newAsset.setAssetName("Chung chi quy");
				newAsset.setAmount(amoutnCCQ);
				newAsset.setBranchCode(null);
				newAsset.setCurrentPrice(new BigDecimal(0));
				newAsset.setDescription("Chung chi quy VIF");
				newAsset.setOrginalPrice(priceCCQ);

				GroupAsset groupAsset = new GroupAsset();
				groupAsset.setId(5); // CCQ VIF phat hanh
				newAsset.setGroupAsset(groupAsset);

				assetService.addAsset(newAsset);
			}

			// 5. Update amount of CCQ and price of CCQ in table Customer
			customer.setLastCCQPrice(priceCCQ);
			BigDecimal newCCQPrice = getOrignalPriceOfCustomerBuy(customer, money, amoutnCCQ);
			if (newCCQPrice != null) {
				customer.setOrginalCCQPrice(newCCQPrice);
				customer.setTotalCcq(amountCCQBefore.add(amoutnCCQ));
			}
			boolean isUpdateCCQ = customerDao.updateCCQCustomer(customer);
			if (!isUpdateCCQ) {
				resultResponse.setCode(500);
				resultResponse.setStatus(false);
				resultResponse.setErrors("Update amount of CCQ and price of CCQ in table Customer failed!");
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				return resultResponse;
			}
		} else {
			resultResponse.setCode(500);
			resultResponse.setStatus(false);
			resultResponse.setErrors("Not exist customer!");
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return resultResponse;
		}
		resultResponse.setCode(200);
		resultResponse.setStatus(true);
		resultResponse.setErrors(null);
		resultResponse.setData("Buy successfully!");
		return resultResponse;
	}

	@Override
	public ApiResponse sellCCQ(Integer customerId, BigDecimal amountCCQ, BigDecimal priceCCQ) {
		// TODO Auto-generated method stub
		ApiResponse resultResponse = new ApiResponse();
		// 1. Calculate money = amountCCQ*price CCQ
		BigDecimal money = amountCCQ.multiply(priceCCQ);
		Customer customer = customerDao.getCustomerById(customerId);
		if (customer != null) {
			// 2. Insert into table Investor History
			InvestorHistory investorHistory = new InvestorHistory();
			String newCodeTrans = customer.getCode() + System.currentTimeMillis();
			investorHistory.setCode(newCodeTrans);
			investorHistory.setAmountCCQ(amountCCQ);
			BigDecimal amountCCQBefore = customer.getTotalCcq() != null ? customer.getTotalCcq() : new BigDecimal(0);
			investorHistory.setAmountCCQBefore(amountCCQBefore);
			investorHistory.setCreateDate(new Date());
			investorHistory.setCustomer(customer);
			investorHistory.setLastUpdate(new Date());
			investorHistory.setPriceOfCCQ(priceCCQ);
			investorHistory.setPriceOfCCQBefore(
					customer.getOrginalCCQPrice() != null ? customer.getOrginalCCQPrice() : new BigDecimal(0));
			investorHistory.setTypeOfTransaction("B");
			investorHistoryDao.addInvestorHistory(investorHistory);

			// 3. Subtract money into table Asset and insert into table Transaction_History
			Asset asset = assetService.getAssetByCode(IContaints.ASSET_CODE.CASH);
			if (asset != null) {
				BigDecimal oldMoney = asset.getCurrentPrice();
				asset.setCurrentPrice(oldMoney.subtract(money));
				assetService.updateAsset(asset);

				// Insert into table Transaction_History
				TransactionHistory transHistory = new TransactionHistory();
				transHistory.setActiveFlg(1);
				transHistory.setAmount(money);
				transHistory.setAsset(asset);
				transHistory.setFeeType(null);
				transHistory.setCreateDate(new Date());
				transHistory.setDescription(customer.getFullName() + " sell CCQ");
				transHistory.setLastUpdate(new Date());
				transHistory.setPrice(new BigDecimal(0));
				transHistory.setStatus(2); // 1 – Pending; 2 – Approved; 3 – Rejected
				transHistory.setTypeOfTransaction("B"); // M: Thêm B: Bớt C: cổ tức tiền S: Cổ tức cổ phiếu
				transHistoryDao.addTransactionHistory(transHistory);
			} else {
				resultResponse.setCode(500);
				resultResponse.setStatus(false);
				resultResponse.setErrors("Not exist CASH asset!");
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				return resultResponse;
			}

			// 4. Subtract amount of CCQ in table Asset. If not exist, can not sell CCQ
			Asset assetCCQ = assetService.getAssetByCode(IContaints.ASSET_CODE.VIF_CCQ);
			if (assetCCQ != null) {
				assetCCQ.setAmount(assetCCQ.getAmount().subtract(amountCCQ));
				assetCCQ.setOrginalPrice(getOrignalPriceOfCCQVif(assetCCQ.getAmount(), money, false));
				assetCCQ.setCurrentPrice(assetCCQ.getOrginalPrice());
				assetService.updateAsset(assetCCQ);
			} else {
				resultResponse.setCode(500);
				resultResponse.setStatus(false);
				resultResponse.setErrors("Not exist CCQ asset!");
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				return resultResponse;
			}

			// 5. Update amount of CCQ and price of CCQ in table Customer
			customer.setLastCCQPrice(customer.getOrginalCCQPrice());
			BigDecimal newCCQPrice = getOrignalPriceOfCustomerSell(customer, money, amountCCQ);
			if (newCCQPrice != null) {
				customer.setOrginalCCQPrice(newCCQPrice);
				customer.setTotalCcq(amountCCQBefore.subtract(amountCCQ));
			}

			boolean isUpdateCCQ = customerDao.updateCCQCustomer(customer);
			if (!isUpdateCCQ) {
				resultResponse.setCode(500);
				resultResponse.setStatus(false);
				resultResponse.setErrors("Update amount of CCQ and price of CCQ in table Customer failed!");
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				return resultResponse;
			}
		}
		resultResponse.setCode(200);
		resultResponse.setStatus(true);
		resultResponse.setErrors(null);
		resultResponse.setData("Sell successfully!");
		return resultResponse;
	}
	
	private BigDecimal getOrignalPriceOfCustomerBuy(Customer customer, BigDecimal moneyAdd, BigDecimal ccqAdd) {
		try {
			BigDecimal currentAmountCCQ = customer.getTotalCcq() != null ? customer.getTotalCcq() : new BigDecimal(0);
			BigDecimal currentMoney = currentAmountCCQ.multiply(
					customer.getOrginalCCQPrice() != null ? customer.getOrginalCCQPrice() : new BigDecimal(0));
			BigDecimal result = (currentMoney.add(moneyAdd)).divide(currentAmountCCQ.add(ccqAdd), 2,
					RoundingMode.HALF_UP);
			return result;
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	}

	// tinh toan ra gia von CCQ cua NDT khi ban ra
	private BigDecimal getOrignalPriceOfCustomerSell(Customer customer, BigDecimal moneySub, BigDecimal ccqSub) {
		try {
			BigDecimal currentAmountCCQ = customer.getTotalCcq() != null ? customer.getTotalCcq() : new BigDecimal(0);
			BigDecimal currentMoney = currentAmountCCQ.multiply(
					customer.getOrginalCCQPrice() != null ? customer.getOrginalCCQPrice() : new BigDecimal(0));
			BigDecimal result = (currentMoney.subtract(moneySub)).divide(currentAmountCCQ.subtract(ccqSub), 2,
					RoundingMode.HALF_UP);
			return result;
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	}

	// tinh toan gia von trung binh cua CCQ
	private BigDecimal getOrignalPriceOfCCQVif(BigDecimal totalCCQ, BigDecimal moneyChange, boolean isBuyCCQ) {
		try {
			BigDecimal result;
			BigDecimal currentTotalMoney = customerDao.getTotalMoneyOfCustomers();
			if (isBuyCCQ) {
				result = (currentTotalMoney.add(moneyChange)).divide(totalCCQ, 2, RoundingMode.HALF_UP);
			} else {
				result = (currentTotalMoney.subtract(moneyChange)).divide(totalCCQ, 2, RoundingMode.HALF_UP);
			}

			return result;
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	}

	@Override
	public void reject(Integer id) {
		// TODO Auto-generated method stub
		investApproDao.reject(id);
	}

	@Override
	public void accept(Integer id) {
		// TODO Auto-generated method stub
		investApproDao.accept(id);
	}
	
}
