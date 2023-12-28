package cloud.jillion.component.common.utils.file;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author leanderlee
 * @since 1.0.0
 */
public class MimeTypeMapper {

    private static final Map<String, Object> MAPPING = new HashMap<>(64);

    static {
        InputStream inputStream = null;
        try {
            inputStream = MimeTypeMapper.class.getResourceAsStream("/mime-type.json");
            if (null == inputStream) {
                throw new RuntimeException("mime-type.json is not exists");
            }
            byte[] bytes = inputStream.readAllBytes();
            String json = new String(bytes, StandardCharsets.UTF_8);
            JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();
            if (null != jsonObject) {
                for (String jsonKey : jsonObject.keySet()) {
                    List<String> extensionNames = new Gson().fromJson(jsonObject.get(jsonKey), new TypeToken<List<String>>(){}.getType());
                    MAPPING.put(jsonKey, extensionNames);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (null != inputStream) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public static List<String> getExtensionNames(String mimeType) {
        return (List<String>) MAPPING.get(mimeType);
    }
}
