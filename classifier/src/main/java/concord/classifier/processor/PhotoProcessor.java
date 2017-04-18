package concord.classifier.processor;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import concord.appmodel.ImageItem;
import concord.appmodel.Photo;
import org.apache.camel.Exchange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.MessageFormat;
import java.util.Optional;

/**
 * Created by aboieriu on 4/18/17.
 */
@Component
public class PhotoProcessor {

	private static final Logger LOGGER = LoggerFactory.getLogger(PhotoProcessor.class);

	private static final String PHOTO_STORAGE_DIR_PATTERN = "{0}/{1}.{2}";

	@Value("${photo.storage.dir}")
	private String basePhotoStorageDir;

	private ObjectMapper objectMapper;

	@PostConstruct
	public void init(){
		objectMapper = new ObjectMapper();
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}

	public void handlePhoto(Exchange exchange) throws Exception {

		String message = exchange.getIn().getBody(String.class);

		Photo photo = null;
		try {
			photo = objectMapper.readValue(message, Photo.class);
		} catch (Exception e) {
			LOGGER.error("Unable to parse to Photo.class", e);
		}

		if (photo != null) {
			Optional<ImageItem> imageItemOptional = getImageItem(photo);
			if (imageItemOptional.isPresent()) {
				saveFileToDisk(photo.getPhotoId(), imageItemOptional.get());
			} else {
				throw new RuntimeException("Unable to get a proper image for photo id: " + photo.getPhotoId());
			}
		}
	}

	public void saveFileToDisk(String photoId, ImageItem imageItem) throws Exception {
		String targetFileName = MessageFormat.format(PHOTO_STORAGE_DIR_PATTERN, basePhotoStorageDir, photoId, imageItem.getFormat());
		Path file = Paths.get(targetFileName);
		if (Files.notExists(file, LinkOption.NOFOLLOW_LINKS)) {
			try (InputStream in = new URL(imageItem.getUrl()).openStream()) {
				Files.copy(in, Paths.get(targetFileName));
			}
		} else {
			LOGGER.info("File " + targetFileName + " already exists ... [skiping]");
		}
	}

	public Optional<ImageItem> getImageItem(Photo photo) {
		if (photo.getImages() == null || photo.getImages().size() == 0) {
			return Optional.empty();
		}

		return Optional.ofNullable(photo.getImages().get(0));
	}
}
