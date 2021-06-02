package de.waffel.gui;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

import de.waffel.gui.screens.MenuScreen;

public class MainWindow extends JFrame {
	
	private static final long serialVersionUID = 2277740774263387282L;
	
	private GuiScreen currentScreen;

	/**
	 * Creates and displays a new Window with a default MenuScreen.
	 */
	public MainWindow() {
		this.setResizable(false);
		
		// Add default MenuScreen
		setGuiScreen(new MenuScreen());
		
		// Center on screen
		this.setLocationRelativeTo(null);
		
		// forward KeyEvents to GuiScreen
		this.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				KeyListener[] listeners = currentScreen.getKeyListeners();
				for(KeyListener kl : listeners) {
					kl.keyPressed(e);
				}
			}
		});
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	
	/**
	 * Display a GuiScreen on this Window
	 * @param screen the new GuiScreen to display
	 */
	public void setGuiScreen(GuiScreen screen) {
		currentScreen = screen;
		this.getContentPane().removeAll();
		this.getContentPane().add(currentScreen);
		this.pack();
	}

	public GuiScreen getCurrentScreen() {
		return currentScreen;
	}
}
