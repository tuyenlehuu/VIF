package vif.online.chungkhoan.dao;

import java.util.List;

import javax.xml.ws.Holder;

import vif.online.chungkhoan.entities.HolderEquity;

public interface HolderEquityDao {
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
