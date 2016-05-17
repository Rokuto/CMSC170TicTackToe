import javax.swing.*;
import java.awt.*;

public class Tile extends JButton {
	public Tile(Board board, int x, int y){
		this.addActionListener(new TurnEvent(board, x, y));
	}
}