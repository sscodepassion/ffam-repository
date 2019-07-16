package com.ffam.workdistapi.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table (name = "skills")
public class Skill implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column (name = "skill_id")
	private Integer id;
	
	@Column (name = "skill_name")
	private String name;
	
	@SuppressWarnings("unused")
	private Skill() {/* To protect anyone from initializing using default constructor */}
	
	public Skill(String name) {
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	@Override
	public int hashCode() {
		return Objects.hash(name);	
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		 
        if (obj == null || getClass() != obj.getClass())
            return false;
 
        Skill that = (Skill) obj;
        return Objects.equals(name, that.name);
	}
}