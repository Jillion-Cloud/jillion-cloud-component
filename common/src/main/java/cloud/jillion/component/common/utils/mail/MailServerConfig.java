package cloud.jillion.component.common.utils.mail;

import lombok.Builder;
import lombok.Data;

/**
 * Mail Server Config
 *
 * @author leanderlee
 * @since 1.0.0
 */
@Data
@Builder
public class MailServerConfig {

    private String serverHost;

    private String serverPort;

    private String serverUsername;

    private String serverPassword;

}
