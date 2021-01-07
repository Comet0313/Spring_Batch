package com.example.demo.db;

import java.sql.Timestamp;
import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface UserCustomMapper{
	List<UserCustom> selectAuth();
	List<UserCustom> selectAuthCondition(@Param("DBQueryDate")Timestamp DBQueryDate , @Param("DBSystemDate")Timestamp DBSystemDate);
}
