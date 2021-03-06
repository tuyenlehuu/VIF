package vif.online.chungkhoan.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vif.online.chungkhoan.dao.BranchDao;
import vif.online.chungkhoan.entities.Branch;
import vif.online.chungkhoan.services.BranchService;

@Service(value = "branchService")
public class BranchServiceImpl implements BranchService {

	@Autowired
	private BranchDao branchDao;

	@Override
	public List<Branch> getAllBranchs() {
		// TODO Auto-generated method stub
		return branchDao.getAllBranchs();
	}

	@Override
	public Branch getBranchById(int id) {
		// TODO Auto-generated method stub
		return branchDao.getBranchById(id);
	}

	@Override
	public Branch getBranchByCode(String code) {
		// TODO Auto-generated method stub
		return branchDao.getBranchByCode(code);
	}

	@Override
	public boolean addBranch(Branch branch) {
		if (branchDao.branchExists(branch)) {
			return false;
		}
		branchDao.addBranch(branch);
		return true;

	}

	@Override
	public void updateBranch(Branch branch) {

		branchDao.updateBranch(branch);
	}

	@Override
	public void deleteBranchByCode(String code) {
		branchDao.deleteBranchByCode(code);

	}

	@Override
	public void deleteBranchById(Integer id) {
		branchDao.deleteBranchById(id);

	}

	@Override
	public List<Branch> SearchBranchByCondition(int page, int pageSize, String columnSortName, Boolean asc,
			String branchCode, Integer activeFlg, String branchName) {
		// TODO Auto-generated method stub
		return branchDao.SearchBranchByCondition(page, pageSize, columnSortName, asc, branchCode, activeFlg,
				branchName);
	}

	@Override
	public int getRowCount(String branchCode, Integer activeFlg, String branchName) {
		// TODO Auto-generated method stub
		return branchDao.getRowCount(branchCode, activeFlg, branchName);
	}

}
