package com.example.lms.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.example.lms.entity.Book;
import com.example.lms.entity.Borrow;
import com.example.lms.entity.Sort;
import com.example.lms.service.BookService;
import com.example.lms.service.BorrowService;
import com.example.lms.service.SortService;
import com.example.lms.vo.BookVO;
import com.example.lms.vo.PageVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author zx
 * @since 2023-10-03
 */
@Controller
@RequestMapping("/book")
public class BookController {

    @Resource
    private BookService bookService;
    @Resource
    private SortService sortService;
    @Resource
    private BorrowService borrowService;

    @GetMapping("/list/{current}")
    public String list(@PathVariable("current") Integer current, Model model) {
        PageVO<BookVO> pageVO = bookService.pageList(current);
        model.addAttribute("page", pageVO);
        model.addAttribute("sortList", sortService.list());
        return "/user/list";
    }

    @PostMapping("/search")
    public String search(String keyWord, Integer current, Integer sid, Model model) {
        PageVO<BookVO> pageVO;
        // 类别检索
        if (!sid.equals(0)) {
            pageVO = bookService.searchBySort(sid, current);
        } else {
            // 关键字检索带分页
            pageVO = bookService.searchByKeyWord(keyWord, current);
        }
        model.addAttribute("page", pageVO);
        model.addAttribute("sortList", sortService.list());
        return "/user/list";
    }

    @PostMapping("/findByKey")
    public String findByKey(String key, Model model) {
        QueryWrapper<Book> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(key), "name", key)
                .or()
                .like(StringUtils.isNotBlank(key), "author", key)
                .or()
                .like(StringUtils.isNotBlank(key), "publish", key);
        List<Book> list = bookService.list(queryWrapper);
        List<BookVO> bookVOList = new ArrayList<>();
        for (Book book : list) {
            BookVO bookVO = new BookVO();
            BeanUtils.copyProperties(book, bookVO);
            Sort sort = sortService.getById(book.getSid());
            bookVO.setSname(sort.getName());
            bookVOList.add(bookVO);
        }
        model.addAttribute("list", bookVOList);
        return "/sysadmin/book";
    }

    @PostMapping("/add")
    public String add(Book book) {
        bookService.save(book);
        return "redirect:/sysadmin/bookList";
    }

    @GetMapping("/findById/{id}")
    public String findById(@PathVariable("id") Integer id, Model model) {
        Book book = bookService.getById(id);
        model.addAttribute("book", book);
        model.addAttribute("list", sortService.list());
        return "/sysadmin/updateBook";
    }

    @PostMapping("/update")
    public String update(Book book) {
        bookService.updateById(book);
        return "redirect:/sysadmin/bookList";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer id) {
        QueryWrapper<Borrow> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("bid", id);
        borrowService.remove(queryWrapper);
        bookService.removeById(id);
        return "redirect:/sysadmin/bookList";
    }
}

