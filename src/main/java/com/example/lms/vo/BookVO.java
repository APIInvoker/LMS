package com.example.lms.vo;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class BookVO {
    @ExcelIgnore
    private Integer id;

    @ExcelProperty("书籍名称")
    private String name;

    @ExcelProperty("书籍类别")
    private String sname;

    @ExcelIgnore
    private Integer number;

    @ExcelProperty("作者")
    private String author;

    @ExcelProperty("出版社")
    private String publish;

    @ExcelProperty("版本")
    private String edition;
}
