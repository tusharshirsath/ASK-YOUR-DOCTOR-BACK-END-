package com.app.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.custome_exception.UserHandlingException;
import com.app.entity.modal.Appointment;
import com.app.entity.modal.Doctor;
import com.app.entity.modal.DoctorTimeTable;
import com.app.entity.modal.Patient;
import com.app.repository.AppointmentRepository;
import com.app.repository.DoctorRepository;
import com.app.repository.PatientRepository;
import com.app.service.intf.AppointmentServiceIntf;
import com.app.service.intf.DoctorServiceIntf;
import com.app.service.intf.PatientServiceIntf;

@Service
@Transactional
public class AppointmentServiceImpl implements AppointmentServiceIntf {

	private AppointmentRepository appointmentRepo;

	private PatientRepository patientRepo;

	private DoctorRepository doctorRepo;

	private PatientServiceIntf patientService;

	private DoctorServiceIntf doctorService;

	@Autowired
	public AppointmentServiceImpl(AppointmentRepository appointmentRepo, PatientRepository patientRepo,
			DoctorRepository doctorRepo, PatientServiceIntf patientService, DoctorServiceIntf doctorService) {

		this.appointmentRepo = appointmentRepo;
		this.patientRepo = patientRepo;
		this.doctorRepo = doctorRepo;
		this.patientService = patientService;
		this.doctorService = doctorService;

	}

	@Override
	public String cancelAppointment(Long appointmentId) {

		Appointment appointment = appointmentRepo.findById(appointmentId)
				.orElseThrow(() -> new UserHandlingException("appointment Id not found"));
		Doctor doctor = appointment.getDoctor();
		LocalDateTime appointmentTime = appointment.getAppointmentTime();
		doctor.getTimeSlot().bookAvailableSlot(appointmentTime);
		appointmentRepo.deleteById(appointmentId);
		return "Appointment cancelled successfully(for " + appointmentId + ")...!!!";
	}

	@Override
	public List<Appointment> getAllPatientCurrentAppoitments(Long patientId) {
		return appointmentRepo.findByPatientAndAppointmentTimeAfter(patientService.getPatientDetails(patientId),
				LocalDateTime.now());
	}

	@Override
	public List<Appointment> getAllPatientAppoitmentsHistory(Long patientId) {
		return appointmentRepo.findByPatientAndAppointmentTimeBeforeOrderByAppointmentTimeDesc(
				patientService.getPatientDetails(patientId), LocalDateTime.now());
	}

	@Override
	public List<Appointment> getAllCurrentAppoitmentsForDoctor(Long doctorId) {
		return appointmentRepo.findByDoctorAndAppointmentTimeAfter(doctorService.getDoctorDetails(doctorId),
				LocalDateTime.now());
	}

	@Override
	public List<Appointment> getPatientAppoitmentsHistoryForDoctor(Long doctorId, Long patientId) {
		return appointmentRepo.findByDoctorAndPatientAndAppointmentTimeBeforeOrderByAppointmentTimeDesc(
				doctorService.getDoctorDetails(doctorId), patientService.getPatientDetails(patientId),
				LocalDateTime.now());
	}

	@Override
	public List<Appointment> getAllAppoitmentsHistoryForDoctor(Long doctorId) {
		return appointmentRepo.findByDoctorAndAppointmentTimeBeforeOrderByAppointmentTimeDesc(
				doctorService.getDoctorDetails(doctorId), LocalDateTime.now());
	}

	@Override
	public List<LocalDateTime> bookAppointmentForPatient(Long doctorId, Long patientId, String stime) {

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		LocalDateTime time = LocalDateTime.parse(stime, formatter);

		Doctor doctor = doctorRepo.findById(doctorId)
				.orElseThrow(() -> new UserHandlingException("Doctor not found...!!!"));

		Patient patient = patientRepo.findById(patientId)
				.orElseThrow(() -> new UserHandlingException("Patient not found...!!!"));

		DoctorTimeTable timeTable = doctor.getTimeSlot();

		Appointment appointment = new Appointment(time, doctor, patient);
		appointmentRepo.save(appointment);

		List<LocalDateTime> availableSlotList = timeTable.bookAvailableSlot(time);

		return availableSlotList;
	}

	@Override
	public Patient getPatientByAppointmentId(Long appointmentId) {

		Appointment appointment = appointmentRepo.findById(appointmentId).get();
		Patient patient = appointment.getPatient();
		return patient;
	}

	@Override
	public List<LocalDateTime> getAllAppointmentSlots(Long doctorId) {

		Doctor doctor = doctorRepo.findById(doctorId).get();
		Map<LocalDateTime, Boolean> availableSlots = doctor.getTimeSlot().getAvailableSlots();
		List<LocalDateTime> list = new ArrayList<>();
		for (Map.Entry<LocalDateTime, Boolean> entry : availableSlots.entrySet()) {
			int currDate = LocalDate.now().getDayOfMonth();
			int currMonth = LocalDate.now().getMonthValue();
			int slotDate = entry.getKey().getDayOfMonth();
			int slotMonth = entry.getKey().getMonthValue();
			if (entry.getValue() == true && entry.getKey().isAfter(LocalDateTime.now()) && currDate == slotDate
					&& currMonth == slotMonth) { // send only list whose boolean value is true (not booked slots)
				list.add(entry.getKey());
			}
		}
		Collections.sort(list);

		return list;
	}

	@Override
	public Doctor getDoctorByAppointmentId(Long appointmentId) {

		Appointment appointment = appointmentRepo.findById(appointmentId)
				.orElseThrow(() -> new UserHandlingException("Invalid appointment id!!!"));
		return appointment.getDoctor();

	}
}