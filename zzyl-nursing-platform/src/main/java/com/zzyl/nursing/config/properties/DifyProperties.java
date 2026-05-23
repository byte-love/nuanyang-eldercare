package com.zzyl.nursing.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Dify 智能咨询配置（请在 application-dev.yml 或环境变量中配置，勿提交密钥）
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "dify")
public class DifyProperties {

    /** Dify 服务地址，如 http://localhost 或内网地址 */
    private String baseUrl = "http://localhost";

    /** API Key，请求头格式：Bearer app-xxx */
    private String apiKey = "";

    public String getBearerToken() {
        if (apiKey == null || apiKey.isEmpty()) {
            return "";
        }
        return apiKey.startsWith("Bearer ") ? apiKey : "Bearer " + apiKey;
    }
}
