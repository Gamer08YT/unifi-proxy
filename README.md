# UniFi Proxy
UniFi Proxy makes it possible to integrate third-party hardware into UniFi Protect.

For testing purposes only, it is recommended to purchase hardware directly from the manufacturer and not to tamper with the system.

### Generate new Adoption Token
To adopt a device in UniFi Protect it is necessary to generate a token for it. This **token is only valid for 60 minutes** and is deleted even after a service restart.

1. Login into your local UniFi Controller (not unifi.ui.com).
2. Open following URL ``https://unifi/proxy/protect/api/cameras/qr`` which shows an QR Code, if your Browser can't parse the DHCP Name you need to replace ``unifi`` with the Controllers IPv4 Address.
3. Decode the QR Code with a [Decoder for QR Codes](https://qrcode-decoder.com/).
4. Copy the Adoption Token out of the QR Code Payload.

<img style="padding-left: 15px" src="src/main/resources/github/qr-1.png" width="50%"/>

### Generate a new KeyStore

For **Communication with the Secure (HTTP/S, WSS) Protocol** you need to create your own Certificate.
Because **Java can't read the raw PEM Files** you need to import your Keypair into a Java KeyStore (JKS).

Since there were some problems with the Keytool CLI in the past, we use the KeyStore Explorer tool for this.

1. Download the [Java Key Store Explorer](http://keystore-explorer.org/downloads.html).
2. Create a new KeyStore File:

<img style="padding-left: 15px" src="src/main/resources/github/keystore-1.png" width="50%"/>

3. Generate new Client Certificate:
```
mkdir tmp
openssl ecparam -out ./tmp/private.key -name prime256v1 -genkey -noout
openssl req -new -sha256 -key ./tmp/private.key -out ./tmp/server.csr -subj "/C=TW/L=Taipei/O=Ubiquiti Networks Inc./OU=devint/CN=camera.ubnt.dev/emailAddress=support@ubnt.com"
openssl x509 -req -sha256 -days 36500 -in ./tmp/server.csr -signkey ./tmp/private.key -out ./tmp/public.key
cat ./tmp/private.key ./tmp/public.key > ./tmp/client.pem
```
4. Import your Certificate into KeyStore as OpenSSL Keypair.

<img style="padding-left: 15px" src="src/main/resources/github/keystore-2.png" width="50%"/>

5. Select your Public and Private Key and untick the Password Checkbox.

<img style="padding-left: 15px" src="src/main/resources/github/keystore-3.png" width="50%"/>

6. Rename the Imported Keypair to your UniFi Controller Host.

<img style="padding-left: 15px" src="src/main/resources/github/keystore-4.png" width="50%"/>

### Adding Recognition Templates

**Experimental Feature (Hard CPU Usage)**

Visit the Official GitHub Repository of OpenCV an look for predefined Templates under [opencv/data/haarcascades](https://github.com/opencv/opencv/tree/master/data/haarcascades).

### Find your Camera

I used following Tool to identify my Camera and the Stream Information: