package com.khwebgame.core.dao;

import com.khwebgame.core.model.LoginInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface LoginDao {

    @Select("select id,nickname from user where id='testID1'")
    public LoginInfo selectTest() throws Exception;
}
