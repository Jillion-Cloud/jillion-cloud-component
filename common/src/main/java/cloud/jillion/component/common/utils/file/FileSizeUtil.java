package cloud.jillion.component.common.utils.file;

import java.text.DecimalFormat;

/**
 * @author leanderlee
 * @since 1.0.0
 */
public class FileSizeUtil {

    public static String formatSize(Long fileLength) {
        String fileSizeString = "";
        if (fileLength == null) {
            return fileSizeString;
        }
        DecimalFormat df = new DecimalFormat("#.00");
        if (fileLength < 1024) {
            fileSizeString = df.format((double) fileLength) + "B";
        }
        else if (fileLength < 1048576) {
            fileSizeString = df.format((double) fileLength / 1024) + "KB";
        }
        else if (fileLength < 1073741824) {
            fileSizeString = df.format((double) fileLength / 1048576) + "MB";
        }
        else {
            fileSizeString = df.format((double) fileLength / 1073741824) + "GB";
        }
        return fileSizeString;
    }
}
