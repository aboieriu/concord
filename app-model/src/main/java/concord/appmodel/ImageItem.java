package concord.appmodel;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by aboieriu on 4/18/17.
 */
public class ImageItem {

	private int size;
	private String url;
	private String httpsUrl;
	private String format;

	@JsonCreator
	public ImageItem(
				@JsonProperty("size") int size,
				@JsonProperty("url") String url,
				@JsonProperty("https_url") String httpsUrl,
				@JsonProperty("format") String format) {
		this.size = size;
		this.url = url;
		this.httpsUrl = httpsUrl;
		this.format = format;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getHttpsUrl() {
		return httpsUrl;
	}

	public void setHttpsUrl(String httpsUrl) {
		this.httpsUrl = httpsUrl;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	@Override
	public String toString() {
		return "ImageItem{" +
						"size=" + size +
						", url='" + url + '\'' +
						", https_url='" + httpsUrl + '\'' +
						", format='" + format + '\'' +
						'}';
	}
}
