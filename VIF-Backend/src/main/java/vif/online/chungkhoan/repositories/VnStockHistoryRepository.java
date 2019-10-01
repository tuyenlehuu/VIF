package vif.online.chungkhoan.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vif.online.chungkhoan.entities.VnStockHistory;

@Repository
public interface VnStockHistoryRepository extends JpaRepository<VnStockHistory, Integer>{

}
