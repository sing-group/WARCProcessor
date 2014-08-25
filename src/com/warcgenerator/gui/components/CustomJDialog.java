package com.warcgenerator.gui.components;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.WindowListener;

import javax.swing.JDialog;
import javax.swing.JFrame;

public class CustomJDialog extends JDialog {
	public CustomJDialog(JFrame frame, boolean modal) {
		super(frame, modal);
		setResizable(false);
	}
	
	@Override
	public void setVisible(boolean visible) {
		showCentered();
		super.setVisible(visible);
	}
	
	public void showCentered() {
		Object obj = getParent();
		
		if (obj instanceof JFrame) {
		    JFrame parent = (JFrame)getParent();
		    Dimension dim = parent.getSize();
		    Point     loc = parent.getLocationOnScreen();
	
		    Dimension size = getSize();
	
		    loc.x += (dim.width  - size.width)/2;
		    loc.y += (dim.height - size.height)/2;
	
		    if (loc.x < 0) loc.x = 0;
		    if (loc.y < 0) loc.y = 0;
	
		    Dimension screen = getToolkit().getScreenSize();
	
		    if (size.width  > screen.width)
		      size.width  = screen.width;
		    if (size.height > screen.height)
		      size.height = screen.height;
	
		    if (loc.x + size.width > screen.width)
		      loc.x = screen.width - size.width;
	
		    if (loc.y + size.height > screen.height)
		      loc.y = screen.height - size.height;
	
		    setBounds(loc.x, loc.y, size.width, size.height);
		}
	  }
}
