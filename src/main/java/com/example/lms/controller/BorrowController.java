package com.example.lms.controller;

import com.example.lms.entity.User;
import com.example.lms.service.BorrowService;
import com.example.lms.vo.BorrowVO;
import javax.annotation.Resource;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpSession;
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
@RequestMapping("/borrow")
public class BorrowController {

    @Resource
    private BorrowService borrowService;

    @GetMapping("/add")
    public String add(Integer bookId, HttpSession session){
        User user = (User) session.getAttribute("user");
        this.borrowService.add(user.getId(), bookId);
        return "redirect:/borrow/list";
    }

    @GetMapping("/list")
    public String list(HttpSession session, Model model){
        User user = (User) session.getAttribute("user");
        List<BorrowVO> borrowVOList = this.borrowService.borrowList(user.getId());
        model.addAttribute("list", borrowVOList);
        return "/user/borrow";
    }

}

