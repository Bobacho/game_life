package org.example;

import java.awt.Rectangle;

public class Cell extends Rectangle {
  public boolean isAlive;

  public Cell(int x, int y, int width, int height, boolean isAlive) {
    super(x, y, width, height);
    this.isAlive = isAlive;
  }
}
