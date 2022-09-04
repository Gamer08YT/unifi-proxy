package de.bytestore.unifi.provider;

import org.bytedeco.javacv.*;
import org.bytedeco.javacv.Frame;

import java.awt.*;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RTSPCam extends CamProvider {
    // Store CamAI via OpenCV.
    private static CamAI camAI = new CamAI();

    public static void main(String[] args) throws IOException {
        // Load OpenCV and Initialize.
        CamAI.load();

        //FFmpegLogCallback.set();

        // Create new Grabber Instance.
        FFmpegFrameGrabber grabberIO = new FFmpegFrameGrabber("https://s3.amazonaws.com/x265.org/video/Tears_400_x264.mp4");

        // Set Transportation of Grabber (Bad Image Fix).
        grabberIO.setOption("rtsp_transport", "tcp");

        // Set Timeout for Socket.
        grabberIO.setTimeout(10);

        // Set Image Size.
        //grabberIO.setImageWidth(1920);
        //grabberIO.setImageHeight(1080);

        // Limit FPS for UniFi.
        grabberIO.setFrameRate(15);

        // Set Timestamp of Frame.
        grabberIO.setTimestamp(new Date().getTime(), true);

        // Start Grabber with getting Stream Info.
        grabberIO.start(true);

        // Grab Frame for the first Time.
        Frame frameIO = grabberIO.grabImage();

        // Show Canvas for Debugging.
        CanvasFrame canvasIO = new CanvasFrame("Camera");

        // Store Date Formatter for Debug.
        SimpleDateFormat formatIO = new SimpleDateFormat("dd.MM.yyyy - HH:mm:ss");

        // Grab Frames of RTSP.
        while (frameIO != null) {
            // Grap next Frame of Stream.
            frameIO = grabberIO.grabImage();

            // Parse Buffer from Frame.
            Image imageIO = camAI.parseBuffer(frameIO);

            // Show Frame on Debug Canvas.
            canvasIO.showImage(imageIO);

            // Display some Debugging Options in the Title.
            canvasIO.setTitle("Camera | " + grabberIO.getFrameRate() + " FPS | " + formatIO.format(grabberIO.getTimestamp()));
        }


    }
}
