package ru.yaneg.graduation_of_topjava_springboot.security;

import ru.yaneg.graduation_of_topjava_springboot.SpringApplicationContext;

public class SecurityConstants {
    public static final long EXPIRATION_TIME = 864000000; // 10 days
    public static final String TOKEN_PREFIX = "GoTJSBA ";
    public static final String H2_CONSOLE = "/h2-console/**";
    public static final String HEADER_STRING = "Authorization";
    public static final String SIGN_UP_URL = "/users";

    public static String getTokenSecret() {
        AppProperties appProperties = (AppProperties) SpringApplicationContext.getBean("AppProperties");
        return appProperties.getTokenSecret();
    }
}
