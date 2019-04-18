package vif.online.chungkhoan.dao;

import java.util.List;

import vif.online.chungkhoan.entities.Branch;

public interface BranchDao {
	List<Branch> getAllBranchs();

	Branch getBranchById(int id);

	Branch getBranchByCode(String code);

	boolean addBranch(Branch branch);

	boolean branchExists(Branch branch);

	void updateBranch(Branch branch);

	void deleteBranchByCode(String code);

	void deleteBranchById(Integer id);

	List<Branch> SearchBranchByCondition(int page, int pageSize, String columnSortName, Boolean asc,String branchCode, Integer activeFlg, String branchName);

	int getRowCount(String branchCode, Integer activeFlg, String branchName);
}
