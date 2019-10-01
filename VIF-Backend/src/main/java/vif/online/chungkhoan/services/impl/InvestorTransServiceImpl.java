package vif.online.chungkhoan.services.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;

import org.hibernate.annotations.common.util.impl.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import vif.online.chungkhoan.dao.CustomerDao;
import vif.online.chungkhoan.dao.InvestorHistoryDao;
import vif.online.chungkhoan.dao.TransactionHistoryDao;
import vif.online.chungkhoan.entities.Asset;
import vif.online.chungkhoan.entities.Customer;
import vif.online.chungkhoan.entities.CustomerAsset;
import vif.online.chungkhoan.entities.GroupAsset;
import vif.online.chungkhoan.entities.InvestorHistory;
import vif.online.chungkhoan.entities.TransactionHistory;
import vif.online.chungkhoan.helper.ApiResponse;
import vif.online.chungkhoan.helper.BuySellDTO;
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
	public ApiResponse buyCCQ(Integer customerId, BigDecimal money, BigDecimal priceCCQ) {
		// TODO Auto-generated method stub
		ApiResponse resultResponse = new ApiResponse();
		try {
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
				investorHistory.setTypeOfInvest(1);
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
		}catch(Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return null;
		}
		
		return resultResponse;
	}

	@Override
	public ApiResponse sellCCQ(Integer customerId, BigDecimal amountCCQ, BigDecimal priceCCQ, String feeSell) {
		// TODO Auto-generated method stub
		try {
			ApiResponse resultResponse = new ApiResponse();
			BigDecimal feeSellCCQ;
			if (feeSell != null) {
				feeSellCCQ = new BigDecimal(feeSell).divide(new BigDecimal(100));
			} else {
				feeSellCCQ = new BigDecimal(0);
			}

			// 1. Calculate money = amountCCQ*price CCQ
			BigDecimal money = amountCCQ.multiply(priceCCQ);
			Customer customer = customerDao.getCustomerById(customerId);
			if (customer != null) {
				// 2. Insert into table Investor History
				InvestorHistory investorHistory = new InvestorHistory();
				String newCodeTrans = customer.getCode() + System.currentTimeMillis();
				investorHistory.setCode(newCodeTrans);
				investorHistory.setAmountCCQ(amountCCQ);
				BigDecimal amountCCQBefore = customer.getTotalCcq() != null ? customer.getTotalCcq()
						: new BigDecimal(0);
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
					asset.setCurrentPrice(oldMoney.subtract(money.multiply(new BigDecimal(1).subtract(feeSellCCQ))));
					assetService.updateAsset(asset);

					// Insert into table Transaction_History
					TransactionHistory transHistory = new TransactionHistory();
					transHistory.setActiveFlg(1);
					transHistory.setAmount(money.multiply(new BigDecimal(1).subtract(feeSellCCQ)));
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

				// 5. Update amount of CCQ and price of CCQ in table Customer
				// customer.setLastCCQPrice(customer.getOrginalCCQPrice());
				// BigDecimal newCCQPrice = getOrignalPriceOfCustomerSell(customer, money,
				// amountCCQ);
				// if (newCCQPrice != null) {
				// customer.setOrginalCCQPrice(newCCQPrice);
				// customer.setTotalCcq(amountCCQBefore.subtract(amountCCQ));
				// }
				// tru di so luong CCQ ban. Gia CCQ giu nguyen, khong can tinh lai
				customer.setTotalCcq(amountCCQBefore.subtract(amountCCQ));

				boolean isUpdateCCQ = customerDao.updateCCQCustomer(customer);
				if (!isUpdateCCQ) {
					resultResponse.setCode(500);
					resultResponse.setStatus(false);
					resultResponse.setErrors("Update amount of CCQ and price of CCQ in table Customer failed!");
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
					return resultResponse;
				}

				// cong them so luong CCQ la fee cho VifAdmin
				Customer vifAdmin = customerDao.getCustomerByCode(IContaints.CONFIG.VIF_ADMIN_CODE);
				if (vifAdmin != null && feeSellCCQ.compareTo(new BigDecimal(0)) > 0) {
					BigDecimal amountVIFAdminCCQBefore = vifAdmin.getTotalCcq();
					vifAdmin.setLastCCQPrice(vifAdmin.getOrginalCCQPrice());
					BigDecimal newCCQVifPrice = getOrignalPriceOfCustomerBuy(vifAdmin, money.multiply(feeSellCCQ),
							amountCCQ.multiply(feeSellCCQ));
					if (newCCQVifPrice != null) {
						vifAdmin.setOrginalCCQPrice(newCCQVifPrice);
						vifAdmin.setTotalCcq(amountVIFAdminCCQBefore.add(amountCCQ.multiply(feeSellCCQ)));
					}

					boolean isUpdateVIFAdmin = customerDao.updateCCQCustomer(vifAdmin);
					if (!isUpdateVIFAdmin) {
						resultResponse.setCode(500);
						resultResponse.setStatus(false);
						resultResponse.setErrors("Update amount of CCQ and price of CCQ in table Customer failed!");
						TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
						return resultResponse;
					}
				}
				
				// 4. Subtract amount of CCQ in table Asset. If not exist, can not sell CCQ
				Asset assetCCQ = assetService.getAssetByCode(IContaints.ASSET_CODE.VIF_CCQ);
				if (assetCCQ != null) {
					assetCCQ.setAmount(
							assetCCQ.getAmount().subtract(amountCCQ.multiply(new BigDecimal(1).subtract(feeSellCCQ))));
					if(vifAdmin != null && feeSellCCQ.compareTo(new BigDecimal(0)) > 0) {
						assetCCQ.setOrginalPrice(getOrignalPriceOfCCQVif(assetCCQ.getAmount(), new BigDecimal(0), false));
					}else {
						assetCCQ.setOrginalPrice(getOrignalPriceOfCCQVif(assetCCQ.getAmount(), money, false));
					}
					assetCCQ.setCurrentPrice(assetCCQ.getOrginalPrice());
					assetService.updateAsset(assetCCQ);
				} else {
					resultResponse.setCode(500);
					resultResponse.setStatus(false);
					resultResponse.setErrors("Not exist CCQ asset!");
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
					return resultResponse;
				}
			}
			resultResponse.setCode(200);
			resultResponse.setStatus(true);
			resultResponse.setErrors(null);
			resultResponse.setData("Sell successfully!");
			return resultResponse;
		} catch (Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return null;
		}
	}

	// tinh toan ra gia von CCQ cua NDT khi mua vao
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
				if(currentTotalMoney.compareTo(new BigDecimal(0)) <= 0) {
					return new BigDecimal(0);
				}
				result = (currentTotalMoney.add(moneyChange)).divide(totalCCQ, 2, RoundingMode.HALF_UP);
			} else {
				if(currentTotalMoney.compareTo(moneyChange) <= 0) {
					return new BigDecimal(0);
				}
				result = (currentTotalMoney.subtract(moneyChange)).divide(totalCCQ, 2, RoundingMode.HALF_UP);
			}

			return result;
		} catch (Exception e) {
			// TODO: handle exception
			return new BigDecimal(0);
		}
	}

	@Override
	public List<InvestorHistory> getAllInvestorHistory() {
		// TODO Auto-generated method stub
		return investorHistoryDao.getAllInvestorHistory();
	}

	@Override
	public List<InvestorHistory> searchInvestorHistoryByCondition(int page, int pageSize, String columnSortName,
			Boolean asc, Integer customerId, String fromDate, String toDate) {
		// TODO Auto-generated method stub
		return investorHistoryDao.searchInvestorHistoryByCondition(page, pageSize, columnSortName, asc, customerId, fromDate, toDate);
	}

	@Override
	public int getRowCount(Integer customerId, String fromDate, String toDate) {
		// TODO Auto-generated method stub
		return investorHistoryDao.getRowCount(customerId, fromDate, toDate);
	}

	@Override
	public ApiResponse buyEnsureCCQ(BuySellDTO buyObject) {
		// TODO Auto-generated method stub
		ApiResponse resultResponse = new ApiResponse();
		BigDecimal money = buyObject.getMoney();
		BigDecimal priceEnsureCCQ = buyObject.getPriceCCQ();
		BigDecimal amoutnCCQ = money.divide(priceEnsureCCQ, 2, RoundingMode.HALF_UP);
		Customer customer = customerDao.getCustomerById(buyObject.getCustomerId());
		if (customer != null) {			
			
			// 1. Add money into table Asset (for cash and debt) and insert into table Transaction_History
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
			
			// 2. Update with ensure CCQ
			String ensureCCQCode = buyObject.getEnsureCCQCode()!=null?buyObject.getEnsureCCQCode():"";
			Asset assetEnsureCCQ = assetService.getAssetByCode(ensureCCQCode);
			if (assetEnsureCCQ != null) {
				
//				BigDecimal oldMoney = assetEnsureCCQ.getCurrentPrice();
				assetEnsureCCQ.setCurrentPrice(getOrignalPriceOfEnsureCCQ(assetEnsureCCQ, money, amoutnCCQ, true));
				assetEnsureCCQ.setAmount(assetEnsureCCQ.getAmount().add(amoutnCCQ));
				assetService.updateAsset(assetEnsureCCQ);
				
				// 3. Insert into table Investor History
				InvestorHistory investorHistory = new InvestorHistory();
				String newCodeTrans = customer.getCode() + System.currentTimeMillis();
				investorHistory.setCode(newCodeTrans);
				investorHistory.setAmountCCQ(amoutnCCQ);
				
				investorHistory.setCreateDate(new Date());
				investorHistory.setCustomer(customer);
				investorHistory.setLastUpdate(new Date());
				investorHistory.setPriceOfCCQ(priceEnsureCCQ);
				
				investorHistory.setTypeOfTransaction("M");
				investorHistory.setTypeOfInvest(2);
				
				// 4. Insert or update into table Customer Asset
				// Check exist customer asset
				CustomerAsset cusAsset = customerDao.getCusAssetByCusAndAssetId(customer.getId(), assetEnsureCCQ.getId());
				if(cusAsset != null) {
					// calculate new price
					cusAsset.setAmount(cusAsset.getAmount().add(amoutnCCQ));
					cusAsset.setCurrentPrice(getNewPriceOfCusAsset(cusAsset, money, amoutnCCQ, true));
					customerDao.updateCustomerAsset(cusAsset);
					
					// set amount and price before of customer
					BigDecimal amountCCQBefore = cusAsset.getAmount() != null ? cusAsset.getAmount() : new BigDecimal(0);
					investorHistory.setAmountCCQBefore(amountCCQBefore);
					investorHistory.setPriceOfCCQBefore(
							cusAsset.getCurrentPrice() != null ? cusAsset.getCurrentPrice() : new BigDecimal(0));
				}else {
					// insert new recode customer asset
					CustomerAsset newItem = new CustomerAsset();
					newItem.setCustomerId(customer.getId());
					newItem.setAssetId(assetEnsureCCQ.getId());
					newItem.setAmount(amoutnCCQ);
					newItem.setCurrentPrice(priceEnsureCCQ);
					customerDao.addCustomerAsset(newItem);
					
					// set amount and price before of customer
					investorHistory.setAmountCCQBefore(new BigDecimal(0));
					investorHistory.setPriceOfCCQBefore(new BigDecimal(0));
				}
				
				investorHistoryDao.addInvestorHistory(investorHistory);
			}else {
				resultResponse.setCode(500);
				resultResponse.setStatus(false);
				resultResponse.setErrors("Ensure CCQ is not exists!");
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				return resultResponse;
			}

			// 5. Insert into table Transaction_History
			TransactionHistory transHistory = new TransactionHistory();
			transHistory.setActiveFlg(1);
			transHistory.setAmount(money);
			transHistory.setAsset(asset);
			transHistory.setFeeType(null);
			transHistory.setCreateDate(new Date());
			transHistory.setDescription(customer.getFullName() + " invest ensure CCQ to VIF");
			transHistory.setLastUpdate(new Date());
			transHistory.setPrice(new BigDecimal(0));
			transHistory.setStatus(2); // 1 – Pending; 2 – Approved; 3 – Rejected
			transHistory.setTypeOfTransaction("M"); // M: Thêm B: Bớt C: cổ tức tiền S: Cổ tức cổ phiếu
			transHistoryDao.addTransactionHistory(transHistory);
		}else {
			resultResponse.setCode(500);
			resultResponse.setStatus(false);
			resultResponse.setErrors("Customer is not exists!");
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return resultResponse;
		}
		
		resultResponse.setCode(200);
		resultResponse.setStatus(true);
		resultResponse.setErrors(null);
		resultResponse.setData("Buy ensure CCQ successfully!");
		return resultResponse;
	}
	
	@Override
	public ApiResponse sellEnsureCCQ(BuySellDTO buyObject) {
		// TODO Auto-generated method stub
		ApiResponse resultResponse = new ApiResponse();
		BigDecimal money = buyObject.getMoney();
		BigDecimal priceEnsureCCQ = buyObject.getPriceCCQ();
		BigDecimal amoutnCCQ = money.divide(priceEnsureCCQ, 2, RoundingMode.HALF_UP);
		Customer customer = customerDao.getCustomerById(buyObject.getCustomerId());
		if (customer != null) {
			// 1. Substract money into table Asset (for cash and debt) and insert into table Transaction_History
			Asset asset = assetService.getAssetByCode(IContaints.ASSET_CODE.CASH);
			if (asset != null) {
				BigDecimal oldMoney = asset.getCurrentPrice();
				asset.setCurrentPrice(oldMoney.subtract(money));
				assetService.updateAsset(asset);
			} else {
				resultResponse.setCode(500);
				resultResponse.setStatus(false);
				resultResponse.setErrors("Cash is not exists!");
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				return resultResponse;
			}
			
			// 3. Update with ensure CCQ
			String ensureCCQCode = buyObject.getEnsureCCQCode()!=null?buyObject.getEnsureCCQCode():"";
			Asset assetEnsureCCQ = assetService.getAssetByCode(ensureCCQCode);
			if (assetEnsureCCQ != null) {
//				BigDecimal oldMoney = assetEnsureCCQ.getCurrentPrice();
				assetEnsureCCQ.setCurrentPrice(getOrignalPriceOfEnsureCCQ(assetEnsureCCQ, money, amoutnCCQ, false));
				assetEnsureCCQ.setAmount(assetEnsureCCQ.getAmount().subtract(amoutnCCQ));
				assetService.updateAsset(assetEnsureCCQ);
				
				// 1. Insert into table Investor History
				InvestorHistory investorHistory = new InvestorHistory();
				String newCodeTrans = customer.getCode() + System.currentTimeMillis();
				investorHistory.setCode(newCodeTrans);
				investorHistory.setAmountCCQ(amoutnCCQ);
				
				investorHistory.setCreateDate(new Date());
				investorHistory.setCustomer(customer);
				investorHistory.setLastUpdate(new Date());
				investorHistory.setPriceOfCCQ(priceEnsureCCQ);
				
				investorHistory.setTypeOfTransaction("B");
				investorHistory.setTypeOfInvest(2);
				
				
				// 4. Insert or update into table Customer Asset
				// Check exist customer asset
				CustomerAsset cusAsset = customerDao.getCusAssetByCusAndAssetId(customer.getId(), assetEnsureCCQ.getId());
				if(cusAsset != null) {
					BigDecimal amountCCQBefore = customer.getTotalCcq() != null ? customer.getTotalCcq() : new BigDecimal(0);
					investorHistory.setAmountCCQBefore(amountCCQBefore);
					
					investorHistory.setPriceOfCCQBefore(
							customer.getOrginalCCQPrice() != null ? customer.getOrginalCCQPrice() : new BigDecimal(0));
					investorHistoryDao.addInvestorHistory(investorHistory);
					
					// calculate new price
					cusAsset.setAmount(cusAsset.getAmount().subtract(amoutnCCQ));
					cusAsset.setCurrentPrice(getNewPriceOfCusAsset(cusAsset, money, amoutnCCQ, false));
					customerDao.updateCustomerAsset(cusAsset);
				}else {
					resultResponse.setCode(500);
					resultResponse.setStatus(false);
					resultResponse.setErrors("Customer don't have any ensure CCQ!");
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
					return resultResponse;
				}
			}else {
				resultResponse.setCode(500);
				resultResponse.setStatus(false);
				resultResponse.setErrors("Ensure CCQ is not exists!");
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				return resultResponse;
			}

			// 5. Insert into table Transaction_History
			TransactionHistory transHistory = new TransactionHistory();
			transHistory.setActiveFlg(1);
			transHistory.setAmount(money);
			transHistory.setAsset(asset);
			transHistory.setFeeType(null);
			transHistory.setCreateDate(new Date());
			transHistory.setDescription(customer.getFullName() + " Sell ensure CCQ from VIF");
			transHistory.setLastUpdate(new Date());
			transHistory.setPrice(new BigDecimal(0));
			transHistory.setStatus(2); // 1 – Pending; 2 – Approved; 3 – Rejected
			transHistory.setTypeOfTransaction("B"); // M: Thêm B: Bớt C: cổ tức tiền S: Cổ tức cổ phiếu
			transHistoryDao.addTransactionHistory(transHistory);
		}else {
			resultResponse.setCode(500);
			resultResponse.setStatus(false);
			resultResponse.setErrors("Customer is not exists!");
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return resultResponse;
		}
		
		resultResponse.setCode(200);
		resultResponse.setStatus(true);
		resultResponse.setErrors(null);
		resultResponse.setData("Buy ensure CCQ successfully!");
		return resultResponse;
	}
	
	// tinh toan ra gia von Ensure CCQ cua NDT khi mua vao
	private BigDecimal getOrignalPriceOfEnsureCCQ(Asset currentEnsureCCQ, BigDecimal moneyAdd, BigDecimal ccqAdd, boolean isBuy) {
		try {
			BigDecimal currentAmountCCQ = currentEnsureCCQ.getAmount() != null ? currentEnsureCCQ.getAmount() : new BigDecimal(0);
			BigDecimal currentMoney = currentAmountCCQ.multiply(
					currentEnsureCCQ.getCurrentPrice() != null ? currentEnsureCCQ.getCurrentPrice() : new BigDecimal(0));
			if(isBuy) {
				BigDecimal result = (currentMoney.add(moneyAdd)).divide(currentAmountCCQ.add(ccqAdd), 2,
						RoundingMode.HALF_UP);
				return result;
			}else {
				if(currentAmountCCQ.subtract(ccqAdd)!= new BigDecimal(0)) {
					BigDecimal result = (currentMoney.subtract(moneyAdd)).divide(currentAmountCCQ.subtract(ccqAdd), 2,
							RoundingMode.HALF_UP);
					return result;
				}else {
					return new BigDecimal(0);
				}
				
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	}
	
	// tinh toan ra gia von Ensure CCQ cua NDT khi mua vao
	private BigDecimal getNewPriceOfCusAsset(CustomerAsset cusAsset, BigDecimal moneyAdd, BigDecimal ccqAdd, boolean isBuy) {
		try {
			BigDecimal currentAmountCCQ = cusAsset.getAmount() != null ? cusAsset.getAmount()
					: new BigDecimal(0);
			BigDecimal currentMoney = currentAmountCCQ
					.multiply(cusAsset.getCurrentPrice() != null ? cusAsset.getCurrentPrice()
							: new BigDecimal(0));
			if(isBuy) {
				BigDecimal result = (currentMoney.add(moneyAdd)).divide(currentAmountCCQ.add(ccqAdd), 2,
						RoundingMode.HALF_UP);
				return result;
			}else {
				if(currentAmountCCQ.subtract(ccqAdd) != new BigDecimal(0)) {
					BigDecimal result = (currentMoney.subtract(moneyAdd)).divide(currentAmountCCQ.subtract(ccqAdd), 2,
							RoundingMode.HALF_UP);
					return result;
				}else {
					return new BigDecimal(0);
				}
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	}

	@Override
	public ApiResponse getEnsureCCQByCusAsset(Integer customerId, String assetCode) {
		// TODO Auto-generated method stub
		ApiResponse resultResponse = new ApiResponse();
		Asset myEnsureCCQ = assetService.getAssetByCode(assetCode);
		if(myEnsureCCQ != null) {
			CustomerAsset cusAsset = customerDao.getCusAssetByCusAndAssetId(customerId, myEnsureCCQ.getId());
			if(cusAsset != null) {
				resultResponse.setData(cusAsset.getAmount());
			}else {
				resultResponse.setData(null);
			}
		}else {
			resultResponse.setData(null);
		}

		resultResponse.setCode(200);
		resultResponse.setStatus(true);
		resultResponse.setErrors(null);
		return resultResponse;
	}
	
	
	public ApiResponse cCommissionDivide(Integer customerId, BigDecimal amount, String cType) {
		// TODO Auto-generated method stub
		ApiResponse resultResponse = new ApiResponse();

		Customer customer = customerDao.getCustomerById(customerId);
		if (customer != null) {
			if (cType.equals("CASH")) {
				// subtract money from table Asset
				Asset asset = assetService.getAssetByCode(IContaints.ASSET_CODE.CASH);
				BigDecimal oldMoney = asset.getCurrentPrice();
				asset.setCurrentPrice(oldMoney.subtract(amount));
				assetService.updateAsset(asset);
				// Insert into table Transaction_History
				TransactionHistory transHistory = new TransactionHistory();
				transHistory.setActiveFlg(1);
				transHistory.setAmount(amount);
				transHistory.setAsset(asset);
				transHistory.setFeeType(null);
				transHistory.setCreateDate(new Date());
				transHistory.setDescription("Chia lợi tức tiền mặt cho VIF");
				transHistory.setLastUpdate(new Date());
				transHistory.setPrice(new BigDecimal(0));
				transHistory.setStatus(2); // 1 – Pending; 2 – Approved; 3 – Rejected
				transHistory.setTypeOfTransaction("C"); // M: Thêm B: Bớt C: cổ tức tiền S: Cổ tức cổ phiếu
				transHistoryDao.addTransactionHistory(transHistory);
			} else {
				// Insert into table Investor History
				InvestorHistory investorHistory = new InvestorHistory();
				String newCodeTrans = customer.getCode() + System.currentTimeMillis();
				investorHistory.setCode(newCodeTrans);
				investorHistory.setAmountCCQ(amount);
				BigDecimal amountCCQBefore = customer.getTotalCcq() != null ? customer.getTotalCcq()
						: new BigDecimal(0);
				investorHistory.setAmountCCQBefore(amountCCQBefore);
				investorHistory.setCreateDate(new Date());
				investorHistory.setCustomer(customer);
				investorHistory.setLastUpdate(new Date());
				investorHistory.setPriceOfCCQ(new BigDecimal(0));
				investorHistory.setPriceOfCCQBefore(
						customer.getOrginalCCQPrice() != null ? customer.getOrginalCCQPrice() : new BigDecimal(0));
				investorHistory.setTypeOfTransaction("S");
				investorHistoryDao.addInvestorHistory(investorHistory);

				// Update amount of CCQ and price of CCQ in table Customer
				customer.setLastCCQPrice(new BigDecimal(0));
				BigDecimal newCCQPrice = getOrignalPriceOfCustomerBuy(customer, new BigDecimal(0), amount);
				if (newCCQPrice != null) {
					customer.setOrginalCCQPrice(newCCQPrice);
					customer.setTotalCcq(amountCCQBefore.add(amount));
				}
				Boolean isUpdateCustomer = customerDao.updateCCQCustomer(customer);

				// Add amount of CCQ in table Asset
				Asset assetCCQ = assetService.getAssetByCode(IContaints.ASSET_CODE.VIF_CCQ);
				BigDecimal curentMoney = assetCCQ.getCurrentPrice().multiply(assetCCQ.getAmount());
				BigDecimal originalMoney = assetCCQ.getOrginalPrice().multiply(assetCCQ.getAmount());
				// add more CCQ
				assetCCQ.setAmount(assetCCQ.getAmount().add(amount));
				// re-calculate Current price
				BigDecimal newCurrentPrice = curentMoney.divide(assetCCQ.getAmount(), RoundingMode.HALF_UP);
				// re-calculate original price
				assetCCQ.setOrginalPrice(originalMoney.divide(assetCCQ.getAmount(), RoundingMode.UP));
				assetCCQ.setCurrentPrice(newCurrentPrice);
				assetService.updateAsset(assetCCQ);

				// Insert into table Transaction_History
				TransactionHistory transHistory = new TransactionHistory();
				transHistory.setActiveFlg(1);
				transHistory.setAmount(amount);
				transHistory.setAsset(assetCCQ);
				transHistory.setFeeType(null);
				transHistory.setCreateDate(new Date());
				transHistory.setDescription("Chia lợi tức CCQ cho VIF");
				transHistory.setLastUpdate(new Date());
				transHistory.setPrice(new BigDecimal(0));
				transHistory.setStatus(2); // 1 – Pending; 2 – Approved; 3 – Rejected
				transHistory.setTypeOfTransaction("S"); // M: Thêm B: Bớt C: cổ tức tiền S: Cổ tức cổ phiếu
				transHistoryDao.addTransactionHistory(transHistory);
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
		resultResponse.setData("Success!");
		return resultResponse;
	}

}
