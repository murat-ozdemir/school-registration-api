package com.bimetri.products.registration.school.bean.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.bimetri.products.registration.school.bean.CourseCategory;
import com.bimetri.products.registration.school.bean.ModelBase;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="course")
@Getter @Setter @NoArgsConstructor
public class Course extends ModelBase {
	private static final long serialVersionUID = -627940639276214688L;
	
	@Id  @GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="courseid")
	private Integer courseId;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "category")
	private CourseCategory category;
	
	@Column(name = "coursename")
	private String courseName;
	
	@Column(name = "credits")
	private Short credits;
	
	@Column(name = "classnumber")
	private Short classNumber;
	
	@OneToMany(mappedBy = "course")
    private List<CourseRegistration> registrations;

}
