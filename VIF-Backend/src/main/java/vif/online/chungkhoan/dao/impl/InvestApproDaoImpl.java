package vif.online.chungkhoan.dao.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import vif.online.chungkhoan.dao.InvestApproDao;
import vif.online.chungkhoan.entities.Customer;
import vif.online.chungkhoan.entities.InvestRequest;
import vif.online.chungkhoan.helper.ApiResponse;
import vif.online.chungkhoan.helper.BuySellDTO;
import vif.online.chungkhoan.services.InvestorTransService;

@Transactional
@Repository
public class InvestApproDaoImpl implements InvestApproDao {

	@Autowired
	private InvestorTransService investorTransService;

	

	@PersistenceContext
	private EntityManager entityManager;
	SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");

	@SuppressWarnings("unchecked")
	@Override
	public List<InvestRequest> getAllInvestRequest() {
		// TODO Auto-generated method stub
		String hql = "FROM InvestRequest as i";
		return (List<InvestRequest>) entityManager.createQuery(hql).getResultList();
	}

	@Override
	public List<InvestRequest> SearchInvestRequestByCondition(int page, int pageSize, Boolean asc,
			Integer typeOfRequest, Integer typeOfInvest, String fromDate, String toDate, Integer status) {
		// TODO Auto-generated method stub
		try {
			CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
			CriteriaQuery<Object> criteriaQuery = criteriaBuilder.createQuery();
			Root<InvestRequest> from = criteriaQuery.from(InvestRequest.class);

			CriteriaQuery<Object> select = criteriaQuery.select(from);

			List<Predicate> predicates = new ArrayList<Predicate>();

			if (typeOfRequest != null) {
				predicates.add(criteriaBuilder.equal(from.get("typeOfRequest"), typeOfRequest));
			}

			if (typeOfInvest != null) {
				predicates.add(criteriaBuilder.equal(from.get("typeOfInvest"), typeOfInvest));
			}

			if (fromDate != null && !fromDate.equals("")) {
				Date fDate = formatter.parse(fromDate);
				predicates.add(criteriaBuilder.greaterThanOrEqualTo(from.get("createDate"), fDate));
			}

			if (toDate != null && !toDate.equals("")) {
				Date tDate = formatter.parse(toDate);
				predicates.add(criteriaBuilder.lessThanOrEqualTo(from.get("createDate"), tDate));
			}

			if (status != null) {
				predicates.add(criteriaBuilder.equal(from.get("status"), status));
			}

			select.select(from).where(predicates.toArray(new Predicate[] {}));

			Query query = entityManager.createQuery(criteriaQuery);
			if (page >= 0 && pageSize >= 0) {
				query.setFirstResult((page - 1) * pageSize);
				query.setMaxResults(pageSize);
			}
			@SuppressWarnings("unchecked")
			List<InvestRequest> lstResult = query.getResultList();

			return lstResult;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public int getRowCount(Integer typeOfRequest, Integer typeOfInvest, String fromDate, String toDate,
			Integer status) {
		// TODO Auto-generated method stub
		try {
			CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
			CriteriaQuery<Object> criteriaQuery = criteriaBuilder.createQuery();
			Root<InvestRequest> from = criteriaQuery.from(InvestRequest.class);

			CriteriaQuery<Object> select = criteriaQuery.select(from);
			List<Predicate> predicates = new ArrayList<Predicate>();

			if (typeOfRequest != null) {
				predicates.add(criteriaBuilder.equal(from.get("typeOfRequest"), typeOfRequest));
			}

			if (typeOfInvest != null) {
				predicates.add(criteriaBuilder.equal(from.get("typeOfInvest"), typeOfInvest));
			}

			if (fromDate != null && !fromDate.equals("")) {
				Date fDate = formatter.parse(fromDate);
				predicates.add(criteriaBuilder.greaterThanOrEqualTo(from.get("createDate"), fDate));
			}

			if (toDate != null && !toDate.equals("")) {
				Date tDate = formatter.parse(toDate);
				predicates.add(criteriaBuilder.lessThanOrEqualTo(from.get("createDate"), tDate));
			}

			if (status != null) {
				predicates.add(criteriaBuilder.equal(from.get("status"), status));
			}

			select.select(from).where(predicates.toArray(new Predicate[] {}));

			Query query = entityManager.createQuery(select);

			@SuppressWarnings("unchecked")
			List<InvestRequest> lstResult = query.getResultList();
			return lstResult.size();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public void reject(Integer id) {
		// TODO Auto-generated method stub
		InvestRequest investRequest = entityManager.find(InvestRequest.class, id);
		investRequest.setStatus(3);
		entityManager.merge(investRequest);
	}

	// cai accpet nay nay
	@Override
	public void accept(InvestRequest investRequest) {
		// TODO Auto-generated method stub
		BuySellDTO buyObject = new BuySellDTO();
		Customer cus = investRequest.getCustomer();
		int customerId = cus.getId();
		BigDecimal money = investRequest.getMoney();
		BigDecimal priceCCQ = investRequest.getPrice();
		BigDecimal amountCCQ = investRequest.getAmount();
		ApiResponse api;
		
		buyObject.setCustomerId(cus.getId());
		buyObject.setAmountCCQ(investRequest.getAmount());
		buyObject.setMoney(investRequest.getMoney());
		buyObject.setPriceCCQ(investRequest.getPrice());
		if (investRequest.getTypeOfRequest().equals(1) && investRequest.getTypeOfInvest().equals(1)) {
			api = investorTransService.buyCCQ(customerId, money, priceCCQ);
		} 
		if (investRequest.getTypeOfRequest().equals(2) && investRequest.getTypeOfInvest().equals(1)) {
			api = investorTransService.sellCCQ(customerId, amountCCQ, priceCCQ);
		}
		if (investRequest.getTypeOfRequest().equals(1) && investRequest.getTypeOfInvest().equals(2)) {
			api = investorTransService.buyEnsureCCQ(buyObject);
		}
		if (investRequest.getTypeOfRequest().equals(2) && investRequest.getTypeOfInvest().equals(2)) {
			api = investorTransService.sellEnsureCCQ(buyObject);
		}
		
		entityManager.merge(investRequest);

	}

}
