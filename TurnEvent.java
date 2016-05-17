import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;


public class TurnEvent implements ActionListener {
	
	private final Board board;
	private int x;
	private int y;

	public TurnEvent(Board board, int x, int y){
		this.board = board;
		this.x = x;
		this.y = y;
	}

	public void actionPerformed(ActionEvent e){
		if(board.getbuttonBoard()[x][y].getText() == "" && !board.isGameOver()){
			board.setButtonText(x, y);

	        Point userMove = new Point(x, y);

	        board.placeAMove(userMove, 2); //2 for O and O is the user
	        board.displayBoard();

	        if(this.board.isGameOver()){
				 if (this.board.hasXWon()) {
					JOptionPane.showMessageDialog(null,"Unfortunately, you lost!");
					switch(JOptionPane.showConfirmDialog(null, "Play Again?", "Pick Me", JOptionPane.YES_NO_OPTION)){
						//When Yes 
						case 0:
							this.board.resetArrays();
							this.board.displayBoard();
							this.board.showMenuDemo();
							
							switch(JOptionPane.showConfirmDialog(null, "Computer first?", "Pick Me", JOptionPane.YES_NO_OPTION)){
								//When Yes 
								case 0:
									Random rand = new Random();
									Point p = new Point(rand.nextInt(3), rand.nextInt(3));
									this.board.placeAMove(p, 1);
									this.board.displayBoard();
									break;
								//When not yes
								default:
									break;
							}
							
							break;
						//When not yes
						default:
							System.exit(0);
							break;
					}					
				} else if (this.board.hasOWon()) {
					JOptionPane.showMessageDialog(null,"You Won!");
					switch(JOptionPane.showConfirmDialog(null, "Play Again?", "Pick Me", JOptionPane.YES_NO_OPTION)){
						//When Yes 
						case 0:
							this.board.resetArrays();
							this.board.displayBoard();
							this.board.showMenuDemo();
							
							switch(JOptionPane.showConfirmDialog(null, "Computer first?", "Pick Me", JOptionPane.YES_NO_OPTION)){
								//When Yes 
								case 0:
									Random rand = new Random();
									Point p = new Point(rand.nextInt(3), rand.nextInt(3));
									this.board.placeAMove(p, 1);
									this.board.displayBoard();
									break;
								//When not yes
								default:
									break;
							}
							
							break;
						//When not yes
						default:
							System.exit(0);
							break;
					}
				} else {
					JOptionPane.showMessageDialog(null,"It's a draw!");
					switch(JOptionPane.showConfirmDialog(null, "Play Again?", "Pick Me", JOptionPane.YES_NO_OPTION)){
						//When Yes 
						case 0:
							this.board.resetArrays();
							this.board.displayBoard();
							this.board.showMenuDemo();
							
							switch(JOptionPane.showConfirmDialog(null, "Computer first?", "Pick Me", JOptionPane.YES_NO_OPTION)){
								//When Yes 
								case 0:
									Random rand = new Random();
									Point p = new Point(rand.nextInt(3), rand.nextInt(3));
									this.board.placeAMove(p, 1);
									this.board.displayBoard();
									break;
								//When not yes
								default:
									break;
							}
							
							break;
						//When not yes
						default:
							System.exit(0);
							break;
					}
				}
			}else{
		        board.alphaBetaMinimax(Integer.MIN_VALUE, Integer.MAX_VALUE, 0, 1);
		        for (PointsAndScores pas : board.getRootsChildrenScore()) 
		            //System.out.println("Point: " + pas.getPoint() + " Score: " + pas.getScore());
		        
		        board.placeAMove(board.returnBestMove(), 1);
		        board.displayBoard();

	        }
			
			if(board.getClassification() == "x"){
				board.setClassification("o");
			}else{
				board.setClassification("x");
			}

		}
		
		if(this.board.isGameOver()){
			if (this.board.hasXWon()) {
				JOptionPane.showMessageDialog(null,"Unfortunately, you lost!");				
				switch(JOptionPane.showConfirmDialog(null, "Play Again?", "Pick Me", JOptionPane.YES_NO_OPTION)){
					//When Yes 
					case 0:
						this.board.resetArrays();
						this.board.displayBoard();
						this.board.showMenuDemo();
						
						switch(JOptionPane.showConfirmDialog(null, "Computer first?", "Pick Me", JOptionPane.YES_NO_OPTION)){
							//When Yes 
							case 0:
								Random rand = new Random();
								Point p = new Point(rand.nextInt(3), rand.nextInt(3));
								this.board.placeAMove(p, 1);
								this.board.displayBoard();
								break;
							//When not yes
							default:
								break;
						}
						
						break;
					//When not yes
					default:
						System.exit(0);
						break;
				}				
				
			} else if (this.board.hasOWon()) {
				JOptionPane.showMessageDialog(null,"You Won!");
				switch(JOptionPane.showConfirmDialog(null, "Play Again?", "Pick Me", JOptionPane.YES_NO_OPTION)){
					//When Yes 
					case 0:
						this.board.resetArrays();
						this.board.displayBoard();
						this.board.showMenuDemo();
						
						switch(JOptionPane.showConfirmDialog(null, "Computer first?", "Pick Me", JOptionPane.YES_NO_OPTION)){
							//When Yes 
							case 0:
								Random rand = new Random();
								Point p = new Point(rand.nextInt(3), rand.nextInt(3));
								this.board.placeAMove(p, 1);
								this.board.displayBoard();
								break;
							//When not yes
							default:
								break;
						}
						
						break;
					//When not yes
					default:
						System.exit(0);
						break;
				}
			} else {
				JOptionPane.showMessageDialog(null,"It's a draw!");
				switch(JOptionPane.showConfirmDialog(null, "Play Again?", "Pick Me", JOptionPane.YES_NO_OPTION)){
					//When Yes 
					case 0:
						this.board.resetArrays();
						this.board.displayBoard();
						this.board.showMenuDemo();
						
						switch(JOptionPane.showConfirmDialog(null, "Computer first?", "Pick Me", JOptionPane.YES_NO_OPTION)){
							//When Yes 
							case 0:
								Random rand = new Random();
								Point p = new Point(rand.nextInt(3), rand.nextInt(3));
								this.board.placeAMove(p, 1);
								this.board.displayBoard();
								break;
							//When not yes
							default:
								break;
						}
						
						break;
					//When not yes
					default:
						System.exit(0);
						break;
				}
			}
			

		}

    }

}