package edu.inforscience.algorithm;

import edu.inforscience.graphics.Cell;

import java.util.*;
import java.io.PrintStream;

public class PathFinder {
  public static final int MAX = 55;
  public static final int INFINITY = 1 << 30;
  private int[][] distance;
  private Cell[][] prev;
  private boolean[][] visited;
  private Cell[] path;
  private PriorityQueue<Node> Q;
  private int[] dr;
  private int[] dc;
  private PrintStream log;

  public PathFinder()
  {
    Q = new PriorityQueue<Node>(MAX * MAX);
    distance = new int[MAX][MAX];
    prev = new Cell[MAX][MAX];
    visited = new boolean[MAX][MAX];

    path = new Cell[MAX*MAX];
    for (int i = 0; i < MAX * MAX; i++)
      path[i] = new Cell();

    dr = new int[] {1, 0, 0, -1, 1, -1, 1, -1};
    dc = new int[] {0, 1, -1, 0, 1, 1, -1, -1};
    log = new PrintStream(System.out);
  }

  public ArrayList<Cell> getShortestPath(int row, int col, Cell[][] G, int n, int endRow, int endCol)
  {
    int i, j, r, c, cost, nr, nc, content;
    Node next;
    Q.clear();
    Q.add(new Node(row, col, 0, 0));

    for (i = 0; i < n; i++) {
      for (j = 0; j < n; j++) {
        distance[i][j] = INFINITY;
        visited[i][j] = false;
      }
    }

    distance[row][col] = 0;
    boolean found = false;
    while (!Q.isEmpty()) {
      next = Q.poll();
      r = next.getRow();
      c = next.getCol();
      cost = next.getCost();

      if (visited[r][c]) continue;
      visited[r][c] = true;

      for (i = 0; i < 8; i++) {
        nr = r + dr[i];
        nc = c + dc[i];

        if (nr < 0 || nr >= n || nc < 0 || nc >= n)
          continue;

        content = G[nr][nc].getContent();

        if ((content != Cell.WALL) && (cost + 1 < distance[nr][nc])) {
          distance[nr][nc] = cost + 1;
          if (nr == endRow && nc == endCol) {
            found = true;
            break;
          }
          Q.add(new Node(nr, nc, cost + 1, 0));
        }
      }
    }

    if (!found)
      return null;

    ArrayList<Cell> path = new ArrayList<Cell>();
    r = endRow;
    c = endCol;
    cost = distance[r][c];
    while (cost > 0) {
      path.add(new Cell(r, c));
      for (i = 0; i < 8; i++) {
        nr = r + dr[i];
        nc = c + dc[i];

        if (nr < 0 || nr >= n || nc < 0 || nc >= n)
          continue;

        if (distance[nr][nc] + 1 == cost) {
          r = nr;
          c = nc;
          break;
        }
      }
      cost--;
    }

    Collections.reverse(path);

    return path;
  }


  public int minCrossPath(int row, int col, Cell[][] G, int n, int endRow, int endCol, ArrayList<Cell> L)
  {
    int i, j, r, c, nr, nc, cost;
    Q.clear();
    Q.add(new Node(row, col, 0, 0));
    Node front;

    for (i = 0; i < n; i++) {
      for (j = 0; j < n; j++) {
        distance[i][j] = INFINITY;
        prev[i][j] = new Cell();
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
        nr = r + dr[i];
        nc = c + dc[i];
        if (nr < 0 || nr >= n || nc < 0 || nc >= n)
          continue;

        int content = G[nr][nc].getContent();
        int newCost = cost +  ((content == Cell.WALL) ? 1 : 0);
        if (newCost < distance[nr][nc]) {
          distance[nr][nc] = newCost;
          prev[nr][nc] = new Cell(r, c);
          if (nr == endRow && nc == endCol) {
            found = true;
            break;
          }
          Q.add(new Node(nr, nc, newCost, 0));
        }
      }
    }

    if (!found)
      return INFINITY;

    //for (i = 0; i < n; i++) {
    //  for (j = 0; j < n; j++) {
    //    log.print(prev[i][j] + "\t");
    //  }
    //  log.println();
    //}

    r = endRow;
    c = endCol;

    while (true) {
      L.add(new Cell(r, c));
      if (r == row && c == col)
        break;
      nr = r;
      nc = c;
      r = prev[nr][nc].getRow();
      c = prev[nr][nc].getCol();
    }

    Collections.reverse(L);
    return distance[endRow][endCol];
  }


  public void collectBombs(int row, int col, Cell[][] G, int n, ArrayList<Cell> B)
  {
    int i, j, r, c;
    Q.clear();
    Q.add(new Node(row, col, 0, 0));
    Node front;

    B.clear();

    for (i = 0; i < n; i++)
      for (j = 0; j < n; j++)
        visited[i][j] = false;

    while (!Q.isEmpty()) {
      front = Q.poll();
      r = front.getRow();
      c = front.getCol();

      if (visited[r][c]) continue;
      visited[r][c] = true;

      if (G[r][c].getContent() == Cell.BOMB)
        B.add(new Cell(r, c));

      for (i = 0; i < 8; i++) {
        if ((r + dr[i] < 0) || (r + dr[i] >= n) ||
          (c + dc[i] < 0) || (c + dc[i] >= n)) {
          continue;
        }

        int content = G[r+dr[i]][c+dc[i]].getContent();
        if (content == Cell.WALL)
          continue;

        Q.add(new Node(r + dr[i], c + dc[i], 0, 0));
      }
    }

  }

  private void print(int n)
  {
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        if (distance[i][j] == INFINITY)
          log.print("- ");
        else
          log.print(distance[i][j] + " ");
      }

      log.println();
    }
  }
}

