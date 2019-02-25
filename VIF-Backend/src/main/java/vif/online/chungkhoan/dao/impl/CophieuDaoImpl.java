package vif.online.chungkhoan.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import org.springframework.stereotype.Repository;

import vif.online.chungkhoan.dao.CophieuDao;
import vif.online.chungkhoan.entities.Cophieu;

@Transactional
@Repository
public class CophieuDaoImpl implements CophieuDao {

	@PersistenceContext
	private EntityManager entityManager;

	@SuppressWarnings("unchecked")
	@Override
	public List<Cophieu> getAllCophieus() {
		// TODO Auto-generated method stub
		String hql = "FROM Cophieu as c ORDER BY c.cpCode asc";
		return (List<Cophieu>) entityManager.createQuery(hql).getResultList();
	}

	@Override
	public Cophieu getCophieuById(int cophieuId) {
		// TODO Auto-generated method stub
		return entityManager.find(Cophieu.class, cophieuId);
	}

	@Override
	public void addCophieu(Cophieu cophieu) {
		// TODO Auto-generated method stub
		entityManager.persist(cophieu);
	}

	@Override
	public void updateCophieu(Cophieu Cophieu) {
		// TODO Auto-generated method stub
		entityManager.flush();
	}

	@Override
	public void deleteCophieu(String cophieuCode) {
		// TODO Auto-generated method stub
		entityManager.remove(getCophieuByCode(cophieuCode));
	}

	@Override
	public boolean cophieuExists(Cophieu cophieu) {
		// TODO Auto-generated method stub
		String hql = "FROM Cophieu as c WHERE c.cpCode = :cophieuCode";
		int count = entityManager.createQuery(hql).setParameter("cophieuCode", cophieu.getCpCode()).getResultList()
				.size();
		return count > 0 ? true : false;
	}

	@Override
	public Cophieu getCophieuByCode(String code) {
		// TODO Auto-generated method stub
		String hql = "FROM Cophieu as c WHERE c.cpCode = :mcode";
		@SuppressWarnings("unchecked")
		List<Cophieu> lstResult = entityManager.createQuery(hql).setParameter("mcode", code).getResultList();
		if (lstResult != null && lstResult.size() > 0) {
			return lstResult.get(0);
		}
		return null;
	}

}
