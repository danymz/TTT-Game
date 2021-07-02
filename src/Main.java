/********************************************************
 * this program allows the user to play a tic tac toe game
 * against the computer, using arrays, methods, loop, 
 * if statements, and scanner and random objects.
 * 
 * Title:		Tic Tac Toe Game
 * Programmer:	Daniel Martienz
 * Date:		16 April 2019
 **********************************************************/

import java.util.*;
public class Main {
	
	/*
	 * main method, calls all the method to run the game
	 */
	public static void main(String[] args) {
		Scanner console = new Scanner(System.in);	//create scanner object
		Random rand = new Random();					//create random object
		
		char[][] board = new char[3][3];			//2D array that holds the board
		boolean gameOver;							//controls if the game is over or not
		
		System.out.println("Welcome to Tic Tac Toe Game!");
		
		/*
		 * this do-while loop runs as long as the user plays the game
		 */
		do {
			
			board = initializeBoard();	//initializes the game board
			gameOver = false;
			
			
			if(yesTo("\nWould u like to go first", console)) {
				printBoard(board);
				while(!(gameOver)){
					doUserMove(console, board);
	    			gameOver = doComputerMove(board, rand);
				}
			}
			else{
				while(!(gameOver)){
	    			gameOver = doComputerMove(board, rand);
	    			if(!gameOver)
	    				doUserMove(console, board);
				}
    	}

		}while(yesTo("\ndo u want to play again?", console));
    
    }
	
	/*
	 * this method prints the game board
	 */
	public static void printBoard(char[][] board){
		System.out.println("\n------------");
		System.out.println("  0   1   2");
		for(int i = 0; i < board.length; i++){
      
			System.out.print(i + " ");
			for(int j = 0; j < board[1].length; j++){
        
				System.out.print(board[i][j]);
				if(j < 2){
					System.out.print(" | ");
				}
			}
      
			System.out.println();
			if(i < 2){
				System.out.println("  -   -   -  ");
			}
		}

	}
	
	/*
	 * this method asks the user where he wants  his next move,
	 * it also checks if the move is valid
	 */
	public static void doUserMove(Scanner console, char[][] board){
		int x, y;	//holds the position the player wants to move
		System.out.print("whats your next move(x, y)? ");
		x = console.nextInt();
		y = console.nextInt();
		
		/*
		 * checks if the position exists 
		 */
		while(x > 2 || x < 0 || y < 0 || y > 2){
			System.out.println("Numbers must be between 0 and 2 ");
			System.out.print("whats your next move(x, y)? ");
			x = console.nextInt();
			y = console.nextInt();
		}
		
		/*
		 * checks if the position is not taken
		 */
		while(board[x][y] != ' '){
			System.out.println("the square is already taken");
			System.out.print("whats your next move(x, y)? ");
			x = console.nextInt();
			y = console.nextInt();
		}

		board[x][y] = 'X';	//places X at the position the player chose 
		printBoard(board);
  }
	
	/*
	 * this method determines the level of priority of a row
	 */
	public static int valueOf(char[] temp) {

		int xCounter = 0;		//holds the number of X
		int oCounter = 0;		//holds the number of O
		int spaceCounter = 0;	//holds the number of spaces
		
		/*
		 * checks how many X, O or Spaces are in a row
		 */
		for(int i : temp) {
			if(i == 'X') {
				xCounter ++;
			}
			if(i == 'O') {
				oCounter ++;
			}
			if(i != ' ') {
				spaceCounter ++;
			}
		}
		
		//return 4 if the user won
		if(xCounter == 3) {
			return 4;
		}
		//return -1 if there is no space
		else if(spaceCounter == 3) {
			return -1;
		}
		//return 3 if computer is about to win
		else if(oCounter == 2) {
			return 3;
		}
		//return 2 if user is about to win
		else if(xCounter == 2) {
			return 2;
		}
		//return 0 if is a mix row
		else if(xCounter == 1 && oCounter == 1) {
			return 0;
		}
		//return 1 if none of the above applies
		else {
			return 1;
		}
	}	
	
	/*
	 * this method does the computer's move
	 * for this it uses the logic from method valueOf
	 * to determine the best position to move
	 * it also return true is the game is over and false if game should continue
	 */
	public static boolean doComputerMove(char[][] board, Random rand){
		char [] temp = new char[3];		//holds a temporary array passed to valueOf
		int [] values = new int[8];		//holds the values return form valueOf
		
		/*
		 * loops through the 8 possible rows and pass it to valueOf
		 * then it stores the returned value into array value
		 */
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 3; j++) {
				if( i < 3) {
					temp[j] = board[i][j];
				}
        
				else if(i < 6) {
					temp[j] = board[j][i-3];
				}
				else if(i == 6) {
					temp[j] = board[j][j];
				}
				else {
					temp[j] = board[j][-j + 2];
				}
			}
			values[i] = valueOf(temp);
		}
    
		int max = values[0];	//holds the maximum priority
		int index = 0;			//holds the place where the max priority is
		
		/*
		 * finds the maximum priority
		 */
		for( int i = 0 ; i < values.length; i++) {
			if(values[i] > max) {
				max = values[i];
				index = i;
			}
		}

		//makes the first move by the computer random
		if(max == 1){
			index = rand.nextInt(7);
		}
		
		//if max is 4, prints that user have won and return true
		if(max == 4) {
			System.out.println("\nCongratulations! you win");
			return true;
		}
		
		// if max is -1, prints that is a tie game and return false
		else if(max == -1) {
			System.out.println("\nTie Game!");
			return true;
		}
		/*
		 *  if maximum is 3, 2, 1 or 0, uses the index to determine in which position 
		 *  the computer' move should be place
		 */
		else {
	   
			if(index == 0) {
				for(int i = 0; i < 3; i++) {
					if(board[0][i] == ' ') {
						board[0][i] = 'O';
						break;
					}
				}
			}
			else if(index == 1) {
				for(int i = 0; i < 3; i++) {
					if(board[1][i] == ' '){
						board[1][i] = 'O';
						break;
					}
				}
			}
			else if(index == 2) {
				for(int i = 0; i < 3; i++) {
					if(board[2][i] == ' ') {
						board[2][i] = 'O';
						break;
					}
				}
			}
			else if(index == 3) {
				for(int i = 0; i < 3; i++) {
					if(board[i][0] == ' ') {
						board[i][0] = 'O';
						break;
					}
				}
			}
			else if(index == 4) {
				for(int i = 0; i < 3; i++) {
					if(board[i][1] == ' ') {
						board[i][1] = 'O';
						break;
					}
				}
			}
			else if(index == 5) {
				for(int i = 0; i < 3; i++) {
					if(board[i][2] == ' ') {
						board[i][2] = 'O';
						break;
					}
				}
			}
			else if(index == 6) {
				for(int i = 0; i < 3; i++) {
					if(board[i][i] == ' ') {
						board[i][i] = 'O';
						break;
					}
				}
			}
			else if(index == 7) {
				for(int i = 0; i < 3; i++) {
					if(board[i][-i + 2] == ' ') {
						board[i][-i + 2] = 'O';
						break;
					}
				}
			}
			// if max is 3, prints that computer have won, then prints the board and return true
			if(max == 3) {
				printBoard(board);
				System.out.println("\nComputer Wins!");
				return true;
			}
		}
		printBoard(board);
		return false;
	}
	
	/*
	 * this method prompts the user with passed question and 
	 * return true if yes and false if no
	 */
	public static boolean yesTo( String prompt, Scanner console){
		boolean yesOrNo = false;	//holds the true or false depending in user answer
		
		System.out.print(prompt + ": ");
		String answer = console.next();		//holds user's answer
		answer = answer.toUpperCase();
		
		while(answer.charAt(0) != 'Y' && answer.charAt(0) != 'N'){
			System.out.print("Answer must be yer or no");
			System.out.print(prompt + ": ");
			answer = console.next();
			answer = answer.toUpperCase();
		}

		if(answer.charAt(0) == 'Y'){
			yesOrNo = true;
		}
		else if(answer.charAt(0) == 'N'){
			yesOrNo = false;
		}

		return yesOrNo;
	}

	
	/*
	 * this method return an empty 2D array
	 */
	public static char[][] initializeBoard(){
		char[][] board = new char[3][3];	//holds the game board
    
		for(char[] row : board)
			Arrays.fill(row, ' ');

		return board;
	}
	}








