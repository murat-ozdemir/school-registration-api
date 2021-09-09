package com.bimetri.products.registration.school.dao.repo;

import org.springframework.data.repository.CrudRepository;

import com.bimetri.products.registration.school.bean.model.Student;

public interface StudentRepository extends CrudRepository<Student, Integer> {

}
