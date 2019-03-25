package vif.online.chungkhoan.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import vif.online.chungkhoan.dao.AssetDao;
import vif.online.chungkhoan.entities.Asset;

@Transactional
@Repository
public class AssetDaoImpl implements AssetDao{
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public boolean updateAsset(Asset asset) {
		// TODO Auto-generated method stub
		entityManager.merge(asset);
		return true;
	}

	@Override
	public Asset getAssetByCode(String assetCode) {
		// TODO Auto-generated method stub
		String hql = "FROM Asset as a WHERE a.assetCode = :assetCode";
		@SuppressWarnings("unchecked")
		List<Asset> lstResult = entityManager.createQuery(hql).setParameter("assetCode", assetCode).getResultList();
		if (lstResult != null && lstResult.size() > 0) {
			return lstResult.get(0);
		}
		return null;
	}

	@Override
	public boolean addAsset(Asset asset) {
		// TODO Auto-generated method stub
		entityManager.persist(asset);
		return true;
	}
	
}
