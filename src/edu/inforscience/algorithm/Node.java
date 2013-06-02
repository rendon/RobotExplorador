package edu.inforscience.algorithm;

public class Node {
  private int row, col, cost;

  public Node()
  {
    setRow(0);
    setCol(0);
    setCost(0);
  }

  public Node(int row, int col, int cost)
  {
    setRow(row);
    setCol(col);
    setCost(cost);
  }

  public int getCol()
  {
    return col;
  }

  public void setCol(int col)
  {
    this.col = col;
  }

  public int getCost()
  {
    return cost;
  }

  public void setCost(int cost)
  {
    this.cost = cost;
  }

  public int getRow()
  {
    return row;
  }

  public void setRow(int row)
  {
    this.row = row;
  }
}

