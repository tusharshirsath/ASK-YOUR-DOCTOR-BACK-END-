package com.app.service.intf;

import java.util.List;

import com.app.dto.PatientDTO;
import com.app.entity.modal.Patient;

public interface PatientServiceIntf {
	
	//register new patient
	Patient savePatient(PatientDTO user);
	
	//delete patient
	String deletePatientById(Long patient_id);
	
	//getAll patients
	List<Patient> getAllPatients();

	//get specific patient
	Patient getPatientDetails(Long id);
	
	//authenticate patient
	//LoginResponse authenticatePatient(String email, String password);
	
	//cancel appointment [implemented in appointment services]
	//String cancelAppointment(Long patientId, Long appointmentId);
	
	//update specific patient
	Patient updatePatientDetails(PatientDTO patient, long id);
	
	
}
