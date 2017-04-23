package concord.classifier;

import concord.appmodel.ClassificationModel;
import concord.appmodel.Photo;
import concord.appmodel.domain.AiClassification;
import concord.appmodel.domain.PhotoCategory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	private static final String CLASSIFY_PYTHON_FILE = "tensorflow/concord-classifier/classifier.py";
	private static final String CLASSIFY_COMMAND_PATTERN = "python {0} {1} {2} {3}";
	private static final String CLASSIFICATION_OUTPUT_MATCHER = "[a-zA-Z0-9]+ \\(score = (?:\\d*\\.)?\\d+\\)";
	private static final String SCORE_OUTPUT_FORMAT = "(?:\\d*\\.)?\\d+";

	public AiClassification classifyPhoto(Photo photo, ClassificationModel classificationModel) throws Exception{
		if (classificationModel.isTrained()) {
			String classificationCommand = buildClassificationCommand(photo, classificationModel);

			// Execute train command
			Process p = Runtime.getRuntime().exec(classificationCommand);

			BufferedReader output = getOutput(p);

			StringBuilder outStringBuilder = new StringBuilder();

			String line;

			while ((line = output.readLine()) != null) {
				LOGGER.info(line);
				outStringBuilder.append(line);
			}

			return parseResponse(outStringBuilder.toString(), classificationModel);

		} else {
			throw new RuntimeException("Unable to classify using model " + classificationModel.getId() + ". Model is not trained yet");
		}
	}

	private static BufferedReader getOutput(Process p) {
		return new BufferedReader(new InputStreamReader(p.getInputStream()));
	}

	private String buildClassificationCommand(Photo photo, ClassificationModel model) {
		return MessageFormat.format(CLASSIFY_COMMAND_PATTERN, this.getClass().getClassLoader().getResource(CLASSIFY_PYTHON_FILE).getFile(), photo.getLocalFilePath(), model.getModelConfig().getLabelsPath(), model.getModelConfig().getGraphPath());
	}

	private AiClassification parseResponse(String commandOutput, ClassificationModel model) {
		Pattern patternForOutput = Pattern.compile(CLASSIFICATION_OUTPUT_MATCHER);
		Matcher matcher = patternForOutput.matcher(commandOutput);
		Map<PhotoCategory, Double> scores = new HashMap<>();

		if (matcher.find()) {
			for (int i=0; i< matcher.groupCount(); i++) {
				String scoreLine = matcher.group(i);
				PhotoCategory label = PhotoCategory.valueOf(scoreLine.substring(0, 6).toUpperCase());

				Pattern patternForScore = Pattern.compile(SCORE_OUTPUT_FORMAT);
				Matcher matcherScore =  patternForScore.matcher(scoreLine);
				if (matcherScore.find()) {
					String scoreStr = matcherScore.group(0);
					Double score = Double.parseDouble(scoreStr);
					scores.put(label, score);
				}
			}
		} else {
			LOGGER.error("Unable to parse classification response");
			return null;
		}

		return new AiClassification(model.getId(), scores);
	}
}
