package com.example.lms.vo;

import lombok.Data;

import java.util.List;

@Data
public class PageVO<T> {
    private Long currentPage;
    private Long totalPage;
    private List<T> data;
}
