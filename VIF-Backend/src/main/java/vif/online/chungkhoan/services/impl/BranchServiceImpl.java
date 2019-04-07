package vif.online.chungkhoan.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vif.online.chungkhoan.dao.BranchDao;
import vif.online.chungkhoan.dao.CustomerDao;
import vif.online.chungkhoan.entities.Branch;
import vif.online.chungkhoan.services.BranchService;

@Service(value="branchService")
public class BranchServiceImpl implements BranchService{
	
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

	
}
