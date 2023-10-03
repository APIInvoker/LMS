package com.example.lms.service;

import com.example.lms.form.LoginForm;
import com.example.lms.result.LoginResult;

public interface LoginService {
    LoginResult login(LoginForm loginForm);
}
