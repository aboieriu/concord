package concord.config;

/**
 * Created by aboieriu on 9/13/16.
 */
public class BaseConfig {
    public static final String BASE_PROTOCOL = "http://";
    public static final String BASE_URL = BASE_PROTOCOL + "500px.com";
    public static final String LOGIN_URL = BASE_URL + "/login";

    public static final String BASE_API_PROTOCOL = "https://";
    public static final String BASE_API_URL = BASE_API_PROTOCOL + "api.500px.com";
    public static final String LOGIN_ENDPOINT_URL = BASE_API_URL + "/v1/session"; //"http://localhost:9000/test";
}
