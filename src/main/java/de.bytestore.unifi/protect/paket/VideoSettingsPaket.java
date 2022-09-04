package de.bytestore.unifi.protect.paket;

import com.google.gson.JsonObject;

public class VideoSettingsPaket extends ProtectPaket {
    public VideoSettingsPaket() {
        // Set Hello Channel.
        this.setChannel("ChangeVideoSettings");

        // Create new JsonObject for Payload.
        JsonObject objectIO = new JsonObject();

        // Create new JsonObject for Audio.
        JsonObject audioIO = new JsonObject();

        audioIO.addProperty("bitRate", 32000);
        audioIO.addProperty("channels", 1);
        audioIO.addProperty("description", "audio track");
        audioIO.addProperty("enableTemporalNoiseShaping", false);
        audioIO.addProperty("enabled", true);
        audioIO.addProperty("mode", 0);
        audioIO.addProperty("quality", 0);
        audioIO.addProperty("sampleRate", 11025);
        audioIO.addProperty("type", "aac");
        audioIO.addProperty("volume", 100);

        // Add Audio Object to Payload.
        objectIO.add("audio", audioIO);

        // Add Firmware Path.
        objectIO.addProperty("firmwarePath", "/lib/firmware/");

        // Create new JsonObject for Video.
        JsonObject videoIO = new JsonObject();

        videoIO.addProperty("enableHrd", false);
        videoIO.addProperty("hdrMode", 0);
        videoIO.addProperty("lowDelay", false);
        videoIO.addProperty("videoMode", "default");
        videoIO.addProperty("", "");
        videoIO.addProperty("", "");
        videoIO.addProperty("", "");
        videoIO.addProperty("", "");
        videoIO.addProperty("", "");
        videoIO.addProperty("", "");
        videoIO.addProperty("", "");
        videoIO.addProperty("", "");
        /*
    "video": {

      "mjpg": {
        "avSerializer": {
          "destinations": [
            "file:///tmp/snap.jpeg",
            "file:///tmp/snap_av.jpg"
          ],
          "parameters": {
            "audioId": 1000,
            "enableTimestampsOverlapAvoidance": false,
            "suppressAudio": true,
            "suppressVideo": false,
            "videoId": 1001
          },
          "type": "mjpg"
        },
        "bitRateCbrAvg": 500000,
        "bitRateVbrMax": 500000,
        "bitRateVbrMin": null,
        "description": "JPEG pictures",
        "enabled": true,
        "fps": 5,
        "height": 720,
        "isCbr": false,
        "maxFps": 5,
        "minClientAdaptiveBitRate": 0,
        "minMotionAdaptiveBitRate": 0,
        "nMultiplier": null,
        "name": "mjpg",
        "quality": 80,
        "sourceId": 3,
        "streamId": 8,
        "streamOrdinal": 3,
        "type": "mjpg",
        "validBitrateRangeMax": 6000000,
        "validBitrateRangeMin": 32000,
        "width": 1280
      },
      "video1": {
        "M": 1,
        "N": 30,
        "avSerializer": {
          "destinations": [
            "tcp://192.168.1.110:7550?retryInterval=1&connectTimeout=5"
          ],
          "parameters": {
            "audioId": null,
            "streamName": "B9brWRbmr1MeqSAo",
            "suppressAudio": null,
            "suppressVideo": null,
            "videoId": null
          },
          "type": "extendedFlv"
        },
        "bitRateCbrAvg": 1400000,
        "bitRateVbrMax": 2800000,
        "bitRateVbrMin": 48000,
        "description": "Hi quality video track",
        "enabled": true,
        "fps": 15,
        "gopModel": 0,
        "height": 1080,
        "horizontalFlip": false,
        "isCbr": false,
        "maxFps": 30,
        "minClientAdaptiveBitRate": 0,
        "minMotionAdaptiveBitRate": 0,
        "nMultiplier": 6,
        "name": "video1",
        "sourceId": 0,
        "streamId": 1,
        "streamOrdinal": 0,
        "type": "h264",
        "validBitrateRangeMax": 2800000,
        "validBitrateRangeMin": 32000,
        "validFpsValues": [
          1,
          2,
          3,
          4,
          5,
          6,
          8,
          9,
          10,
          12,
          15,
          16,
          18,
          20,
          24,
          25,
          30
        ],
        "verticalFlip": false,
        "width": 1920
      },
      "video2": {
        "M": 1,
        "N": 30,
        "avSerializer": {
          "destinations": [
            "file:///dev/null"
          ],
          "parameters": {
            "audioId": null,
            "streamName": "RVMpanYOUa6biovd",
            "suppressAudio": null,
            "suppressVideo": null,
            "videoId": null
          },
          "type": "extendedFlv"
        },
        "bitRateCbrAvg": 500000,
        "bitRateVbrMax": 1200000,
        "bitRateVbrMin": 48000,
        "currentVbrBitrate": 1200000,
        "description": "Medium quality video track",
        "enabled": true,
        "fps": 15,
        "gopModel": 0,
        "height": 720,
        "horizontalFlip": false,
        "isCbr": false,
        "maxFps": 30,
        "minClientAdaptiveBitRate": 0,
        "minMotionAdaptiveBitRate": 0,
        "nMultiplier": 6,
        "name": "video2",
        "sourceId": 1,
        "streamId": 2,
        "streamOrdinal": 1,
        "type": "h264",
        "validBitrateRangeMax": 1500000,
        "validBitrateRangeMin": 32000,
        "validFpsValues": [
          1,
          2,
          3,
          4,
          5,
          6,
          8,
          9,
          10,
          12,
          15,
          16,
          18,
          20,
          24,
          25,
          30
        ],
        "verticalFlip": false,
        "width": 1280
      },
      "video3": {
        "M": 1,
        "N": 30,
        "avSerializer": {
          "destinations": [
            "file:///dev/null"
          ],
          "parameters": {
            "audioId": null,
            "streamName": "AzWBlRCBIhFsDkkp",
            "suppressAudio": null,
            "suppressVideo": null,
            "videoId": null
          },
          "type": "extendedFlv"
        },
        "bitRateCbrAvg": 300000,
        "bitRateVbrMax": 200000,
        "bitRateVbrMin": 48000,
        "currentVbrBitrate": 200000,
        "description": "Low quality video track",
        "enabled": true,
        "fps": 15,
        "gopModel": 0,
        "height": 360,
        "horizontalFlip": false,
        "isCbr": false,
        "maxFps": 30,
        "minClientAdaptiveBitRate": 0,
        "minMotionAdaptiveBitRate": 0,
        "nMultiplier": 6,
        "name": "video3",
        "sourceId": 2,
        "streamId": 4,
        "streamOrdinal": 2,
        "type": "h264",
        "validBitrateRangeMax": 750000,
        "validBitrateRangeMin": 32000,
        "validFpsValues": [
          1,
          2,
          3,
          4,
          5,
          6,
          8,
          9,
          10,
          12,
          15,
          16,
          18,
          20,
          24,
          25,
          30
        ],
        "verticalFlip": false,
        "width": 640
      },
      "vinFps": 30
    }
         */

        // Set Payload Data.
        this.setData(objectIO);
    }
}
