package gui.userWindow;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class MyItemListener implements ItemListener {

	@Override
	public void itemStateChanged(ItemEvent e) {
		if(e.getSource() == UserWindow.privateChek){
			if(e.getStateChange() == ItemEvent.SELECTED){
				UserWindow.isPrivateTalk = true;
			}else{
				UserWindow.isPrivateTalk = false;
			}
		}
	}

}
