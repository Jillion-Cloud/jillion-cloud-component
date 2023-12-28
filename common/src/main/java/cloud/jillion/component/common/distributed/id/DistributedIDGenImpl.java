package cloud.jillion.component.common.distributed.id;

import org.springframework.stereotype.Component;
import cloud.jillion.component.common.distributed.id.leaf.LeafIDGen;
import cloud.jillion.component.common.distributed.id.leaf.common.Result;
import cloud.jillion.component.common.distributed.id.leaf.common.Status;
import cloud.jillion.component.common.distributed.id.tsid.TsID;
import cloud.jillion.component.common.distributed.id.tsid.TsIDCreator;

@Component
public class DistributedIDGenImpl implements DistributedIDGen {

    private final LeafIDGen leafIDGen;

    public DistributedIDGenImpl(LeafIDGen leafIDGen) {
        this.leafIDGen = leafIDGen;
    }

    @Override
    public long getSequenceId(String bizTag) {
        Result result = leafIDGen.get(bizTag);
        if (!result.getStatus().equals(Status.SUCCESS)) {
            throw new IllegalStateException("Sequence ID generate failed");
        }
        return result.getId();
    }

    @Override
    public long getRandomNumberId() {
        return TsIDCreator.getTsid256().toLong();
    }

    @Override
    public String getRandomStringId(boolean lowerCase) {
        TsID tsID = TsIDCreator.getTsid256();
        return lowerCase ? tsID.toLowerCase() : tsID.toString();
    }
}
