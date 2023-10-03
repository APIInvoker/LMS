package com.example.lms.service;

import com.example.lms.entity.Back;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.lms.vo.BackVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zx
 * @since 2023-10-03
 */
public interface BackService extends IService<Back> {
    List<BackVO> backList();
}
