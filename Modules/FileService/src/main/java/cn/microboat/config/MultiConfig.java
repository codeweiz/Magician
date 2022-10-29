package cn.microboat.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.unit.DataSize;
import org.springframework.util.unit.DataUnit;

import javax.servlet.MultipartConfigElement;

/**
 * tomcat 上传文件配置
 *
 * @author zhouwei
 */
@Configuration
@ConfigurationProperties(prefix = "spring.http.multipart")
public class MultiConfig {

    /**
     * 是否开启提升上传文件大小限制
     */
    private boolean enabled;

    /**
     * 单个文件最大尺寸
     */
    private String maxFileSize;

    /**
     * 单次请求中的所有文件最大尺寸
     */
    private String maxRequestSize;


    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        if (enabled) {
            factory.setMaxFileSize(DataSize.parse(maxFileSize, DataUnit.MEGABYTES));
            factory.setMaxRequestSize(DataSize.parse(maxRequestSize, DataUnit.MEGABYTES));
        }
        return factory.createMultipartConfig();
    }


    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getMaxFileSize() {
        return maxFileSize;
    }

    public void setMaxFileSize(String maxFileSize) {
        this.maxFileSize = maxFileSize;
    }

    public String getMaxRequestSize() {
        return maxRequestSize;
    }

    public void setMaxRequestSize(String maxRequestSize) {
        this.maxRequestSize = maxRequestSize;
    }
}
