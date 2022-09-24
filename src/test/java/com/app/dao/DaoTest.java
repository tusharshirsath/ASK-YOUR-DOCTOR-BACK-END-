package com.app.dao;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.app.dto.LoginResponse;
import com.app.entity.modal.Appointment;
import com.app.entity.modal.BloodDonor;
import com.app.entity.modal.BloodGroup;
import com.app.entity.modal.Doctor;
import com.app.entity.modal.DoctorTimeTable;
import com.app.entity.modal.Gender;
import com.app.entity.modal.Patient;
import com.app.repository.BloodDonorRepository;
import com.app.repository.DoctorRepository;
import com.app.repository.PatientRepository;

@SpringBootTest
class DaoTest {

	@Autowired
	private PatientRepository patientRepo;

	@Autowired
	private DoctorRepository doctorRepo;

	@Autowired
	private com.app.service.intf.DoctorServiceIntf doctorService;
	
	@Autowired
	private com.app.service.intf.PatientServiceIntf patientService;

	@Autowired
	private com.app.service.intf.AppointmentServiceIntf appointmentService;

	@Autowired
	private com.app.service.intf.HomeServiceIntf homeService;
	
	@Autowired
	private BloodDonorRepository bloodDonorRepo;
	@Test
	void savePatient() {

		Patient p1 = new Patient("p3", "p3", "p3", "p3@gmail.com", "p3@123", LocalDate.parse("1995-11-01"), Gender.MALE, "48765", BloodGroup.A_POSITIVE, "Kalyan", "Mumbai", "MH"); 
		Patient p2 = new Patient("p4", "p4", "p4", "p4@gmail.com", "p4@123", LocalDate.parse("1995-11-01"), Gender.FEMALE, "48765", BloodGroup.B_POSITIVE, "Bhosari", "Pune", "MH"); 

		patientRepo.save(p1);
		patientRepo.save(p2);

		assertTrue(true);
	}

	@Test
	void TestGetSpecialization() {

		doctorRepo.getSpecializationsByCity("Pune").forEach(e -> System.out.println(e));
		assertTrue(true);
	}

	@Test
	void TestGetDoctorListBySpecialization() {

		doctorRepo.findAllBySpecializationAndCity("Dentist", "Pune").forEach(e -> System.out.println(e));
		assertTrue(true);
	}

	@Test
	void TestCreateTableTimeSlots() {
		DoctorTimeTable timeTable = new DoctorTimeTable(LocalDate.parse("2021-09-21"), LocalDate.parse("2021-09-30"), LocalTime.parse("07:00:00"), LocalTime.parse("15:00:00"), 30, LocalTime.parse("12:00:00"), new ArrayList<>(Arrays.asList("Monday")));
		//timeTable.openSlots();
		List<LocalDateTime> list = doctorService.createAvailableSlotsDetails(1l, timeTable);
		System.out.println(list);
		//System.out.println(g.getAvailableSlots());
		assertTrue(true);
	}

	

	@Test
	void getAllDoctorDetail() {
		List<Doctor> allDoctors = doctorService.getAllDoctors();
		allDoctors.forEach(System.out::println);
	}

	@Test
	void deleteDoctorTest() {
		System.out.println(doctorService.deleteDoctorById(3L));
		List<Doctor> allDoctors = doctorService.getAllDoctors();
		allDoctors.forEach(System.out::println);
	}


	/*@Test
	void saveDoctorTimeTable() {

//		DoctorTimeTable tt = new DoctorTimeTable(LocalTime.parse("07:00:00"), LocalTime.parse("15:00:00"), 30,
//				LocalTime.parse("13:00:00"), 30);
		AppointmentSlot apt = new AppointmentSlot(LocalDate.parse("2021-09-23"), LocalDate.parse("2021-09-30"), LocalTime.parse("08:00:00"), LocalTime.parse("13:00:00"), 20, LocalTime.parse("11:00:00"), 30, new ArrayList<>(Arrays.asList("Saturday", "Sunday")));
		 List<LocalDateTime> list = doctorService.createAvailableSlotsDetails(4l, apt);
		System.out.println(list);
		//System.out.println(g.getAvailableSlots());
		assertTrue(true);
	}*/

	
	@Test
	void authenticateUserTest() {
		LoginResponse authenticateUser = homeService.authenticateUser("sa@gmail.com", "Ak@123");
		System.out.println(authenticateUser);
	}
	
	
	  @Test void bookAppointment() { 
		 // LocalDateTime t1 = LocalDateTime.of(LocalDate.parse("2021-09-30"), LocalTime.parse("10:00:00"));
		  String t1 = "2021-09-30 10:00:00";
			 
		  List<LocalDateTime> list = appointmentService.bookAppointmentForPatient(1l, 3l, t1);
		  list.forEach(System.out::println);  
	  }	 
	  
	  @Test
	  void getAllPatientCurrApp() {
		  List<Appointment> pApp = appointmentService.getAllPatientCurrentAppoitments(1l);
		  System.out.println("************");
		  pApp.forEach(System.out::println);
	  }
	  
	  @Test
	  void getAllPatientAppHistory() {
		  List<Appointment> pApp = appointmentService.getAllPatientAppoitmentsHistory(1l);
		  System.out.println("************");
		  pApp.forEach(System.out::println);
	  }

	  @Test
	  void getAllCurrAppForDoctor() {
		  List<Appointment> pApp = appointmentService.getAllCurrentAppoitmentsForDoctor(2l);
		  System.out.println("************");
		  pApp.forEach(System.out::println);
	  }

	  @Test
	  void getAllAppHistoryForDoctor() {
		  List<Appointment> pApp = appointmentService.getAllAppoitmentsHistoryForDoctor(2l);
		  System.out.println("************");
		  pApp.forEach(System.out::println);
	  }

	  @Test
	  void getAllAppHistoryForOfPatientDoctor() {
		  List<Appointment> pApp = appointmentService.getPatientAppoitmentsHistoryForDoctor(3l, 1l);
		  System.out.println("************");
		  pApp.forEach(System.out::println);
	  }

	  @Test
	  void cancelAppointment() {
		  Long id = 1l;
		  System.out.println(appointmentService.cancelAppointment(id));
	  }
	  
	  @Test
	  void deletePatientTest() {
		 System.out.println(patientService.deletePatientById(2L));
	  }

	  @Test
	  void saveDonor() {
		  List<BloodDonor> bd = Arrays.asList(new BloodDonor("bd2", "bd2@gmail.com", "9809789898", BloodGroup.B_POSITIVE,"Pune", "MH"),
				  new BloodDonor("bd3", "bd3@gmail.com", "9809789898", BloodGroup.A_POSITIVE, "Pune", "MH"),
				  new BloodDonor("bd4", "bd4@gmail.com", "9809789898", BloodGroup.B_POSITIVE,  "Mumbai", "MH"),
				  new BloodDonor("bd5", "bd5@gmail.com", "9809789898", BloodGroup.A_NEGATIVE, "Mumbai", "MH"));
		  
		  System.out.println(bloodDonorRepo.saveAll(bd));
	  }
	  
	  @Test
	  void getAppointmentSlotsForDoctor() {
		  List<LocalDateTime> slots = appointmentService.getAllAppointmentSlots(3L);
		  System.out.println("SLOTS : "+slots);
	  }
	  
	  
	  
}	