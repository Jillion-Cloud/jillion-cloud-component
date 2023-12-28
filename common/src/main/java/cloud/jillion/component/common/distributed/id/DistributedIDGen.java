package cloud.jillion.component.common.distributed.id;

/**
 * 分布式ID生成器接口
 */
public interface DistributedIDGen {

    /**
     * 获取序列号ID
     * @param bizTag 业务标记
     * @return 序列号ID
     */
    long getSequenceId(String bizTag);

    /**
     * 获取随机数ID
     * @return 随机数ID
     */
    long getRandomNumberId();

    /**
     * 获取随机字符串ID
     * @param lowerCase 是否小写
     * @return 随机字符串ID
     */
    String getRandomStringId(boolean lowerCase);
}
