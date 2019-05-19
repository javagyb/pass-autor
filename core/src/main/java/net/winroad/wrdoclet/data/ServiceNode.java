package net.winroad.wrdoclet.data;

import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author xiaohua
 * @date 2019/2/25
 */
@Data
@EqualsAndHashCode
public class ServiceNode  implements Serializable {

  private String name;

  private String description;

}
