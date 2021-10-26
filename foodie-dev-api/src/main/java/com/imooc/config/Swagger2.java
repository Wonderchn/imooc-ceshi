package com.imooc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class Swagger2 {
    //http://localhost:8088/swagger-ui.html
    //http://localhost:8088/doc.html


    //配置Swagger2 核心配置
    @Bean
    public Docket createRestApi(){
        //指定api类型为swagger2
        return  new Docket(DocumentationType.SWAGGER_2)
                //用于定义api文档汇总信息
                .apiInfo(apiInfo())
                .select()
                //指定Controller包
                .apis(RequestHandlerSelectors
                        .basePackage("com.imooc.controller"))
                .paths(PathSelectors.any())
                .build();
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
