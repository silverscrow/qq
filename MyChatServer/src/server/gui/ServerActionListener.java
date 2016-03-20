package server.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JOptionPane;

public class ServerActionListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		//����������
		if (e.getSource() == ServerManage.start) {
			try {
				Server.start();
			} catch (IOException e1) {
				JOptionPane.showMessageDialog(null, "����������ʧ�ܣ�");
				return;
			}
			ServerManage.changeStart();
			JOptionPane.showMessageDialog(null, "��������");
		//����������
		} else if (e.getSource() == ServerManage.restart) {
			try {
				Server.close();
				Server.start();
			} catch (IOException e1) {
				ServerManage.erro(1);
			}
			JOptionPane.showMessageDialog(null, "���������������ɹ���");
			ServerManage.changeStart();
		//�رշ�����
		} else if (e.getSource() == ServerManage.close) {
			try {
				Server.ss.close();
			} catch (IOException e1) {
				ServerManage.erro(2);
			}
			JOptionPane.showMessageDialog(null, "�������ѹرգ�");
			ServerManage.changeClose();
		}

	}

}
