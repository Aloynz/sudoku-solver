import com.formdev.flatlaf.themes.FlatMacDarkLaf;

import javax.swing.JFrame;


public class SudokuFrame extends JFrame {

    public SudokuFrame() {
        super("Sudoku Solver");

        getContentPane().add(new Sudoku());
        setSize(500, 500);
        setResizable(false);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();

    }


        public static void main(String[] args) {
            FlatMacDarkLaf.setup();
            new SudokuFrame();

    }
}
