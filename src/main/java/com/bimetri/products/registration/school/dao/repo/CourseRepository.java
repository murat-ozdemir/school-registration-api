package com.bimetri.products.registration.school.dao.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bimetri.products.registration.school.bean.model.Course;

public interface CourseRepository extends JpaRepository<Course, Integer> {

}
