package edu.inforscience.algorithm;

import edu.inforscience.graphics.Cell;

import java.util.*;

public class PathFinder {
  public static final int MAX = 55;
  public static final int INFINITY = 1 << 30;
  private int[][] distance;
  private boolean[][] visited;
  private Cell[] path;
  private Cell[][] G;
  private PriorityQueue<Node> Q;
  private int[] dr;
  private int[] dc;

  public PathFinder()
  {
    NodeComparator comparator = new NodeComparator();
    Q = new PriorityQueue<Node>(MAX * MAX, comparator);
    distance = new int[MAX][MAX];
    visited = new boolean[MAX][MAX];
    path = new Cell[MAX*MAX];
    for (int i = 0; i < MAX * MAX; i++)
      path[i] = new Cell();

    dr = new int[] {1, 0, 0, -1, 1, -1, 1, -1};
    dc = new int[] {0, 1, -1, 0, 1, 1, -1, -1};
  }

  public ArrayList<Cell> getShortestPath(int row, int col, Cell[][] G, int n)
  {
    int i, j, r, c, cost;
    Q.clear();
    Q.add(new Node(row, col, 0));
    Node front;

    for (i = 0; i < n; i++) {
      for (j = 0; j < n; j++) {
        distance[i][j] = INFINITY;
        visited[i][j] = false;
      }
    }
    distance[row][col] = 0;

    boolean found = false;
    while (!Q.isEmpty()) {
      front = Q.poll();
      r = front.getRow();
      c = front.getCol();
      cost = front.getCost();

      if (visited[r][c]) continue;
      visited[r][c] = true;

      for (i = 0; i < 8; i++) {
        if ((r + dr[i] < 0) || (r + dr[i] >= n) ||
          (c + dc[i] < 0) || (c + dc[i] >= n)) {
          continue;
        }

        int content = G[r+dr[i]][c+dc[i]].getContent();
        if ((content == Cell.EMPTY) && (cost + 1 < distance[r+dr[i]][c+dc[i]])) {
          distance[r+dr[i]][c+dc[i]] = cost + 1;
          if ((r + dr[i] + 1 == n) && (c + dc[i] + 1 == n)) {
            found = true;
            break;
          }
          Q.add(new Node(r + dr[i], c + dc[i], cost + 1));
        }
      }
    }

    if (!found)
      return null;

    ArrayList<Cell> path = new ArrayList<Cell>();

    for (i = 0; i < n; i++) {
      for (j = 0; j < n; j++) {
        if (distance[i][j] == INFINITY)
          System.out.print("- ");
        else
          System.out.print(distance[i][j] + " ");
      }
      System.out.println();
    }
    System.out.println();

    r = c = n - 1;
    cost = distance[r][c];
    System.out.println("cost = " + cost);
    //while (r != row && c != col) {
    while (cost > 0) {
      path.add(new Cell(r, c));
      System.out.println("r = " + r + " c = " + c);
      for (i = 0; i < 8; i++) {
        int nr = r + dr[i];
        int nc = c + dc[i];
        if ((nr < 0) || (nr >= n) || (nc < 0) || (nc >= n))
          continue;

        if (distance[nr][nc] == cost - 1) {
          r = nr;
          c = nc;
          break;
        }
      }

      cost--;
    }

    System.out.println();
    Collections.reverse(path);
    return path;
  }
}

