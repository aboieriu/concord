package concord.fivepxapi.client;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

/**
 * Created by aboieriu on 4/18/17.
 */
public class FivepxHttpClient {

	private static Logger LOGGER = LoggerFactory.getLogger(FivepxHttpClient.class);

	public FivepxHttpClient(){

	}

	public Optional<HttpResponse> executeRequest(HttpUriRequest request) {
		HttpClient httpClient = HttpClientBuilder.create().build();
		try {
			return Optional.ofNullable(httpClient.execute(request));
		} catch (Exception e) {
			LOGGER.error("Something went wrong with the request", e);
		}

		return Optional.empty();
	}
}
