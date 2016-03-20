//package gui.userWindow;
//
//import java.io.DataOutputStream;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.net.Socket;
//import java.net.UnknownHostException;
//
//public class FileSendRunnable implements Runnable {
//
//	Socket sk;
//	DataOutputStream dos;
//	FileInputStream fis;
//	File file = null;
//	long fileLength;
//	byte[] sendByte = null;
//	int length;
//	double sumL = 0;
//	boolean bool;
//	String name;
//	
//	public FileSendRunnable(String s,String name) throws IOException {
//		sk = UserWindow.ur.sk;
//		this.name = name;
//		file = new File(s);
//		fileLength = file.length();
//		sendByte = new byte[1024];
//		length = 0;
//		sumL = 0;
//		bool = false;
//		dos = new DataOutputStream(sk.getOutputStream());
//		fis = new FileInputStream(file);
//	}
//	
//	@Override
//	public void run() {
//		UserWindow.ur.out.println("sendFile");
//		UserWindow.ur.out.println(name);
//		UserWindow.ur.out.flush();
//
//		System.out.println("!");
//		
//		try {
//			long l = file.length();
//			
//			while((length = fis.read(sendByte, 0, sendByte.length))>0){
//				sumL += length;
//				System.out.println("Has send " + ((sumL/l)*100) + "%");
//				
//				dos.write(sendByte, 0, length);
//				dos.flush();
//			}
//			
//			if(sumL == l) bool = true;
//			
//		} catch (UnknownHostException e) {
//			System.out.println("传输异常1");
//			bool = false;
//		} catch (IOException e) {
//			System.out.println("传输异常2");
//			bool = false;
//		} finally {
//			if(fis != null)
//				try {
//					fis.close();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//		}
//		System.out.println(bool?"accept":"erro");
//	}
//
//}
