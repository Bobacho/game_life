package org.example;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

import javax.swing.JPanel;

public class Board extends JPanel {

  private List<List<Cell>> grids = new ArrayList<>();
  private State state;
  private boolean isInializedCells = false;
  private Thread thread = Thread.ofVirtual().unstarted(this::updatingBoard);
  private AtomicLong speed = new AtomicLong(1024);

  private void updatingBoard() {
    while (true) {
      this.state.updateTurn();
      System.out.println("Current Turn:" + this.state.currentTurn);
      try {
        Thread.sleep(speed.get());
      } catch (InterruptedException e) {
        e.printStackTrace();
        return;
      }
      System.out.println("Current speed:" + speed.get());
      repaint();
    }
  }

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    if (!isInializedCells) {
      initializeCells();
      isInializedCells = true;
    }
    g.setColor(Color.WHITE);
    for (List<Cell> row : grids) {
      for (Cell cell : row) {
        g.drawRect((int) cell.getX(), (int) cell.getY(), (int) cell.getWidth(), (int) cell.getHeight());
        if (cell.isAlive) {
          g.fillRect((int) cell.getX(), (int) cell.getY(), (int) cell.getWidth(), (int) cell.getHeight());
        }
      }
    }
  }

  public void updateBoard() {
    this.state.isInPlay = !this.state.isInPlay;

    if (this.state.isInPlay) {
      thread.start();
    } else {
      thread.interrupt();
      thread = Thread.ofVirtual().unstarted(this::updatingBoard);
    }
  }

  private void initializeCells() {
    int widthRect = this.getSize().width / 100;
    int heightRect = this.getSize().height / 100;
    Random random = new Random();
    for (int i = 0; i < this.getSize().height; i += heightRect) {
      int cellAliveNumber = random.nextInt(1000);
      Set<Integer> seedNumbers = new HashSet<>(cellAliveNumber);
      List<Cell> rowCell = new ArrayList<>();
      for (int j = 0; j < cellAliveNumber; j++) {
        seedNumbers.add(random.nextInt(this.getSize().width));
      }
      for (int j = 0; j < this.getSize().width; j += widthRect) {
        rowCell.add(new Cell(j, i, widthRect, heightRect, seedNumbers.contains(j)));
      }
      grids.add(rowCell);
    }

  }

  public Board() {
    this.setBackground(Color.BLACK);
    this.state = new State(grids);
    this.setFocusable(true);
    this.addKeyListener(new KeyListener() {

      @Override
      public void keyTyped(KeyEvent e) {
      }

      @Override
      public void keyPressed(KeyEvent e) {
        System.out.println(e.getKeyCode());
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
          updateBoard();
        } else if (e.getKeyCode() == 61) {
          speed.set(speed.get() / 2);
        } else if (e.getKeyCode() == KeyEvent.VK_MINUS) {
          speed.set(speed.get() * 2);
        }
      }

      @Override
      public void keyReleased(KeyEvent e) {
      }

    });
  }

}
