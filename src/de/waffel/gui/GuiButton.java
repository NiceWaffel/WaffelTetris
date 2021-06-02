package de.waffel.gui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class GuiButton extends JLabel {

	private static final long serialVersionUID = 953702829888176179L;
	
	public ArrayList<ActionListener> actionListeners = new ArrayList<>();

	public GuiButton(int x, int y, int w, int h) {
		this.setLocation(x, y);
		this.setSize(new Dimension(w, h));
		
		this.setCursor(new Cursor(Cursor.HAND_CURSOR));
		
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				sendActionEvent();
			}
		});
		
		this.setHorizontalAlignment(SwingConstants.CENTER);
		this.setVerticalAlignment(SwingConstants.CENTER);
	}
	
	/**
	 * Adds a default 2 pixel wide black border around the component
	 */
	public void addDefaultBorder() {
		this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
	}
	
	private void sendActionEvent() {
		for(ActionListener al : actionListeners) {
			al.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "buttonPressed"));
		}
	}
	
	public void addActionListener(ActionListener al) {
		actionListeners.add(al);
	}
	
	public void removeActionListener(ActionListener al) {
		actionListeners.remove(al);
	}
	
	public void setImage(String assetName) {
		super.setIcon(new ImageIcon(Assets.getImageResource(assetName)));
	}
}
