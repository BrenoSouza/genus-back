package br.edu.ufcg.genus.models;

import java.util.List;
import java.util.ArrayList;

import javax.validation.constraints.Size;

import javax.persistence.*;

import br.edu.ufcg.genus.utils.ServerConstants;

@Entity
public class Institution {
	
	@Id
	@Column(name="id", nullable=false)
    @GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	@Size(min = ServerConstants.MIN_LOGIN_FIELD, max = ServerConstants.MAX_LOGIN_FIELD,  message="O tamanho do nome deve ter entre 6 e 50 dígitos.")
	@Column(unique = true, nullable = false)
	private String name;

	@Size(min = ServerConstants.MIN_LOGIN_FIELD, max = ServerConstants.MAX_LOGIN_FIELD,  message="O tamanho do email deve ter entre 6 e 50 dígitos.")
	@Column(unique = true, nullable = false)
	private String email;

	@Size(min = ServerConstants.MIN_LOGIN_FIELD, max = ServerConstants.MAX_LOGIN_FIELD,  message="O tamanho do endereço da instituição deve ter entre 6 e 50 dígitos.")
	@Column(nullable = false)
	private String address;
	
	@Column(nullable = false)
	private String phone;
	
	@OneToMany(mappedBy="institution")
	@Column(name="grades", nullable=false)
	private List<Grade> grades;

	public Institution() {
		this.grades = new ArrayList<>();		
	}
	
	public Institution(String name, String address, String phone, String email) {
		this();
		this.name = name;
		this.address = address;
		this.phone = phone;
		this.email = email;
	}
	
	public boolean addGrade(Grade grade) {
		return this.grades.add(grade);
	}

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
/*
	public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }
*/
	public List<Grade> getGrades() {
		return grades;
	}

	public void setGrades(List<Grade> grades) {
		this.grades = grades;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Institution other = (Institution) obj;
		if (id != other.id)
			return false;
		return true;
	}
}
