package com.bimetri.products.registration.school.dao.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bimetri.products.registration.school.bean.model.Student;

public interface StudentRepository extends JpaRepository<Student, Integer> {

}
