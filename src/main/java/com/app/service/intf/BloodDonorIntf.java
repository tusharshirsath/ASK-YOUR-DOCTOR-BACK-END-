package com.app.service.intf;

import java.util.List;

import com.app.entity.modal.BloodDonor;

public interface BloodDonorIntf {
	
	//Save new donor
	BloodDonor saveBloodDonor(BloodDonor donor);
	
	//get list of all donors
	List<BloodDonor> getAllBloodDonors();
	
	//get all donors by city and blood group
	List<BloodDonor> getAllBloodDonorsByCityAndBloodGroup(String city, String bloodGroup);
	


}
