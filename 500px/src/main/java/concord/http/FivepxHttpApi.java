package concord.http;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import concord.config.BaseConfig;
import concord.domain.AuthCredentials;
import concord.domain.AuthResult;
import concord.http.connection.ConnectionCtx;
import org.apache.http.client.methods.HttpPost;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Created by aboieriu on 9/13/16.
 */
public class FivePxHttpApi implements IFivePxHttpApi {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public AuthResult authenticateUser(AuthCredentials authCredentials){
        Optional<ConnectionCtx> connectionCtxOptional = getNewLoginAuthorizationContext();
        if (connectionCtxOptional.isPresent()) {
            ConnectionCtx connectionCtx = connectionCtxOptional.get();
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                authCredentials.setAuthenticity_token(connectionCtx.getAuthorizationToken());
                HttpResponse<JsonNode> responseTmp = Unirest.post(BaseConfig.LOGIN_ENDPOINT_URL)
                        .header("content-type", "application/json")
                        .header("cache-control", "no-cache")
                        .header("Cookie", flattenCookies(connectionCtx.getCookies()))
                        .body(objectMapper.writeValueAsString(authCredentials))
                        .asJson();

                if (responseTmp != null && responseTmp.getStatus() == 200) {
                    String cookies = null;
                    List<String> setCookies = responseTmp.getHeaders().get("Set-Cookie");
                    if (setCookies != null && setCookies.size() > 0) {
                        cookies = String.join(";", setCookies);
                    }
                    return new AuthResult(responseTmp.getBody().getObject().get("user").toString(), cookies);
                }
            } catch (UnirestException e) {
                logger.error(e.getMessage(), e);
            } catch (JsonProcessingException e) {
                logger.error(e.getMessage(), e);
            }
        }
        throw new NoConnectionContext();
    }

    private Optional<ConnectionCtx> getNewLoginAuthorizationContext() {
        Optional<ConnectionCtx> token = Optional.empty();
        try {
            Connection.Response response = Jsoup.connect(BaseConfig.LOGIN_URL).execute();
            Document doc = response.parse();
            Elements metaLinks = doc.select("meta[name=csrf-token]");
            if(metaLinks.size() > 0) {
                token = Optional.ofNullable(new ConnectionCtx(response.cookies(), metaLinks.get(0).attr("content")));
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        return token;
    }

    private String flattenCookies(Map<String, String> cookies) {
        StringBuilder sb = new StringBuilder();
        if (cookies != null && !cookies.isEmpty()) {
            cookies.entrySet().iterator().forEachRemaining(entry -> sb.append(entry.getKey()).append("=").append(entry.getValue()).append(";"));
        }
        return sb.toString();
    }
}
