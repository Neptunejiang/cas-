package org.jeecg.modules;

import org.apereo.cas.authentication.AuthenticationEventExecutionPlan;
import org.apereo.cas.authentication.AuthenticationEventExecutionPlanConfigurer;
import org.apereo.cas.authentication.AuthenticationHandler;
import org.apereo.cas.authentication.principal.DefaultPrincipalFactory;
import org.apereo.cas.authentication.principal.PrincipalFactory;
import org.apereo.cas.configuration.CasConfigurationProperties;
import org.apereo.cas.services.ServicesManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
//注入自定义的spring的datasource，以便自己可以通过jdbc执行sql
@Configuration("usernamePasswordCaptchaConfig")
@EnableConfigurationProperties(CasConfigurationProperties.class)
public class UsernamePasswordCaptchaConfig implements AuthenticationEventExecutionPlanConfigurer{

    @Autowired
    private CasConfigurationProperties casProperties;

    @Autowired
    @Qualifier("servicesManager")
    private ServicesManager servicesManager;

    /**
     * 用户定义用户登入处理器
     * @return
     */
    @Bean
    public AuthenticationHandler rememberMeUsernamePasswordCaptchaAuthenticationHandler() {
        UsernamePasswordCaptchaAuthenticationHandler handler = new UsernamePasswordCaptchaAuthenticationHandler(
                UsernamePasswordCaptchaAuthenticationHandler.class.getSimpleName(),
                servicesManager,
                new DefaultPrincipalFactory(),
                9);
        return handler;
    }

    /**
     *
     * <p>Title: configureAuthenticationExecutionPlan</p>
     * <p>Description: 用户自定义表单处理注册 </p>
     * @param plan
     * @see org.apereo.cas.authentication.AuthenticationEventExecutionPlanConfigurer#configureAuthenticationExecutionPlan(org.apereo.cas.authentication.AuthenticationEventExecutionPlan)
     */
    @Override
    public void configureAuthenticationExecutionPlan(AuthenticationEventExecutionPlan plan) {
        // TODO Auto-generated method stub
        plan.registerAuthenticationHandler(rememberMeUsernamePasswordCaptchaAuthenticationHandler());
    }

}
