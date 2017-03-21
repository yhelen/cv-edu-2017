import java.util.ArrayList;

import stuyvision.VisionModule;
import stuyvision.gui.VisionGui;
import stuyvision.gui.IntegerSliderVariable;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

public class Vision extends VisionModule {
	public void run(Mat frame) {
		postImage(frame, "Camera Feed");
		
		Imgproc.cvtColor(frame, frame, Imgproc.COLOR_BGR2HSV);
		
		ArrayList<Mat> channels = new ArrayList<Mat>();
		Core.split(frame, channels);
		
		postImage(channels.get(0), "Hue channel");
		
		Core.inRange(channels.get(0), new Scalar(40), new Scalar(80), channels.get(0));
		Imgproc.medianBlur(channels.get(0), channels.get(0), 5);
		postImage(channels.get(0), "Hue-Filtered Frame");
		
		ArrayList<Mat> greenChannels = new ArrayList<Mat>();
		greenChannels.add(channels.get(0));
		greenChannels.add(channels.get(0));
		greenChannels.add(channels.get(0));
		
		Mat greenFilter = new Mat();
		
		Core.merge(greenChannels, greenFilter);
		
		Imgproc.cvtColor(frame, frame, Imgproc.COLOR_HSV2BGR);
		
		Core.bitwise_and(frame, greenFilter, greenFilter);
		
		postImage(greenFilter, "green");
		
	}
}