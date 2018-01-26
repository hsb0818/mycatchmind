package com.khwebgame.core.bo;

import com.khwebgame.core.dao.LoginDao;
import com.khwebgame.core.model.LoginInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestService {
    @Autowired
    LoginDao loginDao;

    public LoginInfo selectTest() throws Exception {
        return loginDao.selectTest();
    }
}
