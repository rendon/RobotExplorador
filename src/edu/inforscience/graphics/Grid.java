package edu.inforscience.graphics;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import edu.inforscience.algorithm.*;

public class Grid extends JPanel {

  public final int MIN_DIMENSION = 10;
  public final int MAX_DIMENSION = 50;
  private Cell grid[][];
  private int rows, cols;
  private double width, height;
  private boolean activeEraser; // if true the erase tool is active
  private boolean activePencil;
  private boolean activeBomber;

  private ImageIcon bombIcon;
  private ImageIcon playerIcon;
  private ImageIcon wallIcon;
  private Thread playerThread;

  private Cell player;

  public Grid()
  {
    activeEraser = false;
    rows = cols = MIN_DIMENSION;
    grid = new Cell[MAX_DIMENSION][MAX_DIMENSION];

    for (int i = 0; i < grid.length; i++)
      for (int j = 0; j < grid[0].length; j++)
        grid[i][j] = new Cell(0, 0);

    addMouseListener(new MouseAdapter() {
      public void mousePressed(MouseEvent event)
      {
        int x = event.getX();
        int y = event.getY();

        for (int i = 0; i < rows; i++) {
          for (int j = 0; j < cols; j++) {
            if (i == 0 && j == 0)
              continue;

            if ((i + 1 == rows) && (j + 1 == cols))
              continue;

            if (player.getRow() == i && player.getCol() == j)
              continue;

            if ((x >= grid[i][j].getCol() && x < grid[i][j].getCol() + width) &&
              (y >= grid[i][j].getRow() && y < grid[i][j].getRow() + height)) {
              if (isActiveEraser()) {
                grid[i][j].setContent(Cell.EMPTY);
              } else if (isActiveBomber()) {
                grid[i][j].setContent(Cell.BOMB);
              } else {
                grid[i][j].setContent(Cell.WALL);
              }
              break;
            }
          }
        }
        repaint();
      }
    }
    );

    addMouseMotionListener(new MouseMotionListener() {
      public void mouseDragged(MouseEvent event)
      {
        int x = event.getX();
        int y = event.getY();

        for (int i = 0; i < rows; i++) {
          for (int j = 0; j < cols; j++) {
            if (i == 0 && j == 0)
              continue;

            if ((i + 1 == rows) && (j + 1 == cols))
              continue;

            if (player.getRow() == i && player.getCol() == j)
              continue;

            if ((x >= grid[i][j].getCol() && x <= grid[i][j].getCol() + width) &&
              (y >= grid[i][j].getRow() && y <= grid[i][j].getRow() + height)) {

              if (isActiveEraser()) {
                grid[i][j].setContent(Cell.EMPTY);
              } else if (isActiveBomber()) {
                grid[i][j].setContent(Cell.BOMB);
              } else {
                grid[i][j].setContent(Cell.WALL);
              }
              break;
            }
          }
        }
        repaint();
      }
      public void mouseMoved(MouseEvent event) { }
    });

    bombIcon = loadPicture("bomb.png");
    playerIcon = loadPicture("robot.png");
    wallIcon = loadPicture("wall.png");

    player = new Cell(0, 0);
    playerThread = new Thread(new PlayerThread());
  }

  public void paintComponent(Graphics g)
  {
    super.paintComponent(g);

    width  = getWidth()  / (double)cols;
    height = getHeight() / (double)rows;
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        grid[i][j].setCol((int)Math.round(j * width));
        grid[i][j].setRow((int)Math.round(i * height));
      }
    }

    // Draw grid
    g.setColor(Color.LIGHT_GRAY);
    for (int i = 0; i < rows; i++) {
      int y = (int)Math.round(i * height);
      g.drawLine(0, y, getWidth() - 1, y);
    }

    for (int i = 0; i < cols; i++) {
      int x = (int)Math.round(i * width);
      g.drawLine(x, 0, x, getHeight() - 1);
    }

    // Fill pixels which are active
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        int content = grid[i][j].getContent();
        int w = (int)Math.round(width);
        int h = (int)Math.round(height);
        int r = grid[i][j].getRow();
        int c = grid[i][j].getCol();

        if (player.getRow() == i && player.getCol() == j) {
          g.drawImage(playerIcon.getImage(), c, r, w, h, null);
        } if (content == Cell.WALL) {
          g.drawImage(wallIcon.getImage(), c, r, w, h, null);
        } else if (content == Cell.BOMB) {
          g.drawImage(bombIcon.getImage(), c, r, w, h, null);
        }
      }
    }

    // Draw contour
    g.setColor(Color.RED);
    g.drawLine(0, 0, getWidth() - 1, 0);
    g.drawLine(0, getHeight() - 1, getWidth() - 1, getHeight() - 1);
    g.drawLine(0, 0, 0, getHeight() - 1);
    g.drawLine(getWidth() - 1, 0, getWidth() - 1, getHeight() - 1);

  } // End paintComponent()

  public void setRows(int value)
  {
    rows = value;
    repaint();
  }
  public void setCols(int value)
  {
    cols = value;
    repaint();
  }


  public boolean isActiveEraser()
  {
    return activeEraser;
  }

  public void setActiveEraser(boolean activeEraser)
  {
    this.activeEraser = activeEraser;
  }

  public boolean isActivePencil()
  {
    return activePencil;
  }

  public void setActivePencil(boolean activePencil)
  {
    this.activePencil = activePencil;
  }

  public boolean isActiveBomber()
  {
    return activeBomber;
  }

  public void setActiveBomber(boolean activeBomber)
  {
    this.activeBomber = activeBomber;
  }


  // Util
  private ImageIcon loadPicture(String pictureName)
  {
    return new ImageIcon(getClass().getResource("/" + pictureName));
  }

  public void play()
  {
    playerThread.start();
  }

  private class PlayerThread implements Runnable {
    @Override
    public void run()
    {
      int r = player.getRow();
      int c = player.getCol();
      boolean restart;
      PathFinder pathFinder = new PathFinder();
      while ((r + 1 != rows) || (c + 1 != cols)) {
        System.out.println("new start: " + r + ", " + c);
        ArrayList<Cell> path = pathFinder.getShortestPath(r, c, grid, rows);
        if (path != null) {
          restart = false;
          for (int i = 0; i < path.size() && !restart; i++) {
            Cell cell = path.get(i);
            int cr = cell.getRow();
            int cc = cell.getCol();
            int content = grid[cr][cc].getContent();

            System.out.println("(" + cr + ", " + cc + ")");

            if (content != Cell.EMPTY) {
              restart = true;
              System.out.println("RESTART!");
              break;
            }

            r = cr;
            c = cc;

            player.setRow(cr);
            player.setCol(cc);
            repaint();
            try {
              Thread.sleep(100);
            } catch (InterruptedException e) {
              System.out.println(e.toString());
            }
          }
        } else {
          System.out.println("No path!");
        }

        try {
          Thread.sleep(500);
        } catch (InterruptedException e) {
        }
      }

    }
  }
}

