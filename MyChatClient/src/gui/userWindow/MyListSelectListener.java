package gui.userWindow;

import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class MyListSelectListener implements ListSelectionListener {

	@Override
	public void valueChanged(ListSelectionEvent e) {
		JList<String> list = UserWindow.peopleList;
		JComboBox<String> talkList = UserWindow.talkList;
		if(e.getSource() == list){
			boolean b = true;
			String name = list.getSelectedValue();
			if(name.equals(UserWindow.username)) return;
			
			String[] s = getTalkListElemts(talkList);
			
			for(int i=s.length-1;i>=0;i--){
				if(name.equals(s[i])){
					b=false;
					break;
				}
			}
			if(b) talkList.addItem(name);
			talkList.setSelectedItem(name);
			
		}
	}
	
	public String[] getTalkListElemts(JComboBox<String> talkList){
		String[] s = new String[talkList.getItemCount()];
		for(int i = s.length-1;i>=0;i--){
			s[i] = talkList.getItemAt(i);
		}
		return s;
	}

}
