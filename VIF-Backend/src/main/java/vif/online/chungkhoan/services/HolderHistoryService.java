package vif.online.chungkhoan.services;

import java.util.List;

import org.springframework.stereotype.Service;

import vif.online.chungkhoan.entities.HolderHistory;

@Service
public interface HolderHistoryService {
	List<HolderHistory> getAllsHolder();
	boolean addHolder(HolderHistory holderHistory);
}
