import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Scanner;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.*;

public class Board {

    private Scanner scan = new Scanner(System.in);
    
    private JFrame mainFrame;
    private Label headerLabel;
    private Label statusLabel;
    private JPanel panel1;
    
    private Tile[][] buttonBoard = new Tile[3][3];
    private int[][] board = new int[3][3]; 
    private String classification = "x";

    private List<Point> availablePoints;
    private List<PointsAndScores> rootsChildrenScore = new ArrayList<>();

    public Scanner getScan(){
        return scan;
    }

    public Tile[][] getbuttonBoard(){
        return buttonBoard;
    }

    public String getClassification(){
        return classification;
    }

    public void setClassification(String turn){
        classification = turn;
    }

    public void setButtonText(int x, int y){
			//buttonBoard[x][y].setText(classification);
    }
	
	public void resetArrays(){
		//loop for adding the Tiles to the panel and setting the correct color
		this.buttonBoard = new Tile[3][3];
		this.panel1.removeAll();
		
        for(int i=0; i<3; i++){
            for(int j=0; j<3; j++){

                Tile button = new Tile(this, i, j);

                this.buttonBoard[i][j] = button;
                this.panel1.add(buttonBoard[i][j]);
            }
        } 
		
		this.board = new int[3][3]; 
		this.classification = "x";
	}

    public void prepareGUI(){
        mainFrame = new JFrame("Tic Tac Toe");
        mainFrame.setSize(400,400);
        mainFrame.setLayout(new BorderLayout());
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);   
        headerLabel = new Label();
        headerLabel.setAlignment(Label.CENTER);
        statusLabel = new Label();        
        statusLabel.setAlignment(Label.CENTER);
        statusLabel.setSize(350,100);

        panel1 = new JPanel();
        panel1.setLayout(new GridLayout(3, 3));

        panel1.setLayout(new GridLayout(3,3));
        panel1.setPreferredSize(new Dimension(500,500));

        //loop for adding the Tiles to the panel and setting the correct color
        for(int i=0; i<3; i++){
            for(int j=0; j<3; j++){

                Tile button = new Tile(this, i, j);

                buttonBoard[i][j] = button;
                panel1.add(buttonBoard[i][j]);
            }
        }     


        mainFrame.add(headerLabel, BorderLayout.NORTH);
        mainFrame.add(panel1, BorderLayout.CENTER);
        mainFrame.add(statusLabel, BorderLayout.SOUTH);
        mainFrame.setVisible(true);  
    }

    public List<PointsAndScores> getRootsChildrenScore(){
        return rootsChildrenScore;
    }

    public int evaluateBoard() {
        int score = 0;

        //Check all rows
        for (int i = 0; i < 3; ++i) {
            int blank = 0;
            int X = 0;
            int O = 0;
            for (int j = 0; j < 3; ++j) {
                if (board[i][j] == 0) {
                    blank++;
                } else if (board[i][j] == 1) {
                    X++;
                } else {
                    O++;
                }

            } 
            score+=changeInScore(X, O); 
        }

        //Check all columns
        for (int j = 0; j < 3; ++j) {
            int blank = 0;
            int X = 0;
            int O = 0;
            for (int i = 0; i < 3; ++i) {
                if (board[i][j] == 0) {
                    blank++;
                } else if (board[i][j] == 1) {
                    X++;
                } else {
                    O++;
                } 
            }
            score+=changeInScore(X, O);
        }

        int blank = 0;
        int X = 0;
        int O = 0;

        //Check diagonal (first)
        for (int i = 0, j = 0; i < 3; ++i, ++j) {
            if (board[i][j] == 1) {
                X++;
            } else if (board[i][j] == 2) {
                O++;
            } else {
                blank++;
            }
        }

        score+=changeInScore(X, O);

        blank = 0;
        X = 0;
        O = 0;

        //Check Diagonal (Second)
        for (int i = 2, j = 0; i > -1; --i, ++j) {
            if (board[i][j] == 1) {
                X++;
            } else if (board[i][j] == 2) {
                O++;
            } else {
                blank++;
            }
        }

        score+=changeInScore(X, O);

        return score;
    }

    private int changeInScore(int X, int O){
        int change;
        if (X == 3) {
            change = 100;
        } else if (X == 2 && O == 0) {
            change = 10;
        } else if (X == 1 && O == 0) {
            change = 1;
        } else if (O == 3) {
            change = -100;
        } else if (O == 2 && X == 0) {
            change = -10;
        } else if (O == 1 && X == 0) {
            change = -1;
        } else {
            change = 0;
        } 
        return change;
    }
    
    //Set this to some value if you want to have some specified depth limit for search
    private int uptoDepth = -1;
    
    public int alphaBetaMinimax(int alpha, int beta, int depth, int turn){
        
        if(beta<=alpha){ 
            //System.out.println("Pruning at depth = "+depth);
            if(turn == 1) return Integer.MAX_VALUE; 
            else return Integer.MIN_VALUE; 
        }
        
        if(depth == uptoDepth || isGameOver()) return evaluateBoard();
        
        List<Point> pointsAvailable = getAvailableStates();
        
        if(pointsAvailable.isEmpty()) return 0;
        
        if(depth==0) rootsChildrenScore.clear(); 
        
        int maxValue = Integer.MIN_VALUE, minValue = Integer.MAX_VALUE;
        
        for(int i=0;i<pointsAvailable.size(); ++i){
            Point point = pointsAvailable.get(i);
            
            int currentScore = 0;
            
            if(turn == 1){
                placeAMove(point, 1); 
                currentScore = alphaBetaMinimax(alpha, beta, depth+1, 2);
                maxValue = Math.max(maxValue, currentScore); 
                
                //Set alpha
                alpha = Math.max(currentScore, alpha);
                
                if(depth == 0)
                    rootsChildrenScore.add(new PointsAndScores(currentScore, point));
            }else if(turn == 2){
                placeAMove(point, 2);
                currentScore = alphaBetaMinimax(alpha, beta, depth+1, 1); 
                minValue = Math.min(minValue, currentScore);
                
                //Set beta
                beta = Math.min(currentScore, beta);
            }
            //reset board
            board[point.getX()][point.getY()] = 0; 
            
            //If a pruning has been done, don't evaluate the rest of the sibling states
            if(currentScore == Integer.MAX_VALUE || currentScore == Integer.MIN_VALUE) break;
        }
        return turn == 1 ? maxValue : minValue;
    }  

    public boolean isGameOver() {
        //Game is over is someone has won, or board is full (draw)
        return (hasXWon() || hasOWon() || getAvailableStates().isEmpty());
    }

    public boolean hasXWon() {
        if ((board[0][0] == board[1][1] && board[0][0] == board[2][2] && board[0][0] == 1) || (board[0][2] == board[1][1] && board[0][2] == board[2][0] && board[0][2] == 1)) {
            //System.out.println("X Diagonal Win");
            return true;
        }
        for (int i = 0; i < 3; ++i) {
            if (((board[i][0] == board[i][1] && board[i][0] == board[i][2] && board[i][0] == 1)
                    || (board[0][i] == board[1][i] && board[0][i] == board[2][i] && board[0][i] == 1))) {
                // System.out.println("X Row or Column win");
                return true;
            }
        }
        return false;
    }

    public boolean hasOWon() {
        if ((board[0][0] == board[1][1] && board[0][0] == board[2][2] && board[0][0] == 2) || (board[0][2] == board[1][1] && board[0][2] == board[2][0] && board[0][2] == 2)) {
            // System.out.println("O Diagonal Win");
            return true;
        }
        for (int i = 0; i < 3; ++i) {
            if ((board[i][0] == board[i][1] && board[i][0] == board[i][2] && board[i][0] == 2)
                    || (board[0][i] == board[1][i] && board[0][i] == board[2][i] && board[0][i] == 2)) {
                //  System.out.println("O Row or Column win");
                return true;
            }
        }

        return false;
    }

    public List<Point> getAvailableStates() {
        availablePoints = new ArrayList<>();
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                if (board[i][j] == 0) {
                    availablePoints.add(new Point(i, j));
                }
            }
        }
        return availablePoints;
    }

    public void placeAMove(Point point, int player) {
        int x = point.getX();
        int y = point.getY();
        /*while(board[x][y] != 0){
            System.out.println("Enter row:");
            x = scan.nextInt();
            System.out.println("Enter col:");
            y = scan.nextInt();
        }*/ 
        if(board[x][y] == 0){
            //System.out.println("Available");
            board[x][y] = player;   //player = 1 for X, 2 for O
        }
    }

    public Point returnBestMove() {
        int MAX = -100000;
        int best = -1;

        for (int i = 0; i < rootsChildrenScore.size(); ++i) {
            if (MAX < rootsChildrenScore.get(i).getScore()) {
                MAX = rootsChildrenScore.get(i).getScore();
                best = i;
            }
        }

        return rootsChildrenScore.get(best).getPoint();
    }

    public void takeHumanInput() {
        System.out.println("Your move: ");
        int x = scan.nextInt();
        int y = scan.nextInt();
        Point point = new Point(x, y);
        placeAMove(point, 2);
    }

    public void displayBoard() {
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                if(board[i][j] == 1){
                    try {
						BufferedImage img = ImageIO.read(new File("x.jpg"));
						buttonBoard[i][j].setIcon(new ImageIcon(img));
					} catch (IOException e) {}
                }else if (board[i][j] == 2) {
                    try {
						BufferedImage img = ImageIO.read(new File("o.jpg"));
						buttonBoard[i][j].setIcon(new ImageIcon(img));
					} catch (IOException e) {}
                }
            }
        }
    } 
    
    public void resetBoard() {
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                board[i][j] = 0;
            }
        }
    } 

    public void showMenuDemo(){
        //create a menu bar
        final MenuBar menuBar = new MenuBar();

        //create menus
        Menu fileMenu = new Menu("Action");

        //create menu items
        MenuItem newMenuItem = 
        new MenuItem("New",new MenuShortcut(KeyEvent.VK_N));
        newMenuItem.setActionCommand("New");

        newMenuItem.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {            
         statusLabel.setText(e.getActionCommand() 
            + " MenuItem clicked.");
        }
        });


        fileMenu.add(newMenuItem);

        menuBar.add(fileMenu);

        mainFrame.setMenuBar(menuBar);
        mainFrame.setVisible(true);  
   }
}