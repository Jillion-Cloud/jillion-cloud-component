package cloud.jillion.component.common.entity;

import cloud.jillion.component.common.domain.AbstractDomain;

/**
 * @author leanderlee
 * @since 1.0.0
 */
public interface BaseEntityConvertor<S extends AbstractEntity, T extends AbstractDomain> {

    S toEntity(T domain);

    T toDomain(S entity);
}
