package cloud.jillion.component.common.i18n;

import org.springframework.context.i18n.LocaleContextHolder;

import java.util.Locale;

/**
 * Locale Helper
 *
 * @author leanderlee
 * @since 1.0.0
 */
public class LocaleHelper {

    public static boolean isZhCN() {
        Locale locale = LocaleContextHolder.getLocale();
        return Locale.CHINA.equals(locale);
    }

    public static boolean isEnUS() {
        Locale locale = LocaleContextHolder.getLocale();
        return Locale.US.equals(locale);
    }
}
