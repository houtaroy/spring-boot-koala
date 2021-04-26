package cn.houtaroy.springboot.koala.tools;

import cn.houtaroy.springboot.koala.tools.models.ITreeNode;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 树工具类
 *
 * @author hyh
 * 2020/4/28
 */
public class TreeUtil {

    /**
     * 构建树形结构
     *
     * @param list 无树形结构数组
     * @param <T>  实体
     * @return 树形结构数组
     */
    public static <IdType, T extends ITreeNode<IdType>> List<T> buildTree(List<T> list) {
        List<T> result = new ArrayList<>();
        if (list == null) {
            return result;
        }
        // 筛选全部顶级节点
        result = list.stream().filter(o -> o.getParent() == null || o.getParent().getId() == null)
                .collect(Collectors.toList());
        // 递归全部子节点
        result.forEach(o -> o.setChildren(buildTree(list, o.getId())));
        return result;
    }

    /**
     * 构建子树形结构
     *
     * @param <T>      实体类
     * @param list     无树形结构数组
     * @param parentId 上级ID
     * @return 子树形结构数组
     */
    private static <IdType, T extends ITreeNode<IdType>> List<T> buildTree(List<T> list, IdType parentId) {
        if (list == null || parentId == null) {
            return buildTree(list);
        }
        List<T> result = list.stream()
                .filter(o -> o.getParent() != null && parentId.equals(o.getParent().getId()))
                .collect(Collectors.toList());
        result.forEach(o -> o.setChildren(buildTree(list, o.getId())));
        return result;
    }

}

