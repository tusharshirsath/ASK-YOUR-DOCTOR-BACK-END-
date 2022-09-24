package com.app.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.entity.modal.BloodDonor;
import com.app.entity.modal.BloodGroup;
import com.app.repository.BloodDonorRepository;
import com.app.service.intf.BloodDonorIntf;

@Service
@Transactional
public class BloodDonorImpl implements BloodDonorIntf{

	@Autowired
	private BloodDonorRepository bloodDonorRepo;
	
	@Override
	public BloodDonor saveBloodDonor(BloodDonor donor) {
		return  bloodDonorRepo.save(donor);
	}

	@Override
	public List<BloodDonor> getAllBloodDonors() {
		return bloodDonorRepo.findAll();
	}

	@Override
	public List<BloodDonor> getAllBloodDonorsByCityAndBloodGroup(String city, String bloodGroup) {
		BloodGroup gp = BloodGroup.valueOf(bloodGroup);
		return bloodDonorRepo.findByCityAndBloodGroup(city, gp);
	}
	
}
