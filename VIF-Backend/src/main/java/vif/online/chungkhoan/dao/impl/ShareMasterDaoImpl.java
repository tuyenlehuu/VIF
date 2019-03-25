package vif.online.chungkhoan.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import org.springframework.stereotype.Repository;

import vif.online.chungkhoan.dao.ShareMasterDao;
import vif.online.chungkhoan.entities.ShareMaster;

@Transactional
@Repository
public class ShareMasterDaoImpl implements ShareMasterDao {

	@PersistenceContext
	private EntityManager entityManager;

	@SuppressWarnings("unchecked")
	@Override
	public List<ShareMaster> getAllCophieus() {
		// TODO Auto-generated method stub
		String hql = "FROM ShareMaster as c ORDER BY c.cpCode asc";
		return (List<ShareMaster>) entityManager.createQuery(hql).getResultList();
	}

	@Override
	public ShareMaster getCophieuById(int cophieuId) {
		// TODO Auto-generated method stub
		return entityManager.find(ShareMaster.class, cophieuId);
	}

	@Override
	public void addCophieu(ShareMaster cophieu) {
		// TODO Auto-generated method stub
		entityManager.persist(cophieu);
	}

	@Override
	public void updateCophieu(ShareMaster Cophieu) {
		// TODO Auto-generated method stub
		entityManager.flush();
	}

	@Override
	public void deleteCophieu(String cophieuCode) {
		// TODO Auto-generated method stub
		entityManager.remove(getCophieuByCode(cophieuCode));
	}

	@Override
	public boolean cophieuExists(ShareMaster cophieu) {
		// TODO Auto-generated method stub
		String hql = "FROM ShareMaster as c WHERE c.cpCode = :cophieuCode";
		int count = entityManager.createQuery(hql).setParameter("cophieuCode", cophieu.getCpCode()).getResultList()
				.size();
		return count > 0 ? true : false;
	}

	@Override
	public ShareMaster getCophieuByCode(String code) {
		// TODO Auto-generated method stub
		String hql = "FROM ShareMaster as c WHERE c.cpCode = :mcode";
		@SuppressWarnings("unchecked")
		List<ShareMaster> lstResult = entityManager.createQuery(hql).setParameter("mcode", code).getResultList();
		if (lstResult != null && lstResult.size() > 0) {
			return lstResult.get(0);
		}
		return null;
	}

}
