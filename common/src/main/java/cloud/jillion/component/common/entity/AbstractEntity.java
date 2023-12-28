package cloud.jillion.component.common.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.util.Date;

/**
 * BaseEntity
 *
 * @author leanderlee
 * @since 1.0.0
 */
@MappedSuperclass
@Data
@EntityListeners(AuditingEntityListener.class)
public abstract class AbstractEntity implements Serializable {
    public static final Byte UN_DELETED = 0;
    public static final Byte DELETED = 1;
    public static final Integer DEF_VERSION = 0;

    @Column(nullable = false)
    @CreatedDate
    private Date created;

    @Column(nullable = false)
    @LastModifiedDate
    private Date modified;

    @Column(nullable = false)
    @CreatedBy
    private String creator;

    @Column(nullable = false)
    @LastModifiedBy
    private String modifier;

    @Column(nullable = false)
    private Byte deleted;

    @Column(nullable = false)
    private Integer version;
}
