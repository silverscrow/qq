package server.gui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientRunnable implements Runnable {
	
	public BufferedReader in;
	public PrintWriter out;
	public Socket sk;
	public String name;
	public String pwd;
	
	public ClientRunnable(Socket sk) throws IOException {
		this.sk = sk;
		in = new BufferedReader(new InputStreamReader(sk.getInputStream()));
		out = new PrintWriter(sk.getOutputStream());
	}
	
	public void talk(String x,String s){
		out.println(x);
		out.println(s);
		out.flush();
	}
	
	private void getUserList(String s){
		String list = name;
		for(int i = Server.clients.size()-1;i>=0;i--){
			if(!Server.clients.get(i).name.equals(name))
				list+="#" + Server.clients.get(i).name;
		}
		out.println(s);
		out.println(list);
		out.flush();
	}
	
	@Override
	public void run() {
		String s = "";
		while(true){
			try {
				s = in.readLine();
			} catch (IOException e) {
				s = "^(^*(&%(*^&%(";
			}
			
			if(s.equals("all")){
				try {
					s = in.readLine();
				} catch (IOException e) {
					System.out.println("系统错误！\n用户登录服务器线程");
				}
				
				Server.sendAll(s);
			}else if(s.equals("login")){
				try {
					s = in.readLine();
					this.name = s;
					s = in.readLine();
					this.pwd = s;
				} catch (IOException e) {
				}
				Server.sendAll(this.name + "已进入聊天室！");
				getUserList("login");
			}else if(s.equals("refrushUserList")){
				getUserList(s);
			}else if(s.equals("out")){
				Server.clients.removeElement(this);
				try {
					Server.sendAll(in.readLine());
					this.sk.close();
				} catch (IOException e) {
					System.out.println("退出失败！");
				}
			}else if(s.equals("sendFile")){
				try {
//					s = in.readLine();
					Server.sendFile(sk);
				} catch (IOException e) {
					talk("system", "文件发送失败");
				}
			}else{
				String name = s;
				try {
					s = in.readLine();
				} catch (IOException e) {
				}
				Server.sendOne(name, s);
			}
			
		}
	}
	
}
