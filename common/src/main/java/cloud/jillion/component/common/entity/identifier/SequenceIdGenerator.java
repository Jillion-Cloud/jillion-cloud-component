package cloud.jillion.component.common.entity.identifier;

import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.type.Type;
import org.springframework.util.Assert;

import java.util.Properties;

/**
 * @author leanderlee
 * @since 1.0.0
 */
public class SequenceIdGenerator extends AbstractIdentifierGenerator {
    final static private String PARAM_BIZ_TAG = "bizTag";

    private String bizTag;

    @Override
    public Object generate(SharedSessionContractImplementor sharedSessionContractImplementor, Object o) throws HibernateException {
        return distributedIDGen().getSequenceId(bizTag);
    }

    @Override
    public void configure(Type type, Properties params, ServiceRegistry serviceRegistry) throws MappingException {
        super.configure(type, params, serviceRegistry);
        if (params.containsKey(PARAM_BIZ_TAG)) {
            this.bizTag = params.getProperty(PARAM_BIZ_TAG);
            Assert.hasText(this.bizTag, "Parameter [bizTag] must not be empty");
        } else {
            throw new IllegalArgumentException("Parameter [bizTag] must not be empty");
        }
    }
}
