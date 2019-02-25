package vif.online.chungkhoan.services;

import java.util.List;

import vif.online.chungkhoan.entities.Cophieu;

public interface CophieuService {
	List<Cophieu> getAllCophieu();
	Cophieu getCophieuById(int id);
	Cophieu getCophieuByCode(String code);
    boolean addCophieu(Cophieu cophieu);
    void updateCophieu(Cophieu cophieu);
    void deleteCophieu(String cophieuCode);
}
