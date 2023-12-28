package cloud.jillion.component.common.utils.file;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

/**
 * @author leanderlee
 * @since 1.0.0
 */
public class FileIOUtils {

    public static void writeString(String content, String filepath) {
        writeString(content, filepath, StandardCharsets.UTF_8, false);
    }

    public static void writeString(String content, String filepath, boolean isAppend) {
        writeString(content, filepath, StandardCharsets.UTF_8, isAppend);
    }

    public static void writeString(String content, String filepath, Charset charset, boolean isAppend) {
        try (BufferedWriter writer = getWriter(filepath, charset, isAppend)) {
            writer.write(content);
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static File writeBytes(byte[] bytes, String destFilepath) throws IOException {
        File destFile = new File(destFilepath);
        if (destFile.exists() && destFile.isFile()) {
            throw new IllegalArgumentException(destFilepath + " exists");
        }
        FileUtils.forceMkdirParent(destFile);
        boolean createFile = destFile.createNewFile();
        if (!createFile) {
            throw new IllegalStateException(String.format("create destination file[%s] failed", destFilepath));
        }
        return write(bytes, destFile, 0, bytes.length, false);
    }

    private static File write(byte[] data, File destFile, int off, int len, boolean isAppend) {
        try (FileOutputStream out = new FileOutputStream(destFile, isAppend)) {
            out.write(data, off, len);
            out.flush();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return destFile;
    }

    private static BufferedWriter getWriter(String filepath, Charset charset, boolean isAppend) throws IOException {
        return new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filepath, isAppend), charset));
    }

    public static String readString(File file) throws IOException {
        return readString(file, StandardCharsets.UTF_8);
    }

    public static String readString(File file, Charset charset) throws IOException {
        return new String(readBytes(file), charset);
    }

    public static byte[] readBytes(File file) throws IOException {
        long len = file.length();
        if (len >= Integer.MAX_VALUE) {
            throw new IOException("File is larger then max array size");
        }

        byte[] bytes = new byte[(int) len];
        int readLength;
        try (FileInputStream in = new FileInputStream(file)) {
            readLength = in.read(bytes);
            if (readLength < len) {
                throw new IOException(String.format("File length is [%s] but read [%s]!", len, readLength));
            }
        } catch (Exception e) {
            throw new IOException(e);
        }
        return bytes;
    }

    /**
     * 获取文件MD5
     *
     * @param stream
     * @return
     */
    public static String getFileMD5(InputStream stream) {
        try {
            return DigestUtils.md5Hex(stream);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String copyStreamToFileAndGetMd5Hex(InputStream inputStream, File file) throws IOException {
        MessageDigest digest = DigestUtils.getMd5Digest();
        FileOutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(file);
            byte[] buffer = new byte[2048];
            int read = inputStream.read(buffer);
            while (read > -1) {
                // 计算MD5,顺便写到文件
                digest.update(buffer, 0, read);
                outputStream.write(buffer, 0, read);
                read = inputStream.read(buffer);
            }
        } finally {
            IOUtils.closeQuietly(outputStream);
        }
        return Hex.encodeHexString(digest.digest());
    }
}
