package vif.online.chungkhoan.services.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vif.online.chungkhoan.dao.AssetDao;
import vif.online.chungkhoan.dao.GroupAssetDao;
import vif.online.chungkhoan.dao.ShareMasterDao;
import vif.online.chungkhoan.dao.TransactionHistoryDao;
import vif.online.chungkhoan.entities.Asset;
import vif.online.chungkhoan.entities.Customer;
import vif.online.chungkhoan.entities.GroupAsset;
import vif.online.chungkhoan.entities.ShareMaster;
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
		// TODO Auto-generated method stub
		ApiResponse response = new ApiResponse();
		// get share master
		ShareMaster shareMaster = shareMasterDao.getCophieuById(assetId);

		// get asset if null create a new asset
		Asset sercurity = assetDao.getAssetByCode(shareMaster.getCpCode());
		if (sercurity == null) {
			sercurity = new Asset();
			sercurity.setAssetCode(shareMaster.getCpCode());
			sercurity.setAssetName(shareMaster.getCpName());
			sercurity.setAmount(new BigDecimal(0));
			sercurity.setActiveFlg(IContaints.ASSET_CODE.ACTIVE);
			sercurity.setBranchCode(null);
			sercurity.setCurrentPrice(price);
			sercurity.setDescription(shareMaster.getCpName());
			sercurity.setOrginalPrice(price);
			// set group asset
			GroupAsset groupAsset = groupAssetDao.getGroupByCode(IContaints.ASSET_CODE.GROUP_ASSET_SHARE_CODE);
			sercurity.setGroupAsset(groupAsset);
			assetService.addAsset(sercurity); // add new share
		}
		// add to asset transaction
		TransactionHistory transHistory = new TransactionHistory();
		transHistory.setActiveFlg(1);
		transHistory.setAmount(amount);
		transHistory.setAsset(sercurity);
		transHistory.setFeeType(null);
		transHistory.setCreateDate(new Date());
		transHistory.setDescription("VIF mua " + amount + " cổ phiếu" + sercurity.getAssetCode());
		transHistory.setLastUpdate(new Date());
		transHistory.setPrice(price);
		transHistory.setStatus(2); // 1 – Pending; 2 – Approved; 3 – Rejected
		transHistory.setTypeOfTransaction("M"); // M: Thêm B: Bớt C: cổ tức tiền S: Cổ tức cổ phiếu
		transHistoryDao.addTransactionHistory(transHistory);

		BigDecimal money = amount.multiply(price);
		// subtract money
		Asset cAsset = assetService.getAssetByCode(IContaints.ASSET_CODE.CASH);
		if (cAsset == null) {
			response.setCode(500);
			response.setStatus(false);
		}
		BigDecimal oldMoney = cAsset.getCurrentPrice();
		cAsset.setCurrentPrice(oldMoney.subtract(money));
		assetService.updateAsset(cAsset);
		// update amount of asset
		BigDecimal newAmount = sercurity.getAmount().add(amount);
		BigDecimal assetOriginalValue = sercurity.getAmount().multiply(sercurity.getOrginalPrice());
		BigDecimal newOriginalPrice = (money.add(assetOriginalValue).divide(newAmount, 2, RoundingMode.HALF_UP));
		sercurity.setAmount(newAmount);
		sercurity.setOrginalPrice(newOriginalPrice);
		sercurity.setActiveFlg(IContaints.ASSET_CODE.ACTIVE);
		assetService.updateAsset(sercurity);
		// response
		response.setCode(200);
		response.setStatus(true);
		response.setErrors(null);
		response.setData("Buy success");
		return response;
	}

	@Override
	public ApiResponse sellSercurities(Integer assetId, BigDecimal amount, BigDecimal price) {
		// TODO Auto-generated method stub
		ApiResponse response = new ApiResponse();
		
		// get asset
		Asset sercurity = assetDao.getByAssetId(assetId);
		// add to asset transaction
		TransactionHistory transHistory = new TransactionHistory();
		transHistory.setActiveFlg(1);
		transHistory.setAmount(amount);
		transHistory.setAsset(sercurity);
		transHistory.setFeeType(null);
		transHistory.setCreateDate(new Date());
		transHistory.setDescription("VIF bán " + amount + " cổ phiếu" + sercurity.getAssetCode());
		transHistory.setLastUpdate(new Date());
		transHistory.setPrice(price);
		transHistory.setStatus(2); // 1 – Pending; 2 – Approved; 3 – Rejected
		transHistory.setTypeOfTransaction("C"); // M: Thêm B: Bớt C: cổ tức tiền S: Cổ tức cổ phiếu
		transHistoryDao.addTransactionHistory(transHistory);
		// add money
		Asset cAsset = assetService.getAssetByCode(IContaints.ASSET_CODE.CASH);
		if (cAsset == null) {
			response.setCode(500);
			response.setStatus(false);
		}
		BigDecimal oldMoney = cAsset.getCurrentPrice();
		BigDecimal money = amount.multiply(price);
		cAsset.setCurrentPrice(oldMoney.add(money));
		assetService.updateAsset(cAsset);
		// update amount of asset, if amount is new
		BigDecimal newAmount = sercurity.getAmount().subtract(amount);
	    if(amount.equals(sercurity.getAmount())) {
	    	sercurity.setActiveFlg(IContaints.ASSET_CODE.DEACTIVE_FLAG);
	    }
	    sercurity.setAmount(newAmount);
		assetService.updateAsset(sercurity);
		
		response.setCode(200);
		response.setStatus(true);
		response.setErrors(null);
		response.setData("Sell success");
		return response;
	}

	@Override
	public ApiResponse cashDividend(Integer assetId, BigDecimal amount, BigDecimal dividendRate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ApiResponse sercuritiesDividend(Integer assetId, BigDecimal amount, BigDecimal dividendRate) {
		// TODO Auto-generated method stub
		return null;
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

}
