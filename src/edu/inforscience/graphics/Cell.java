package edu.inforscience.graphics;

public class Cell implements Comparable<Cell> {
  public static final int EMPTY   = 0;
  public static final int WALL    = 1;
  public static final int BOMB    = 2;
  public static final int PLAYER  = 3;
  private int row, col;
  private int content;


  public Cell()
  {
    setRow(0);
    setCol(0);
    setContent(EMPTY);
  }

  public Cell(int row, int col)
  {
    setRow(row);
    setCol(col);
    setContent(EMPTY);
  }

  public int getCol()
  {
    return col;
  }

  public void setCol(int col)
  {
    this.col = col;
  }

  public int getRow()
  {
    return row;
  }

  public void setRow(int row)
  {
    this.row = row;
  }

  public int getContent()
  {
    return content;
  }

  public void setContent(int content)
  {
    this.content = content;
  }

  @Override
  public String toString()
  {
    return "(" + getRow() + ", " + getCol() + ")";
  }

  @Override
  public int compareTo(Cell other)
  {
    if ((getRow() < other.getRow()) || (getCol() < other.getCol()))
      return -1;
    if ((getRow() == other.getRow()) && (getCol() == other.getCol()))
      return 0;
    return 1;
  }

  @Override
  public boolean equals(Object obj)
  {
    Cell other = (Cell)obj;
    return (getRow() == other.getRow()) && (getCol() == other.getCol());
  }
}


