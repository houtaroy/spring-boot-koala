package cn.houtaroy.springboot.koala.domain.models;

import cn.houtaroy.springboot.koala.domain.enums.IsDelete;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @author Houtaroy
 */
@Data
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public class BaseState<IdType> extends Base<IdType> {

    private IsDelete isDelete;

}
