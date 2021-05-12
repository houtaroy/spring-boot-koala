package cn.houtaroy.springboot.mybatis.repositories;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

/**
 * 分页查询数据方法接口
 *
 * @param <T>  实体类型
 * @param <ID> 实体的ID类型
 * @author shihongjun
 */
public interface PageableRepository<T, ID> extends CrudRepository<T, ID> {
  /**
   * 分页查询数据访问接口
   *
   * @param parameters 查询条件
   * @param pageable   分析信息
   * @return 查询结果列表
   */
  List<T> search(@Param("parameters") Map<String, Object> parameters, @Param("pageable") Pageable pageable);
}
