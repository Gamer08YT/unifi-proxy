package de.bytestore.unifi.provider;

import de.bytestore.unifi.utils.LogHandler;
import de.bytestore.unifi.utils.LogType;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.objdetect.Objdetect;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class CamAI {
    // Store Frame Converter.
    private Java2DFrameConverter converterIO = new Java2DFrameConverter();

    // Define Min Object Size.
    private float sizeIO = 0.1f;

    public static void load() {
        // Load Native Library of OpenCV.
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        // Print Debug Message.
        LogHandler.print(LogType.SUCCESS, "Loaded OpenCV Native Library.");
    }

    /**
     * Parse Stream Buffer for any Recognition with OpenCV.
     *
     * @param frameIO
     * @return
     */
    public Image parseBuffer(Frame frameIO) {
        // Create Buffered Image from Frame.
        BufferedImage imageIO = converterIO.convert(frameIO);

        try {
            // Convert BufferedImage to Material.
            Mat materialIO = this.toMaterial(imageIO);

            // Add Rect for Debug.
            MatOfRect faceIO = new MatOfRect();

            // Create new Classifier for Cascades.
            CascadeClassifier classifierIO = new CascadeClassifier();

            // Calculate minimal Object size.
            int minIO = Math.round(sizeIO * materialIO.rows());

            // Load Cascades File for Face Recognition.
            classifierIO.load("./src/main/resources/haarcascades/haarcascade_frontalface_default.xml");

            // Detect in Multi Scale.
            classifierIO.detectMultiScale(materialIO,
                    faceIO,
                    1.1,
                    3,
                    Objdetect.CASCADE_DO_ROUGH_SEARCH,
                    new Size(minIO, minIO),
                    new Size()
            );

            // Get Recognised Objects as Array.
            Rect[] objectsIO = faceIO.toArray();

            // Loop trough Items from Array.
            for (Rect itemIO : objectsIO) {
                // Draw Rectangle around Object.
                Imgproc.rectangle(materialIO, itemIO.tl(), itemIO.br(), new Scalar(0, 0, 255), 3);
            }

            // Return Image back to Canvas or Stream.
            return this.toImage(materialIO);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * Convert Image to Material.
     *
     * @return
     */
    public Mat toMaterial(BufferedImage imageIO) throws IOException {
        // Convert BufferedImage to Array.
        byte[] pixelsIO = ((DataBufferByte) imageIO.getRaster().getDataBuffer()).getData();

        // Create a Matrix the same size of image
        Mat materialIO = new Mat(imageIO.getHeight(), imageIO.getWidth(), CvType.CV_8UC3);

        // Fill Matrix with image values
        materialIO.put(0, 0, pixelsIO);

        return materialIO;
    }

    /**
     * Convert Material to Image.
     *
     * @return
     */
    public Image toImage(Mat materialIO) throws IOException {
        // Get Bytes of Material.
        MatOfByte bytesIO = new MatOfByte();

        // Encode Image via Bytes.
        Imgcodecs.imencode(".jpg", materialIO, bytesIO);

        // Push Byte Array into Stream.
        InputStream streamIO = new ByteArrayInputStream(bytesIO.toArray());

        return ImageIO.read(streamIO);
    }
}
