package org.example;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

public class Game extends JFrame {
  private Board board;

  public Game() {
    board = new Board();
    this.setExtendedState(JFrame.MAXIMIZED_BOTH);
    this.add(board);
  }
}
