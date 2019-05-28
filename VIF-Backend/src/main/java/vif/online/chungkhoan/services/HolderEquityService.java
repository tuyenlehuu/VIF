package vif.online.chungkhoan.services;

import java.util.List;

import vif.online.chungkhoan.entities.HolderEquity;

public interface HolderEquityService {
	List<HolderEquity> getAllsHolder();

	HolderEquity getHolderbyId(Integer id);

//	HolderEquity getHolderbyName(String fullName);

	boolean addHolder(HolderEquity holderEquity);

//	boolean holderExists(HolderEquity holderEquity);

	void updateHolder(HolderEquity holderEquity);

	void deleteHolderById(Integer id);

//	void deleteHolderByName(String fullName);

	List<HolderEquity> SearchHolderByCondition();

	int getRowCount();
}
