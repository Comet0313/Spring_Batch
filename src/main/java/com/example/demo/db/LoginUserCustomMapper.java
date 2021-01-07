package com.example.demo.db;

import java.sql.Timestamp;
import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface LoginUserCustomMapper{
	List<LoginUserCustom> selectAuth();
	List<LoginUserCustom> selectAuthCondition(@Param("DBQueryDate")Timestamp DBQueryDate , @Param("DBSystemDate")Timestamp DBSystemDate);
	
	//这里没写具体sql，自行填充
	void insert(LoginUserCustom loginUserAuth);
}
