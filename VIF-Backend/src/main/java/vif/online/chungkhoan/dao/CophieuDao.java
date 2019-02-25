package vif.online.chungkhoan.dao;

import java.util.List;

import vif.online.chungkhoan.entities.Cophieu;

public interface CophieuDao {
	List<Cophieu> getAllCophieus();
	Cophieu getCophieuById(int CophieuId);
    void addCophieu(Cophieu Cophieu);
    void updateCophieu(Cophieu Cophieu);
    void deleteCophieu(String cophieuCode);
    boolean cophieuExists(Cophieu Cophieu);
	Cophieu getCophieuByCode(String code);
}
