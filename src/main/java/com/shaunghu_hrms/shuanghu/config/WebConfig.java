package com.shaunghu_hrms.shuanghu.config;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Configuration
@MapperScan("com.shaunghu_hrms.shuanghu.mapper")
public class WebConfig implements WebMvcConfigurer {

    /**
     * 1. 解决“界面加载不出来”的核心配置
     * 显式告诉 Spring Boot：所有静态资源都在 classpath:/static/ 下找
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 映射所有路径 /** 到 static 目录
        // 这样 http://localhost:8080/BuMen/department_manage.html 才能被找到
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/");
    }

    /**
     * 2. 解决跨域问题 (CORS)
     * 允许前端从任意域名访问接口，防止浏览器报 CORS 错误
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowCredentials(true)
                .maxAge(3600);
    }

    /**
     * 3. 配置 FastJson 消息转换器 (保持你原来的逻辑，这部分没问题)
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        // 创建 FastJson 消息转换器
        FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();

        // 配置 FastJson 的序列化规则
        FastJsonConfig config = new FastJsonConfig();
        config.setSerializerFeatures(
                SerializerFeature.PrettyFormat,           // 格式化输出 JSON
                SerializerFeature.WriteMapNullValue,      // 保留空的字段
                SerializerFeature.WriteDateUseDateFormat, // 统一日期格式 yyyy-MM-dd HH:mm:ss
                SerializerFeature.DisableCircularReferenceDetect // 禁止循环引用检测
        );
        converter.setFastJsonConfig(config);

        // 解决 "Content-Type cannot contain wildcard type '*'" 报错
        List<MediaType> supportedMediaTypes = new ArrayList<>();
        supportedMediaTypes.add(MediaType.APPLICATION_JSON);
        supportedMediaTypes.add(new MediaType("application", "json", StandardCharsets.UTF_8));
        converter.setSupportedMediaTypes(supportedMediaTypes);

        // 将转换器添加到列表第一位，确保优先使用
        converters.add(0, converter);
    }

    // ⚠️ 注意：如果你有 LoginInterceptor (登录拦截器)，需要在这里重写 addInterceptors 方法进行注册
    // 目前你的代码里没写，如果需要请告诉我，我再发给你。
}