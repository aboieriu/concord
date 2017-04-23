package concord.classifier.processor;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import concord.appdao.repository.IPhotoRepository;
import concord.appmodel.ClassifiedPhoto;
import concord.appmodel.ImageItem;
import concord.appmodel.Photo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
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
public class PhotoDownloadProcessor {

	private static final Logger LOGGER = LoggerFactory.getLogger(PhotoDownloadProcessor.class);

	private static final String PHOTO_STORAGE_DIR_PATTERN = "{0}/{1}";

	private static final String PHOTO_STORAGE_PATH_PATTERN = PHOTO_STORAGE_DIR_PATTERN + "/{2}.{3}";


	@Value("${photo.storage.dir}")
	private String basePhotoStorageDir;


	private ObjectMapper objectMapper;

	@Resource
	private IPhotoRepository photoRepository;


	@PostConstruct
	public void init(){
		objectMapper = new ObjectMapper();
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}

	public void handlePhoto(ClassifiedPhoto classifiedPhoto) {
		try {
			handlePhotoInternal(classifiedPhoto.getPhoto());
		} catch (Throwable e) {
			LOGGER.error("Exception caught while downloading classifiedPhoto", e);
			classifiedPhoto.getPhoto().setDownloadFailed(true);
			photoRepository.save(classifiedPhoto.getPhoto());
		}
	}

	public void handlePhoto(Photo photo) {
		try {
			handlePhotoInternal(photo);
		} catch (Throwable e) {
			LOGGER.error("Exception caught while downloading classifiedPhoto", e);
			photo.setDownloadFailed(true);
			photoRepository.save(photo);
		}
	}

	private void handlePhotoInternal(Photo photo) throws Exception{
		LOGGER.info("Download photo -> " + photo.getPhotoId());
		Optional<ImageItem> imageItemOptional = getImageItem(photo);
		String filePath = saveFileToDisk(photo.getPhotoId(), photo.getCategory(), imageItemOptional.get());
		photo.setLocalFilePath(filePath);
		photoRepository.save(photo);
	}

	private String saveFileToDisk(String photoId, Long category, ImageItem imageItem) throws Exception {
		String directoryPath = getFileDirectory(category);
		String targetFileName = getFilePath(photoId,category, imageItem.getFormat());

		Path file = Paths.get(targetFileName);
		if (Files.notExists(file, LinkOption.NOFOLLOW_LINKS)) {
			InputStream in = new URL(imageItem.getUrl()).openStream();
			createDirectoryIfNotExists(directoryPath);
			Files.copy(in, Paths.get(targetFileName));
		} else {
			LOGGER.info("File " + targetFileName + " already exists ... [skiping]");
		}

		return targetFileName;
	}

	private void createDirectoryIfNotExists(String directoryPath) throws Exception {
		Path dirPath = Paths.get(directoryPath);
		if (Files.notExists(dirPath)) {
			Files.createDirectories(dirPath);
		}
	}

	private Optional<ImageItem> getImageItem(Photo photo) {
		if (photo.getImages() == null || photo.getImages().size() == 0) {
			return Optional.empty();
		}

		return Optional.ofNullable(photo.getImages().get(0));
	}

	private String getFileDirectory(Long category) {
		return MessageFormat.format(PHOTO_STORAGE_DIR_PATTERN, basePhotoStorageDir, category);
	}

	private String getFilePath(String photoId, Long category, String format){
		return MessageFormat.format(PHOTO_STORAGE_PATH_PATTERN, basePhotoStorageDir, category, photoId, format);
	}
}
