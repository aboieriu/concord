package concord.fivepxapi.parser;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

/**
 * Created by aboieriu on 4/18/17.
 */
public class HttpEntityResponseParser {
	private static Logger LOGGER = LoggerFactory.getLogger(HttpEntityResponseParser.class);

	private ObjectMapper objectMapper;

	public HttpEntityResponseParser() {
		objectMapper = new ObjectMapper();
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}

	public <T> Optional<T> parseResponse (Optional<HttpResponse> responseOptional, Class <T> type) {
		if (!responseOptional.isPresent()) {
			return Optional.empty();
		}

		HttpResponse httpResponse = responseOptional.get();
		try {
			InputStream entityInputStream = httpResponse.getEntity().getContent();
			String result = IOUtils.toString(entityInputStream, StandardCharsets.UTF_8.displayName());
			T entity = objectMapper.readValue(result, type);
			return Optional.of(entity);

		} catch (IOException e) {
			LOGGER.error("Unable to read entity", e);
		}

		return Optional.empty();
	}
}
