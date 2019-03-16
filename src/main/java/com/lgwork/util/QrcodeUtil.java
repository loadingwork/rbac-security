package com.lgwork.util;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

/**
 * 
 * 二维码工具
 * 
 *   这个我自己写的 扩展可以使用https://github.com/kenglxn/QRGen
 * 
 * @author irays
 *
 */
public class QrcodeUtil {
	
	
	private final static int DEFAULT_WIDTH = 400;
	private final static int DEFAULT_HEIGHT = 400;
	

	
	/**
	 * 
	 * 获取二维码
	 * 
	 * @param content 内容
	 * @param width  二维码宽度
	 * @param height  二维码高度
	 * @return
	 * @throws WriterException
	 * @throws IOException
	 */
	public static byte[] createQrcodeStream(String content, int width, int height) throws WriterException, IOException {
		// 创建写入
		MultiFormatWriter multiFormatWriter = new MultiFormatWriter();

		// 配置参数
		Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>(3);
		// 字符集
		hints.put(EncodeHintType.CHARACTER_SET, "utf-8"); 
		// 纠错级别
		hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L); 
		// 白边大小
		hints.put(EncodeHintType.MARGIN, 0); 

		BitMatrix matrix = multiFormatWriter.encode(content, BarcodeFormat.QR_CODE, width, height, hints);
		
		// 输出流
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		
		MatrixToImageWriter.writeToStream(matrix, "png", out);
		
		byte[] byteArray = out.toByteArray();
		
		return byteArray;
	}
	
	
	
	/**
	 * 
	 *  创建二维码在本地路径
	 * 
	 * @param fullPath  全绝对路径路径
	 * @param content  内容
	 * @param width   宽度
	 * @param height  高度
	 * @throws IOException 
	 * @throws WriterException 
	 */
	public static void createQrcodeToDisk(String fullPath,String content, int width, int height) throws WriterException, IOException {
		byte[] byteArray = createQrcodeStream(content, width, height);
		FileOutputStream out = new FileOutputStream(fullPath); 
		out.write(byteArray);
		// 关闭资源
		out.flush();
		out.close();
	}
	
	/**
	 * 创建二维码
	 * @param fullPath  本地全路径
	 * @param content  本地内容
	 * @throws WriterException
	 * @throws IOException
	 */
	public static void createQrcodeToDisk(String fullPath,String content) throws WriterException, IOException {
		createQrcodeToDisk(fullPath, content, DEFAULT_WIDTH, DEFAULT_HEIGHT);
	}
	
	
	
	public static void main(String[] args) throws WriterException, IOException {
//		System.out.println(createQrcodeStream("1111", 100, 100));
		
//		FileOutputStream fileOutputStream = new FileOutputStream("D://storage//temp.png");
		
//		fileOutputStream.write(createQrcodeStream("1111", 100, 100));
//		fileOutputStream.write(QRCode.from("Hello World").stream());
		
//		QRCode.from("Hello World").writeTo(fileOutputStream);
		
//		fileOutputStream.flush();
//		fileOutputStream.close();
		
	}
	
	
	

}
