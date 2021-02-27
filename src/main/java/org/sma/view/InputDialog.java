package org.sma.view;

import javax.swing.*;

public class InputDialog extends JOptionPane{
	private Object[] answers = {"yes", "no"};
	private Object parent;
	
	public InputDialog(Object parent, String message) {
		this.parent = parent;
		this.setInitialValue((String) answers[0]);
		this.setMessage(message);
 	}
	
	public String getAnswer() {
		return (String) this.showInputDialog(parent, answers);
	}
}
