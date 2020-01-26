package com.numeric.bidtracker.models;

public interface IUser {

	String getFullName();

	String getEmail();

	String getPhoneNumber();

	void setFullName(String fullName);

	void setEmail(String email);

	void setPhoneNumber(String phoneNumber);

}