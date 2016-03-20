package gui.login;

import gui.userWindow.UserWindow;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class UserRunnable implements Runnable {

	public String host;
	public BufferedReader in;
	public DataInputStream testin;
	public DataOutputStream testout;
	public PrintWriter out;
	public Socket sk;
	public String name;
	public String pwd;
	
	@Override
	public void run() {
		String s = new String();
		while(true){
			try {
				s = in.readLine();
			} catch (IOException e) {
				continue;
			}
			
			if(s.equals("public")){
				try {
					s = in.readLine();
				} catch (IOException e) {
				}
				
				UserWindow.addPublic(s);
				
			}else if(s.equals("private")){
				try {
					s = in.readLine();
				} catch (IOException e) {
				}
				UserWindow.addPrivate(s);
			}else if(s.equals("refrushUserList")){
				try {
					s = in.readLine();
				} catch (IOException e) {
				}
				
				UserWindow.refrushUserList(s);
				
			}else if(s.equals("login")){
				try {
					s = in.readLine();
				} catch (IOException e) {
				}
				UserWindow.firstUserList(s);
			}else if(s.equals("system")){
				try {
					s = in.readLine();
				} catch (IOException e) {
				}
				UserWindow.addSystem(s);
			}else if(s.equals("sendFile")){
				try {
					UserWindow.fileCatcher();
				} catch (IOException e) {
				}
			}
			
		}
	}

	public UserRunnable(String host,String name,String pwd) throws UnknownHostException, IOException {
		sk = new Socket("localhost", 2333);
		this.host = host;
		this.name = name;
		this.pwd = pwd;
		in = new BufferedReader(new InputStreamReader(sk.getInputStream()));
		out = new PrintWriter(sk.getOutputStream());
		testin = new DataInputStream(sk.getInputStream());
		testout = new DataOutputStream(sk.getOutputStream());
		
	}
	
	public void send(String who,String txt){
		if(who.equals("All")){
			out.println("all");
		}else{
			out.println(who);
		}
		out.flush();
		out.println(txt);
		out.flush();
	}

	public void login() {
		out.println("login");
		out.println(this.name);
		out.println(this.pwd);
		out.flush();
	}
	
}
