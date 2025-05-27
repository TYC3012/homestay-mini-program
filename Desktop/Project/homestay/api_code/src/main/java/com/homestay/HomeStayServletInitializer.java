package com.homestay;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * web容器中进行部署
 * 
 * @author ruoyi
 */
public class HomeStayServletInitializer extends SpringBootServletInitializer
{
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application)
    {
        return application.sources(HomeStayApplication.class);
    }
}
