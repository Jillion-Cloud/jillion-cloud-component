package cloud.jillion.component.common.distributed.id.leaf;

import com.alibaba.druid.pool.DruidDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import cloud.jillion.component.common.distributed.id.leaf.common.ZeroIDGen;
import cloud.jillion.component.common.distributed.id.leaf.exception.LeafInitException;
import cloud.jillion.component.common.distributed.id.leaf.segment.SegmentIDGenImpl;
import cloud.jillion.component.common.distributed.id.leaf.segment.dao.IDAllocDao;
import cloud.jillion.component.common.distributed.id.leaf.segment.dao.impl.IDAllocDaoImpl;

import java.sql.SQLException;
import java.util.Objects;

@Configuration
@Slf4j
public class LeafInitializer {

    @Bean
    public LeafIDGen leafIDGen(Environment environment) throws SQLException, LeafInitException {
        LeafIDGen leafIdGen = null;
        boolean flag = Boolean.parseBoolean(environment.getProperty(LeafConstants.LEAF_SEGMENT_ENABLE, "true"));
        if (flag) {
            // Config dataSource
            DruidDataSource dataSource = new DruidDataSource();
            dataSource.setUrl(Objects.isNull(environment.getProperty(LeafConstants.LEAF_JDBC_URL))
                    ? environment.getProperty(LeafConstants.SPRING_DATASOURCE_URL) : environment.getProperty(LeafConstants.LEAF_JDBC_URL));
            dataSource.setUsername(Objects.isNull(environment.getProperty(LeafConstants.LEAF_JDBC_USERNAME))
                    ? environment.getProperty(LeafConstants.SPRING_DATASOURCE_USERNAME) : environment.getProperty(LeafConstants.LEAF_JDBC_USERNAME));
            dataSource.setPassword(Objects.isNull(environment.getProperty(LeafConstants.LEAF_JDBC_PASSWORD))
                    ? environment.getProperty(LeafConstants.SPRING_DATASOURCE_PASSWORD) : environment.getProperty(LeafConstants.LEAF_JDBC_PASSWORD));
            dataSource.setName("LeafIdService_Datasource");
            dataSource.init();

            // Config Dao
            IDAllocDao dao = new IDAllocDaoImpl(dataSource);

            // Config ID Gen
            leafIdGen = new SegmentIDGenImpl();
            ((SegmentIDGenImpl) leafIdGen).setDao(dao);
            if (leafIdGen.init()) {
                log.info("Segment Id Service Init Successfully");
            } else {
                throw new LeafInitException("Segment Id Service Init Fail");
            }
        } else {
            leafIdGen = new ZeroIDGen();
            log.info("Zero ID Gen Service Init Successfully");
        }
        return leafIdGen;
    }

}
