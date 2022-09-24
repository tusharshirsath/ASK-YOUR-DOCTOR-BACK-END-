package com.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.entity.modal.DoctorTimeTable;

public interface DoctorTimeTableRepository extends JpaRepository<DoctorTimeTable, Long> {

}
