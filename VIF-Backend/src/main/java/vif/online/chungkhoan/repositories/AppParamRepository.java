package vif.online.chungkhoan.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vif.online.chungkhoan.entities.AppParam;

@Repository
public interface AppParamRepository extends JpaRepository<AppParam, Integer>{

}
