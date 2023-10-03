package com.example.lms.controller;

import com.example.lms.entity.User;
import com.example.lms.service.UserService;
import javax.annotation.Resource;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpSession;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zx
 * @since 2023-10-03
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @GetMapping("/{url}")
    public String redirect(@PathVariable("url") String url){
        return "/user/"+url;
    }

    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "login";
    }

    @PostMapping("/add")
    public String add(User user){
        this.userService.save(user);
        return "redirect:/sysadmin/userList";
    }

    @GetMapping("/findById/{id}")
    public String findById(@PathVariable("id") Integer id, Model model){
        User user = this.userService.getById(id);
        model.addAttribute("user", user);
        return "/sysadmin/updateUser";
    }

    @PostMapping("/update")
    public String update(User user){
        this.userService.updateById(user);
        return "redirect:/sysadmin/userList";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer id){
        this.userService.removeById(id);
        return "redirect:/sysadmin/userList";
    }
}

