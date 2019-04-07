package vif.online.chungkhoan.dao.impl;

import java.sql.Connection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.naming.NamingException;
import javax.naming.Reference;
import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContexts;
import javax.persistence.PersistenceUnitUtil;
import javax.persistence.Query;
import javax.persistence.SynchronizationType;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.metamodel.Metamodel;

import org.hibernate.Cache;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionBuilder;
import org.hibernate.SessionFactory;
import org.hibernate.StatelessSession;
import org.hibernate.StatelessSessionBuilder;
import org.hibernate.TypeHelper;
import org.hibernate.boot.spi.SessionFactoryOptions;
import org.hibernate.engine.spi.FilterDefinition;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.metadata.CollectionMetadata;
import org.hibernate.stat.Statistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import vif.online.chungkhoan.dao.BranchDao;
import vif.online.chungkhoan.entities.Branch;
import vif.online.chungkhoan.entities.Customer;
import vif.online.chungkhoan.entities.User;


@Transactional
@Repository(value="branchDao")
public class BranchDaoImpl implements BranchDao{

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
		if(branch==null) {
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
		Branch branch= getBranchByCode(code);
		branch.setActiveFlg(0);
		entityManager.merge(branch);
	
	}

	@Override
	public void deleteBranchById(Integer id) {
		Branch branch=entityManager.find(Branch.class, id);
		branch.setActiveFlg(0);
		entityManager.merge(branch);
	}

}
