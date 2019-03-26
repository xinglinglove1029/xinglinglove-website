package com.xingling.model.vo;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * <p>Title:	  TreeNode. </p>
 * <p>Description 树vo </p>
 * <p>Company:    http://www.xinglinglove.com </p>
 *
 * @Author <a href="190332447@qq.com"/>杨文生</a>
 * @CreateDate 2017/8/18 13:45
 */
public class TreeNode {

    private String id;

    private String parentId;

    List<TreeNode> children = Lists.newArrayList();

    public List<TreeNode> getChildren() {
        return children;
    }

    public void setChildren(List<TreeNode> children) {
        this.children = children;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public void add(TreeNode node) {
        children.add(node);
    }
}
