package cloud.jillion.component.common.entity.identifier;

import org.hibernate.id.IdentifierGenerator;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import cloud.jillion.component.common.distributed.id.DistributedIDGen;

public abstract class AbstractIdentifierGenerator implements IdentifierGenerator, ApplicationContextAware {

    protected ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    protected DistributedIDGen distributedIDGen() {
        return applicationContext.getBean(DistributedIDGen.class);
    }
}
