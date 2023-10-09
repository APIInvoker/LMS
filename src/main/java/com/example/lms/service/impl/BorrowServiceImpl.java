package com.example.lms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.lms.entity.Book;
import com.example.lms.entity.Borrow;
import com.example.lms.entity.Sort;
import com.example.lms.entity.User;
import com.example.lms.mapper.BookMapper;
import com.example.lms.mapper.BorrowMapper;
import com.example.lms.mapper.SortMapper;
import com.example.lms.mapper.UserMapper;
import com.example.lms.service.BorrowService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.lms.vo.AdminBorrowVO;
import com.example.lms.vo.BorrowVO;
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
public class BorrowServiceImpl extends ServiceImpl<BorrowMapper, Borrow> implements BorrowService {

    @Resource
    private BorrowMapper borrowMapper;
    @Resource
    private BookMapper bookMapper;
    @Resource
    private SortMapper sortMapper;
    @Resource
    private UserMapper userMapper;

    @Override
    public void add(Integer uid, Integer bid) {
        Borrow borrow = new Borrow();
        borrow.setBid(bid);
        borrow.setUid(uid);
        borrowMapper.insert(borrow);
        Book book = bookMapper.selectById(bid);
        book.setNumber(book.getNumber()-1);
        bookMapper.updateById(book);
    }

    @Override
    public List<BorrowVO> borrowList(Integer uid) {
        QueryWrapper<Borrow> borrowQueryWrapper = new QueryWrapper<>();
        borrowQueryWrapper.eq("uid", uid);
        List<Borrow> borrowList = borrowMapper.selectList(borrowQueryWrapper);
        List<BorrowVO> borrowVOList = new ArrayList<>();
        for (Borrow borrow : borrowList) {
            BorrowVO borrowVO = new BorrowVO();
            BeanUtils.copyProperties(borrow, borrowVO);
            Book book = bookMapper.selectById(borrow.getBid());
            BeanUtils.copyProperties(book, borrowVO);
            borrowVO.setBookName(book.getName());
            Sort sort = sortMapper.selectById(book.getSid());
            borrowVO.setSortName(sort.getName());
            borrowVOList.add(borrowVO);
        }
        return borrowVOList;
    }

    @Override
    public List<BorrowVO> backList(Integer uid) {
        QueryWrapper<Borrow> borrowQueryWrapper = new QueryWrapper<>();
        borrowQueryWrapper.eq("uid", uid);
        borrowQueryWrapper.eq("status", 1);
        List<Borrow> borrowList = borrowMapper.selectList(borrowQueryWrapper);
        List<BorrowVO> borrowVOList = new ArrayList<>();
        for (Borrow borrow : borrowList) {
            BorrowVO borrowVO = new BorrowVO();
            BeanUtils.copyProperties(borrow, borrowVO);
            Book book = bookMapper.selectById(borrow.getBid());
            BeanUtils.copyProperties(book, borrowVO);
            borrowVO.setId(borrow.getId());
            borrowVO.setBookName(book.getName());
            borrowVOList.add(borrowVO);
        }
        return borrowVOList;
    }

    @Override
    public List<AdminBorrowVO> adminBorrowList() {
        QueryWrapper<Borrow> borrowQueryWrapper = new QueryWrapper<>();
        borrowQueryWrapper.eq("status", 0);
        List<Borrow> borrowList = borrowMapper.selectList(borrowQueryWrapper);
        List<AdminBorrowVO> adminBorrowVOList = new ArrayList<>();
        for (Borrow borrow : borrowList) {
            AdminBorrowVO adminBorrowVO = new AdminBorrowVO();
            BeanUtils.copyProperties(borrow, adminBorrowVO);
            User user = userMapper.selectById(borrow.getUid());
            adminBorrowVO.setUserName(user.getUsername());
            Book book = bookMapper.selectById(borrow.getBid());
            adminBorrowVO.setBookName(book.getName());
            BeanUtils.copyProperties(book, adminBorrowVO);
            Sort sort = sortMapper.selectById(book.getSid());
            adminBorrowVO.setSortName(sort.getName());
            adminBorrowVO.setId(borrow.getId());
            adminBorrowVOList.add(adminBorrowVO);
        }
        return adminBorrowVOList;
    }
}
