package com.shaunghu_hrms.shuanghu.config;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry; // 导入这个
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Configuration
@MapperScan("com.shaunghu_hrms.shuanghu.mapper")
public class WebConfig implements WebMvcConfigurer {

    // --- 1. 拦截器配置 (新增部分) ---
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册我们刚才写的 LoginInterceptor
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/api/**")  // 拦截所有 /api/ 开头的接口
                .excludePathPatterns(        // 排除不需要登录就能访问的接口
                        "/api/login",        // 登录接口
                        "/api/register",     // 注册接口
                        "/static/**"         // 静态资源
                );
    }

    // --- 2. 静态资源映射 (保持不变) ---
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/");

        // 头像映射
        registry.addResourceHandler("/profile_photos/**")
                .addResourceLocations("file:F:/IDEA/IdeaProject/ShuangHu/user_avatars/");
    }

    // --- 3. 跨域配置 (保持不变) ---
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowCredentials(true)
                .maxAge(3600);
    }

    // --- 4. FastJson 配置 (保持不变) ---
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
        FastJsonConfig config = new FastJsonConfig();
        config.setSerializerFeatures(
                SerializerFeature.PrettyFormat,
                SerializerFeature.WriteMapNullValue,
                SerializerFeature.WriteDateUseDateFormat,
                SerializerFeature.DisableCircularReferenceDetect
        );
        converter.setFastJsonConfig(config);
        List<MediaType> supportedMediaTypes = new ArrayList<>();
        supportedMediaTypes.add(MediaType.APPLICATION_JSON);
        supportedMediaTypes.add(new MediaType("application", "json", StandardCharsets.UTF_8));
        converter.setSupportedMediaTypes(supportedMediaTypes);
        converters.add(0, converter);
    }
}