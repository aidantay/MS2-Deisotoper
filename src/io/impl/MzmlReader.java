package io.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.SpectraReader;
import model.Spectra;

public class MzmlReader implements SpectraReader {

	// THIS CLASS IS NOT USED. ONCE WE FIGURE OUT HOW TO READ THE BINARY ENCODED PEAK LIST
	// THEN WE CAN IMPLEMENT THIS. OTHERWISE JUST LEAVE IT OUT.
	
	private File file;
	private List<Spectra> spectraList;

	public MzmlReader(String filePath) {
		this.file = new File(filePath);
		this.spectraList = new ArrayList<Spectra>();
	}
	
	@Override
	public File getFile() {
		return file;
	}
	
	@Override
	public List<Spectra> getSpectraList() {
		return spectraList;
	}

	@Override
	public void readFile() throws NumberFormatException, IOException {

	}
	
	
////<cvParam cvRef="MS" accession="MS:1000523" name="64-bit float" />
////<cvParam cvRef="MS" accession="MS:1000576" name="no compression" />		
//String encodedList1 = "Qj7o2awiUEDhC5OpgsVRQH0/NV66BVJANe84RUeCUkAep+hILptTQDSAt0CCRlVAF7fRAN5qVUABTYQNT79YQF1txf6yS1xAP1dbsb+gYkCfzarP1cBiQFvTvOMU4WRA5/up8dIbZkDiWBe30V5mQNnO91PjPXNAJLn8h/RNc0CASL99HV50QAmKH2PunXRA1CtlGeL8d0COBvAWSCx6QIC3QILiLXpAHhZqTfM9ekDnjCjtDU57QEhQ/Bjzr4JA5WGh1rRCiEA=";
//
//float[] decodedList1 = decodeLocation(encodedList1, "64");
//for (float d : decodedList1) {
//	System.out.println(d);			
//}
//System.out.println("\n\n\n");
//
////<cvParam cvRef="MS" accession="MS:1000521" name="32-bit float" />
////<cvParam cvRef="MS" accession="MS:1000576" name="no compression" />
//String encodedList2 = "M/MbRDOIxEYAQFNEzYzWQ5oZ8EOaYXpGmlnQQzNzCUTNTPJDM3IFRwAQ7UQAYGFEAADYQ5pZ6kMA6ElFmtkxRM1UG0UAIDJEAOAmRDNTDkRmlnFFM/P6QzNTE0QAoAREzQwwRA==";
//
//float[] decodedList2 = decodeLocation(encodedList2, "64");
//for (float d : decodedList2) {
//	System.out.println(d);
//}
//
////float[] binaryList1 = {(float) 64.5418, (float) 71.0861, (float) 72.0895, (float) 74.0356, (float) 78.4247, (float) 85.1017, (float) 85.6698, (float) 98.9892};
////String encodedList1  = encodeLocation(binaryList1);
////float[] decodedList1 = decodeLocation(encodedList1);
//
////float[] binaryList2 = {(float) 623.8, (float) 25156.1, (float) 845, (float) 429.1, (float) 480.2, (float) 16024.4, (float) 416.7, (float) 549.8};
////String encodedList2  = encodeLocation(binaryList2);
////float[] decodedList2 = decodeLocation(encodedList2);
//
//}
//
//private static String encodeLocation(float[] doubleArray) {
//Base64 decoder64 = new Base64();
//return decoder64.encodeToString(doubleToByteArray(doubleArray));
//}
//
//private static float[] decodeLocation(String base64Encoded, String decoder) {
//Base32 decoder32 = new Base32();
//Base64 decoder64 = new Base64();
//
//boolean isBase64 = Base64.isBase64(base64Encoded.getBytes());
//System.out.println(isBase64);
//
//if (decoder.equals("32")) {
//	return byteToDoubleArray(decoder32.decode(base64Encoded));    	
//} else {
//	return byteToDoubleArray(decoder64.decode(base64Encoded));
//}
////return byteToDoubleArray(Base64.getDecoder().decode(base64Encoded));
//}
//
//private static byte[] doubleToByteArray(float[] doubleArray) {
//ByteBuffer buf = ByteBuffer.allocate(Float.SIZE / Byte.SIZE * doubleArray.length);
//buf.asFloatBuffer().put(doubleArray);
//return buf.array();
//}
//
//private static float[] byteToDoubleArray(byte[] bytes) {
//FloatBuffer buf = ByteBuffer.wrap(bytes).order(ByteOrder.LITTLE_ENDIAN).asFloatBuffer();
//float[] doubleArray = new float[buf.limit()];
//buf.get(doubleArray);
//return doubleArray;
//}
//

	
}
