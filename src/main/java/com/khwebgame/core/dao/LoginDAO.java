package com.khwebgame.core.dao;

import com.khwebgame.core.dto.LoginInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface LoginDAO {
    public LoginInfo Verification(@Param("id") String id, @Param("pw")String pw);
    public LoginInfo GetUser(@Param("id") String id);
}
