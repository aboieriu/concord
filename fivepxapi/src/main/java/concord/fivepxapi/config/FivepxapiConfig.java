package concord.fivepxapi.config;

import concord.fivepxapi.client.FivepxHttpClient;
import concord.fivepxapi.impl.FivepxImpl;
import concord.fivepxapi.parser.HttpEntityResponseParser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by aboieriu on 4/18/17.
 */
@Configuration
public class FivepxapiConfig {

	@Bean
	public FivepxImpl getFivepxImpl(){
		return new FivepxImpl(getFivepxHttpClient(), getHttpEntityResponseParser());
	}

	@Bean
	public HttpEntityResponseParser getHttpEntityResponseParser(){
		return new HttpEntityResponseParser();
	}

	@Bean
	public FivepxHttpClient getFivepxHttpClient(){
		return new FivepxHttpClient();
	}
}
