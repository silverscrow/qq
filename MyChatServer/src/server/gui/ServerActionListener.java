package server.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JOptionPane;

public class ServerActionListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		//启动服务器
		if (e.getSource() == ServerManage.start) {
			try {
				Server.start();
			} catch (IOException e1) {
				JOptionPane.showMessageDialog(null, "服务器开启失败！");
				return;
			}
			ServerManage.changeStart();
			JOptionPane.showMessageDialog(null, "已启动！");
		//重启服务器
		} else if (e.getSource() == ServerManage.restart) {
			try {
				Server.close();
				Server.start();
			} catch (IOException e1) {
				ServerManage.erro(1);
			}
			JOptionPane.showMessageDialog(null, "重新启动服务器成功！");
			ServerManage.changeStart();
		//关闭服务器
		} else if (e.getSource() == ServerManage.close) {
			try {
				Server.ss.close();
			} catch (IOException e1) {
				ServerManage.erro(2);
			}
			JOptionPane.showMessageDialog(null, "服务器已关闭！");
			ServerManage.changeClose();
		}

	}

}
