package com.khwebgame.core.bo;

import com.khwebgame.core.dao.LoginDAO;
import com.khwebgame.core.model.LoginInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginBO {
    @Autowired
    LoginDAO loginDAO;

    public LoginInfo Verification(final String id, final String pw) {
        LoginInfo loginInfo = loginDAO.Verification(id, pw);

        if (loginInfo == null)
            return null;

        return loginInfo;
    }
}
