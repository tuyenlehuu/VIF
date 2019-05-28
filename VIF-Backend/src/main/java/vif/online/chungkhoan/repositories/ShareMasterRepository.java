package vif.online.chungkhoan.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vif.online.chungkhoan.entities.ShareMaster;

@Repository
public interface ShareMasterRepository extends JpaRepository<ShareMaster, Integer> {

}


