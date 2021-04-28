package cn.houtaroy.springboot.koala.domain.models;

import cn.houtaroy.springboot.koala.domain.enums.IsDelete;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @author Houtaroy
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public class BaseState<IdType> extends Base<IdType> {

    private IsDelete isDelete;

}
