package edu.inforscience.util;

import java.awt.*;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.ImageIcon;

public class About extends JFrame {
  public About()
  {
    super("Acerca de");
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
    ImageIcon icon = new ImageIcon(getClass().getResource("/applications-graphics.png"));
    JLabel iconLabel = new JLabel(icon);
    iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
    add(iconLabel);
    JLabel programName = new JLabel("Juego_IA v1.0");
    programName.setAlignmentX(Component.CENTER_ALIGNMENT);
    programName.setFont(new Font("Monospaced", Font.BOLD, 18));
    add(programName);

    JLabel copyrightLabel = new JLabel("Copyright (c) 2013 Rafael Rendon Pablo");
    copyrightLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
    add(copyrightLabel);
    setSize(300, 200);
    setLocationRelativeTo(null);
    setVisible(true);
  }
}

