package vif.online.chungkhoan.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vif.online.chungkhoan.entities.InvestRequest;

@Repository
public interface InvestRequestRepository extends JpaRepository<InvestRequest, Integer> {

}
