package org.example;

import java.awt.Panel;
import java.util.List;
import java.util.Set;

public class State {
  public int currentTurn = 0;
  public boolean isInPlay = false;

  private List<List<Cell>> cells;

  public State(List<List<Cell>> cells) {
    this.cells = cells;
  }

  public void updateTurn() {
    checkLifeStateCells();
    currentTurn++;
    System.out.println("Cells alive:" + cellsAliveNumber());
  }

  private int cellsAliveNumber() {
    int result = 0;
    for (List<Cell> row : cells) {
      for (Cell cell : row) {
        if (cell.isAlive) {
          result++;
        }
      }
    }
    return result;
  }

  public void checkLifeStateCells() {
    boolean[][] nextState = new boolean[cells.size()][];
    for (int i = 0; i < cells.size(); i++) {
      nextState[i] = new boolean[cells.get(i).size()];
      for (int j = 0; j < cells.get(i).size(); j++) {
        nextState[i][j] = cells.get(i).get(j).isAlive;
      }
    }

    for (int i = 0; i < cells.size(); i++) {
      for (int j = 0; j < cells.get(i).size(); j++) {
        int neighborsAlive = 0;

        for (int k = i - 1; k <= i + 1; k++) {
          for (int m = j - 1; m <= j + 1; m++) {
            if (k >= 0 && k < cells.size() && m >= 0 && m < cells.get(k).size()) {
              if (!(k == i && m == j) && cells.get(k).get(m).isAlive) {
                neighborsAlive++;
              }
            }
          }
        }

        boolean currentAlive = cells.get(i).get(j).isAlive;
        if (currentAlive) {
          if (neighborsAlive < 2 || neighborsAlive > 3) {
            nextState[i][j] = false;
          }
        } else {
          if (neighborsAlive == 3) {
            nextState[i][j] = true;
          }
        }
      }
    }

    for (int i = 0; i < cells.size(); i++) {
      for (int j = 0; j < cells.get(i).size(); j++) {
        cells.get(i).get(j).isAlive = nextState[i][j];
      }
    }
  }

}
