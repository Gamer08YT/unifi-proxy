package de.bytestore.unifi.provider;

import com.github.manevolent.ffmpeg4j.FFmpegException;
import org.bytedeco.javacv.*;
import org.bytedeco.javacv.Frame;

import javax.swing.text.DateFormatter;
import java.awt.*;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RTSPCam extends CamProvider {
    public static void main(String[] args) throws IOException, FFmpegException {

        FFmpegLogCallback.set();

        // Create new Grabber Instance.
        FFmpegFrameGrabber grabberIO = new FFmpegFrameGrabber("rtsp://192.168.1.144:554/11");

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

        // Set Timestamp of Frame.
        grabberIO.setTimestamp(new Date().getTime(), true);

        // Grab Frame for the first Time.
        Frame frameIO = grabberIO.grab();

        // Show Canvas for Debugging.
        CanvasFrame canvasIO = new CanvasFrame("Camera");

        // Store Date Formatter for Debug.
        SimpleDateFormat formatIO = new SimpleDateFormat("dd.MM.yyyy - HH:mm:ss");

        // Grab Frames of RTSP.
        while (frameIO != null) {
            // Grap next Frame of Stream.
            frameIO = grabberIO.grab();

            // Show Frame on Debug Canvas.
            canvasIO.showImage(frameIO);

            // Display some Debugging Options in the Title.
            canvasIO.setTitle("Camera | " + grabberIO.getFrameRate() + " FPS | " + formatIO.format(grabberIO.getTimestamp()));
        }


    }
}
