package cloud.jillion.component.common.distributed.id.leaf.common;

import cloud.jillion.component.common.distributed.id.leaf.LeafIDGen;

public class ZeroIDGen implements LeafIDGen {
    @Override
    public Result get(String key) {
        return new Result(0, Status.SUCCESS);
    }

    @Override
    public boolean init() {
        return true;
    }
}
