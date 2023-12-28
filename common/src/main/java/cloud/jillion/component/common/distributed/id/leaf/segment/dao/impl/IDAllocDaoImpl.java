package cloud.jillion.component.common.distributed.id.leaf.segment.dao.impl;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.JdbcTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;
import cloud.jillion.component.common.distributed.id.leaf.segment.dao.IDAllocDao;
import cloud.jillion.component.common.distributed.id.leaf.segment.model.LeafAlloc;

import javax.sql.DataSource;
import java.util.List;

public class IDAllocDaoImpl implements IDAllocDao {

    final private JdbcClient jdbcClient;
    final private TransactionTemplate transactionTemplate;

    public IDAllocDaoImpl(DataSource dataSource) {
        jdbcClient = JdbcClient.create(dataSource);
        transactionTemplate = new TransactionTemplate(new JdbcTransactionManager(dataSource));
    }

    @Override
    public List<LeafAlloc> getAllLeafAllocs() {
        return jdbcClient.sql("SELECT biz_tag, max_id, step, update_time FROM leaf_alloc")
                .query(LeafAlloc.class)
                .list();
    }

    @Override
    public LeafAlloc updateMaxIdAndGetLeafAlloc(String tag) {
        return transactionTemplate.execute(status -> {
            try {
                jdbcClient.sql("UPDATE leaf_alloc SET max_id = max_id + step WHERE biz_tag = ?")
                        .param(tag)
                        .update();
                return jdbcClient.sql("SELECT biz_tag, max_id, step FROM leaf_alloc WHERE biz_tag = ?")
                        .param(tag)
                        .query(LeafAlloc.class)
                        .single();
            } catch (Exception e) {
                status.setRollbackOnly();
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public LeafAlloc updateMaxIdByCustomStepAndGetLeafAlloc(LeafAlloc leafAlloc) {
        return transactionTemplate.execute(status -> {
            try {
                jdbcClient.sql("UPDATE leaf_alloc SET max_id = max_id + ? WHERE biz_tag = ?")
                        .param(leafAlloc.getStep())
                        .param(leafAlloc.getKey())
                        .update();
                return jdbcClient.sql("SELECT biz_tag, max_id, step FROM leaf_alloc WHERE biz_tag = ?")
                        .param(leafAlloc.getKey())
                        .query(LeafAlloc.class)
                        .single();
            } catch (Exception e) {
                status.setRollbackOnly();
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public List<String> getAllTags() {
        return jdbcClient.sql("SELECT biz_tag FROM leaf_alloc")
                .query(String.class)
                .list();
    }
}
