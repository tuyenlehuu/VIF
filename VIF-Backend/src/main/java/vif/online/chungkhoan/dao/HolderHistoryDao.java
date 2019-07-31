package vif.online.chungkhoan.dao;

import java.util.List;

import vif.online.chungkhoan.entities.HolderHistory;

public interface HolderHistoryDao {
	List<HolderHistory> getAllsHolder();
	boolean addHolders(HolderHistory holderHistory);
}
