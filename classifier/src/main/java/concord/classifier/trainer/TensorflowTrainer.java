package concord.classifier.trainer;

import concord.appdao.repository.IClassificationModelRepository;
import concord.appmodel.ClassificationModel;
import concord.appmodel.ClassifiedPhoto;
import concord.appmodel.domain.ModelConfig;
import concord.appmodel.domain.TrainingParameters;
import concord.appmodel.domain.TrainingResults;
import concord.appmodel.domain.TrainingStatus;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.MessageFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by aboieriu on 4/21/17.
 */
@Component
public class TensorflowTrainer {

	private static final String GRAPH_PATH_PATTERN = "/{0}/{1}.pb";
	private static final String LABELS_PATH_PATTERN = "/{0}/{1}.txt";
	private static final String DATA_PATH_PATTERN = "/{0}/data";
	private static final String PYTHON_TRAIN_COMMAND = "python {0} --bottleneck_dir={1} --how_many_training_steps {2} --model_dir={3} --output_graph={4} --output_labels={5} --image_dir {6}";

	private static final String TRAIN_OUTPUT_FORMAT = "<!--###t(?:\\d*\\.)?\\d+%###-->";
	private static final String TRAIN_OUTPUT_DIGIT_FORMAT = "(?:\\d*\\.)?\\d+";

	private static final Logger LOGGER = LoggerFactory.getLogger(TensorflowTrainer.class);


	@Value("${model.training.storage}")
	private String modelTrainingStorage;

	@Value("${retrain.file.path}")
	private String trainFilePath;

	@Resource
	private IClassificationModelRepository classificationModelRepository;

	public void trainModel(ClassificationModel model) throws Exception{
		LOGGER.info("Begin training for model " + model.getId());

		Path trainingStorageBase = Paths.get(modelTrainingStorage + "/" +  model.getId());
		Path dataPath = Paths.get(MessageFormat.format(DATA_PATH_PATTERN, trainingStorageBase));

		Path graphPath = Paths.get(MessageFormat.format(GRAPH_PATH_PATTERN, trainingStorageBase, model.getId()));
		Path labelPath = Paths.get(MessageFormat.format(LABELS_PATH_PATTERN, trainingStorageBase, model.getId()));

		try {
			trainModelInternal(model, trainingStorageBase, dataPath, graphPath, labelPath);
			model.setTrainingStatus(TrainingStatus.TRAINED);
			model.setTrained(true);
			model.getTrainingResults().setEndTime(new Date());
			classificationModelRepository.save(model);
			LOGGER.info("Finished training for model " + model.getId());
		} catch (Exception e) {
			LOGGER.error("Failed to train model " + model.getId());
			LOGGER.error("something crashed", e);

			model.setTrainingStatus(TrainingStatus.FAILED);
			classificationModelRepository.save(model);
			FileUtils.deleteDirectory(trainingStorageBase.toFile());
		}
	}


	private void trainModelInternal(ClassificationModel model, Path basePath, Path dataPath, Path graphPath, Path labelsPath) throws Exception {
		Files.createDirectories(dataPath);
		Files.createDirectories(Paths.get(basePath + "/bottlenecks"));

		buildModelData(model, dataPath);

		beginTraining(model, basePath, dataPath, graphPath, labelsPath);

		FileUtils.deleteDirectory(dataPath.toFile());
	}

	private void beginTraining(ClassificationModel model,Path basePath, Path dataPath, Path graphPath, Path labelsPath) throws Exception{
		ModelConfig modelConfig = new ModelConfig();

		String basePathAbsolute = basePath.toFile().getAbsolutePath();
		String graphPathAbsolute = graphPath.toFile().getAbsolutePath();
		String dataPathAbsolute = dataPath.toFile().getAbsolutePath();
		String labelsPathAbsolute = labelsPath.toFile().getAbsolutePath();

		String buildCommand = buildTrainCommand(basePathAbsolute, model.getTrainingParameters(), labelsPathAbsolute, graphPathAbsolute, dataPathAbsolute);

		LOGGER.info("Excuting command: " + buildCommand);
		// Execute train command
		Process p = Runtime.getRuntime().exec(buildCommand);

		BufferedReader output = getOutput(p);
		BufferedReader errorOutput = getError(p);

		StringBuilder outStringBuilder = new StringBuilder();
		String line;
		while ((line = output.readLine()) != null) {
			LOGGER.info(line);
			outStringBuilder.append(line);
		}

		LOGGER.info("Checking out error output of command");
		while ((line = errorOutput.readLine()) != null) {
			LOGGER.info(line);
		}

		model.getTrainingResults().setTestAccuracy(getTrainingResultsFromOutput(outStringBuilder.toString()));
		modelConfig.setGraphPath(graphPathAbsolute);
		modelConfig.setLabelsPath(labelsPathAbsolute);
		model.setModelConfig(modelConfig);

		classificationModelRepository.save(model);
	}

	private Double getTrainingResultsFromOutput(String commandOutput) {
		Pattern patternForOutput = Pattern.compile(TRAIN_OUTPUT_FORMAT);
		Pattern patternForDigit = Pattern.compile(TRAIN_OUTPUT_DIGIT_FORMAT);
		Matcher matcher = patternForOutput.matcher(commandOutput);
		try {
			if (matcher.find()) {
				String output = matcher.group(0);
				Matcher matcherForDigits = patternForDigit.matcher(output);
				if (matcherForDigits.find()) {
					String digits = matcherForDigits.group(0);
					return Double.parseDouble(digits);
				}
			}
		} catch (Exception e) {
			LOGGER.error("Unable to get training results");
			throw e;
		}
		return 0d;
	}


	private String buildTrainCommand(String basePath, TrainingParameters parameters, String labelsPathAbsolute, String graphPathAbsolute, String dataPathAbsolute) {
		return MessageFormat.format(PYTHON_TRAIN_COMMAND,
						trainFilePath,
						basePath + "/bottlenecks",
						String.valueOf(parameters.getSteps()),
						"/Users/aboieriu/Documents/concord-model/inception",
						graphPathAbsolute,
						labelsPathAbsolute,
						dataPathAbsolute);
	}

	private void buildModelData(ClassificationModel model, Path dataPath) throws Exception{
		for (ClassifiedPhoto classifiedPhoto : model.getValidData()) {
			try {
				File input = new File(classifiedPhoto.getPhoto().getLocalFilePath());
				File dest = new File(
								dataPath.toFile().getAbsolutePath() + "/" + classifiedPhoto.getHumanClassification());

				Files.createDirectories(dest.toPath());
				FileUtils.copyFileToDirectory(input, dest);
			} catch (Exception e) {
				LOGGER.info("Unable to move file from " + classifiedPhoto.getPhoto().getLocalFilePath() + " [skipping]");
			}
		}
	}


	private static BufferedReader getOutput(Process p) {
		return new BufferedReader(new InputStreamReader(p.getInputStream()));
	}

	private static BufferedReader getError(Process p) {
		return new BufferedReader(new InputStreamReader(p.getErrorStream()));
	}

}
