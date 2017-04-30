package concord.classifier;

import concord.appmodel.ClassificationModel;
import concord.appmodel.Photo;
import concord.appmodel.domain.AiClassification;
import concord.appmodel.domain.PhotoCategory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by aboieriu on 4/23/17.
 */
@Component
public class TensorFlowClassifier {
	private static final Logger LOGGER = LoggerFactory.getLogger(TensorFlowClassifier.class);
	private static final String CLASSIFY_COMMAND_PATTERN = "python {0} {1} {2} {3}";
	private static final String CLASSIFICATION_OUTPUT_MATCHER = "[a-zA-Z0-9]+ \\(score = (?:\\d.\\d+)\\)";
	private static final String SCORE_OUTPUT_FORMAT = "(?:\\d*\\.)?\\d+";

	@Value("${classify.file.path}")
	private String classifierFilePath;

	public AiClassification classifyPhoto(Photo photo, ClassificationModel classificationModel) throws Exception{
		if (classificationModel.isTrained()) {
			String classificationCommand = buildClassificationCommand(photo, classificationModel);

			// Execute train command
			LOGGER.info("Excuting command: " + classificationCommand);
			Process p = Runtime.getRuntime().exec(classificationCommand);

			BufferedReader output = getOutput(p);

			StringBuilder outStringBuilder = new StringBuilder();
			BufferedReader errorOutput = getError(p);

			String line;

			while ((line = output.readLine()) != null) {
				LOGGER.info(line);
				outStringBuilder.append(line);
			}

			LOGGER.info("Checking out error output of command");
			while ((line = errorOutput.readLine()) != null) {
				LOGGER.info(line);
			}

			return parseResponse(outStringBuilder.toString(), classificationModel);

		} else {
			throw new RuntimeException("Unable to classify using model " + classificationModel.getId() + ". Model is not trained yet");
		}
	}

	private static BufferedReader getOutput(Process p) {
		return new BufferedReader(new InputStreamReader(p.getInputStream()));
	}

	private static BufferedReader getError(Process p) {
		return new BufferedReader(new InputStreamReader(p.getErrorStream()));
	}

	private String buildClassificationCommand(Photo photo, ClassificationModel model) {
		return MessageFormat.format(CLASSIFY_COMMAND_PATTERN, classifierFilePath, photo.getLocalFilePath(), model.getModelConfig().getLabelsPath(), model.getModelConfig().getGraphPath());
	}

	private AiClassification parseResponse(String commandOutput, ClassificationModel model) {
		Pattern patternForOutput = Pattern.compile(CLASSIFICATION_OUTPUT_MATCHER);
		Matcher matcher = patternForOutput.matcher(commandOutput);
		Map<PhotoCategory, Double> scores = new HashMap<>();

		LOGGER.info(commandOutput);
		LOGGER.info("Pars: found " + matcher.groupCount());

		while(matcher.find()) {
			String scoreLine = matcher.group();
			LOGGER.info(scoreLine);
			PhotoCategory label = PhotoCategory.valueOf(scoreLine.substring(0, 6).toUpperCase());
			scoreLine = scoreLine.substring(6, scoreLine.length());

			Pattern patternForScore = Pattern.compile(SCORE_OUTPUT_FORMAT);
			Matcher matcherScore =  patternForScore.matcher(scoreLine);
			if (matcherScore.find()) {
				String scoreStr = matcherScore.group(0);
				Double score = Double.parseDouble(scoreStr);
				scores.put(label, score);
			} else {
				LOGGER.info("unable to extract score:" + commandOutput );
			}
		}

		AiClassification classification = new AiClassification(model.getId(), scores);
		LOGGER.info(classification.toString());

		return classification;
	}
}
