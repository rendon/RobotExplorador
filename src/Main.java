import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import edu.inforscience.graphics.Grid;
import edu.inforscience.util.About;

public class Main extends JFrame {

  private JMenuBar menuBar;
  private JMenu fileMenu, helpMenu;
  private JMenuItem quitItem, aboutItem;
  private JToolBar toolBar;
  private JButton drawBombButton, quitButton, eraserButton, pencilButton;
  private JButton playButton;
  private JSpinner dimessionSpinner;
  private ImageIcon bombIcon, pencilIcon, eraserIcon, playIcon, finishIcon;
  private Grid grid;

  public Main()
  {
    super("Juego_IA");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(600, 680);
    setResizable(false);

    initializeComponents();

    setLocationRelativeTo(null);
    setVisible(true);
  }

  private ImageIcon loadPicture(String pictureName)
  {
    return new ImageIcon(getClass().getResource("/" + pictureName));
  }

  public void initializeComponents()
  {
    bombIcon = loadPicture("bomb_icon.png");
    pencilIcon = loadPicture("wall_icon.png");
    eraserIcon = loadPicture("eraser_icon.png");
    playIcon = loadPicture("play_icon.png");

    menuBar = new JMenuBar();
    fileMenu = new JMenu("Archivo");

    quitItem = new JMenuItem("Salir", loadPicture("application-exit.png"));

    fileMenu.add(quitItem);

    ComponentBehavior behavior = new ComponentBehavior();

    quitItem.addActionListener(behavior);

    helpMenu = new JMenu("Ayuda");
    aboutItem = new JMenuItem("Acerca de", loadPicture("help-about.png"));
    helpMenu.add(aboutItem);
    aboutItem.addActionListener(behavior);

    menuBar.add(fileMenu);
    menuBar.add(helpMenu);

    setJMenuBar(menuBar);

    quitButton = new JButton("Salir", loadPicture("application-exit.png"));
    eraserButton = new JButton("Borrador", eraserIcon);
    pencilButton = new JButton("Pared", pencilIcon);
    drawBombButton = new JButton("Bomba", bombIcon);
    playButton = new JButton("Iniciar", playIcon);
    dimessionSpinner = new JSpinner(
        new SpinnerNumberModel(10, Grid.MIN_DIMENSION, Grid.MAX_DIMENSION, 1)
    );

    dimessionSpinner.setValue(10);

    toolBar = new JToolBar("main tool bar");

    toolBar.add(eraserButton);
    toolBar.add(pencilButton);
    toolBar.add(drawBombButton);
    toolBar.add(playButton);
    toolBar.add(quitButton);
    toolBar.add(new JLabel("Dimensión: "));
    toolBar.add(dimessionSpinner);

    quitButton.addActionListener(behavior);
    eraserButton.addActionListener(behavior);
    pencilButton.addActionListener(behavior);
    drawBombButton.addActionListener(behavior);
    playButton.addActionListener(behavior);
    dimessionSpinner.addChangeListener(behavior);

    add(toolBar, BorderLayout.PAGE_START);

    grid = new Grid();
    add(grid, BorderLayout.CENTER);
  }


  public static void main(String[] args)
  {
    Main myGame = new Main();
  }


  class ComponentBehavior implements ActionListener, ChangeListener {

    public void actionPerformed(ActionEvent event)
    {
      if (event.getSource() == quitButton ||
        event.getSource() == quitItem) {
        dispose();
      } else if (event.getSource() == aboutItem) {
        About about = new About();
      } else if (event.getSource() == eraserButton) {
        grid.setActiveBomber(false);
        grid.setActivePencil(false);
        grid.setActiveEraser(true);
      } else if (event.getSource() == pencilButton) {
        grid.setActiveBomber(false);
        grid.setActivePencil(true);
        grid.setActiveEraser(false);
      } else if (event.getSource() == drawBombButton) {
        grid.setActiveBomber(true);
        grid.setActivePencil(false);
        grid.setActiveEraser(false);
      } else if (event.getSource() == playButton) {
        grid.play();
        playButton.setEnabled(false);
      }
    }

    public void stateChanged(ChangeEvent event)
    {
      JSpinner tmp = (JSpinner)event.getSource();
      if (event.getSource() == dimessionSpinner) {
        grid.setRows((Integer)tmp.getValue());
        grid.setCols((Integer)tmp.getValue());
      }
    }
  }// End of ComponentBehavior --inner class--
}// End of Pixelize --class--



