import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.HashSet;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Sudoku extends JPanel {

    private JTextField[][] grid;
    private JPanel[][] cellPanels;
    private JPanel gridPanel;
    private JPanel buttonPanel;
    private JButton solveButton;
    private JButton newButton;


    public Sudoku() {

        this.grid = new JTextField[9][9];

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                JTextField field = new JTextField();
                field.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
                field.setPreferredSize(new Dimension(30, 30));
                field.setFont(new Font("Helvetica", Font.BOLD, 20));
                this.grid[i][j] = field;

            }
        }

        this.gridPanel = new JPanel();
        this.buttonPanel = new JPanel();

        this.gridPanel.setLayout(new GridLayout(3, 3));
        this.cellPanels = new JPanel[3][3];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                JPanel panel = new JPanel();
                panel.setLayout(new GridLayout(3, 3));
                panel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
                this.cellPanels[i][j] = panel;
                this.gridPanel.add(panel);

            }
        }

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                this.cellPanels[i/3][j/3].add(grid[i][j]);
            }
        }

        this.gridPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

        this.newButton = new JButton("New");
        this.newButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                clear();
            }
        });

        this.solveButton = new JButton("Solve");
        this.solveButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isSolvable()) {
                    solve(0, 0);
                } else {
                    JOptionPane.showMessageDialog(buttonPanel, "Invalid Sudoku input!");
                }
            }
        });

        this.buttonPanel.setLayout(new BorderLayout());
        this.buttonPanel.add(newButton, BorderLayout.WEST);
        this.buttonPanel.add(solveButton, BorderLayout.EAST);

        this.setLayout(new BorderLayout());
        this.add(gridPanel, BorderLayout.NORTH);
        this.add(buttonPanel, BorderLayout.SOUTH);


    }

    private void clear() {
        for (int i = 0; i< grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                grid[i][j].setText("");
                grid[i][j].setForeground(Color.WHITE);
            }
        }
    }

    private boolean solve(int row, int col) {

            int max_row = 9;
            int max_col = 9;


            if (row == max_row) {
                return true;
            }
            if (col == max_col) {
                return solve(row + 1, 0);
            }

            if (!grid[row][col].getText().strip().equals("")) {
                return solve(row, col + 1);

            }

            String[] strings = {"1","2","3", "4", "5", "6", "7", "8", "9"};
            //empty slot
            for (String str : strings) {
                if (isValid(row, col, str)) {
                    grid[row][col].setText(str);
                    grid[row][col].setForeground(Color.RED);

                    if (solve(row, col + 1)) {
                        return true;
                    } else {
                        grid[row][col].setText("");

                    }

                }
            }
            return false;


    }

    private boolean isValid(int row, int col, String str) {
        for (int i = 0; i < 9; i++) {
            if (grid[row][i].getText().strip().equals(str) || grid[i][col].getText().strip().equals(str)
                || grid[i / 3 + 3 * (row / 3)][i % 3 + 3 * (col /3)].getText().strip().equals(str)) {
                return false;
            }
        }
        return true;
    }

    private boolean isSolvable() {
        HashSet<String> hs = new HashSet<>();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                String cell = grid[i][j].getText().strip();
                if (!cell.equals("")) {
                    if (isOneToNine(cell)) {
                        if (!hs.add(cell + " on row " + i) || !hs.add(cell + " on col " + j) || !hs.add(cell + " at square " + i / 3 + "," + j / 3)) {
                            return false;
                        }
                    } else {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private boolean isOneToNine(String cell) {
        String[] strings = {"1","2","3", "4", "5", "6", "7", "8", "9"};
        for (String str: strings) {
            if (cell.equals(str)) {
                return true;
            }
        }
        return false;
    }

}
