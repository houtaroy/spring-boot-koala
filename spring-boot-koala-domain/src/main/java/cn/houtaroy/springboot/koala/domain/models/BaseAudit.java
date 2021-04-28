package cn.houtaroy.springboot.koala.domain.models;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Date;

/**
 * @author Houtaroy
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public class BaseAudit<IdType> extends BaseState<IdType> {

    protected Date createTime;

    protected User createUser;

    protected Date lastModifyTime;

    protected User lastModifyUser;

    protected Date deleteTime;

    protected User deleteUser;

}
