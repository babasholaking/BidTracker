package com.numeric.bidtracker.models;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class User implements Serializable, IUser {
	private static final long serialVersionUID = 1918259613596286492L;

	private String fullName;
	private String email;
	private String phoneNumber;

	private Long id; // persistence scope only - beyond scope

	public User(String fullName, String email, String phoneNumber) {
		this.fullName = fullName;
		this.email = email;
		this.phoneNumber = phoneNumber;
	}

	@Id
	@GeneratedValue
	public Long getId() { return id; }
	public void setId(Long id) { this.id = id;}

	@Override
	public String getFullName() { return fullName; }
	@Override
	public String getEmail() { return email; }
	@Override
	public String getPhoneNumber() { return phoneNumber; }

	@Override
	public void setFullName(String fullName) { this.fullName = fullName; }
	@Override
	public void setEmail(String email) { this.email = email; }
	@Override
	public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((fullName == null) ? 0 : fullName.hashCode());
		result = prime * result + ((phoneNumber == null) ? 0 : phoneNumber.hashCode());
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
		User other = (User) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (fullName == null) {
			if (other.fullName != null)
				return false;
		} else if (!fullName.equals(other.fullName))
			return false;
		if (phoneNumber == null) {
			if (other.phoneNumber != null)
				return false;
		} else if (!phoneNumber.equals(other.phoneNumber))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "User [fullName=" + fullName + ", email=" + email + ", phoneNumber=" + phoneNumber + "]";
	}
}
