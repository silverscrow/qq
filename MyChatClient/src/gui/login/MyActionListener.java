package gui.login;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MyActionListener implements ActionListener {
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Login.exactName = Login.nameText.getText().trim();
		if (e.getSource() == Login.login) {
			if(Login.loginJudge())
				Login.login();
		} else if (e.getSource() == Login.register) {
			Login.close();
		}

	}

}
