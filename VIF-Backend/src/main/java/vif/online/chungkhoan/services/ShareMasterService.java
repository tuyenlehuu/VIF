package vif.online.chungkhoan.services;

import java.util.List;

import vif.online.chungkhoan.entities.ShareMaster;

public interface ShareMasterService {
	List<ShareMaster> getAllCophieu();
	ShareMaster getCophieuById(int id);
	ShareMaster getCophieuByCode(String code);
    boolean addCophieu(ShareMaster cophieu);
    void updateCophieu(ShareMaster cophieu);
    void deleteCophieu(String cophieuCode);
}
