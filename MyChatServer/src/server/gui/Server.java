package server.gui;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

import javax.swing.JOptionPane;

public class Server {
	static ServerSocket ss = null;
	static Vector<ClientRunnable> clients = null;
	static int port = 2333;

	static void sendFile(Socket sk) throws IOException {
/*		byte[] inputByte = null;
		int length = 0;
		long l = Long.valueOf(ln);
		DataInputStream dis = null;
		FileOutputStream fos = null;
		String filePath = "D:/test/newtest.exe";
		
		dis = new DataInputStream(sk.getInputStream());
		fos = new FileOutputStream(new File(filePath));
		
		File f = new File("D:/test");
		if(!f.exists()){
			f.mkdir();
		}
		
		inputByte = new byte[1024];
		System.out.println(l);
		System.out.println("Now catching!");
		
		long readed = 0;
		while((length = dis.read(inputByte))>0){
//			System.out.println(length);
			readed += length;
			System.out.println(readed);
			fos.write(inputByte, 0, length);
			fos.flush();
			if(readed == l) break;
		}
		
		System.out.println("Catch finished!");
		
		if(fos != null) fos.close();*/
		
		DataInputStream din = new DataInputStream(sk.getInputStream());
		FileOutputStream fos = new FileOutputStream(new File("D:/test1.exe"));
		byte imags[]=new byte[1024];
		
		System.out.println("catch");
		
		long l = 0;
		long x = 0;
		
		while((l = din.read(imags))!=-1)
		{
			x+=l;
			System.out.println(x);
//			tt.dout.write(imags);
			fos.write(imags);
		}
		fos.close();
		System.out.println("end catch");
	}

	private static void addClients(ClientRunnable cr) {
		if (clients == null) {
			clients = new Vector<ClientRunnable>();
		}

		clients.addElement(cr);

	}

	public static void sendAll(String s) {
		for (int i = clients.size() - 1; i >= 0; i--) {
			clients.get(i).talk("public", s);
		}
	}

	public static void sendOne(String name, String s) {
		for (int i = clients.size() - 1; i >= 0; i--) {
			if (clients.get(i).name.equals(name)) {
				clients.get(i).talk("private", s);
				break;
			}
		}
	}

	public static void start() throws IOException {
		ss = new ServerSocket(port);
	}
	
	public static void close() throws IOException{
		if(clients.size()>0){
			for(int i = 0;i<clients.size();i++){
				clients.get(i).talk("system", "【系统消息】 与服务器断开连接，请重新登录！");
				clients.remove(i);
			}
		}
		
		clients.clear();
		ss.close();
		
	}

	static void service() {
		try {
			start();
			ServerManage.changeStart();
			JOptionPane.showMessageDialog(null, "服务器已启动");
		} catch (IOException e1) {
			System.out.println("启动失败");
			System.exit(1);
		}

		while (true) {
			try {
				Socket sk = ss.accept();

				ClientRunnable cr = new ClientRunnable(sk);
				Thread th = new Thread(cr);
				th.start();

				addClients(cr);
			} catch (IOException e) {
				continue;
			}
		}
	}

}
