package cn.houtaroy.springboot.koala.starter.log.repositories;

import cn.houtaroy.springboot.koala.starter.log.models.Log;
import cn.houtaroy.springboot.mybatis.repositories.PageableRepository;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Houtaroy
 */
@Mapper
public interface LogRepository extends PageableRepository<Log, Long> {



}
