package com.app.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.dto.DoctorDTO;
import com.app.entity.modal.BloodDonor;
import com.app.entity.modal.Doctor;
import com.app.entity.modal.Patient;
import com.app.service.intf.BloodDonorIntf;
import com.app.service.intf.DoctorServiceIntf;
import com.app.service.intf.PatientServiceIntf;

@RestController
@RequestMapping("/admin")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AdminController {

	// dependencies added in constructor by @Autowired
	private DoctorServiceIntf doctorService;

	private PatientServiceIntf patientService;

	private BloodDonorIntf bloodDonorService;

	@Autowired // constructor level autowiring
	public AdminController(DoctorServiceIntf doctorService, PatientServiceIntf patientService,
			BloodDonorIntf bloodDonorService) {
		this.doctorService = doctorService;
		this.bloodDonorService = bloodDonorService;
		this.patientService = patientService;
	}

	@PostMapping("/doctorSignUp")
	public ResponseEntity<?> saveDoctor(@RequestBody @Valid DoctorDTO doctor) {
		System.out.println("Doc DTO from ctrler : "+doctor);
		
		Doctor d = doctorService.saveDoctor(doctor);
		if(d == null) {
			System.out.println("BAD REQ IF BLOCK");
			return ResponseEntity.badRequest().body(null);
		}
		return new ResponseEntity<>(d, HttpStatus.CREATED);
	}

	@GetMapping("/getAllDoctors")
	public List<Doctor> getAllDoctorDetails() {
		return doctorService.getAllDoctors();
	}

	@DeleteMapping("/removeDoctor/{doctorId}")
	public String deleteDoctor(@PathVariable Long doctorId) {
		return doctorService.deleteDoctorById(doctorId);
	}

	@GetMapping("/getAllPatients")
	public List<Patient> getAllPatientDetails() {
		return patientService.getAllPatients();
	}

	@DeleteMapping("/removePatient/{patientId}")
	public String deletePatient(@PathVariable Long patientId) {
		return patientService.deletePatientById(patientId);
	}

	@PostMapping("/bloodDonor")
	public ResponseEntity<?> saveBloodDonor(@RequestBody @Valid BloodDonor donor) {
		return new ResponseEntity<>(bloodDonorService.saveBloodDonor(donor), HttpStatus.CREATED);
	}

	@GetMapping("/searchDonors")
	public List<BloodDonor> getAllBloodDonors() {
		return bloodDonorService.getAllBloodDonors();
	}

}
