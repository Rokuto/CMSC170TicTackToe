import javax.swing.*;
import java.util.*;

public class AlphaBetaMinimax {

    public static void main(String[] args) { 
        Board b = new Board();
        Random rand = new Random();

        b.displayBoard();
        b.prepareGUI();
        b.showMenuDemo();

        switch(JOptionPane.showConfirmDialog(null, "Computer first?", "Pick Me", JOptionPane.YES_NO_OPTION)){
            //When Yes 
            case 0:
                Point p = new Point(rand.nextInt(3), rand.nextInt(3));
                b.placeAMove(p, 1);
                b.displayBoard();
                break;
            //When not yes
            default:
                break;
        }
        

    }
}