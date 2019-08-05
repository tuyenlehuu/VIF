package vif.online.chungkhoan.dao.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import vif.online.chungkhoan.dao.CustomerDao;
import vif.online.chungkhoan.dao.DashboardDao;
import vif.online.chungkhoan.dao.UserDao;
import vif.online.chungkhoan.entities.AppParam;
import vif.online.chungkhoan.entities.Asset;
import vif.online.chungkhoan.entities.CustomerAsset;
import vif.online.chungkhoan.entities.DashBoard;
import vif.online.chungkhoan.entities.User;
import vif.online.chungkhoan.helper.IContaints;
import vif.online.chungkhoan.helper.KeyNameValueDTO;
import vif.online.chungkhoan.helper.NAVDTO;
import vif.online.chungkhoan.services.AppParamService;
import vif.online.chungkhoan.services.AssetService;

@Transactional
@Repository
public class DashboardDaoImpl implements DashboardDao {

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private UserDao userDao;

	@Autowired
	private AssetService assetService;

	@Autowired
	private CustomerDao cusDao;

	@Autowired
	private AppParamService appParamService;

	@SuppressWarnings("unchecked")
	@Override
	public DashBoard getData() {
		// TODO Auto-generated method stub
		String sql = "SELECT " + "	MAX(t.CASH) cash, " + "    MAX(t.CS) cs, " + "    MAX(t.SHARES) shares, "
				+ "    MAX(t.IT_RT) itRt, MAX(t.DEBT_LONG) debtLong, MAX(t.DEBT_SHORT) debtShort, "
				+ "    MAX(t.VIF_CCQ_PRICE) vifCCQPrice, MAX(t.VIF_CCQ) vifAmountCCQ, "
				+ " MAX(t.CASH)+MAX(t.CS)+MAX(t.SHARES)+MAX(t.IT_RT) totalAsset, MAX(t.DEBT_LONG)+MAX(t.DEBT_SHORT) totalDebt "
				+ " FROM (SELECT " + "  MAX(CASE WHEN code = 'CASH' THEN current_price END) CASH, "
				+ "  MAX(CASE WHEN code = 'VIF_CCQ' THEN amount END) VIF_CCQ, "
				+ "  MAX(CASE WHEN code = 'VIF_CCQ' THEN current_price END) VIF_CCQ_PRICE, "
				+ "  MAX(CASE WHEN code = 'IT_RT' THEN current_price*amount END) IT_RT, "
				+ "  MAX(CASE WHEN code = 'CS' THEN current_price END) CS, "
				+ "  MAX(CASE WHEN code = 'DEBT_LONG' THEN current_price END) DEBT_LONG, "
				+ "  MAX(CASE WHEN code = 'DEBT_SHORT' THEN current_price END) DEBT_SHORT, " + "  0 SHARES "
				+ " FROM asset " + " where active_flg = 1 " + " UNION "
				+ " SELECT 0 CASH, 0 VIF_CCQ, 0 VIF_CCQ_PRICE, 0 IT_RT, 0 CS, 0 DEBT_LONG, 0 DEBT_SHORT, "
				+ " SUM(current_price*amount*1000) SHARES "
				+ " FROM asset WHERE group_id = 2 AND branch_code IS NOT NULL AND active_flg = 1 " + ") t";

		List<Object[]> rows = entityManager.createNativeQuery(sql).getResultList();
		DashBoard dashboard = new DashBoard();
		if (rows != null && rows.size() > 0) {
			for (Object[] row : rows) {
				if (row[0] != null && !row[0].equals("")) {
					dashboard.setCash((BigDecimal) row[0]);
				}

				if (row[1] != null && !row[1].equals("")) {
					dashboard.setCs((BigDecimal) row[1]);
				}

				if (row[2] != null && !row[2].equals("")) {
					dashboard.setShares((BigDecimal) row[2]);
				}

				if (row[3] != null && !row[3].equals("")) {
					dashboard.setItRt((BigDecimal) row[3]);
				}

				if (row[4] != null && !row[4].equals("")) {
					dashboard.setDebtLong((BigDecimal) row[4]);
				}

				if (row[5] != null && !row[5].equals("")) {
					dashboard.setDebtShort((BigDecimal) row[5]);
				}

				if (row[6] != null && !row[6].equals("")) {
					dashboard.setVifCCQPrice((BigDecimal) row[6]);
				}

				if (row[7] != null && !row[7].equals("")) {
					dashboard.setVifAmountCCQ((BigDecimal) row[7]);
				}

				if (row[8] != null && !row[8].equals("")) {
					dashboard.setTotalAsset((BigDecimal) row[8]);
				}

				if (row[9] != null && !row[9].equals("")) {
					dashboard.setTotalDebt((BigDecimal) row[9]);
				}
			}
		}

		return dashboard;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<KeyNameValueDTO> getDataTotalAsset() {
		// TODO Auto-generated method stub
		String sql = "SELECT DATE_FORMAT(update_date, '%Y/%m') Month, "
				+ " SUM(CASE WHEN amount = 0 AND code!='VIF_CCQ' THEN price END) + SUM(CASE WHEN amount <> 0 AND code!='VIF_CCQ' THEN amount*price END) TotalAsset "
				+ " FROM asset_history " + " WHERE last_of_month_flg=1 AND active_flg=1 " + " GROUP BY update_date";

		List<Object[]> rows = entityManager.createNativeQuery(sql).getResultList();
		List<KeyNameValueDTO> assetLst = new ArrayList<KeyNameValueDTO>();
		if (rows != null && rows.size() > 0) {
			for (Object[] row : rows) {
				KeyNameValueDTO item = new KeyNameValueDTO();
				if (row[0] != null && !row[0].equals("")) {
					item.setKey(row[0].toString());
				}
				if (row[1] != null && !row[1].equals("")) {
					item.setValue((BigDecimal) row[1]);
				}
				assetLst.add(item);
			}
		}

		return assetLst;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<NAVDTO> getNAVReport(Integer customerId, String fromDate, String toDate) {
		// TODO Auto-generated method stub
		StringBuilder queryStr = new StringBuilder("");
		queryStr.append(
				" SELECT a.customer_id, a.NDT, MAX(CASE WHEN a.last_update = a.mini THEN a.CCQ_Dau_Ky END) CCQ_Dau_Ky,");
		queryStr.append(
				" MAX(CASE WHEN a.last_update = a.mini THEN a.Gia_Dau_Ky END) Gia_Dau_Ky, MAX(CASE WHEN a.last_update = a.maxi THEN a.CCQ_Cuoi_Ky END) CCQ_Cuoi_Ky, ");
		queryStr.append(
				" MAX(CASE WHEN a.last_update = a.maxi THEN a.Gia_Cuoi_Ky END) Gia_Cuoi_Ky, (SELECT a.price FROM asset_history a, (SELECT MAX(ah.update_date) update_date FROM asset_history AS ah ");
		queryStr.append(" WHERE ah.code = 'VIF_CCQ' ");

		if (fromDate != null && !fromDate.equals("")) {
			queryStr.append(" AND ah.update_date >= STR_TO_DATE(':fromDate', '%m/%d/%Y') ");
		}

		if (toDate != null && !toDate.equals("")) {
			queryStr.append(" AND ah.update_date <= STR_TO_DATE(':toDate', '%m/%d/%Y')");
		}

		queryStr.append(" ) t");
		queryStr.append(" WHERE a.update_date = t.update_date AND a.code = 'VIF_CCQ') Gia_TT,");
		queryStr.append(
				" MAX(CASE WHEN a.last_update = a.maxi THEN a.CCQ_Cuoi_Ky END) * MAX(CASE WHEN a.last_update = a.maxi THEN a.Gia_Cuoi_Ky END) TS_Rong");
		queryStr.append(" FROM");
		queryStr.append(
				" (SELECT c.id customer_id, ih.last_update, c.full_name NDT, ih.type_of_transaction, ih.amount_ccq_before CCQ_Dau_Ky,");
		queryStr.append(
				" ih.price_of_ccq_before Gia_Dau_Ky, CASE WHEN ih.type_of_transaction = 'M' THEN ih.amount_ccq + ih.amount_ccq_before ELSE ih.amount_ccq_before - ih.amount_ccq END CCQ_Cuoi_Ky,");
		queryStr.append(
				" CASE WHEN ih.type_of_transaction = 'M' THEN (ih.amount_ccq * ih.price_of_ccq + ih.amount_ccq_before * ih.price_of_ccq_before) / (ih.amount_ccq + ih.amount_ccq_before)");
		queryStr.append(
				" ELSE (ih.amount_ccq_before * ih.price_of_ccq_before - ih.amount_ccq * ih.price_of_ccq) / (ih.amount_ccq_before - ih.amount_ccq) END Gia_Cuoi_Ky,");
		queryStr.append(" m.mini, m.maxi");
		queryStr.append(" FROM investor_history ih,");
		queryStr.append(
				" (SELECT ih.customer_id, c.full_name, MIN(ih.last_update) AS mini, MAX(ih.last_update) AS maxi");
		queryStr.append(" FROM customer c");
		queryStr.append(" LEFT JOIN investor_history ih ON c.id = ih.customer_id");
		queryStr.append(" WHERE 1 = 1");

		if (fromDate != null && !fromDate.equals("")) {
			queryStr.append(" AND ih.last_update >= STR_TO_DATE(':fromDate', '%m/%d/%Y') ");
		}

		if (toDate != null && !toDate.equals("")) {
			queryStr.append(" AND ih.last_update <= STR_TO_DATE(':toDate', '%m/%d/%Y')");
		}
		queryStr.append(" GROUP BY ih.customer_id");
		queryStr.append(" UNION");
		queryStr.append(
				" SELECT ih.customer_id, c.full_name, MAX(ih.last_update) AS mini, MAX(ih.last_update) AS maxi");
		queryStr.append(" FROM customer c");
		queryStr.append(" LEFT JOIN investor_history ih ON c.id = ih.customer_id");
		queryStr.append(" WHERE 1=1");

		if (fromDate != null && !fromDate.equals("")) {
			queryStr.append(" AND ih.last_update < STR_TO_DATE(':fromDate', '%m/%d/%Y') ");
		}

		queryStr.append(" AND c.id NOT IN (SELECT ih.customer_id FROM investor_history ih");
		queryStr.append(" WHERE 1=1");
		if (fromDate != null && !fromDate.equals("")) {
			queryStr.append(" AND ih.last_update >= STR_TO_DATE(':fromDate', '%m/%d/%Y') ");
		}

		if (toDate != null && !toDate.equals("")) {
			queryStr.append(" AND ih.last_update <= STR_TO_DATE(':toDate', '%m/%d/%Y')");
		}

		queryStr.append(" GROUP BY ih.customer_id)");
		queryStr.append(" GROUP BY ih.customer_id) AS m, customer c");
		queryStr.append(" WHERE 1=1");
		queryStr.append(" AND (m.maxi = ih.last_update OR m.mini = ih.last_update)");
		queryStr.append(" AND c.id = ih.customer_id AND c.id = m.customer_id) a WHERE 1=1 ");

		if (customerId != null) {
			queryStr.append(" AND a.customer_id = :customerId");
		}
		queryStr.append(" GROUP BY a.customer_id");

		if (fromDate != null && !fromDate.equals("")) {
			Pattern p = Pattern.compile(":fromDate");
			queryStr = replaceAll(queryStr, p, fromDate);
		}

		if (toDate != null && !toDate.equals("")) {
			Pattern p = Pattern.compile(":toDate");
			queryStr = replaceAll(queryStr, p, toDate);
		}

		if (customerId != null) {
			Pattern p = Pattern.compile(":customerId");
			queryStr = replaceAll(queryStr, p, customerId.toString());
		}

		Query mQuery = entityManager.createNativeQuery(queryStr.toString());
		List<Object[]> rows = mQuery.getResultList();
		List<NAVDTO> NAVList = new ArrayList<NAVDTO>();
		if (rows != null && rows.size() > 0) {
			for (Object[] row : rows) {
				NAVDTO item = new NAVDTO();
				if (row[0] != null && !row[0].equals("")) {
					item.setCustomerId(((BigInteger) row[0]).intValue());
				}

				if (row[1] != null && !row[1].equals("")) {
					item.setCustomerFullName(row[1].toString());
				}

				if (row[2] != null && !row[2].equals("")) {
					item.setAmountCCQBefore((BigDecimal) row[2]);
				}

				if (row[3] != null && !row[3].equals("")) {
					item.setPriceCCQBefore((BigDecimal) row[3]);
				}

				if (row[4] != null && !row[4].equals("")) {
					item.setAmountCCQAfter((BigDecimal) row[4]);
				}

				if (row[5] != null && !row[5].equals("")) {
					item.setPriceCCQAfter((BigDecimal) row[5]);
				}

				if (row[6] != null && !row[6].equals("")) {
					item.setPriceCCQMarket((BigDecimal) row[6]);
				}

				if (row[7] != null && !row[7].equals("")) {
					item.setRealAssetOfCus((BigDecimal) row[7]);
				}

				NAVList.add(item);
			}
		}

		return NAVList;
	}

	public static StringBuilder replaceAll(StringBuilder sb, Pattern pattern, String replacement) {
		Matcher m = pattern.matcher(sb);
		int start = 0;
		while (m.find(start)) {
			sb.replace(m.start(), m.end(), replacement);
			start = m.start() + replacement.length();
		}
		return sb;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<KeyNameValueDTO> getNavChartData(boolean isByMonth) {
		// TODO Auto-generated method stub
		String query = "SELECT " + "    CASE"
				+ "        WHEN last_of_month_flg = 0 THEN DATE_FORMAT(update_date, '%d/%m/%Y')"
				+ "        ELSE DATE_FORMAT(update_date, '%m/%Y')" + "    END time," + "    orginal_price nav" + " FROM"
				+ " asset_history" + " WHERE code = 'VIF_CCQ'";

		if (isByMonth) {
			query = query + " AND last_of_month_flg = 1";
		} else {
			query = query + " AND last_of_month_flg = 0 AND update_date >= DATE_SUB(NOW(),INTERVAL 31 DAY)";
		}

		List<Object[]> rows = entityManager.createNativeQuery(query).getResultList();
		List<KeyNameValueDTO> navLst = new ArrayList<KeyNameValueDTO>();
		if (rows != null && rows.size() > 0) {
			for (Object[] row : rows) {
				KeyNameValueDTO item = new KeyNameValueDTO();
				if (row[0] != null && !row[0].equals("")) {
					item.setKey(row[0].toString());
				}
				if (row[1] != null && !row[1].equals("")) {
					item.setValue((BigDecimal) row[1]);
				}
				navLst.add(item);
			}
		}

		return navLst;
	}

	@Override
	public List<Asset> getDebtDataByUsername(String username) {
		// TODO Auto-generated method stub
		List<Asset> resultLst = new ArrayList<>();
		List<Asset> tempLst;
		// get user by username
		User mUser = userDao.getUserByUserName(username);
		if (mUser != null) {
			if (mUser.getRole().equals(IContaints.ROLE.ROLE_ADMIN)) {
				// user is administrator => get all debt in asset
				tempLst = assetService.getAssetByGroupId(3);
			} else {
				// user is customer => get debt of this customer in cus_asset
				tempLst = getDebtAssetByCustomer(mUser.getCustomer().getId());
			}

			if (tempLst != null && tempLst.size() > 0) {
				for (Asset asset : tempLst) {
					AppParam itemAppParam = appParamService.getAppParamByKeyType(asset.getAssetCode(),
							IContaints.CONFIG.INTEREST_RATE);
					if (itemAppParam != null) {
						asset.setDescription(itemAppParam.getPropValue());
						resultLst.add(asset);
					} else {
						asset.setDescription("0");
						resultLst.add(asset);
					}
				}
			}

			if (resultLst != null && resultLst.size() > 0) {
				return resultLst;
			}
		}

		return null;
	}

	@SuppressWarnings("unchecked")
	private List<Asset> getDebtAssetByCustomer(Integer customerId) {
		String query = "SELECT a.code, a.name, ca.current_price, ca.amount"
				+ " from asset a, customer_asset ca where ca.customer_id =:customerId and a.id=ca.asset_id";

		List<Object[]> rows = entityManager.createNativeQuery(query).setParameter("customerId", customerId)
				.getResultList();
		List<Asset> debtLst = new ArrayList<Asset>();
		if (rows != null && rows.size() > 0) {
			for (Object[] row : rows) {
				Asset item = new Asset();
				if (row[0] != null && !row[0].equals("")) {
					item.setAssetCode(row[0].toString());
				}
				if (row[1] != null && !row[1].equals("")) {
					item.setAssetName(row[1].toString());
				}
				if (row[2] != null && !row[2].equals("")) {
					item.setCurrentPrice((BigDecimal) row[2]);
				}
				if (row[3] != null && !row[3].equals("")) {
					item.setAmount((BigDecimal) row[3]);
				}
				debtLst.add(item);
			}
		}

		return debtLst;
	}

}
