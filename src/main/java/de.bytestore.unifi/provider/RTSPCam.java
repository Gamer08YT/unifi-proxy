package de.bytestore.unifi.provider;

import de.bytestore.unifi.UniFi;
import org.bytedeco.ffmpeg.global.avcodec;
import org.bytedeco.javacv.*;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

public class RTSPCam extends CamProvider {
    // Store CamAI via OpenCV.
    // private static CamAI camAI = new CamAI();

    // Store last Frame.
    private static Frame frameIO;

    public RTSPCam() throws IOException {
        long timestampIO = System.currentTimeMillis();

        FFmpegLogCallback.set();

        Socket socketIO = new Socket();
        socketIO.setKeepAlive(true);
        socketIO.setSendBufferSize(1024);
        socketIO.connect(new InetSocketAddress("192.168.1.110", 7550));

        FFmpegFrameRecorder recorderIO = new FFmpegFrameRecorder(socketIO.getOutputStream(), 1);
        recorderIO.setCloseOutputStream(false);
        recorderIO.setVideoCodec(avcodec.AV_CODEC_ID_H264);
        recorderIO.setMetadata("streamname", "B9brWRbmr1MeqSAo");
        recorderIO.setFormat("flv");

        // Submit new Thread for Recording and Grabbing.
        UniFi.poolIO.submit(new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    FFmpegFrameGrabber grabberIO = new FFmpegFrameGrabber("rtsp://rtsp.stream/pattern");
                    grabberIO.start();

                    // Set Height and Width of Recorder.
                    recorderIO.setImageHeight(100);
                    recorderIO.setImageWidth(100);

                    // Start Recorder.
                    //recorderIO.start();

                    // Grab Frame for the first Time.
                    frameIO = grabberIO.grabFrame();

                    // Show Canvas for Debugging.
                    CanvasFrame canvasIO = new CanvasFrame("Camera");

                    // Grab next Frame.
                    frameIO = grabberIO.grabFrame();

                    // Grab Frames of RTSP.
                    recorderIO.setTimestamp((System.currentTimeMillis() - timestampIO) * 1000);
                    //recorderIO.record(frameIO);

                    System.out.println("AAAAAAA");
                    System.out.println("SS" + frameIO.pictType);

                    // Show Image in Canvas.
                    canvasIO.showImage(frameIO, false);


                    recorderIO.stop();
                } catch (IOException exceptionIO) {
                    exceptionIO.printStackTrace();
                }
            }
        }));
    }

    public Frame getFrame() {
        return this.frameIO;
    }

    public static void main(String[] args) throws IOException {

    }

    /*public static void main(String[] args) throws IOException {
        // Load OpenCV and Initialize.
        //CamAI.load();

        FFmpegLogCallback.set();

        // Create new Grabber Instance.
        FFmpegFrameGrabber grabberIO = new FFmpegFrameGrabber("https://s3.amazonaws.com/x265.org/video/Tears_400_x264.mp4");

        // Set Transportation of Grabber (Bad Image Fix).
        grabberIO.setOption("rtsp_transport", "tcp");

        // Set Timeout for Socket.
        grabberIO.setTimeout(10);

        // Set Image Size.
        grabberIO.setImageWidth(800);
        grabberIO.setImageHeight(350);

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

        Socket socketIO = new Socket();
        socketIO.setKeepAlive(true);
        socketIO.connect(new InetSocketAddress("192.168.1.110", 7550));

        FFmpegFrameRecorder recorderIO = new FFmpegFrameRecorder(socketIO.getOutputStream(), 1);
        recorderIO.setFormat("flv");
        recorderIO.setImageWidth(800);
        recorderIO.setMetadata("streamname", "RVMpanYOUa6biovd");
        recorderIO.setImageHeight(350);
        recorderIO.start();

        // Grab Frames of RTSP.
        while (frameIO != null) {
            // Grap next Frame of Stream.
            frameIO = grabberIO.grab();

            // Parse Buffer from Frame.
            //Image imageIO = camAI.parseBuffer(frameIO);

            if (frameIO != null) {
                recorderIO.record(frameIO);

                // Display some Debugging Options in the Title.
                canvasIO.setTitle("Camera | " + grabberIO.getFrameRate() + " FPS | " + formatIO.format(grabberIO.getTimestamp()));
            }
        }

        recorderIO.stop();
    }*/
}
