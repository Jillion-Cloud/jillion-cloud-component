package cloud.jillion.component.common.distributed.id.leaf;

import cloud.jillion.component.common.distributed.id.leaf.common.Result;

public interface LeafIDGen {

    Result get(String key);

    boolean init();
}
