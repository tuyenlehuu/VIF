package vif.online.chungkhoan.services.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vif.online.chungkhoan.dao.CustomerDao;
import vif.online.chungkhoan.dao.InvestorHistoryDao;
import vif.online.chungkhoan.dao.TransactionHistoryDao;
import vif.online.chungkhoan.entities.Asset;
import vif.online.chungkhoan.entities.Customer;
import vif.online.chungkhoan.entities.GroupAsset;
import vif.online.chungkhoan.entities.InvestorHistory;
import vif.online.chungkhoan.entities.TransactionHistory;
import vif.online.chungkhoan.helper.IContaints;
import vif.online.chungkhoan.services.AssetService;
import vif.online.chungkhoan.services.InvestorTransService;

@Service(value = "investorTransService")
@Transactional
public class InvestorTransServiceImpl implements InvestorTransService {
	@Autowired
	private CustomerDao customerDao;

	@Autowired
	private InvestorHistoryDao investorHistoryDao;

	@Autowired
	private AssetService assetService;

	@Autowired
	private TransactionHistoryDao transHistoryDao;

	@Override
	public boolean buyCCQ(Integer customerId, BigDecimal money, BigDecimal priceCCQ) {
		// TODO Auto-generated method stub
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
				transHistory.setTypeOfTransaction("A"); // A: Thêm; B: Bớt; C: Cổ tức tiền; S: Cổ tức cổ phiếu
				transHistoryDao.addTransactionHistory(transHistory);
			} else {
				return false;
			}

			// 4. Add amount of CCQ in table Asset. If not exist, insert new recored
			Asset assetCCQ = assetService.getAssetByCode(IContaints.ASSET_CODE.VIF_CCQ);
			if (assetCCQ != null) {
				assetCCQ.setAmount(assetCCQ.getAmount().add(amoutnCCQ));
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
				newAsset.setOrginalPrice(new BigDecimal(0));

				GroupAsset groupAsset = new GroupAsset();
				groupAsset.setId(2);
				newAsset.setGroupAsset(groupAsset);

				assetService.addAsset(newAsset);
			}

			// 5. Update amount of CCQ and price of CCQ in table Customer
			boolean isUpdateCCQ = customerDao.updateCCQCustomer(customer, priceCCQ, amountCCQBefore.add(amoutnCCQ));
			if (isUpdateCCQ) {
				return true;
			}
			return false;
		}
		return false;
	}

	@Override
	public boolean sellCCQ(Integer customerId, BigDecimal amountCCQ, BigDecimal priceCCQ) {
		// TODO Auto-generated method stub
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
				transHistory.setTypeOfTransaction("B"); // A: Thêm; B: Bớt; C: Cổ tức tiền; S: Cổ tức cổ phiếu
				transHistoryDao.addTransactionHistory(transHistory);
			} else {
				return false;
			}

			// 4. Subtract amount of CCQ in table Asset. If not exist, can not buy CCQ
			Asset assetCCQ = assetService.getAssetByCode(IContaints.ASSET_CODE.VIF_CCQ);
			if (assetCCQ != null) {
				assetCCQ.setAmount(assetCCQ.getAmount().subtract(amountCCQ));
				assetService.updateAsset(assetCCQ);
			} else {
				return false;
			}

			// 5. Update amount of CCQ and price of CCQ in table Customer
			boolean isUpdateCCQ = customerDao.updateCCQCustomer(customer, customer.getOrginalCCQPrice(), amountCCQBefore.subtract(amountCCQ));
			if (isUpdateCCQ) {
				return true;
			}
			return false;
		}
		return false;
	}

}
