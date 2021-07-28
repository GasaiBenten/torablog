package com.wanghl.torablog.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author wanghl
 * @since 2021-04-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="ToraBlogTag对象", description="")
public class ToraBlogTag implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer blogId;

    private Integer tagId;

    private Date gmtCreate;

    private Date gmtModified;

    @TableField(exist = false)
    private String name;

}
