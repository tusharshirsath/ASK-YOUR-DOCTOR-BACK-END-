package com.app.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.entity.modal.Appointment;
import com.app.service.intf.AppointmentServiceIntf;
import com.app.service.intf.DoctorServiceIntf;
import com.app.service.intf.EmailSenderServiceIntf;

@RestController
@RequestMapping("/appointment")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AppointmentController {

	// dependencies added in constructor by @Autowired
	AppointmentServiceIntf appointmentService;

	DoctorServiceIntf doctorService;

	EmailSenderServiceIntf emailSenderService;
	
	// constructor level autowiring
	@Autowired
	public AppointmentController(AppointmentServiceIntf appointmentService, DoctorServiceIntf doctorService, EmailSenderServiceIntf emailSenderService) {
		this.appointmentService = appointmentService;
		this.doctorService = doctorService;
		this.emailSenderService = emailSenderService;
	}

	@GetMapping("/patient/{appointmentId}")
	public ResponseEntity<?> getPatientByAppointmentId(@PathVariable Long appointmentId) {
		return ResponseEntity.ok(appointmentService.getPatientByAppointmentId(appointmentId));
	}

	@GetMapping("/specialization/{city}")
	public ResponseEntity<?> getSpecializationByCity(@PathVariable String city) {
		return ResponseEntity.ok(doctorService.getSpecializationsByCity(city));
	}

	@GetMapping("/search/{specialization}/{city}")
	public ResponseEntity<?> getDoctorsBySpecializationAndCity(@PathVariable String specialization,
			@PathVariable String city) {
		return ResponseEntity.ok(doctorService.getAllDoctorsBySpecializationAndCity(specialization, city));
	}

	@GetMapping("/currAppointmentP/{patientId}")
	public List<Appointment> getAllCurrentAppoinments(@PathVariable Long patientId) {
		return appointmentService.getAllPatientCurrentAppoitments(patientId);
	}

	@GetMapping("/appointementHistoryP/{patientId}")
	public List<Appointment> getAllAppoinmentsHistory(@PathVariable Long patientId) {
		return appointmentService.getAllPatientAppoitmentsHistory(patientId);
	}

	@GetMapping("/currAppointmentD/{doctorId}")
	public List<Appointment> getAllCurrentAppoinmentsForDoctor(@PathVariable Long doctorId) {
		return appointmentService.getAllCurrentAppoitmentsForDoctor(doctorId);
	}

	@GetMapping("/appointementHistoryD/{doctorId}/{patientId}")
	public List<Appointment> getAppoinmentsHistoryOfPatientForDoctor(@PathVariable Long doctorId,
			@PathVariable Long patientId) {
		return appointmentService.getPatientAppoitmentsHistoryForDoctor(doctorId, patientId);
	}

	@GetMapping("/appointementHistoryD/{doctorId}")
	public List<Appointment> getAllAppoinmentsHistoryForDoctor(@PathVariable Long doctorId) {
		return appointmentService.getAllAppoitmentsHistoryForDoctor(doctorId);
	}

	@GetMapping("/getAppointmentSlots/{doctorId}")
	public List<LocalDateTime> getAllAppointmentSlots(@PathVariable Long doctorId) {
		return appointmentService.getAllAppointmentSlots(doctorId);
	}

	@GetMapping("/bookAppointment/{doctorId}/{patientId}/{time}")
	public List<LocalDateTime> bookAppointmentForPatient(@PathVariable Long doctorId, @PathVariable Long patientId,
			@PathVariable String time) {
		emailSenderService.sendEmailOnAppointmentBooking(patientId,time);
		return appointmentService.bookAppointmentForPatient(doctorId, patientId, time);
	}

	@DeleteMapping("/cancelAppointment/{appointmentId}")
	public void cancelAppointment(@PathVariable Long appointmentId) {
		emailSenderService.sendEmailOnCancelAppointment(appointmentId);
		System.out.println(appointmentService.cancelAppointment(appointmentId));
	}
	
	@GetMapping("/doctor/{appointmentId}")
	public ResponseEntity<?> getDoctorByAppointmentId(@PathVariable Long appointmentId) {
		System.out.println("In ctrler...");
		return ResponseEntity.ok(appointmentService.getDoctorByAppointmentId(appointmentId));
	}
	
}