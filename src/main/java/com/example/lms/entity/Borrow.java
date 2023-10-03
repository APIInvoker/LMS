package com.example.lms.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author zx
 * @since 2023-10-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Borrow implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer uid;

    private Integer bid;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime startTime;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime endTime;

    private Integer status = 0;

}
