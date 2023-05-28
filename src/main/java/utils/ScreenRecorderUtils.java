package utils;

import constants.FrwConstants;
import org.monte.media.Format;
import org.monte.media.Registry;
import org.monte.media.math.Rational;
import org.monte.screenrecorder.ScreenRecorder;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.monte.media.FormatKeys.*;
import static org.monte.media.VideoFormatKeys.*;

public class ScreenRecorderUtils extends ScreenRecorder {

	public static ScreenRecorder screenRecorder;
	public String name;
	private String fileName;
	private File currentFile;
	private SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH-mm-ss");

	public ScreenRecorderUtils() throws IOException, AWTException {
		super(GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration(),
				new Rectangle(0, 0, Toolkit.getDefaultToolkit().getScreenSize().width,
						Toolkit.getDefaultToolkit().getScreenSize().height),
				new Format(MediaTypeKey, MediaType.FILE, MimeTypeKey, MIME_AVI),
				new Format(MediaTypeKey, MediaType.VIDEO, EncodingKey, ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE,
						CompressorNameKey, ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE, DepthKey, 24, FrameRateKey,
						Rational.valueOf(15), QualityKey, 1.0f, KeyFrameIntervalKey, 15 * 60),
				new Format(MediaTypeKey, MediaType.VIDEO, EncodingKey, "black", FrameRateKey, Rational.valueOf(30)),
				null, new File("./" + FrwConstants.EXPORT_VIDEO_PATH + "/"));
	}
	public ScreenRecorderUtils(GraphicsConfiguration cfg, Rectangle captureArea, Format fileFormat, Format screenFormat, Format mouseFormat, Format audioFormat, File movieFolder, String name) throws IOException, AWTException {
		super(cfg, captureArea, fileFormat, screenFormat, mouseFormat, audioFormat, movieFolder);
		this.name = name;
	}

	@Override
	protected File createMovieFile(Format fileFormat) throws IOException {
		if (!movieFolder.exists()) {
			movieFolder.mkdirs();
		} else if (!movieFolder.isDirectory()) {
			throw new IOException("\"" + movieFolder + "\" is not a directory.");
		}

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH-mm-ss");
		return new File(movieFolder,
				name + "-" + dateFormat.format(new Date()) + "." + Registry.getInstance().getExtension(fileFormat));
	}

	private File getFileWithUniqueName(String fileName) {
		String extension = "";
		String name = "";

		int idxOfDot = fileName.lastIndexOf('.'); // Get the last index of . to separate extension
		extension = fileName.substring(idxOfDot + 1);
		name = fileName.substring(0, idxOfDot);

		Path path = Paths.get(fileName);
		int counter = 1;
		while (Files.exists(path)) {
			fileName = name + "-" + counter + "." + extension;
			path = Paths.get(fileName);
			counter++;
		}
		return new File(fileName);
	}

	// Hàm Start record video
	public static void startRecord(String methodName) {
		try {
			//Tạo thư mục để lưu file video vào
			File file = new File("./VideoRecord/");
			if (!file.exists()) {
				file.mkdirs();
			}

			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			int width = screenSize.width;
			int height = screenSize.height;

			Rectangle captureSize = new Rectangle(0, 0, width, height);

			GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
			try {
				screenRecorder = new ScreenRecorderUtils(gc, captureSize, new Format(MediaTypeKey, MediaType.FILE, MimeTypeKey, MIME_AVI),
						new Format(MediaTypeKey, MediaType.VIDEO, EncodingKey, ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE, CompressorNameKey,
								ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE, DepthKey, 24, FrameRateKey, Rational.valueOf(15), QualityKey, 1.0f,
								KeyFrameIntervalKey, 15 * 60), new Format(MediaTypeKey, MediaType.VIDEO, EncodingKey, "black", FrameRateKey,
						Rational.valueOf(30)), null, file, methodName);
			} catch (IOException e) {
				throw new RuntimeException(e);
			} catch (AWTException e) {
				throw new RuntimeException(e);
			}
			try {
				screenRecorder.start();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
			LogUtils.info("Executing: Recording...");
		} catch (Exception e) {
			LogUtils.error("Cannot record");
		}
	}

	public static void stopRecord() {
		try {
			LogUtils.info("Stop record");
			screenRecorder.stop();

		} catch (IOException e) {
			LogUtils.error("Cannot stop record ");
			throw new RuntimeException(e);
		}
	}

	private void deleteRecording() {
		boolean deleted = false;
		try {
			if (currentFile.exists()) {
				deleted = currentFile.delete();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (deleted)
			currentFile = null;
		else
			System.out.println("Could not delete the screen record!");
	}

}