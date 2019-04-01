package vif.online.chungkhoan.dao.impl;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import vif.online.chungkhoan.dao.DashboardDao;
import vif.online.chungkhoan.entities.DashBoard;

@Transactional
@Repository
public class DashboardDaoImpl implements DashboardDao {

	@PersistenceContext
	private EntityManager entityManager;

	@SuppressWarnings("unchecked")
	@Override
	public DashBoard getData() {
		// TODO Auto-generated method stub
		String sql = "SELECT " + "	MAX(t.CASH) cash, " + "    MAX(t.CS) cs, " + "    MAX(t.SHARES) shares, "
				+ "    MAX(t.IT_RT) itRt, MAX(t.DEBT_LONG) debtLong, MAX(t.DEBT_SHORT) debtShort, "
				+ "    MAX(t.VIF_CCQ_PRICE) vifCCQPrice, MAX(t.VIF_CCQ) vifAmountCCQ, "
				+ " MAX(t.CASH)+MAX(t.CS)+MAX(t.SHARES)+MAX(t.IT_RT) totalAsset, MAX(t.DEBT_LONG)+MAX(t.DEBT_SHORT) totalDebt " 
				+ " FROM (SELECT "
				+ "  MAX(CASE WHEN code = 'CASH' THEN current_price END) CASH, "
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

}
