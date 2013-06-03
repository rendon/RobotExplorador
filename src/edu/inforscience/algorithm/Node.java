package edu.inforscience.algorithm;

public class Node implements Comparable<Node> {
  private int row, col, cost, bombs;

  public Node()
  {
    setRow(0);
    setCol(0);
    setCost(0);
    setBombs(0);
  }

  public Node(int row, int col, int cost, int bombs)
  {
    setRow(row);
    setCol(col);
    setCost(cost);
    setBombs(bombs);
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

  public int getBombs()
  {
    return bombs;
  }

  public void setBombs(int bombs)
  {
    this.bombs = bombs;
  }

  @Override
  public int compareTo(Node other)
  {
    return getCost() - other.getCost();
  }

}

