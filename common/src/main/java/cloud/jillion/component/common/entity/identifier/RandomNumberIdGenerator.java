package cloud.jillion.component.common.entity.identifier;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;

/**
 * @author leanderlee
 * @since 1.0.0
 */
public class RandomNumberIdGenerator extends AbstractIdentifierGenerator {

    @Override
    public Object generate(SharedSessionContractImplementor sharedSessionContractImplementor, Object o) throws HibernateException {
        return distributedIDGen().getRandomNumberId();
    }

}
