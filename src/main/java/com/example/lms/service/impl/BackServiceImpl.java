package com.example.lms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.lms.entity.Back;
import com.example.lms.entity.Book;
import com.example.lms.entity.Borrow;
import com.example.lms.entity.User;
import com.example.lms.mapper.BackMapper;
import com.example.lms.mapper.BookMapper;
import com.example.lms.mapper.BorrowMapper;
import com.example.lms.mapper.UserMapper;
import com.example.lms.service.BackService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.lms.vo.BackVO;
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
public class BackServiceImpl extends ServiceImpl<BackMapper, Back> implements BackService {

    @Resource
    private BackMapper backMapper;
    @Resource
    private BorrowMapper borrowMapper;
    @Resource
    private UserMapper userMapper;
    @Resource
    private BookMapper bookMapper;

    @Override
    public List<BackVO> backList() {
        QueryWrapper<Back> backQueryWrapper = new QueryWrapper<>();
        backQueryWrapper.eq("status", 0);
        List<Back> backList = this.backMapper.selectList(backQueryWrapper);
        List<BackVO> backVOList = new ArrayList<>();
        for (Back back : backList) {
            BackVO backVO = new BackVO();
            Borrow borrow = this.borrowMapper.selectById(back.getBrid());
            User user = this.userMapper.selectById(borrow.getUid());
            backVO.setUserName(user.getUsername());
            Book book = this.bookMapper.selectById(borrow.getBid());
            BeanUtils.copyProperties(book, backVO);
            backVO.setBookName(book.getName());
            BeanUtils.copyProperties(back, backVO);
            backVOList.add(backVO);
        }
        return backVOList;
    }
}
