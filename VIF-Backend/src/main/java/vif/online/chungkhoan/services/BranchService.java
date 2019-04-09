package vif.online.chungkhoan.services;

import java.util.List;

import vif.online.chungkhoan.entities.Branch;
import vif.online.chungkhoan.entities.Customer;
import vif.online.chungkhoan.entities.User;

public interface BranchService {

	List<Branch> getAllBranchs(); 

	Branch getBranchById(int id);

	Branch getBranchByCode(String code);

	boolean addBranch(Branch branch);

	void updateBranch(Branch branch);

	void deleteBranchByCode(String code);

	void deleteBranchById(Integer id);
	
	List<Branch> SearchBranchByCondition(String branchCode, Integer activeFlg, String branchName);

	int getRowCount(String branchCode, Integer activeFlg, String branchName);
}
