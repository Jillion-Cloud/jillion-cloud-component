package cloud.jillion.component.common.entity;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import cloud.jillion.component.common.entity.identifier.SequenceIdGenerator;

/**
 * @author leanderlee
 * @since 1.0.0
 */
@EqualsAndHashCode(callSuper = true)
@MappedSuperclass
@Data
public abstract class AbstractSequenceIdEntity extends AbstractEntity {

    @Id
    @GeneratedValue(generator = "SequenceIdGenerator")
    @GenericGenerator(
            name = "SequenceIdGenerator",
            type = SequenceIdGenerator.class,
            parameters = {@Parameter(name = "bizTag", value = "default_tag")}
    )
    @Column(nullable = false, length = 20)
    private Long id;
}
