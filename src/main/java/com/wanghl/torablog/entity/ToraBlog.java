package com.wanghl.torablog.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableLogic;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
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
@ApiModel(value="ToraBlog对象", description="")
public class ToraBlog implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "是否发布")
    private Boolean tPublish;

    @ApiModelProperty(value = "是否开启评论")
    private Boolean tComment;

    @ApiModelProperty(value = "是否推荐")
    private Boolean tRecommend;

    private String classify;

    private String username;

    private String title;

    private String cover;

    private String content;

    private String type;

    @TableLogic
    private Integer isDeleted;

    private Date gmtCreate;

    private Date gmtModified;

    @TableField(exist = false)
    private Integer userId;

    @TableField(exist = false)
    private String avatar;

    @TableField(exist = false)
    private Integer classifyId;


}
