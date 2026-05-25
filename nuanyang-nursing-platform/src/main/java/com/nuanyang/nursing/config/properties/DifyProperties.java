package com.nuanyang.nursing.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Dify ������ѯ���ã����� application-dev.yml �򻷾����������ã����ύ��Կ��
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "dify")
public class DifyProperties {

    /** Dify �����ַ���� http://localhost ��������ַ */
    private String baseUrl = "http://localhost";

    /** API Key������ͷ��ʽ��Bearer app-xxx */
    private String apiKey = "";

    public String getBearerToken() {
        if (apiKey == null || apiKey.isEmpty()) {
            return "";
        }
        return apiKey.startsWith("Bearer ") ? apiKey : "Bearer " + apiKey;
    }
}
