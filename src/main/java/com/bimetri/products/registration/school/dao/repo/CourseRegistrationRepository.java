package com.bimetri.products.registration.school.dao.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bimetri.products.registration.school.bean.model.Course;
import com.bimetri.products.registration.school.bean.model.CourseRegistration;
import com.bimetri.products.registration.school.bean.model.Student;

public interface CourseRegistrationRepository extends JpaRepository<CourseRegistration, Integer> {

	public Integer countByCourseCourseId(Integer courseId);

	public Integer countByStudentStudentId(Integer studentId);
	
	@Query("from Student s where s.registrations is empty")
	public List<Student> findStudentsHavingNoCourseRegistration();
	
	@Query("from Course c where c.registrations is empty")
	public List<Course> findCoursesHavingNoStudentsRegistered();
}
