package server.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class ServerManage extends JFrame {
	private static final long serialVersionUID = 1L;
	
	static JButton start;
	static JButton restart;
	static JButton close;
	static JLabel lable;
	static JLabel status;
	
	
	static ServerActionListener listener;
	
	ServerManage() {
		super("服务器");
		this.setLayout(new FlowLayout());
		
		start = new JButton("启动服务器");
		restart = new JButton("重启服务器");
		close = new JButton("关闭服务器");
		lable = new JLabel("服务器状态：");
		status = new JLabel("已停止！");
		listener = new ServerActionListener();
		
		start.addActionListener(listener);
		restart.addActionListener(listener);
		close.addActionListener(listener);
		status.setForeground(Color.red);
		
		this.add(start);
		this.add(restart);
		this.add(close);
		this.add(lable);
		this.add(status);
		
		start.setPreferredSize(new Dimension(199, 50));
		restart.setPreferredSize(new Dimension(199, 50));
		close.setPreferredSize(new Dimension(199, 50));
		
		this.setBounds(300, 300, 200, 220);
		this.setResizable(false);
		this.setVisible(true);
		
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		
		Server.service();
	}

	static void changeStart() {
		ServerManage.status.setText("运行中！");
		ServerManage.status.setForeground(Color.green);
		ServerManage.start.setEnabled(false);
		ServerManage.close.setEnabled(true);
	}
	
	static void changeClose(){
		ServerManage.status.setText("已停止！");
		ServerManage.status.setForeground(Color.red);
		ServerManage.close.setEnabled(false);
		ServerManage.start.setEnabled(true);
	}
	
	public static void main(String[] args) {
		new ServerManage();
	}

	static void erro(int x) {
		JOptionPane.showMessageDialog(null, "启动失败！即将退出！");
		ServerManage.status.setText("出现错误！");
		ServerManage.status.setForeground(Color.gray);
		System.exit(x);
	}

}
