package cloud.jillion.component.common.utils.crypto;

import cn.hutool.crypto.SmUtil;
import cn.hutool.crypto.digest.DigestUtil;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.util.Objects;

/**
 * @author leanderlee
 * @since 1.0.0
 */
public class SignUtils {

    public static String sign(String rawText, SignAlgorithmEnum algorithmEnum) {
        return switch (algorithmEnum) {
            case BCRYPT -> DigestUtil.bcrypt(rawText);
            case MD5 -> DigestUtil.md5Hex(rawText);
            case SM3 -> SmUtil.sm3(rawText);
        };
    }

    public static boolean verify(String rawText, String encryptText, SignAlgorithmEnum algorithmEnum) {
        return switch (algorithmEnum) {
            case BCRYPT -> BCrypt.checkpw(rawText, encryptText);
            case MD5 -> {
                String encrypt = sign(rawText, algorithmEnum);
                yield Objects.equals(encrypt, encryptText);
            }
            case SM3 -> {
                String sm3 = SmUtil.sm3(rawText);
                yield Objects.equals(sm3, encryptText);
            }
        };
    }
}
