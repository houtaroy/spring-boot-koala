package cn.houtaroy.springboot.koala.tools.models;

import java.util.List;

/**
 * @param <IdType> 主键类型
 * @author Houtaroy
 */
public interface ITreeNode<IdType> {

    /**
     * 获取id
     *
     * @return id
     */
    IdType getId();

    /**
     * 获取父级
     *
     * @return 父级实体
     */
    ITreeNode<IdType> getParent();

    /**
     * 设置子集
     *
     * @param children 实体数组
     */
    void setChildren(List<? extends ITreeNode<IdType>> children);

}
