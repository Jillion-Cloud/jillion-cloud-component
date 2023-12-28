package cloud.jillion.component.common.entity;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.GenericGenerator;
import cloud.jillion.component.common.entity.identifier.RandomNumberIdGenerator;

/**
 * @author leanderlee
 * @since 1.0.0
 */
@EqualsAndHashCode(callSuper = true)
@MappedSuperclass
@Data
public abstract class AbstractRandomNumberIdEntity extends AbstractEntity {

    @Id
    @GeneratedValue(generator = "RandomNumberIdGenerator")
    @GenericGenerator(
            name = "RandomNumberIdGenerator",
            type = RandomNumberIdGenerator.class
    )
    @Column(nullable = false, length = 20)
    private Long id;
}
