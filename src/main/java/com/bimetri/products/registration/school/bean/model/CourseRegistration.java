package com.bimetri.products.registration.school.bean.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.bimetri.products.registration.school.bean.ModelBase;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="course_registration")
@Getter @Setter @NoArgsConstructor
public class CourseRegistration extends ModelBase {
	private static final long serialVersionUID = -4471380436705559932L;

	@Id  @GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "registrationid")
	private Integer registrationId;
	
	@ManyToOne
    @JoinColumn(name = "studentid")
	private Student student;
	
	@ManyToOne
    @JoinColumn(name = "courseid")
	private Course course;
	
	@Column(name="registerationtime")
	private LocalDateTime registerationTime;
	
	@Column(name = "grade")
    private int grade;
}
