package wx.java.test.config.corp;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@Data
@ConfigurationProperties(prefix = "wx.cp")
public class WxCpProperties {
    /**
     * 设置企业微信的corpId
     */
    private String corpId;

    private List<AppConfig> appConfigs;

    @Data
    public static class AppConfig {
        /**
         * 设置企业微信应用的AgentId
         */
        private Integer agentId;

        /**
         * 设置企业微信应用的Secret
         */
        private String secret;

        /**
         * 设置企业微信应用的token
         */
        private String token;

        /**
         * 设置企业微信应用的EncodingAESKey
         */
        private String aesKey;
    }
}
