package concord.fivepxapi.impl;

import com.google.common.base.Preconditions;
import concord.fivepxapi.api.IFivepx;
import concord.fivepxapi.api.response.PhotoResponse;
import concord.fivepxapi.api.response.UserResponse;
import concord.fivepxapi.client.FivepxHttpClient;
import concord.fivepxapi.constant.FivePxApiConstants;
import concord.fivepxapi.parser.HttpEntityResponseParser;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.net.URLEncoder;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;

/**
 * Created by aboieriu on 4/18/17.
 */
public class FivepxImpl implements IFivepx {

	private static Logger LOGGER = LoggerFactory.getLogger(FivepxImpl.class);

	private final FivepxHttpClient client;
	private final HttpEntityResponseParser parser;

	public FivepxImpl(FivepxHttpClient client, HttpEntityResponseParser parser) {
		this.client = Preconditions.checkNotNull(client, "client must be initialized");
		this.parser = Preconditions.checkNotNull(parser, "parser must be initialized");
	}

	@Override
	public Optional<UserResponse> getUserDetails(Serializable userId){

		HttpGet httpGet = new HttpGet(getUserUrl(userId));

		Optional<HttpResponse> responseOptional = client.executeRequest(httpGet);

		return parser.parseResponse(responseOptional, UserResponse.class);
	}

	@Override
	public Optional<PhotoResponse> getPhotos(String category, String feature, int page) {

		HttpGet httpGet = new HttpGet(getPhotosUrl(category, feature, page));

		Optional<HttpResponse> responseOptional = client.executeRequest(httpGet);

		return parser.parseResponse(responseOptional, PhotoResponse.class);
	}

	private String getBaseUrl(){
		return FivePxApiConstants.baseApiUrl;
	}

	private String getUserUrl(Serializable userId){
		String userEndpoint = MessageFormat.format(getBaseUrl() + FivePxApiConstants.userEndpoint, userId);
		return buildFinalUrl(userEndpoint, getBaseParameters());
	}

	private String getPhotosUrl(String category, String feature, int page) {
		Map<String, String> parameters = getBaseParameters();
		if (category != null) {
			parameters.put(FivePxApiConstants.PHOTO_CATEGORY, category);
		}
		if (feature != null) {
			parameters.put(FivePxApiConstants.PHOTO_FEATURE, feature);
		}
		parameters.put(FivePxApiConstants.PAGE, String.valueOf(page));
		parameters.put(FivePxApiConstants.SIZE, FivePxApiConstants.DEFAULT_PHOTO_SIZE);
		parameters.put(FivePxApiConstants.PER_PAGE, FivePxApiConstants.PER_PAGE_VALUE);

		String photoEndpoint = getBaseUrl() + FivePxApiConstants.photoEndpoint;

		return buildFinalUrl(photoEndpoint, parameters);

	}

	private Map<String, String> getBaseParameters(){
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put(FivePxApiConstants.CONSUMER_KEY, FivePxApiConstants.appConsumerKey);
		return parameters;
	}

	private String buildFinalUrl(String endpoint, Map<String, String> parameters) {
		return endpoint + FivePxApiConstants.QUERY_URL_SEPARATOR + getParametersAsQueryString(parameters);
	}

	private String getParametersAsQueryString(Map<String, String> stringStringMap){
		StringBuilder stringBuilder = new StringBuilder();

		Iterator<Map.Entry<String, String>> iterator = stringStringMap.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry<String, String> entry = iterator.next();
            try {
	            stringBuilder.append(entry.getKey()).append(FivePxApiConstants.QUERY_KEY_VALUE_SEPARATOR).append(URLEncoder.encode(entry.getValue(), "UTF-8"));
	            if (iterator.hasNext()) {
		            stringBuilder.append(FivePxApiConstants.QUERY_PARAM_SEPARATOR);
	            }
            } catch (Exception e) {
	            LOGGER.error("Url encoding for param failed" , e);
            }
		}

		return stringBuilder.toString();
	}
}
