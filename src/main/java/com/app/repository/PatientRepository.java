package com.app.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.entity.modal.Admin;
import com.app.entity.modal.Patient;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

	//authenticate patient
	Optional<Patient> findByEmailAndPassword(String email, String password);

	Optional<Patient> findByEmail(String email);
}
