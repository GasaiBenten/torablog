package com.wanghl.torablog.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wanghl.torablog.entity.ToraComment;
import com.wanghl.torablog.mapper.ToraCommentMapper;
import com.wanghl.torablog.service.ToraCommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wanghl
 * @since 2021-04-12
 */
@Service
public class ToraCommentServiceImpl extends ServiceImpl<ToraCommentMapper, ToraComment> implements ToraCommentService {

    @Override
    public List<ToraComment> getCommentPage(String blogId, Page<ToraComment> page) {
        long size = page.getSize();
        long current = (page.getCurrent() - 1) * size;
        //一级评论
        List<ToraComment> nodes = baseMapper.selectByBlogId(blogId,current,size);
        //二级评论
        List<ToraComment> roots = baseMapper.selectReplyByBlogId(blogId);
        //封装所有评论 树状结构
        List<ToraComment> finalNodes = buildTree(nodes,roots);
        //提取所有二级评论到一级评论下
        List<ToraComment> finalList = new ArrayList<>();
        List<ToraComment> children = new ArrayList<>();
        eachComment(finalNodes,finalList,children);
        return finalList;
    }

    private void eachComment(List<ToraComment> finalNodes, List<ToraComment> finalList, List<ToraComment> children) {
        for (int i = 0; i < finalNodes.size(); i++) {
            if (finalNodes.get(i).getLevel()==1){
                finalList.add(finalNodes.get(i));
                pickChildren(finalNodes.get(i),children);
                finalList.get(i).setChildren(children);
                children = new ArrayList<>();
            }
        }
    }

    private void pickChildren(ToraComment toraComment, List<ToraComment> children) {
        if (toraComment.getChildren().size()>1){
            for (int i = 0; i < toraComment.getChildren().size(); i++) {
                ToraComment reply = toraComment.getChildren().get(i);
                children.add(reply);
                pickChildren(reply,children);
            }
        }
    }

    private List<ToraComment> buildTree(List<ToraComment> nodes, List<ToraComment> roots) {
        List<ToraComment> finalList = new ArrayList<>();
        for (int i = 0; i < nodes.size(); i++) {
            ToraComment nodeComment = nodes.get(i);
            nodeComment.setLevel(1);
            finalList.add(selectChildren(nodeComment,roots));
        }
        return finalList;
    }

    private ToraComment selectChildren(ToraComment nodeComment, List<ToraComment> roots) {
        nodeComment.setChildren(new ArrayList<>());
        for (int i = 0; i < roots.size(); i++) {
            ToraComment rootComment = roots.get(i);
            if (nodeComment.getId().equals(rootComment.getParentId())){
                rootComment.setLevel(nodeComment.getLevel()+1);
                nodeComment.getChildren().add(selectChildren(rootComment,roots));
            }
        }
        return nodeComment;
    }


}
