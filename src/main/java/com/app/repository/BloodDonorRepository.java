package com.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.entity.modal.BloodDonor;
import com.app.entity.modal.BloodGroup;

@Repository
public interface BloodDonorRepository extends JpaRepository<BloodDonor, Long>{
	
	List<BloodDonor> findByCityAndBloodGroup(String city, BloodGroup bloodGroup);
}
