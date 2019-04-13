package vif.online.chungkhoan.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vif.online.chungkhoan.entities.TransactionHistory;
@Repository
public interface TransactionRepository extends JpaRepository<TransactionHistory, Integer> {

}
