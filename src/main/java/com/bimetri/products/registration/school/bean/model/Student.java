package com.bimetri.products.registration.school.bean.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.bimetri.products.registration.school.bean.ModelBase;
import com.bimetri.products.registration.school.bean.Sex;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="student")
@Getter @Setter @NoArgsConstructor
public class Student extends ModelBase {
	private static final long serialVersionUID = 7631361560672418798L;
	
	@Id
	@Column(name = "studentid")
	private Integer studentId;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "surname")
	private String surname;
	
	@Column(name = "schoolclass")
	private String schoolClass;
	
	@Column(name = "age")
	private Short age;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "sex")
	private Sex sex;
	
	@OneToMany(mappedBy = "student")
    private List<CourseRegistration> registrations;

}
