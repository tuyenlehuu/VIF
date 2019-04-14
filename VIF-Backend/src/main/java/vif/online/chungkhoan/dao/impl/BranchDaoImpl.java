package vif.online.chungkhoan.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import vif.online.chungkhoan.dao.BranchDao;
import vif.online.chungkhoan.entities.Branch;

@Transactional
@Repository(value = "branchDao")
public class BranchDaoImpl implements BranchDao {

	@PersistenceContext
	private EntityManager entityManager;

	@SuppressWarnings("unchecked")

	@Override
	public List<Branch> getAllBranchs() {
		// TODO Auto-generated method stub
		String hql = "FROM Branch as b WHERE b.activeFlg = 1";
		return (List<Branch>) entityManager.createQuery(hql).getResultList();
	}

	@Override
	public Branch getBranchById(int id) {
		return entityManager.find(Branch.class, id);

	}

	@Override
	public Branch getBranchByCode(String code) {

		String hql = "FROM Branch as b WHERE b.branchCode = :branchCode and b.activeFlg=1";
		@SuppressWarnings("unchecked")
		List<Branch> lstResult = entityManager.createQuery(hql).setParameter("branchCode", code).getResultList();
		if (lstResult != null && lstResult.size() > 0) {
			return lstResult.get(0);
		}
		return null;
	}

	@Override
	public boolean addBranch(Branch branch) {
		if (branch == null) {
			return false;
		}
		entityManager.persist(branch);
		return true;
	}

	@Override
	public void updateBranch(Branch branch) {

		entityManager.merge(branch);

	}

	@Override
	public void deleteBranchByCode(String code) {
		Branch branch = getBranchByCode(code);
		branch.setActiveFlg(0);
		entityManager.merge(branch);

	}

	@Override
	public void deleteBranchById(Integer id) {
		Branch branch = entityManager.find(Branch.class, id);
		branch.setActiveFlg(0);
		entityManager.merge(branch);
	}

	@Override
	public List<Branch> SearchBranchByCondition(int page, int pageSize, String columnSortName, Boolean asc,
			String branchCode, Integer activeFlg, String branchName) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Object> criteriaQuery = criteriaBuilder.createQuery();
		Root<Branch> from = criteriaQuery.from(Branch.class);

		CriteriaQuery<Object> select = criteriaQuery.select(from);

		List<Predicate> predicates = new ArrayList<Predicate>();

		if (branchCode != null && !branchCode.equals("")) {

			predicates.add(criteriaBuilder.like(from.get("branchCode"), "%" + branchCode + "%"));

		}

		if (activeFlg != null) {
			predicates.add(criteriaBuilder.equal(from.get("activeFlg"), activeFlg));
		}

		if (branchName != null && !branchName.equals("")) {
			predicates.add(criteriaBuilder.like(from.get("branchName"), "%" + branchName + "%"));
		}

		select.select(from).where(predicates.toArray(new Predicate[] {}));

		Query query = entityManager.createQuery(criteriaQuery);

		List<Branch> lstResult = query.getResultList();

		return lstResult;
	}

	@Override
	public int getRowCount(String branchCode, Integer activeFlg, String branchName) {
		// TODO Auto-generated method stub
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Object> criteriaQuery = criteriaBuilder.createQuery();
		Root<Branch> from = criteriaQuery.from(Branch.class);

		CriteriaQuery<Object> select = criteriaQuery.select(from);
		List<Predicate> predicates = new ArrayList<Predicate>();

		if(branchCode != null && !branchCode.equals("")) {

			predicates.add(criteriaBuilder.like(from.get("branchCode"), "%" + branchCode + "%"));
		}

		if (activeFlg != null) {
			predicates.add(criteriaBuilder.equal(from.get("activeFlg"), activeFlg));
		}

		if (branchName != null && !branchName.equals("")) {
			predicates.add(criteriaBuilder.like(from.get("branchName"), "%" + branchName + "%"));
		}

		select.select(from).where(predicates.toArray(new Predicate[] {}));

		Query query = entityManager.createQuery(select);

		List<Branch> lstResult = query.getResultList();
		return lstResult.size();
	}

	@Override
	public boolean branchExists(Branch branch) {
		if (getBranchByCode(branch.getBranchCode()) != null)
			return true;
		else
			return false;
	}

}
