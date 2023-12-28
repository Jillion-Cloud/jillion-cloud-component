package cloud.jillion.component.common.utils.date;

/**
 * @author leanderlee
 * @since 1.0.0
 */
public enum DateFormatEnum {

    PATTERN_0("yyyy-MM-dd HH:mm:ss"),
    PATTERN_1("yyyy-MM-dd-HH-mm-ss"),
    PATTERN_2("yyyy-MM-dd-HH-mm"),
    PATTERN_3("yyyy-MM-dd")
    ;

    private final String pattern;

    DateFormatEnum(String pattern) {
        this.pattern = pattern;
    }

    public String getPattern() {
        return pattern;
    }
}
