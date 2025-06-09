import java.awt.*;
import java.awt.event.*;
import java.util.Set;

import javax.swing.*;

public class TicTacToe {

    int boardWidth = 600;
    int boardHeight = 650; // 50px for the text panel on top

    // let's create our window
    JFrame frame = new JFrame("Tic-Tac-Toe"); // add title to window
    // let's add some panels
    JLabel textLabel = new JLabel();
    // for this lable add JPanel
    JPanel textPanel = new JPanel();
    // let's create board panel
    JPanel boardPanel = new JPanel();
    // Let's add some tiles
    JButton[][] board = new JButton[3][3];
    // playerX, palyerO: Represent the players.
    String playerX = "X";
    String palyerO = "O";
    // // currentPlayer: Tracks whose turn it is.
    String currentPlayer = playerX;
    // to get winner //gameOver: Stops the game after win/tie.
    boolean gameOver = false;
    // to check for a tie //turn: Increments after every move, helps detect tie.
    int turn = 0;
    // to add restart button
    JButton restartButton = new JButton("Restart");

    // Let's add the constructor
    TicTacToe() {

        // main game window
        frame.setVisible(true);
        frame.setSize(boardWidth, boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());// BorderLayout: Used to place components in NORTH, CENTER, etc

        // Text display (e.g., "X's turn", "Winner", etc.).
        // Sets label color, font, alignment, and makes it visible.
        textLabel.setBackground(Color.black);
        textLabel.setForeground(Color.white);
        textLabel.setFont(new Font("Arial", Font.BOLD, 50));
        textLabel.setHorizontalAlignment(JLabel.CENTER);
        textLabel.setText("Tic-Tac-Toe");
        textLabel.setOpaque(true);

        // Adds text label to the top of the frame.
        // Panel to hold textLabel.
        // to add design to text
        textPanel.setLayout(new BorderLayout());
        textPanel.add(textLabel);
        frame.add(textPanel, BorderLayout.NORTH); // To move panel up add parameter - BorderLayout.NORTH

        // to add design to board
        // Creates a 3x3 grid of buttons (tiles) for the game board.
        boardPanel.setLayout(new GridLayout(3, 3)); // it will create 3X3 grid layout to our board
        boardPanel.setBackground(Color.black);
        frame.add(boardPanel); // frame.add(boardPanel) to make our changes visible(add board panel setting)

        // to add design restart button
        restartButton.setFont(new Font("Arial", Font.PLAIN, 20));
        restartButton.setFocusable(false);
        textPanel.add(restartButton, BorderLayout.SOUTH);
        frame.add(boardPanel);

        // Loop creates each tile (JButton) in the 3x3 board.
        // Adds color, font, and click listener to each tile.
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                JButton tile = new JButton();
                // 2D array of buttons (tiles for the game).
                board[row][col] = tile;
                boardPanel.add(tile);

                // let's add some properties to style ore tiles
                tile.setBackground(Color.black);
                tile.setForeground(Color.white);
                tile.setFont(new Font("Arial", Font.BOLD, 120));
                tile.setFocusable(false);
                // tile.setText(currentPlayer);

                // add ActionLictener to each tile
                tile.addActionListener(new ActionListener() {
                    // function
                    public void actionPerformed(ActionEvent e) {

                        // game over
                        // If tile is empty and game not over:
                        // Set the tile to current player's symbol.
                        // Check for win/tie.
                        // Switch players if game still on.
                        if (gameOver)
                            return;
                        // event coming from button
                        JButton tile = (JButton) e.getSource();

                        // if don't let value override add thi "if condition"
                        if (tile.getText() == "") {
                            tile.setText(currentPlayer); // when on click button set text of button to currentplayer "X"

                            // increment turns
                            turn++;
                            // calling function
                            checkWinner();
                            if (!gameOver) {
                                // let's alternate the current player
                                currentPlayer = currentPlayer == playerX ? palyerO : playerX; // ternary expression
                                // let's update text lable
                                textLabel.setText(currentPlayer + "'s turn.");
                            }

                            // we don't want to override the value which already entered/don't want existing
                            // tile to change
                        }

                    }
                });

                // add action listener for restart button
                restartButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        resetGame();
                    }
                });
            }
        }

    }

    // to check winner
    // check all condition
    void checkWinner() {

        // horizontal
        for (int row = 0; row < 3; row++) {
            if (board[row][0].getText() == "")
                continue;

            if (board[row][0].getText() == board[row][1].getText()
                    && board[row][1].getText() == board[row][2].getText()) {
                // change background color and font
                for (int i = 0; i < 3; i++) {
                    // Called when a player wins.
                    setWinner(board[row][i]);
                }
                gameOver = true;
                return;
            }
        }

        // vertical
        for (int col = 0; col < 3; col++) {
            if (board[0][col].getText() == "")
                continue;
            if (board[0][col].getText() == board[1][col].getText()
                    && board[1][col].getText() == board[2][col].getText()) {
                // change background color and font
                for (int i = 0; i < 3; i++) {
                    setWinner(board[i][col]);
                }
                gameOver = true;
                return;

            }
        }

        // diagonally
        if (board[0][0].getText() == board[1][1].getText() && board[1][1].getText() == board[2][2].getText()
                && board[0][0].getText() != "") {
            for (int i = 0; i < 3; i++) {
                setWinner(board[i][i]);
            }
            gameOver = true;
            return;
        }

        // ant-diagonally
        if (board[0][2].getText() == board[1][1].getText() && board[1][1].getText() == board[2][0].getText()
                && board[2][0].getText() != "") {
            for (int i = 0; i < 3; i++) {
                setWinner((board[0][2]));
                setWinner(board[1][1]);
                setWinner(board[2][0]);
            }
            gameOver = true;
            return;
        }

        // tie
        if (turn == 9) {
            for (int row = 0; row < 3; row++) {
                for (int col = 0; col < 3; col++) {
                    // Called when game ends in a tie.
                    setTie(board[row][col]);
                }
            }
            gameOver = true;
        }

    }

    // for winner
    // Called when a player wins. Changes style and shows winner.
    void setWinner(JButton tile) {
        tile.setForeground(Color.green);
        tile.setBackground(Color.black);
        textLabel.setText(currentPlayer + " is a Winner.");
    }

    // for tie
    // Called when game ends in a tie.
    void setTie(JButton tile) {
        tile.setForeground(Color.pink);
        tile.setBackground(Color.black);
        textLabel.setText("It's a tie");
    }

    // adding method resetGame() - to restart game
    void resetGame() {
        currentPlayer = playerX;
        gameOver = false;
        turn = 0;
        textLabel.setText("Tic-Tac-Toe");

        // to reset game and vanish text from tile after game end
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                board[row][col].setText("");
                board[row][col].setBackground(Color.black);
                board[row][col].setForeground(Color.white);
            }
        }
    }
}
