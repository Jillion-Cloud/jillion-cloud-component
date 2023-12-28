package cloud.jillion.component.common.entity.identifier;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.type.Type;

import java.util.Properties;

/**
 * @author leanderlee
 * @since 1.0.0
 */
public class RandomStringIdGenerator extends AbstractIdentifierGenerator {
    final static private String PARAM_LOWERCASE = "lowerCase";

    private boolean lowerCase;

    @Override
    public Object generate(SharedSessionContractImplementor sharedSessionContractImplementor, Object o) throws HibernateException {
        return distributedIDGen().getRandomStringId(this.lowerCase);
    }

    @Override
    public void configure(Type type, Properties parameters, ServiceRegistry serviceRegistry) {
        super.configure(type, parameters, serviceRegistry);
        String value = String.valueOf(parameters.getOrDefault(PARAM_LOWERCASE, "false"));
        this.lowerCase = Boolean.parseBoolean(value);
    }
}
