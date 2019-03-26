package vif.online.chungkhoan.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vif.online.chungkhoan.entities.Branch;

@Repository
public interface BranchRepository extends JpaRepository<Branch, Integer>  {

}
