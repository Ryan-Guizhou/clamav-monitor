package com.clamav.backend.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Indexed;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * @Author Mr Shu
 * @Version 1.0.0
 * @Description //TODO
 * @CreateTime 2025/7/12 15:13
 */
@Slf4j
@Indexed
@Configuration
public class Knife4jConfig {

    @Bean
    public Docket docket() {
        Contact contact = new Contact("PEACH","https://github.com/Ryan-Guizhou","huanhuanshu48@gmail.com");
        Docket docket=new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(new ApiInfoBuilder()
                        .title("CLAMAV_MOITOR-API文档")
                        .description("CLAMAV_MOITOR-API文档")
                        .contact(contact)
                        .version("CLAMAV_MOITOR-1.0.0")
                        .build())
                //分组名称
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.clamav.backend"))
                .build();
        log.info("knife4j FILE_API has been configured");
        return docket;
    }
}
