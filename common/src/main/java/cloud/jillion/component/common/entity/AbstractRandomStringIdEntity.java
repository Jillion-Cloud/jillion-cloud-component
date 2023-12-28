package cloud.jillion.component.common.entity;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.GenericGenerator;
import cloud.jillion.component.common.entity.identifier.RandomStringIdGenerator;

/**
 * @author leanderlee
 * @since 1.0.0
 */
@EqualsAndHashCode(callSuper = true)
@MappedSuperclass
@Data
public abstract class AbstractRandomStringIdEntity extends AbstractEntity {

    @Id
    @GeneratedValue(generator = "RandomStringIdGenerator")
    @GenericGenerator(
            name = "RandomStringIdGenerator",
            type = RandomStringIdGenerator.class
    )
    @Column(nullable = false, length = 32)
    private String id;
}
