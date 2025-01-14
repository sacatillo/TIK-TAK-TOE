/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.xogame;

/**
 *
 * @author Martin Montes
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class XOGame extends JFrame {
    private JTextField player1TextField;
    private JTextField player2TextField;
    private JButton[][] buttons;
    private String[][] board;
    private String currentPlayer;
    private boolean gameOver;

    public XOGame() {
        super("Juego X-0");
        setLayout(new BorderLayout());

        // Panel de jugadores
        JPanel playerPanel = new JPanel();
        playerPanel.add(new JLabel("Jugador 1 (X):"));
        player1TextField = new JTextField(10);
        playerPanel.add(player1TextField);
        playerPanel.add(new JLabel("Jugador 2 (0):"));
        player2TextField = new JTextField(10);
        playerPanel.add(player2TextField);
        add(playerPanel, BorderLayout.NORTH);

        // Tablero
        JPanel boardPanel = new JPanel();
        boardPanel.setLayout(new GridLayout(3, 3));
        buttons = new JButton[3][3];
        board = new String[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j] = new JButton();
                buttons[i][j].setFont(new Font("Arial", Font.PLAIN, 60));
                buttons[i][j].addActionListener(new ButtonListener());
                boardPanel.add(buttons[i][j]);
            }
        }
        add(boardPanel, BorderLayout.CENTER);

        // Iniciar juego
        currentPlayer = "X";
        gameOver = false;

        // Configuración de la ventana
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private class ButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (gameOver) return;
            JButton button = (JButton) e.getSource();
            int row = -1, col = -1;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (buttons[i][j] == button) {
                        row = i;
                        col = j;
                        break;
                    }
                }
            }
            if (row == -1 || col == -1) return;
            if (board[row][col] != null) {
                JOptionPane.showMessageDialog(XOGame.this, "Celda ocupada, seleccione otra");
                return;
            }
            board[row][col] = currentPlayer;
            button.setText(currentPlayer);
            button.setEnabled(false); // Deshabilitar el botón después de hacer clic
            checkWin();
            currentPlayer = (currentPlayer.equals("X")) ? "0" : "X";
        }
    }

    private void checkWin() {
        // Verificar horizontal
        for (int i = 0; i < 3; i++) {
            if (board[i][0] != null && board[i][0].equals(board[i][1]) && board[i][0].equals(board[i][2])) {
                gameOver = true;
                JOptionPane.showMessageDialog(XOGame.this, "Usted es el Gananador " + board[i][0]);
                askToPlayAgain();
                return;
            }
        }
        // Verificar vertical
        for (int i = 0; i < 3; i++) {
            if (board[0][i] != null && board[0][i].equals(board[1][i]) && board[0][i].equals(board[2][i])) {
                gameOver = true;
                JOptionPane.showMessageDialog(XOGame.this, "Usted es el Gananador " + board[0][i]);
                askToPlayAgain();
                return;
            }
        }
        // Verificar diagonal
        if (board[0][0] != null && board[0][0].equals(board[1][1]) && board[0][0].equals(board[2][2])) {
            gameOver = true;
            JOptionPane.showMessageDialog(XOGame.this, "Usted es el Gananador " + board[0][0]);
            askToPlayAgain();
            return;
        }
        if (board[0][2] != null && board[0][2].equals(board [1][1]) && board[0][2].equals(board[2][0])) {
            gameOver = true;
            JOptionPane.showMessageDialog(XOGame.this, "Usted es el Gananador " + board[0][2]);
            askToPlayAgain();
            return;
        }
        // Verificar empate
        boolean isFull = true;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == null) {
                    isFull = false;
                    break;
                }
            }
        }
        if (isFull) {
            gameOver = true;
            JOptionPane.showMessageDialog(XOGame.this, "Empate");
            askToPlayAgain();
        }
    }

    private void askToPlayAgain() {
        int response = JOptionPane.showConfirmDialog(this, "Quieres jugar de nuevo?", "Juego Terminado", JOptionPane.YES_NO_OPTION);
        if (response == JOptionPane.YES_OPTION) {
            resetGame();
        } else {
            System.exit(0);
        }
    }

    private void resetGame() {
        currentPlayer = "X";
        gameOver = false;
        board = new String[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText("");
                buttons[i][j].setEnabled(true);
            }
        }
    }

    public static void main(String[] args) {
        new XOGame();
    }
}