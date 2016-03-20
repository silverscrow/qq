package gui.userWindow;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class MyActionListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == UserWindow.refrushUser){
			UserWindow.getNewUserList();
		}else if(e.getSource() == UserWindow.send){
			String s = UserWindow.talkText.getText().trim();
			if(s.length()==0){
				JOptionPane.showMessageDialog(null, "发送内容不能为空！");
				return;
			}
			
			if(UserWindow.talkList.getSelectedItem().equals("所有人")){
				UserWindow.addPrivate("你说：   " + s);
				s = UserWindow.username + "  " + " 说：    " + s;
				UserWindow.ur.send("All", s);
				
			}else if(!UserWindow.isPrivateTalk){
				String m = (String) UserWindow.talkList.getSelectedItem();
				UserWindow.addPrivate("你对  " + m +"  说： " + s);
				s = UserWindow.username + "  " + "对" + "  " + m + "   说：    " + s;
				UserWindow.ur.send("All", s);
				
			}else if(UserWindow.isPrivateTalk){
				String m = (String) UserWindow.talkList.getSelectedItem();
				UserWindow.addPrivate("[私聊]  你对  " + m +"  说： " + s);
				s = "[私聊]   " + UserWindow.username + "  对你说：" + s;
				UserWindow.ur.send(m, s);
			}
			UserWindow.talkText.setText("");
		}else if(e.getSource() == UserWindow.sendFileBtn){
			FileSender fs = null;
			JFileChooser fcs = new JFileChooser();
			int x = fcs.showOpenDialog(null);
			if(x == JFileChooser.APPROVE_OPTION){
				 String filepath = fcs.getSelectedFile().getAbsolutePath();
				 String name = (String) UserWindow.talkList.getSelectedItem();
				 fs = new FileSender(filepath,name);
			}else if(x == JFileChooser.CANCEL_OPTION){
				
			}
			fs.dispose();
		}
	}

}
