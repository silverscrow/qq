package gui.login;


import gui.userWindow.UserWindow;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

public class Login extends JFrame {
	private static final long serialVersionUID = 1L;
	
	static Login lg;
	static String exactName;
	
	static JLabel name;
	static JLabel pwd;
	static JTextField nameText;
	static JTextField pwdText;
	static JButton register;
	static JButton login;
	static JPanel username;
	static JPanel userpwd;
	static JPanel btn;
	final static boolean testbool = true;
	
	static boolean isPrivateChat;
	
	public Login() {
		super("登录");
		
		MyActionListener actionListener = new MyActionListener();
		MyKeyListener keyListener = new MyKeyListener();
		
		name = new JLabel("用户名： ");
		pwd = new JLabel("密    码： ");
		
		nameText = new JTextField(15);
		nameText.setBorder(new LineBorder(new Color(70,61,129)));
		pwdText = new JTextField(15);
		pwdText.setBorder(new LineBorder(new Color(70,61,129)));
		nameText.addKeyListener(keyListener);
		pwdText.addKeyListener(keyListener);
		
		register = new JButton("关闭");
		register.addActionListener(actionListener);
		login = new JButton("登录");
		login.addActionListener(actionListener);
		
		username = new JPanel(new FlowLayout(FlowLayout.LEFT));
		username.add(name);
		username.add(nameText);
		
		userpwd = new JPanel(new FlowLayout(FlowLayout.LEFT));
		userpwd.add(pwd);
		userpwd.add(pwdText);
		
		btn = new JPanel(new FlowLayout(FlowLayout.LEFT));
		btn.add(register);
		btn.add(login);
		
		this.setBounds(300, 300, 300, 200);
		this.setLayout(new FlowLayout());
		this.add(username);
		this.add(userpwd);
		this.add(btn);
		this.setVisible(true);

		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e){
				System.exit(0);
			}
		});
	}
	
	static void close(){
		System.exit(0);
	}
	
	static boolean login(){
		Msg msg = new Msg();
		UserRunnable ur;
		Thread uth;
		try {
			ur = new UserRunnable("192.168.12.2",exactName,pwdText.getText());
			uth = new Thread(ur);
			uth.start();
		} catch (UnknownHostException e1) {
			JOptionPane.showMessageDialog(null, "无法连接服务器！");
			msg.dispose();
			return false;
		} catch (IOException e1) {
			JOptionPane.showMessageDialog(null, "建立连接失败！");
			msg.dispose();
			return false;
		}
		
		msg.dispose();
		new UserWindow(exactName, ur, uth);
		lg.dispose();
		
		ur.login();
		return true;
	}
	
	static boolean loginJudge(){
		if (exactName.length() < 6 || exactName.length() > 12) {
			JOptionPane.showMessageDialog(null, "请输入6~12位用户名！");
			return testbool;

		} else if (Login.pwdText.getText().trim().length() < 6
				|| Login.pwdText.getText().trim().length() > 12) {

			JOptionPane.showMessageDialog(null, "请输入6~12位密码！");
			Login.pwdText.setText("");
			return testbool;
		}
		return true;
	}

	public static void main(String[] args) {
		lg = new Login();
		
	}
	
}
