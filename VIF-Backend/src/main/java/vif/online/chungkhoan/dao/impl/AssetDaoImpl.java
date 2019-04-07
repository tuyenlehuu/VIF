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
		Asset mAsset = entityManager.find(Asset.class, asset.getId());
		if (mAsset != null) {
			asset.setAssetsHistory(mAsset.getAssetsHistory());
			asset.setAssetsTransaction(mAsset.getAssetsTransaction());
			entityManager.merge(asset);
			return true;
		}
		return false;
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
		Asset mAsset = getAssetByCode(code);
		mAsset.setActiveFlg(0);
		entityManager.merge(mAsset);
		return true;
	}

	@Override
	public boolean deleteAssetById(int id) {
		// TODO Auto-generated method stub
		Asset mAsset = entityManager.find(Asset.class, id);
		if (mAsset != null) {
			mAsset.setActiveFlg(0);
			entityManager.merge(mAsset);
			return true;
		}
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

	@Override
	public Asset getByAssetId(int assetId) {
		// TODO Auto-generated method stub
		return entityManager.find(Asset.class, assetId);
	}

	public List<Asset> getAllShares() {
		// TODO Auto-generated method stub
		String hql = "FROM Asset as a WHERE a.activeFlg = 1 AND a.groupAsset = 2 ORDER BY a.branchCode desc";
		return (List<Asset>) entityManager.createQuery(hql).getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Asset> getOtherAssetNotShares() {
		// TODO Auto-generated method stub
		String hql = "FROM Asset as a WHERE a.activeFlg = 1 AND a.groupAsset <> 2 ORDER BY a.branchCode desc";
		return (List<Asset>) entityManager.createQuery(hql).getResultList();
	}

	@Override
	public boolean isExists(Asset asset) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		String hql = "FROM Asset as a WHERE a.activeFlg = 1 AND a.assetCode = :assetCode";
		return entityManager.createQuery(hql).setParameter("assetCode", asset.getAssetCode()).getResultList().size() > 0
				? true
				: false;
	}

	@Override
	public Asset getAssetById(Integer id) {
		// TODO Auto-generated method stub
		Asset asset = entityManager.find(Asset.class, id);
		if(asset != null) {
			return asset;
		}
		return null;
	}

}
