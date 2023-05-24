package org.jeecg.modules;

import org.apache.commons.lang3.StringUtils;
import org.apereo.cas.authentication.*;
import org.apereo.cas.authentication.handler.support.AbstractPreAndPostProcessingAuthenticationHandler;
import org.apereo.cas.authentication.principal.PrincipalFactory;
import org.apereo.cas.services.ServicesManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.security.auth.login.AccountNotFoundException;
import javax.security.auth.login.FailedLoginException;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class UsernamePasswordCaptchaAuthenticationHandler extends AbstractPreAndPostProcessingAuthenticationHandler {

    /**
     * 数据源驱动类型
     */
    @Value("${jdbc.ds.driverClassName}")
    private String driver;

    /**
     * 连接地址
     */
    @Value("${jdbc.ds.url}")
    private String url;

    /**
     * 用户名
     */
    @Value("${jdbc.ds.username}")
    private String dataUsername;

    /**
     * 密码
     */
    @Value("${jdbc.ds.password}")
    private String dataPassword;

    public UsernamePasswordCaptchaAuthenticationHandler(String name, ServicesManager servicesManager,
                                                        PrincipalFactory principalFactory, Integer order) {
        super(name, servicesManager, principalFactory, order);
        // TODO Auto-generated constructor stub
    }

    @Override
    protected AuthenticationHandlerExecutionResult doAuthentication(Credential credential)
            throws GeneralSecurityException, PreventedException {
        // TODO Auto-generated method stub
        // 用户凭证
        UsernamePasswordCredential myCredential = (UsernamePasswordCredential) credential;

        //验证码相关
        /*String requestCaptcha = myCredential.getCaptcha();
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        Object attribute = attributes.getRequest().getSession().getAttribute("captcha");

        String realCaptcha = attribute == null ? null : attribute.toString();

        if (StringUtils.isBlank(requestCaptcha) || !requestCaptcha.equalsIgnoreCase(realCaptcha)) {
            throw new CaptchaException("验证码错误");
        }*/
        String username = myCredential.getUsername();
        String password = myCredential.getPassword();
//        String password = "pilAHbNOG7KnGHSu+4VG29PRDDLms221EnDKh0R2K4AQ/VrSHl2EEFCofXd4a7lD";
        // 验证用户名和密码
        DriverManagerDataSource d = new DriverManagerDataSource();
        d.setDriverClassName(driver);
        d.setUrl(url);
        d.setUsername(dataUsername);
        d.setPassword(dataPassword);

        JdbcTemplate template = new JdbcTemplate();
        template.setDataSource(d);

        // 查询数据库加密的的密码
        Map<String, Object> user = template.queryForMap("select * from sys_user where username=?",
                username);

        if (user == null) {
            throw new AccountNotFoundException("用户名输入错误或用户名不存在");
        }

        // 返回多属性
        Map<String, Object> map = new HashMap<>();
        map.put("username", username);
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(password.getBytes());
            byte b[] = md.digest();

            int i;

            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            // 32位加密
            password = buf.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        /*try {
            password = AesEncryptUtil.desEncrypt(password).trim();
        }catch (Exception e){
            throw new FailedLoginException("密码错误");
        }
        password = password.substring(13);*/
        String userpassword = PasswordUtil.encrypt(username, password, user.get("salt").toString());
        String syspassword = user.get("password").toString();
        if (syspassword.equals(userpassword)) {
            final List<MessageDescriptor> list = new ArrayList<>();

            return createHandlerResult(myCredential,
                    this.principalFactory.createPrincipal(username, Collections.emptyMap()), list);
        }

        throw new FailedLoginException("密码输入错误");
    }

    // 判断是否支持自定义用户登入凭证
    @Override
    public boolean supports(Credential credential) {
        // TODO Auto-generated method stub
        return credential instanceof UsernamePasswordCredential;
    }
}
