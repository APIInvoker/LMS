package com.example.lms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.lms.entity.Admin;
import com.example.lms.entity.Sysadmin;
import com.example.lms.entity.User;
import com.example.lms.form.LoginForm;
import com.example.lms.mapper.AdminMapper;
import com.example.lms.mapper.SysadminMapper;
import com.example.lms.mapper.UserMapper;
import com.example.lms.result.LoginResult;
import com.example.lms.service.LoginService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class LoginServiceImpl implements LoginService {

    @Resource
    private UserMapper userMapper;
    @Resource
    private AdminMapper adminMapper;
    @Resource
    private SysadminMapper sysadminMapper;

    @Override
    public LoginResult login(LoginForm loginForm) {
        LoginResult loginResult = new LoginResult();
        switch (loginForm.getType()) {
            case 1:
                QueryWrapper<User> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("username", loginForm.getUsername());
                User user = userMapper.selectOne(queryWrapper);
                if (user == null) {
                    loginResult.setMsg("用户名不存在");
                    loginResult.setCode(-1);
                    return loginResult;
                }
                // 验证密码
                if (!user.getPassword().equals(loginForm.getPassword())) {
                    loginResult.setMsg("密码错误");
                    loginResult.setCode(-2);
                    return loginResult;
                }
                loginResult.setMsg("登录成功");
                loginResult.setCode(0);
                loginResult.setObject(user);
                break;
            case 2:
                QueryWrapper<Admin> adminQueryWrapper = new QueryWrapper<>();
                adminQueryWrapper.eq("username", loginForm.getUsername());
                Admin admin = adminMapper.selectOne(adminQueryWrapper);
                if (admin == null) {
                    loginResult.setMsg("用户名不存在");
                    loginResult.setCode(-1);
                    return loginResult;
                }
                // 验证密码
                if (!admin.getPassword().equals(loginForm.getPassword())) {
                    loginResult.setMsg("密码错误");
                    loginResult.setCode(-2);
                    return loginResult;
                }
                loginResult.setMsg("登录成功");
                loginResult.setCode(0);
                loginResult.setObject(admin);
                break;
            case 3:
                QueryWrapper<Sysadmin> sysadminQueryWrapper = new QueryWrapper<>();
                sysadminQueryWrapper.eq("username", loginForm.getUsername());
                Sysadmin sysadmin = sysadminMapper.selectOne(sysadminQueryWrapper);
                if (sysadmin == null) {
                    loginResult.setMsg("用户名不存在");
                    loginResult.setCode(-1);
                    return loginResult;
                }
                // 验证密码
                if (!sysadmin.getPassword().equals(loginForm.getPassword())) {
                    loginResult.setMsg("密码错误");
                    loginResult.setCode(-2);
                    return loginResult;
                }
                loginResult.setMsg("登录成功");
                loginResult.setCode(0);
                loginResult.setObject(sysadmin);
                break;
        }
        return loginResult;
    }

}
