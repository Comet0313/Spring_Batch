package com.example.demo.db;

import com.opencsv.bean.CsvBindByPosition;

public class LoginUserCustom {

	@CsvBindByPosition(position = 0, required = true)
	private String pref_cd;

	@CsvBindByPosition(position = 1, required = true)
	private String user_id;

	@CsvBindByPosition(position = 2, required = true)
	private String password;

	public LoginUserCustom() {
		super();
	}

	public LoginUserCustom(String pref_cd, String user_id, String password) {
		super();
		this.pref_cd = pref_cd;
		this.user_id = user_id;
		this.password = password;

	}

	public String getPref_cd() {
		return pref_cd;
	}

	public void setPref_cd(String pref_cd) {
		this.pref_cd = pref_cd;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
