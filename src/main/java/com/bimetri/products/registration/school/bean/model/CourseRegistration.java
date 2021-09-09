package com.bimetri.products.registration.school.bean.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.bimetri.products.registration.school.bean.ModelBase;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter @NoArgsConstructor
@Table(name="course_registration")
public class CourseRegistration extends ModelBase {
	private static final long serialVersionUID = -4471380436705559932L;

	@Id
	@Column(name = "registrationid")
	private Integer registrationId;
	
	@ManyToOne
    @JoinColumn(name = "studentid")
	private Student student;
	
	@ManyToOne
    @JoinColumn(name = "courseid")
	private Course course;
	
	//@Temporal(TemporalType.TIMESTAMP)
	@Column(name="registerationtime")
	private LocalDateTime registerationTime;
	
	@Column(name = "grade")
    private int grade;
}
