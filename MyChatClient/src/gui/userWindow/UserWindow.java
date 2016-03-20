package gui.userWindow;

import gui.login.UserRunnable;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;

public class UserWindow {
	private static final boolean textEditable = false;
	static UserRunnable ur;
	static Thread th;
	
	static boolean isPrivateTalk=false;
	static String username;
	
	static JFrame frm;
	
	static JPanel title;
	static JPanel left;
	static JPanel right;
	static JPanel mainpd;
	static JPanel mypd;
	static JPanel lt;
	static JPanel lt1;
	static JPanel mySend;
	static JPanel leftdown;
	
	static JLabel name;
	static JLabel mainLable;
	static JTextArea mainText;
	static JLabel myLable;
	static JTextArea myText;
	static JComboBox<String> talkList;
	static JCheckBox privateChek;
	static JTextArea talkText;
	static JButton send;
	static DefaultListModel<String> userList;
	static JList<String> peopleList;
	static JButton refrushUser;
	static JLabel dui = new JLabel("对");
	static JButton sendFileBtn;
	
	static JScrollPane publicTalkSP;
	static JScrollPane privateTalkSP;
	static JScrollPane sendText;
	
	static MyListSelectListener listListener;
	static MyActionListener actionListener;
	static MyItemListener itemListener;
	
	public UserWindow(String username,UserRunnable ur,Thread th) {
		frm = new JFrame("聊天室");
		UserWindow.ur = ur;
		UserWindow.th = th;
		UserWindow.username = username;
		
		listListener = new MyListSelectListener();
		actionListener = new MyActionListener();
		itemListener = new MyItemListener();
		
		frm.setLayout(new BorderLayout());
		frm.setBounds(100, 100, 500, 600);
		frm.setVisible(true);
		
		title = new JPanel();
		name = new JLabel(username);
		title.add(name);
		
		mainpd = new JPanel();
		mainpd.setLayout(new BorderLayout());
		mainLable = new JLabel("公共频道");
		mainText = new JTextArea();
		mainText.setEditable(textEditable);
		mainText.setLineWrap(true);
		publicTalkSP = new JScrollPane(mainText);
		publicTalkSP.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		publicTalkSP.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		mainpd.add(mainLable,BorderLayout.NORTH);
		mainpd.add(publicTalkSP,BorderLayout.CENTER);
		
		mypd = new JPanel();
		mypd.setLayout(new BorderLayout());
		myLable = new JLabel("我的频道");
		myText = new JTextArea(6,10);
		myText.setEditable(textEditable);
		myText.setLineWrap(true);
		privateTalkSP = new JScrollPane(myText);
		privateTalkSP.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		privateTalkSP.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		mypd.add(myLable,BorderLayout.NORTH);
		mypd.add(privateTalkSP,BorderLayout.CENTER);
		
		lt1 = new JPanel();
		lt1.setLayout(new FlowLayout(FlowLayout.LEFT));
		lt1.add(dui);
		talkList = new JComboBox<String>();
		talkList.addItem("所有人");
		lt1.add(talkList);
		privateChek = new JCheckBox("私聊");
		sendFileBtn = new JButton("文件");
//		sendFileBtn.setEnabled(false);
		sendFileBtn.addActionListener(new MyActionListener());
		privateChek.addItemListener(itemListener);
		lt1.add(privateChek);
		lt1.add(sendFileBtn);
		
		mySend = new JPanel();
		mySend.setLayout(new BorderLayout());
		talkText = new JTextArea(3,10);
		talkText.setLineWrap(true);
		talkText.addKeyListener(new MyKeyListener());
		sendText = new JScrollPane(talkText);
		sendText.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		sendText.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		mySend.add(sendText,BorderLayout.CENTER);
		send = new JButton("发送");
		send.addActionListener(actionListener);
		mySend.add(send,BorderLayout.EAST);
		
		lt = new JPanel();
		lt.setLayout(new BorderLayout());
		lt.add(lt1,BorderLayout.NORTH);
		lt.add(mySend,BorderLayout.CENTER);
		
		leftdown = new JPanel();
		leftdown.setLayout(new BorderLayout());
		leftdown.add(mypd,BorderLayout.NORTH);
		leftdown.add(lt,BorderLayout.SOUTH);
		
		left = new JPanel();
		left.setLayout(new BorderLayout());
		left.add(mainpd,BorderLayout.CENTER);
		left.add(leftdown,BorderLayout.SOUTH);
		
		right = new JPanel();
		right.setLayout(new BorderLayout());
		userList = new DefaultListModel<String>();
		peopleList = new JList<String>(userList);
		peopleList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		peopleList.addListSelectionListener(listListener);
		JScrollPane jsp = new JScrollPane(peopleList);
		jsp.setPreferredSize(new Dimension(150, 400));
		
		right.add(jsp,BorderLayout.CENTER);
		refrushUser = new JButton("刷新用户列表");
		refrushUser.addActionListener(actionListener);
		right.add(refrushUser,BorderLayout.SOUTH);
		
		frm.add(title,BorderLayout.NORTH);
		frm.add(left,BorderLayout.CENTER);
		frm.add(right,BorderLayout.EAST);
		
		frm.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				exit();
			}
		});
		
	}
	
	protected void exit() {
		ur.send("out", "【系统消息】：" + username + "，离开了聊天室");
		try {
			ur.sk.close();
		} catch (IOException e) {
		}
		System.exit(0);
	}

	static public void addPublic(String s){
		mainText.append(s + "\n");
		mainText.setCaretPosition(mainText.getText().length());
	}
	
	static public void addPrivate(String s){
		myText.append(s + "\n");
		myText.setCaretPosition(myText.getText().length());
	}

	public static void addSystem(String s) {
		mainText.append(s + "\n");
		mainText.setCaretPosition(mainText.getText().length());
	}
	
	static public void getNewUserList(){
		UserWindow.userList.removeAllElements();
		UserWindow.ur.out.println("refrushUserList");
		UserWindow.ur.out.flush();
	}
	
	static public void firstUserList(String s){
		String[] list = s.split("#");
		for(int i = list.length-1;i>=0;i--){
			userList.addElement(list[i]);
		}
	}
	
	static public void refrushUserList(String s){
		String[] list = s.split("#");
		if(list.length == 0) JOptionPane.showMessageDialog(null, "目前没有其他用户");
		else{
			for(int i = list.length-1;i>=0;i--){
				userList.addElement(list[i]);
			}
			JOptionPane.showMessageDialog(null, "刷新完成！");
		}
	}
	
	static public void fileCatcher() throws IOException{
		byte[] inputByte = null;
		int length = 0;
		InputStream dis = null;
		FileOutputStream fos = null;
		String filePath = "D:/test/newtest.exe";
		
		dis = UserWindow.ur.sk.getInputStream();
		File f = new File("D:/test");
		if(!f.exists()) f.mkdir();
		
		fos = new FileOutputStream(new File(filePath));
		inputByte = new byte[1024];
		System.out.println("Now catching!");
		
		while((length = dis.read(inputByte, 0, inputByte.length))>=0){
			fos.write(inputByte, 0, length);
			fos.flush();
		}
		
		System.out.println("Catch finished!");
		
		if(fos != null) fos.close();
	}
	
}