package cloud.jillion.component.common.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author leanderlee
 * @since 1.0.0
 */
@Data
public abstract class AbstractDomain implements Serializable {

    private Date created;

    private Date modified;

    private String creator;

    private String modifier;

    private Byte deleted = 0;

    private Integer version = 0;
}
