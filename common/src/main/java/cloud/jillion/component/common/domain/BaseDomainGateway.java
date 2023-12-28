package cloud.jillion.component.common.domain;

/**
 * @author leanderlee
 * @since 1.0.0
 */
public interface BaseDomainGateway<T extends AbstractDomain, I extends Object> {

    T save(T domain);

    void remove(T domain);

    T getById(I id);
}
