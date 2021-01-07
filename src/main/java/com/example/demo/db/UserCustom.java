package com.example.demo.db;

import java.util.Date;

import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvDate;

public class UserCustom {

    @CsvBindByPosition(position = 0, required = true)
	private	String	pref_cd;

	@CsvBindByPosition(position = 1, required = true)
	private	String	user_id;

	@CsvBindByPosition(position = 2, required = true)
	private	String	password;

	@CsvBindByPosition(position = 3, required = true)
	private	int		password_miss_cut;

	@CsvBindByPosition(position = 4, required = true)
	@CsvDate("yyyy-MM-dd HH:mm:ss")
	private	Date	password_miss_date;
	
	@CsvBindByPosition(position = 5, required = true)
	private	int		login_flg;

	@CsvBindByPosition(position = 6, required = true)
	private	String	group_cd;

	@CsvBindByPosition(position = 7, required = true)
	private	String	delegate_kbn;

	@CsvBindByPosition(position = 8, required = true)
	@CsvDate("yyyy-MM-dd")
	private	Date	expiry_begin_date;

	@CsvBindByPosition(position = 9, required = true)
	@CsvDate("yyyy-MM-dd")
	private	Date	expiry_end_date;

	@CsvBindByPosition(position = 10, required = true)
	private	String	operation_information;

	@CsvBindByPosition(position = 11, required = true)
	private	int		del_flg;


	public UserCustom() {
		super();
	}

	public UserCustom(String pref_cd, String user_id, String password, int password_miss_cut, Date password_miss_date,
			int login_flg, String group_cd, String delegate_kbn, Date expiry_begin_date, Date expiry_end_date,
			String operation_information, int del_flg) {
		super();
		this.pref_cd = pref_cd;
		this.user_id = user_id;
		this.password = password;
		this.password_miss_cut = password_miss_cut;
		this.password_miss_date = password_miss_date;
		this.login_flg = login_flg;
		this.group_cd = group_cd;
		this.delegate_kbn = delegate_kbn;
		this.expiry_begin_date = expiry_begin_date;
		this.expiry_end_date = expiry_end_date;
		this.operation_information = operation_information;
		this.del_flg = del_flg;
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

	public int getPassword_miss_cut() {
		return password_miss_cut;
	}

	public void setPassword_miss_cut(int password_miss_cut) {
		this.password_miss_cut = password_miss_cut;
	}

	public Date getPassword_miss_date() {
		return password_miss_date;
	}

	public void setPassword_miss_date(Date password_miss_date) {
		this.password_miss_date = password_miss_date;
	}

	public int getLogin_flg() {
		return login_flg;
	}

	public void setLogin_flg(int login_flg) {
		this.login_flg = login_flg;
	}

	public String getGroup_cd() {
		return group_cd;
	}

	public void setGroup_cd(String group_cd) {
		this.group_cd = group_cd;
	}

	public String getDelegate_kbn() {
		return delegate_kbn;
	}

	public void setDelegate_kbn(String delegate_kbn) {
		this.delegate_kbn = delegate_kbn;
	}

	public Date getExpiry_begin_date() {
		return expiry_begin_date;
	}

	public void setExpiry_begin_date(Date expiry_begin_date) {
		this.expiry_begin_date = expiry_begin_date;
	}

	public Date getExpiry_end_date() {
		return expiry_end_date;
	}

	public void setExpiry_end_date(Date expiry_end_date) {
		this.expiry_end_date = expiry_end_date;
	}

	public String getOperation_information() {
		return operation_information;
	}

	public void setOperation_information(String operation_information) {
		this.operation_information = operation_information;
	}

	public int getDel_flg() {
		return del_flg;
	}

	public void setDel_flg(int del_flg) {
		this.del_flg = del_flg;
	}


}
