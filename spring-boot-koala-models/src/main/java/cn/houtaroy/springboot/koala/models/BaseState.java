package cn.houtaroy.springboot.koala.models;

import cn.houtaroy.springboot.koala.enums.IsDelete;
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
