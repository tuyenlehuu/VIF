package vif.online.chungkhoan.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vif.online.chungkhoan.entities.Cophieu;

@Repository
public interface CophieuRepository extends JpaRepository<Cophieu, Integer> {

}
