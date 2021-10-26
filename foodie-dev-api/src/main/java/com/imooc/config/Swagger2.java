package com.imooc.config;

import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class Swagger2 {
    //配置Swagger2 核心配置
    public Docket createRestApi(){
        //指定api类型为swagger2
        return  new Docket(DocumentationType.SWAGGER_2)
                //用于定义api文档汇总信息
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo()
    {
        return new ApiInfoBuilder()
                //文档页标题
                .title("天天吃货，电商平台接口API")

                .contact(new Contact("wonder",
                        "https://www.cnblogs.com/xiaochenNN/",
                        "wonderchn@foxmail.com"
                        ))
                .description("天天吃货的API文档")
                .version("1.0.1")
                .termsOfServiceUrl("https://www.imooc.com")
                .build();

    }}
