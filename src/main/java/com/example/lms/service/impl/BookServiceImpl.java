package com.example.lms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.lms.entity.Book;
import com.example.lms.entity.Sort;
import com.example.lms.mapper.BookMapper;
import com.example.lms.mapper.SortMapper;
import com.example.lms.service.BookService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.lms.vo.BookVO;
import com.example.lms.vo.PageVO;
import org.springframework.beans.BeanUtils;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zx
 * @since 2023-10-03
 */
@Service
public class BookServiceImpl extends ServiceImpl<BookMapper, Book> implements BookService {

    @Resource
    private BookMapper bookMapper;
    @Resource
    private SortMapper sortMapper;

    @Override
    public PageVO<BookVO> pageList(Integer currentPage) {
        QueryWrapper<Book> bookQueryWrapper = new QueryWrapper<>();
        bookQueryWrapper.gt("number", 0);
        Page<Book> page = new Page<>(currentPage, 5);
        Page<Book> resultPage = this.bookMapper.selectPage(page, bookQueryWrapper);
        PageVO<BookVO> pageVO = new PageVO<>();
        pageVO.setCurrentPage(resultPage.getCurrent());
        pageVO.setTotalPage(resultPage.getPages());
        List<BookVO> result = new ArrayList<>();
        for (Book book : resultPage.getRecords()) {
            BookVO bookVO = new BookVO();
            BeanUtils.copyProperties(book, bookVO);
            QueryWrapper<Sort> sortQueryWrapper = new QueryWrapper<>();
            sortQueryWrapper.eq("id", book.getSid());
            Sort sort = this.sortMapper.selectOne(sortQueryWrapper);
            bookVO.setSname(sort.getName());
            result.add(bookVO);
        }
        pageVO.setData(result);
        return pageVO;
    }

    @Override
    public PageVO<BookVO> searchByKeyWord(String keyWord,Integer currentPage) {
        QueryWrapper<Book> bookQueryWrapper = new QueryWrapper<>();
        bookQueryWrapper.gt("number", 0);
        bookQueryWrapper.like(StringUtils.isNotBlank(keyWord), "name", keyWord)
                .or()
                .like(StringUtils.isNotBlank(keyWord), "author", keyWord)
                .or()
                .like(StringUtils.isNotBlank(keyWord), "publish", keyWord);
        Page<Book> page = new Page<>(currentPage, 5);
        Page<Book> resultPage = this.bookMapper.selectPage(page, bookQueryWrapper);
        PageVO<BookVO> pageVO = new PageVO<>();
        pageVO.setCurrentPage(resultPage.getCurrent());
        pageVO.setTotalPage(resultPage.getPages());
        List<BookVO> result = new ArrayList<>();
        for (Book book : resultPage.getRecords()) {
            BookVO bookVO = new BookVO();
            BeanUtils.copyProperties(book, bookVO);
            QueryWrapper<Sort> sortQueryWrapper = new QueryWrapper<>();
            sortQueryWrapper.eq("id", book.getSid());
            Sort sort = this.sortMapper.selectOne(sortQueryWrapper);
            bookVO.setSname(sort.getName());
            result.add(bookVO);
        }
        pageVO.setData(result);
        return pageVO;
    }

    @Override
    public PageVO<BookVO> searchBySort(Integer sid, Integer currentPage) {
        QueryWrapper<Book> bookQueryWrapper = new QueryWrapper<>();
        bookQueryWrapper.gt("number", 0);
        bookQueryWrapper.eq("sid",sid);
        Page<Book> page = new Page<>(currentPage, 5);
        Page<Book> resultPage = this.bookMapper.selectPage(page, bookQueryWrapper);
        PageVO<BookVO> pageVO = new PageVO<>();
        pageVO.setCurrentPage(resultPage.getCurrent());
        pageVO.setTotalPage(resultPage.getPages());
        List<BookVO> result = new ArrayList<>();
        for (Book book : resultPage.getRecords()) {
            BookVO bookVO = new BookVO();
            BeanUtils.copyProperties(book, bookVO);
            QueryWrapper<Sort> sortQueryWrapper = new QueryWrapper<>();
            sortQueryWrapper.eq("id", book.getSid());
            Sort sort = this.sortMapper.selectOne(sortQueryWrapper);
            bookVO.setSname(sort.getName());
            result.add(bookVO);
        }
        pageVO.setData(result);
        return pageVO;
    }
}
