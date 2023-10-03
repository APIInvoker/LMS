package com.example.lms.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.example.lms.entity.Admin;
import com.example.lms.entity.Book;
import com.example.lms.entity.Sort;
import com.example.lms.entity.User;
import com.example.lms.service.AdminService;
import com.example.lms.service.BookService;
import com.example.lms.service.SortService;
import com.example.lms.service.UserService;
import com.example.lms.vo.BookVO;
import org.springframework.beans.BeanUtils;
import javax.annotation.Resource;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zx
 * @since 2023-10-03
 */
@Controller
@RequestMapping("/sysadmin")
public class SysadminController {

    @Resource
    private UserService userService;
    @Resource
    private BookService bookService;
    @Resource
    private SortService sortService;

    @GetMapping("/{url}")
    public String redirect(@PathVariable("url") String url){
        return "/sysadmin/"+url;
    }

    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "login";
    }

    @GetMapping("/userList")
    public String userList(Model model){
        List<User> list = this.userService.list();
        model.addAttribute("list", list);
        return "/sysadmin/user";
    }

    @GetMapping("/sortList")
    public String sortList(Model model){
        List<Sort> list = this.sortService.list();
        model.addAttribute("list", list);
        return "/sysadmin/sort";
    }

    @PostMapping("/findByName")
    public String findByName(String username,Model model){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(username), "username", username);
        List<User> list = this.userService.list(queryWrapper);
        model.addAttribute("list", list);
        return "/sysadmin/user";
    }

    @GetMapping("/bookList")
    public String bookList(Model model){
        List<Book> list = this.bookService.list();
        List<BookVO> bookVOList = new ArrayList<>();
        for (Book book : list) {
            BookVO bookVO = new BookVO();
            BeanUtils.copyProperties(book, bookVO);
            Sort sort = this.sortService.getById(book.getSid());
            bookVO.setSname(sort.getName());
            bookVOList.add(bookVO);
        }
        model.addAttribute("list", bookVOList);
        return "/sysadmin/book";
    }
}

