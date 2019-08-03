package vif.online.chungkhoan.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import vif.online.chungkhoan.dao.GroupAssetDao;
import vif.online.chungkhoan.entities.GroupAsset;

@Transactional
@Repository
public class GroupAssetDaoImpl implements GroupAssetDao {
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public GroupAsset getGroupByCode(String groupCode) {
		// TODO Auto-generated method stub
		String hql = "FROM GroupAsset as g WHERE g.groupCode = :groupCode";
		@SuppressWarnings("unchecked")
		List<GroupAsset> lstResult = entityManager.createQuery(hql).setParameter("groupCode", groupCode).getResultList();
		if (lstResult != null && lstResult.size() > 0) {
			return lstResult.get(0);
		}
		return null;
	}

	@Override
	public List<GroupAsset> getAllGroupsAssets() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<GroupAsset> getAlls() {
		// TODO Auto-generated method stub
		String hql = "FROM GroupAsset as g WHERE g.activeFlg = 1 ORDER BY g.groupName asc";
		return (List<GroupAsset>) entityManager.createQuery(hql).getResultList();
	}

	@Override
	public GroupAsset getByGroupById(int groupId) {
		// TODO Auto-generated method stub
		return entityManager.find(GroupAsset.class, groupId);
	}
	
}
