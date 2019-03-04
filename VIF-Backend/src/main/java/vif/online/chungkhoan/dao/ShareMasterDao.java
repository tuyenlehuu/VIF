package vif.online.chungkhoan.dao;

import java.util.List;

import vif.online.chungkhoan.entities.ShareMaster;

public interface ShareMasterDao {
	List<ShareMaster> getAllCophieus();
	ShareMaster getCophieuById(int CophieuId);
    void addCophieu(ShareMaster Cophieu);
    void updateCophieu(ShareMaster Cophieu);
    void deleteCophieu(String cophieuCode);
    boolean cophieuExists(ShareMaster Cophieu);
	ShareMaster getCophieuByCode(String code);
}
