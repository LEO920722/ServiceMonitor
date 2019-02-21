package com.yisquare.tools;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Properties;
import java.util.UUID;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.apache.log4j.Logger;

import com.yisquare.servlet.DeleteApi;
import com.yisquare.servlet.Log4jListener;

public class DesUtil {

	private static Logger logger = Logger.getLogger(DeleteApi.class);
	static String path = Log4jListener.path;
	
    /**
     * 加密
     * @param srcStr
     * @param charset
     * @param sKey
     * @return
     */
    public static String encrypt(String srcStr, Charset charset, String sKey) {
        byte[] src = srcStr.getBytes(charset);
        byte[] buf = Des.encrypt(src, sKey);
        return Des.parseByte2HexStr(buf);
    }

    /**
     * 解密
     *
     * @param hexStr
     * @param sKey
     * @return
     * @throws Exception
     */
    public static String decrypt(String hexStr, Charset charset, String sKey) throws Exception {
        byte[] src = Des.parseHexStr2Byte(hexStr);
        byte[] buf = Des.decrypt(src, sKey);
        return new String(buf, charset);
    }
    
    private static final String  SKEY    = UUID.randomUUID().toString().replace("-", "").toLowerCase().substring(23, 31);
    private static final Charset CHARSET = Charset.forName("gb2312");
    
	public static void saveDBinfo(String string) throws Exception {
		//获取DBInfo
		//String str = PropertiesUtil.getDBInfo();
		String encryptResult = DesUtil.encrypt(string, CHARSET, SKEY);
		File writename = new File(path); // 相对路径，如果没有则要建立一个新的output。txt文件
		BufferedWriter out = new BufferedWriter(new FileWriter(writename));
		String etxt = SKEY + "," +encryptResult;
		out.write(etxt); // \r\n即为换行,Base64加密
		out.flush(); // 把缓存区内容压入文件
		out.close(); // 最后记得关闭文件
	}
	
	public static String getInfo() throws Exception {
		logger.warn("Load File Path is: " + path);
		// 从文件中读取数据，从输入流对象读取文件。
		FileInputStream fileInputStream = new FileInputStream(path);
		InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
		BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
		StringBuffer sb = new StringBuffer();
		String text = null;
		while ((text = bufferedReader.readLine()) != null) {
			sb.append(text);
		}
		bufferedReader.close();
		
		if (sb.toString().equals("") || sb.toString() == null) {
			return sb.toString();
		} else {
			String[] sblist = sb.toString().split(",");
			String SKEY = sblist[0];
			String encryptResult = sblist[1];

			// 待加密内容
			String decryResult = "";
			// try {
			//decryResult = DesUtil.decrypt(encryptResult, CHARSET,SKEY);
			decryResult = decrypt(encryptResult, CHARSET, SKEY);
			//logger.warn(decryResult);
			//System.out.println(decryResult);
			// } catch (Exception e1) {
			// e1.printStackTrace();
			// }
			return decryResult;
		}
	}
    
}
