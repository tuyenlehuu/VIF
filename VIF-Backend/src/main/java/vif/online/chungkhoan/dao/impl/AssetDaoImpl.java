package vif.online.chungkhoan.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import vif.online.chungkhoan.dao.AssetDao;
import vif.online.chungkhoan.entities.Asset;
import vif.online.chungkhoan.entities.User;

@Transactional
@Repository
public class AssetDaoImpl implements AssetDao {
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

	@Override
	public boolean deleteAssetByCode(String code) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteAssetById(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Asset> SearchAssetByCondition(int page, int pageSize, String columnSortName, Boolean asc, String code,
			Integer activeFlg, int group_id, String branch_code) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Asset> getAlls() {
		// TODO Auto-generated method stub
		String hql = "FROM Asset as a WHERE a.activeFlg = 1 ORDER BY a.branchCode desc";
		return (List<Asset>) entityManager.createQuery(hql).getResultList();
	}

}
