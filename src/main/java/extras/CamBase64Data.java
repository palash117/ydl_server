package extras;

import java.io.File;
import java.io.FileInputStream;

import org.apache.commons.codec.binary.Base64;

public class CamBase64Data {

	public String getData(){
		String encodedData = "no data";
		try{
		File file = new File("/home/psd/Pictures/Webcam/camImage.jpeg");

//		File file = new File("/home/pi/Pictures/Webcam/camImage.jpeg");
		FileInputStream fis = new FileInputStream(file);
		byte[] imageByteArray = new byte[(int)file.length()];
		fis.read(imageByteArray);
		encodedData = encodeImage(imageByteArray);
		System.out.println(encodedData);
		}
		catch(Exception e){
			System.out.println("exception "+ e.toString());
		}
		return encodedData;
	}
	
	public static String encodeImage(byte[] imageByteArray) {
		return new String(Base64.encodeBase64(imageByteArray));
	}

}
