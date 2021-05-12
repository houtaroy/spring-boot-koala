package cn.houtaroy.springboot.mybatis.repositories;

/**
 * 基本增删改查数据访问接口
 *
 * @param <T>  数据实体类型的泛型
 * @param <ID> 数据ID类型的泛型
 * @author shihongjun
 */
public interface CrudRepository<T, ID> {
  /**
   * 根据ID加载实体，不会加载已删除数据
   *
   * @param id ID
   * @return ID所对应的实体
   */
  T load(ID id);

  /**
   * 根据ID加载实体，会加载已删除的数据
   *
   * @param id ID
   * @return ID对应实体
   */
  T loadById(ID id);

  /**
   * 添加实体
   *
   * @param entity 要添加的实体
   */
  void add(T entity);

  /**
   * 修改实体
   *
   * @param entity 要修改的实体
   */
  void update(T entity);

  /**
   * 删除实体
   *
   * @param entity 需要删除的实体
   */
  void delete(T entity);
}
