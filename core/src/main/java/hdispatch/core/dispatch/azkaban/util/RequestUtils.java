package hdispatch.core.dispatch.azkaban.util;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.HttpRequest;
import com.mashape.unirest.request.HttpRequestWithBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.Map;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by liuneng on 16-8-30.
 */

/**
 * Supports http request method.
 */
public class RequestUtils {
    private static Logger logger = LoggerFactory.getLogger(RequestUtils.class);
    private static final String SESSION_ID = "session.id";
    private static final String URL_SEPERATOR = "/";
    private static final Gson gson = new Gson();

    private static SessionIdGetter getter = new SessionIdGetter();

    private static String host;
    private static String username;
    private static String password;
    private static String period;

    private static Timer timer = new Timer();

    static {
        Properties properties = new Properties();
        try {
            properties.load(RequestUtils.class.getClassLoader().getResourceAsStream("config.properties"));
            host = properties.getProperty("azkaban.address");
            username = properties.getProperty("azkaban.user");
            password = properties.getProperty("azkaban.password");
            period = properties.getProperty("azkaban.period", String.valueOf(20 * 60 * 1000));

            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    getter.setActive(false);
                }
            }, Integer.valueOf(period), Integer.valueOf(period));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static HttpRequest get(String uri) {
        return Unirest.get(host + URL_SEPERATOR + uri).queryString(SESSION_ID, getter.getSessionId());
    }

    public static HttpRequestWithBody post(String uri) {

        return Unirest.post(host + URL_SEPERATOR + uri + "?session.id=" + getter.getSessionId());
    }

    private static class SessionIdGetter {
        private String sessionId;
        private boolean active = false;

        public synchronized String getSessionId() {
            if (StringUtils.isEmpty(sessionId) || !isActive()) {
                String response;
                try {
                    response = Unirest.post(host).header("accept", "application/json").field("action", "login")
                            .field("username", username).field("password", password).asString().getBody();
                } catch (UnirestException e) {
                    logger.error("登录azkaban出错", e);
                    throw new IllegalStateException("登录azkaban出错", e);
                }
                Map<String, String> result = gson.fromJson(response, new TypeToken<Map<String, String>>() {
                }.getType());
                String error = result.get("error");
                if (!StringUtils.isEmpty(error)) {
                    logger.error("azkaban用户名或密码配置错误");
                    throw new IllegalStateException("azkaban用户名或密码配置错误");
                } else {
                    sessionId = result.get("session.id");
                }
                active = true;
            }
            return sessionId;
        }

        public synchronized SessionIdGetter setActive(boolean active) {
            this.active = active;
            return this;
        }

        private synchronized boolean isActive() {
            return active;
        }
    }

}
