package com.wanghl.torablog.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.util.Date;
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
@ApiModel(value="ToraTag对象", description="")
public class ToraTag implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String name;

    @ApiModelProperty(value = "是否删除")
    @TableLogic
    private Integer isDeleted;

    @ApiModelProperty(value = "乐观锁版本号")
    @Version
    private Integer version;

    private Date gmtCreate;

    private Date gmtModified;

    @TableField(exist = false)
    private Integer count;


}
