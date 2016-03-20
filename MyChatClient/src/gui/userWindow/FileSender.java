package gui.userWindow;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class FileSender extends JFrame {
	private static final long serialVersionUID = 1L;

	JLabel jindu;
	JButton cancel;
	Socket sk;
	JPanel jp;
	String path;
	String name;
	static FileSender fs;

	public FileSender(String filePath,String name) {
		super("发送进度");
		
		this.path = filePath;
		this.sk = UserWindow.ur.sk;
		this.name = name;
		FileSender.fs = this;
		
		jp = new JPanel();
		jindu = new JLabel("0%");
		cancel = new JButton("取消");
		jp.add(jindu);
		jp.setPreferredSize(new Dimension(199, 30));
		
		this.setLayout(new FlowLayout());
		this.add(jp);
		this.add(cancel);

		this.setBounds(300, 200, 200, 100);
		this.setResizable(false);
		this.setVisible(true);
		
		cancel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				FileSender.fs.dispose();
			}
		});
		
		sendFile();
		
	}
	
	public void sendFile() {
		System.out.println("!");
		UserWindow.ur.out.println("sendFile");
		UserWindow.ur.out.flush();
		File file = new File("D:/test.exe");
		System.out.println(file.length());
		
		byte imags[]=new byte[1024];
		try 
		{
			DataOutputStream dout = null;
			try {
				dout = new DataOutputStream(sk.getOutputStream());
				
			} catch (IOException e1) {
			}
			FileInputStream in=new FileInputStream(file);
			try {
				while(in.read(imags)!=-1)
				{
					dout.write(imags);
				}
			} catch (IOException e) {
			}
		} catch (FileNotFoundException e) {
		}
	}
	
}
