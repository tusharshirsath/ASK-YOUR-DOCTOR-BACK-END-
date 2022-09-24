package com.app.service.impl;

import static com.app.util.UtilityClass.getNullPropertyNames;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.app.custome_exception.UserHandlingException;
import com.app.dto.PatientDTO;
import com.app.entity.modal.Admin;
import com.app.entity.modal.Doctor;
import com.app.entity.modal.Patient;
import com.app.repository.AdminRepository;
import com.app.repository.AppointmentRepository;
import com.app.repository.DoctorRepository;
import com.app.repository.PatientRepository;
import com.app.service.intf.PatientServiceIntf;

@Service
@Transactional
public class PatientServiceImpl implements PatientServiceIntf {

	private PatientRepository patientRepo;

	private AdminRepository adminRepo;

	private DoctorRepository doctorRepo;

	private AppointmentRepository appointmentRepo;

	private DoctorServiceImpl doctorService;

	private PasswordEncoder passwordEncoder;

	@Autowired
	public PatientServiceImpl(PatientRepository patientRepo, AppointmentRepository appointmentRepo,
			PasswordEncoder passwordEncoder, DoctorServiceImpl doctorService, AdminRepository adminRepo,
			DoctorRepository doctorRepo) {

		this.patientRepo = patientRepo;
		this.appointmentRepo = appointmentRepo;
		this.passwordEncoder = passwordEncoder;
		this.doctorService = doctorService;
		this.adminRepo = adminRepo;
		this.doctorRepo = doctorRepo;

	}

	public Patient savePatient(PatientDTO patient) {

		String email = patient.getEmail();
		try {
			Doctor doctor = doctorRepo.findByEmail(email).get();
			return null;
		} catch (Exception e) {
			System.out.println("DoctorErr : " + e);
		}

		try {
			Admin admin = adminRepo.findByEmail(email).get();
			return null;
		} catch (Exception e) {
			System.out.println("AdminErr : " + e);
		}

		Patient newPatient = Patient.createPatient(patient);
		newPatient.setPassword(passwordEncoder.encode(newPatient.getPassword()));
		return patientRepo.save(newPatient);
	}

	@Override
	public String deletePatientById(Long patient_id) {
		List<Long> appoitments = appointmentRepo.getAppointmentIdListForPatient(patient_id);
		appoitments.forEach(System.out::println);
		Long appointmentId = null;
		for (int i = 0; i < appoitments.size(); i++) {
			appointmentId = appoitments.get(0);
			doctorService.makeSlotsAvailable(appointmentId);
		}

		patientRepo.deleteById(patient_id);
		return "Successfully Deleted Patient with id : " + patient_id;
	}

	@Override
	public List<Patient> getAllPatients() {
		return patientRepo.findAll();
	}

	@Override
	public Patient getPatientDetails(Long id) {
		return patientRepo.findById(id).orElseThrow(() -> new UserHandlingException("Invalid patient ID..."));
	}

	@Override
	public Patient updatePatientDetails(PatientDTO detachedPatient, long id) {
		Patient p = patientRepo.findById(id).orElseThrow(() -> new UserHandlingException("Invalid Patient id!!!!"));
		Patient patient = Patient.createPatient(detachedPatient);
		patient.setId(id);
		patient.setPassword(p.getPassword());
		BeanUtils.copyProperties(id, patient, getNullPropertyNames(patient));

		return patientRepo.save(patient);

	}

}
