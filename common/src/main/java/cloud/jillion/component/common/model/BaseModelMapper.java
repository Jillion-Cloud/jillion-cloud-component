package cloud.jillion.component.common.model;

import org.mapstruct.Named;

import java.util.Arrays;
import java.util.List;

/**
 * @author leanderlee
 * @since 1.0.0
 */
public interface BaseModelMapper {

    @Named("stringToList")
    default List<String> stringToList(String value) {
        if (value == null) {
            return null;
        }
        return Arrays.stream(value.split(",")).toList();
    }

    @Named("listToString")
    default String listToString(List<String> values) {
        if (values == null) {
            return null;
        }
        return String.join(",", values);
    }
}
