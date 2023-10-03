package com.example.lms.service;

import com.example.lms.entity.Borrow;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.lms.vo.AdminBorrowVO;
import com.example.lms.vo.BorrowVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zx
 * @since 2023-10-03
 */
public interface BorrowService extends IService<Borrow> {
    void add(Integer uid,Integer bid);
    List<BorrowVO> borrowList(Integer uid);
    List<BorrowVO> backList(Integer uid);
    List<AdminBorrowVO> adminBorrowList();
}
