package hdispatch.core.dispatch.azkaban.util;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.HttpRequest;
import com.mashape.unirest.request.HttpRequestWithBody;
import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * Created by liuneng on 16-8-30.
 */

/**
 * Supports http request method.
 */
public class RequestUtils {
    private static Logger logger = Logger.getLogger(RequestUtils.class);
    private static final String SESSION_ID = "session.id";
    private static final String URL_SEPERATOR = "/";
    private static final Gson gson = new Gson();

    private static SessionIdGetter getter = new SessionIdGetter();

    private static String host;
    private static String username;
    private static String password;

    static {
        // 需要添加从配置文件读取的逻辑
        host = "http://172.20.0.28:12320";
        username = "azkaban";
        password = username;
    }

    public static HttpRequest get(String uri) {
        return Unirest.get(host + URL_SEPERATOR + uri);
    }

    public static HttpRequestWithBody post(String uri) {
        return Unirest.post(host + URL_SEPERATOR + uri);
    }

    private static class SessionIdGetter {
        private String sessionId;

        public String getSessionId() {
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
            }
            return sessionId;
        }

        private boolean isActive() {
            return true;
        }
    }

}
