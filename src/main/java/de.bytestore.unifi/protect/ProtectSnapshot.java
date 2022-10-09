package de.bytestore.unifi.protect;

import de.bytestore.unifi.UniFi;
import de.bytestore.unifi.protect.tls.ProtectSSLContext;
import de.bytestore.unifi.utils.LogHandler;
import de.bytestore.unifi.utils.LogType;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.ByteArrayBuffer;
import org.bytedeco.flycapture.FlyCapture2.Image;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import static de.bytestore.unifi.protect.ProtectProvider.clientIO;

public class ProtectSnapshot {
    public static void sendSnapshot(String uriIO) throws IOException, UnrecoverableKeyException, CertificateException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        // Create new HTTP Request.
        HttpPost postIO = new HttpPost(uriIO);

        // Set Content Type.
        postIO.setHeader("Content-Type", "image/jpg");

        // Get Frame from current Camera.
        Frame frameIO = UniFi.camIO.getFrame();

        // Check if Frame is set.
        if (frameIO != null) {
            // Create new Converter Instance.
            Java2DFrameConverter converterIO = new Java2DFrameConverter();

            // Convert Frame to BufferedImage.
            BufferedImage imageIO = converterIO.getBufferedImage(frameIO, (converterIO.getBufferedImageType(frameIO) == BufferedImage.TYPE_CUSTOM ? 1.0 : 1.0), false, null);

            System.out.println("aabc");
            System.out.println(imageIO);

            // Check if Image is not null (eq. Frame decoding Error).
            if (imageIO != null) {
                // Create new Buffered Stream.
                ByteArrayOutputStream outputIO = new ByteArrayOutputStream();

                // Write Image into Buffer.
                ImageIO.write(imageIO, "jpg", outputIO);

                // Set POST Body.
                postIO.setEntity(new ByteArrayEntity(outputIO.toByteArray()));

                // Execute Post Request.
                HttpResponse responseIO = clientIO.build().execute(postIO);

                System.out.println(responseIO.getAllHeaders().toString());

                // Print Debug Message.
                LogHandler.print(LogType.HTTP, "Send Snapshot to UniFi Protect.");
            }
        }
    }
}
