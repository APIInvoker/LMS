package com.example.lms.controller;

import com.example.lms.form.LoginForm;
import com.example.lms.result.LoginResult;
import com.example.lms.service.LoginService;
import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class LoginController {

    @Resource
    private LoginService loginService;

    @PostMapping("/login")
    public String login(LoginForm loginForm, Model model, HttpServletRequest request){
        LoginResult result = loginService.login(loginForm);
        String url = "";
        ModelAndView modelAndView = new ModelAndView();
        if(result.getCode().equals(-1) || result.getCode().equals(-2)){
            url = "login";
            model.addAttribute("msg", result.getMsg());
        }
        if(result.getCode().equals(0)){
            HttpSession session = request.getSession();
            switch (loginForm.getType()){
                case 1:
                    session.setAttribute("user", result.getObject());
                    url = "redirect:/user/index";
                    break;
                case 2:
                    session.setAttribute("admin", result.getObject());
                    url = "redirect:/admin/index";
                    break;
                case 3:
                    session.setAttribute("sysadmin", result.getObject());
                    url = "redirect:/sysadmin/index";
                    break;
            }
        }
        return url;
    }

}
