package com.files.models;

public class User {
	private String name,email,password;
	private int id;
	public User( int id,String name, String email, String password) {
		super();
		this.name = name;
		this.email = email;
		this.password = password;
		this.id = id;
	}
	public User( int id,String name, String email) {
		super();
		this.name = name;
		this.email = email;
		this.id = id;
	}
	public User(String name, String email, String password) {
		super();
		this.name = name;
		this.email = email;
		this.password = password;
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	@Override
	public String toString() {
		return "User [name=" + name + ", email=" + email + ", id=" + id + "]";
	}
	
	
}
