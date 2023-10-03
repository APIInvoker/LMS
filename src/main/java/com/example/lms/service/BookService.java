package com.example.lms.service;

import com.example.lms.entity.Book;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.lms.vo.BookVO;
import com.example.lms.vo.PageVO;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zx
 * @since 2023-10-03
 */
public interface BookService extends IService<Book> {
    PageVO<BookVO> pageList(Integer currentPage);
    PageVO<BookVO> searchByKeyWord(String keyWord,Integer currentPage);
    PageVO<BookVO> searchBySort(Integer sid, Integer currentPage);
}
