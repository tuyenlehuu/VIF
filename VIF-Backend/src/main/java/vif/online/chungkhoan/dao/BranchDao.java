package vif.online.chungkhoan.dao;

import java.util.List;

import vif.online.chungkhoan.entities.Branch;


public interface BranchDao {
	List<Branch> getAllBranchs();

	Branch getBranchById(int id);

	Branch getBranchByCode(String code);

	boolean addBranch(Branch branch);

	void updateBranch(int id,Branch branch);

	void deleteBranchByCode(String code);

	void deleteBranchById(Integer id);
}
