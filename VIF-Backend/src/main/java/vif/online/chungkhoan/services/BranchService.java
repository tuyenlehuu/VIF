package vif.online.chungkhoan.services;

import java.util.List;

import vif.online.chungkhoan.entities.Branch;
import vif.online.chungkhoan.entities.Customer;

public interface BranchService {

	List<Branch> getAllBranchs(); 

	Branch getBranchById(int id);

	Branch getBranchByCode(String code);

	boolean addBranch(Branch branch);

	void updateBranch(Branch branch);

	void deleteBranchByCode(String code);

	void deleteBranchById(Integer id);
}
