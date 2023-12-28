package cloud.jillion.component.common.utils.file;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import java.util.List;

/**
 * @author leanderlee
 * @since 1.0.0
 */
public class FileBaseUtils {

    /**
     * 根据文件名获取拓展名
     *
     * @param filename
     * @return
     */
    public static String getExtension(String filename) {
        return FilenameUtils.getExtension(filename);
    }

    /**
     * 获取文件类型（后缀）
     *
     * @param filename
     * @param fullContentType
     * @return
     */
    public static String getContentType(String filename, String fullContentType) {
        String extensionName = getExtension(filename);
        List<String> extensionNames = MimeTypeMapper.getExtensionNames(fullContentType);
        if (CollectionUtils.isNotEmpty(extensionNames)) {
            if (!extensionNames.contains(extensionName)) {
                return extensionNames.get(0);
            }
        }
        return extensionName;
    }

    /**
     * 格式化文件大小
     *
     * @param fullContentSize
     * @return
     */
    public static String formatContentSize(Long fullContentSize) {
        return FileUtils.byteCountToDisplaySize(fullContentSize);
    }
}
