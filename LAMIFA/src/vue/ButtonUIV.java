/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vue;

import java.awt.Color;
import java.awt.Dimension;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static java.nio.file.Files.size;
import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicButtonUI;

/**
 *
 * @author p1800666
 */
public class ButtonUIV extends BasicButtonUI {

    	public static final int BUTTON_WIDTH = 80;
	public static final int BUTTON_HEIGHT = 24;

	private static final ButtonUIV INSTANCE = new ButtonUIV();  

	public static ComponentUI createUI(JComponent b) {
		return INSTANCE;
	}
	
	@Override
	public void paint(Graphics g, JComponent c) {
		AbstractButton button = (AbstractButton) c;
                c.setBorder(new EmptyBorder(5, 15, 5, 15));
                c.setBackground(Color.WHITE);
                
                
                c.addMouseListener(new MouseAdapter() {
                    public void mouseEntered(MouseEvent me) {
                       c.setForeground(Color.red);
                    }
                    public void mouseExited(MouseEvent me) {
                       c.setForeground(Color.BLACK);
                    }
                });
                
		Graphics2D g2 = (Graphics2D) g;
                Dimension size = c.getSize();

                g.setColor(new Color(40, 173, 131));
                g.fillRoundRect(0, 0, size.width, size.height, 10, 10);
                g.setColor(c.getBackground());
                g.fillRoundRect(0, size.height/10, size.width, size.height-size.height/5, 10, 10);
		super.paint(g, button);
	}

	@Override
	public Dimension getPreferredSize(JComponent c) {
		AbstractButton button = (AbstractButton) c;
		int width = Math.max(button.getWidth(), BUTTON_WIDTH);
		int height = Math.max(button.getHeight(), BUTTON_HEIGHT);
		return new Dimension(width, height);
	}
}

