package com.gszcn.aptrunner.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author leiyunlong
 * @version 1.0
 * @date 2019/12/16 10:56
 */
@Component
@Configuration
@ConfigurationProperties("app.sql.config")
@Getter
@Setter
public class SqlContext {
    private Boolean enable=false;
    /**
     * sql路径
     */
    private List<String> data;
    /**
     * sql执行策略
     * true 有错误继续向下执行
     * false 有错误不再执行
     */
    private boolean continueOnError = true;
}
