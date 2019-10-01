package vif.online.chungkhoan.services.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import vif.online.chungkhoan.dao.AppParamDao;
import vif.online.chungkhoan.dao.AssetDao;
import vif.online.chungkhoan.dao.GroupAssetDao;
import vif.online.chungkhoan.dao.ShareMasterDao;
import vif.online.chungkhoan.dao.TransactionHistoryDao;
import vif.online.chungkhoan.entities.AppParam;
import vif.online.chungkhoan.entities.Asset;
import vif.online.chungkhoan.entities.TransactionHistory;
import vif.online.chungkhoan.helper.ApiResponse;
import vif.online.chungkhoan.helper.IContaints;
import vif.online.chungkhoan.services.AssetService;

@Service(value = "assetService")
public class AssetServiceImpl implements AssetService {

	@Autowired
	private AssetDao assetDao;

	@Autowired
	private AssetService assetService;

	@Autowired
	private ShareMasterDao shareMasterDao;

	@Autowired
	private TransactionHistoryDao transHistoryDao;

	@Autowired
	private GroupAssetDao groupAssetDao;

	@Autowired
	private AppParamDao appParamDao;

	@Override
	public void updateAsset(Asset asset) {
		// TODO Auto-generated method stub
		assetDao.updateAsset(asset);
	}

	@Override
	public Asset getAssetByCode(String assetCode) {
		// TODO Auto-generated method stub
		return assetDao.getAssetByCode(assetCode);
	}

	@Override
	public boolean addAsset(Asset asset) {
		// TODO Auto-generated method stub
		if (assetDao.isExists(asset)) {
			return false;
		} else {
			return assetDao.addAsset(asset);
		}
	}

	@Override
	public List<Asset> getAlls() {
		// TODO Auto-generated method stub
		return assetDao.getAlls();
	}

	@Override
	public ApiResponse buySercurities(Integer assetId, BigDecimal amount, BigDecimal price) {
		try {
			// TODO Auto-generated method stub
			ApiResponse response = new ApiResponse();
			AppParam buyFeeConfig = appParamDao.getAppParamByPropKey(IContaints.INVEST.BUY_FEE);
			// fee rate
			BigDecimal buyFeeConfigRate;
			if (buyFeeConfig == null) {
				buyFeeConfigRate = new BigDecimal(0);
			}else {
				buyFeeConfigRate = new BigDecimal(buyFeeConfig.getPropValue()).divide(new BigDecimal(100));
			}

			BigDecimal money = amount.multiply(price).multiply(new BigDecimal(1).add(buyFeeConfigRate));

			// subtract money
			Asset cAsset = assetService.getAssetByCode(IContaints.ASSET_CODE.CASH);
			if (cAsset == null) {
				response.setCode(500);
				response.setStatus(false);
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				return response;
			}

			BigDecimal oldMoney = cAsset.getCurrentPrice();
			cAsset.setCurrentPrice(oldMoney.subtract(money));
			assetService.updateAsset(cAsset);

			// update amount of asset
			// get asset
			Asset stock = assetDao.getByAssetId(assetId);
			BigDecimal newAmount = stock.getAmount().add(amount);
			BigDecimal oldMoneyStock = stock.getAmount().multiply(stock.getOrginalPrice()).multiply(new BigDecimal(1000));
			BigDecimal newOriginalPrice = (money.add(oldMoneyStock)).divide(new BigDecimal(1000)).divide(newAmount, 2, RoundingMode.HALF_UP);
			stock.setAmount(newAmount);
			stock.setOrginalPrice(newOriginalPrice);
			stock.setActiveFlg(IContaints.ASSET_CODE.ACTIVE);
			assetService.updateAsset(stock);
			
			// add to asset transaction
			TransactionHistory transHistory = new TransactionHistory();
			transHistory.setActiveFlg(1);
			transHistory.setAmount(amount);
			transHistory.setAsset(stock);
			transHistory.setFeeType(IContaints.INVEST.BUY_FEE);
			transHistory.setCreateDate(new Date());
			transHistory.setDescription("VIF mua " + amount + " cổ phiếu" + stock.getAssetCode());
			transHistory.setLastUpdate(new Date());
			transHistory.setPrice(price);
			transHistory.setStatus(2); // 1 – Pending; 2 – Approved; 3 – Rejected
			transHistory.setTypeOfTransaction("M"); // M: Thêm B: Bớt C: cổ tức tiền S: Cổ tức cổ phiếu
			transHistoryDao.addTransactionHistory(transHistory);
			
			// response
			response.setCode(200);
			response.setStatus(true);
			response.setErrors(null);
			response.setData("Buy success");
			return response;
		}catch(Exception ex) {
			ex.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return null;
		}
	}

	@Override
	public ApiResponse sellSercurities(Integer assetId, BigDecimal amount, BigDecimal price) {
		try {
			// TODO Auto-generated method stub
			ApiResponse response = new ApiResponse();
			
			// add money
			Asset cAsset = assetService.getAssetByCode(IContaints.ASSET_CODE.CASH);
			if (cAsset == null) {
				response.setCode(500);
				response.setStatus(false);
			}

			BigDecimal oldMoney = cAsset.getCurrentPrice();
			// BigDecimal money = amount.multiply(price);
			AppParam sellFeeConfig = appParamDao.getAppParamByPropKey(IContaints.INVEST.SELL_FEE);
			BigDecimal sellFeeConfigRate;
			if(sellFeeConfig == null) {
				sellFeeConfigRate = new BigDecimal(0);
			}else {
				// fee rate
				sellFeeConfigRate = new BigDecimal(sellFeeConfig.getPropValue()).divide(new BigDecimal(100));
			}
			
			BigDecimal money = amount.multiply(price).multiply(new BigDecimal(1).subtract(sellFeeConfigRate));
			cAsset.setCurrentPrice(oldMoney.add(money));
			assetService.updateAsset(cAsset);
			
			// update amount of asset, if amount is new
			// get asset
			Asset sercurity = assetDao.getByAssetId(assetId);		
			BigDecimal newAmount = sercurity.getAmount().subtract(amount);
			if (amount.equals(sercurity.getAmount())) {
				sercurity.setActiveFlg(IContaints.ASSET_CODE.DEACTIVE_FLAG);
			}
			sercurity.setAmount(newAmount);
			assetService.updateAsset(sercurity);
			
			// add to asset transaction
			TransactionHistory transHistory = new TransactionHistory();
			transHistory.setActiveFlg(1);
			transHistory.setAmount(amount);
			transHistory.setAsset(sercurity);
			transHistory.setFeeType(IContaints.INVEST.SELL_FEE);
			transHistory.setCreateDate(new Date());
			transHistory.setDescription("VIF bán " + amount + " cổ phiếu " + sercurity.getAssetCode());
			transHistory.setLastUpdate(new Date());
			transHistory.setPrice(price);
			transHistory.setStatus(2); // 1 – Pending; 2 – Approved; 3 – Rejected
			transHistory.setTypeOfTransaction("B"); // M: Thêm B: Bớt C: cổ tức tiền S: Cổ tức cổ phiếu
			transHistoryDao.addTransactionHistory(transHistory);

			response.setCode(200);
			response.setStatus(true);
			response.setErrors(null);
			response.setData("Sell success");
			return response;
		}catch(Exception e) {
			// e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return null;
		}
	}

	@Override
	public ApiResponse dividendTrans(Integer assetId, BigDecimal amount, int dType, BigDecimal dRate) {
		// TODO Auto-generated method stub
		ApiResponse response = new ApiResponse();
		// get asset
		Asset share = assetDao.getByAssetId(assetId);
		if (dType == IContaints.INVEST.TYPE_CASH_DIVIDEND) {
			// Case 1: Cash Dividend
			Asset cAsset = assetService.getAssetByCode(IContaints.ASSET_CODE.CASH);
			if (cAsset == null) {
				response.setCode(500);
				response.setStatus(false);
			}
			// fee rate
			BigDecimal dividenFeeRate;
			AppParam dividendFeeConfig = appParamDao.getAppParamByPropKey(IContaints.INVEST.CASH_DIVIDEND_FEE);
			if(dividendFeeConfig == null) {
				// fee rate
				dividenFeeRate = new BigDecimal(0);
			}else {
				dividenFeeRate = new BigDecimal(dividendFeeConfig.getPropValue()).divide(new BigDecimal(100));
			}
			
			// money after fee rate = 100% - dividenFeeRate
			BigDecimal realMoneyRecieveRate = new BigDecimal(1).subtract(dividenFeeRate);
			// dividend rate
			BigDecimal dividenRate = dRate.divide(new BigDecimal(100));
			// calculate receive money
			BigDecimal dividendMoney = share.getAmount().multiply(new BigDecimal(10000)).multiply(dividenRate)
					.multiply(realMoneyRecieveRate);
			// add cash to Fund's asset
			BigDecimal currentMoney = cAsset.getCurrentPrice();
			cAsset.setCurrentPrice(currentMoney.add(dividendMoney));
			assetService.updateAsset(cAsset);

			// recalculate share original price
			BigDecimal newShareOriginalValue = share.getAmount()
					.multiply(share.getOrginalPrice().multiply(new BigDecimal(1000))).subtract(dividendMoney);
			BigDecimal newOriginalPrice = newShareOriginalValue.divide(share.getAmount()).divide(new BigDecimal(1000));
			share.setOrginalPrice(newOriginalPrice);
			assetService.updateAsset(share);

			// transaction history
			TransactionHistory transactionHistory = new TransactionHistory();
			transactionHistory.setPrice(dividendMoney.divide(share.getAmount()).divide(new BigDecimal(1000)));
			transactionHistory.setAmount(share.getAmount());
			transactionHistory.setDescription("Chi trả cổ tức tiền mặt. " + share.getAmount() + " "
					+ share.getAssetCode() + " - Tỷ lệ " + dRate + "%");
			transactionHistory.setActiveFlg(IContaints.INVEST.TRANS_ACTIVE);
			transactionHistory.setStatus(IContaints.INVEST.APPROVED);
			transactionHistory.setTypeOfTransaction("C");
			transactionHistory.setFeeType(IContaints.INVEST.CASH_DIVIDEND_FEE);
			transactionHistory.setAsset(share);
			transactionHistory.setCreateDate(new Date());
			transactionHistory.setLastUpdate(new Date());
			transHistoryDao.addTransactionHistory(transactionHistory);
		} else {
			// Case 2: Share Dividend
			// add more share & recalculate share original price
			BigDecimal dividenShareAmount = share.getAmount().multiply(dRate).divide(new BigDecimal(100), 0,
					BigDecimal.ROUND_HALF_EVEN);
			// new original price
			BigDecimal newShareAmount = share.getAmount().add(dividenShareAmount);
			BigDecimal newOriginalPrice = share.getAmount().multiply(share.getOrginalPrice()).divide(newShareAmount,
					BigDecimal.ROUND_UP);
			share.setOrginalPrice(newOriginalPrice);
			share.setAmount(newShareAmount);
			assetService.updateAsset(share);
			// transaction history
			TransactionHistory transactionHistory = new TransactionHistory();
			transactionHistory.setPrice(new BigDecimal(0));
			transactionHistory.setAmount(dividenShareAmount);
			transactionHistory.setDescription("Chi trả cổ tức cổ phiếu. " + share.getAmount() + " "
					+ share.getAssetCode() + " - Tỷ lệ " + dRate + "%");
			transactionHistory.setActiveFlg(IContaints.INVEST.TRANS_ACTIVE);
			transactionHistory.setStatus(IContaints.INVEST.APPROVED);
			transactionHistory.setTypeOfTransaction("S");
			transactionHistory.setFeeType(null);
			transactionHistory.setAsset(share);
			transactionHistory.setCreateDate(new Date());
			transactionHistory.setLastUpdate(new Date());
			transHistoryDao.addTransactionHistory(transactionHistory);
		}

		response.setCode(200);
		response.setStatus(true);
		response.setErrors(null);
		response.setData("devidend trans success");
		return response;
	}

	public List<Asset> getAllShares() {
		// TODO Auto-generated method stub
		return assetDao.getAllShares();

	}

	@Override
	public List<Asset> getOtherAssetNotShares() {
		// TODO Auto-generated method stub
		return assetDao.getOtherAssetNotShares();
	}

	@Override
	public void deleteAssetByCode(String assetCode) {
		// TODO Auto-generated method stub
		assetDao.deleteAssetByCode(assetCode);
	}

	@Override
	public void deleteAssetById(Integer id) {
		// TODO Auto-generated method stub
		assetDao.deleteAssetById(id);
	}

	@Override
	public Asset getAssetById(Integer id) {
		// TODO Auto-generated method stub
		return assetDao.getAssetById(id);
	}

	@Override
	public List<Asset> SearchAssetsByCondition(int page, int pageSize, String columnSortName, Boolean asc,
			String assetCode, Integer groupAssetId, String assetName) {
		// TODO Auto-generated method stub
		return assetDao.searchAssetsByCondition(page, pageSize, columnSortName, asc, assetCode, groupAssetId,
				assetName);
	}

	@Override
	public int getRowCount(String assetCode, Integer groupAssetId, String assetName) {
		// TODO Auto-generated method stub
		return assetDao.getRowCount(assetCode, groupAssetId, assetName);
	}

	@Override
	public List<Asset> getAllSharesForBuy() {
		// TODO Auto-generated method stub
		return assetDao.getAllSharesForBuy();
	}

	@Override
	public List<Asset> getAssetByGroupId(Integer groupId) {
		// TODO Auto-generated method stub
		return assetDao.getAssetByGroupId(groupId);
	}

}
