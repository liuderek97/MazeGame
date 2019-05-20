import java.io.*;
import java.util.*;

/**
 * Maze Game Class.
 *
 * INFO1103 Assignment 2, Semester 1, 2017.
 *
 * The Maze Game.
 * In this assignment you will be designing a maze game.
 * You will have a maze board and a player moving around the board.
 * The player can step left, right, up or down.
 * However, you need to complete the maze within a given number of steps.
 *
 * As in any maze, there are walls that you cannot move through. If you try to
 * move through a wall, you lose a life. You have a limited number of lives.
 * There is also gold on the board that you can collect if you move ontop of it.
 *
 * Please implement the methods provided, as some of the marks are allocated to
 * testing these methods directly.
 *
 * @author YOU :)
 * @date April, 2017
 *
 */
public class MazeGame {
    /* You can put variables that you need throughout the class up here.
     * You MUST INITIALISE ALL of these class variables in your initialiseGame
     * method.
     */

    // A sample variable to show you can put variables here.
    // You would initialise it in initialiseGame method.
    // e.g. Have the following line in the initialiseGame method.
    // sampleVariable = 1;
	static int lives;
	static int steps;
    static int gold;
    static int numRows;
    static char [] board;
    static int width;
    static String lineReader;
    static int XCoord;
    static int YCoord;
    static int XDestination;
    static int YDestination;
    
    /**
     * Initialises the game from the given configuration file.
     * This includes the number of lives, the number of steps, the starting gold
     * and the board.
     *
     * If the configuration file name is "DEFAULT", load the default
     * game configuration.
     *
     * NOTE: Please also initialise all of your class variables.
     *
     * @args configFileName The name of the game configuration file to read from.
     * @throws IOException If there was an error reading in the game.
     *         For example, if the input file could not be found.
     */
    public static void initialiseGame(String configFileName) throws IOException {
		// Scanner scan = new Scanner(new File("easy_board.txt"));
		if (configFileName.equals("DEFAULT")) {
			lives = 3;
			steps = 20;
			gold = 0;
			numRows = 4;
			width = 10;
			XCoord = 7;
			YCoord = 0;
			char[] def = { '#', '@', ' ', '#', '#', ' ', ' ', '&', '4', '#', '#', '#', ' ', ' ', '#', ' ', '#', '#',
					' ', '#', '#', '#', '#', ' ', ' ', '3', '#', ' ', ' ', ' ', '#', '#', '#', '#', '#', '#', '#', ' ',
					' ', '#' };
			board = def;

			// static int num_of_columns = char at function
			// (int [][] MazeBoard = new int [rows][columns]; //default)
		} else {
			File mazeFile = new File(configFileName);
			Scanner readFile = new Scanner(mazeFile);
			
			lives = readFile.nextInt();
			steps = readFile.nextInt();
			gold = readFile.nextInt();
			numRows = readFile.nextInt();
			
			readFile.nextLine(); // empty
			
			String line = readFile.nextLine();
			
			width = line.length();
			
			board = new char[width * numRows];

			for (int i = 0; i < width; i++) 
			{
				board[i] = line.charAt(i);
			}
			int increment = 1;
			
			while (readFile.hasNextLine()) 
			{
				line = readFile.nextLine();
				for (int i = 0; i < width; i++)
				{
					board[(increment) * width + i] = line.charAt(i);
				}
				increment++;
			}

			for (int i = 0; i < board.length; i++)
			{
				if (board[i] == '&')
				{
					XCoord = i % width;
					YCoord = i / width;
				}
			}
			for (int i = 0; i < board.length; i++)
			{
				if (board[i] == '@')
				{
					XDestination = i % width;
					YDestination = i / width;
				}
			}
			readFile.close();
		}
	}
	// TODO: Implement this method.
	// this part has IO function import the easy board here to throw the
	// exception.
	// static int lives = hasNextLine; look at what we're initialising again

	/**
	 * Save the current board to the given file name. Note: save it in the same
	 * format as you read it in. That is:
	 *
	 * <number of lives> <number of steps> <amount of gold> <number of rows on
	 * the board> <BOARD>
	 *
	 * @args toFileName The name of the file to save the game configuration to.
	 * @throws IOException
	 *             If there was an error writing the game to the file.
	 */
	public static void saveGame(String toFileName) throws IOException {
		PrintWriter write = new PrintWriter(toFileName);
		write.printf("%d %d %d %d",numberOfLives(), numberOfStepsRemaining(), amountOfGold(), numRows);
		write.println();
		
		for (int i = 0; i < numRows; i++) 
		{
			for (int j = 0; j < width; j++) 
			{
				write.print(board[j + width*i]);
			}
			write.println();
			// use a for loop for the array in printBoard
		}
		
		write.close();
	}

	/**
	 * Gets the current x position of the player.
	 *
	 * @return The players current x position.
	 */
	public static int getCurrentXPosition() {
		return XCoord;
	}

	/**
	 * Gets the current y position of the player.
	 *
	 * @return The players current y position.
	 */
	public static int getCurrentYPosition() {
		return YCoord;
	}

	/**
	 * Gets the number of lives the player currently has.
	 *
	 * @return The number of lives the player currently has.
	 */
	public static int numberOfLives() {
		return lives;
	}

	/**
	 * Gets the number of remaining steps that the player can use.
	 *
	 * @return The number of steps remaining in the game.
	 */
	public static int numberOfStepsRemaining() {
		return steps;
	}

	/**
	 * Gets the amount of gold that the player has collected so far.
	 *
	 * @return The amount of gold the player has collected so far.
	 */
	public static int amountOfGold() {
		return gold; // not sure
	}

	/**
	 * Checks to see if the player has completed the maze. The player has
	 * completed the maze if they have reached the destination.
	 *
	 * @return True if the player has completed the maze.
	 */
	public static boolean isMazeCompleted(){
		if (board[YCoord * width + XCoord] == board[YDestination * width + XDestination]) 
		{
			return true;
		}
		return false;
	}

	/**
	 * Checks to see if it is the end of the game. It is the end of the game if
	 * one of the following conditions is true: - There are no remaining steps.
	 * - The player has no lives. - The player has completed the maze.
	 *
	 * @return True if any one of the conditions that end the game is true.
	 */
	public static boolean isGameEnd() {
		
		if (isMazeCompleted())
		{
			  System.out.println("Congratulations! You completed the maze!");
	  		  System.out.println("Your final status is:");
	  		  System.out.println("Number of live(s): " + lives);
	  	      System.out.println("Number of step(s) remaining: " + steps);
	  	      System.out.println("Amount of gold: " + gold);
	  				   
			return true;
		} 
		else if (steps == 0 && lives == 0) 
		{
			System.out.println("Oh no! You have no lives and no steps left.");
			System.out.println("Better luck next time!");
			return true;
		} 
		else if (steps == 0) 
		{
			System.out.println("Oh no! You have no steps left.");
			System.out.println("Better luck next time!");
			return true;
		}
		else if (lives == 0) 
		{
			System.out.println("Oh no! You have no lives left.");
			System.out.println("Better luck next time!");
			return true;
		}

		return false;
	}

	/**
	 * Checks if the coordinates (x, y) are valid. That is, if they are on the
	 * board.
	 *
	 * @args x The x coordinate.
	 * @args y The y coordinate.
	 * @return True if the given coordinates are valid (on the board),
	 *         otherwise, false (the coordinates are out or range).
	 */
	public static boolean isValidCoordinates(int x, int y) {
		if (x >= 0 && x < width && y >= 0 && y < numRows)
		{
			return true;
		} 
		else 
		{
			return false;
		}
	}

	/**
	 * Checks if a move to the given coordinates is valid. A move is invalid if:
	 * - It is move to a coordinate off the board. - There is a wall at that
	 * coordinate. - The game is ended.
	 *
	 * @args x The x coordinate to move to.
	 * @args y The y coordinate to move to.
	 * @return True if the move is valid, otherwise false.
	 */
	public static boolean canMoveTo(int x, int y) {
		if (isValidCoordinates(x, y))
		{
			if (board[y * width + x] != '#') 
			{
				if (!isGameEnd()) 
				{
					return true;
				}
			}
		}

		return false;
	}

	/**
	 * Move the player to the given coordinates on the board. After a successful
	 * move, it prints "Moved to (x, y)." where (x, y) were the coordinates
	 * given.
	 *
	 * If there was gold at the position the player moved to, the gold should be
	 * collected and the message "Plus n gold." should also be printed, where n
	 * is the amount of gold collected.
	 *
	 * If it is an invalid move, a life is lost. The method prints:
	 * "Invalid move. One life lost."
	 *
	 * @args x The x coordinate to move to.
	 * @args y The y coordinate to move to.
	 */
	public static void moveTo(int x, int y) {
		if (canMoveTo(x, y)) 
		{
			steps = steps - 1;
			board[XCoord + YCoord*width] = '.';
			XCoord = x;
			YCoord = y;

			System.out.println("Moved to (" + x + ", " + y + ").");
			
			if (Character.isDigit(board[x + y*width])) 
			{
				int num = Character.getNumericValue(board[y * width + x]);
				gold = gold + num;
				System.out.println("Plus " + num + " gold.");
			}
			board[YCoord * width + XCoord] = '&';

		} else 
		{
			lives = lives - 1;
			steps = steps - 1;
			System.out.println("Invalid move. One life lost.");

		}

	}

	/**
	 * Prints out the help message.
	 */
	public static void printHelp() {
		
		System.out.println("Usage: You can type one of the following commands.");
		System.out.println("help         Print this help message.");
		System.out.println("board        Print the current board.");
		System.out.println("status       Print the current status.");
		System.out.println("left         Move the player 1 square to the left.");
		System.out.println("right        Move the player 1 square to the right.");
		System.out.println("up           Move the player 1 square up.");
		System.out.println("down         Move the player 1 square down.");
		System.out.println("save <file>  Save the current game configuration to the given file.");
	}

	/**
	 * Prints out the status message.
	 */
	public static void printStatus() {
		
		System.out.println("Number of live(s): " + lives);
		System.out.println("Number of step(s) remaining: " + steps);
		System.out.println("Amount of gold: " + gold);
	}

	/**
	 * Prints out the board.
	 */
	public static void printBoard() {
		for (int i = 0; i < numRows; i++) {
			for (int j = 0; j < width; j++) {
				System.out.print(board[j + i*width]);
			}
			System.out.println();
			
		}

	}

	/**
	 * Performs the given action by calling the appropriate helper methods. [For
	 * example, calling the printHelp() method if the action is "help".]
	 *
	 * The valid actions are "help", "board", "status", "left", "right", "up",
	 * "down", and "save". [Note: The actions are case insensitive.] If it is
	 * not a valid action, an IllegalArgumentException should be thrown.
	 *
	 * @args action The action we are performing.
	 * @throws IllegalArgumentException
	 *             If the action given isn't one of the allowed actions.
	 */
	public static void performAction(String action) throws IllegalArgumentException {
		try {
			if(action.equals(""))
				return;
			if (action.equals("help")) 
			{
				printHelp();
			} 
			else if (action.equals("status")) 
			{
				printStatus();
			}
			else if (action.equals("board")) 
			{
				printBoard();
			} 
			else if (action.equals("up")) 
			{
				moveTo(XCoord, YCoord - 1);
			} 
			else if (action.equals("down")) 
			{
				moveTo(XCoord, YCoord + 1);
			} 
			else if (action.equals("left")) 
			{
				moveTo(XCoord - 1, YCoord);
			} 
			else if (action.equals("right")) 
			{
				moveTo(XCoord + 1, YCoord);
			} 
			else if(action.startsWith("save"))
			{
				String[] saveFile = action.split(" ");
				if(saveFile.length != 2)
					throw new IllegalArgumentException("");
				try 
				{
					saveGame(saveFile[1]);
					System.out.printf("Successfully saved the current game configuration to '%s'.\n",saveFile[1]);
				} 
				catch (IOException e) 
				{
					System.out.printf("Error: Could not save the current game configuration to '%s'.\n",saveFile[1]);
				} 
			}
			else
			{
				throw new IllegalArgumentException("");
			}
		}
		catch (IllegalArgumentException e) 
		{
			System.out.println("Error: Could not find command '" + action + "'.");
			System.out.println("To find the list of valid commands, please type 'help'.");
			throw e;
		}

	}

	/**
	 * The main method of your program.
	 *
	 * @args args[0] The game configuration file from which to initialise the
	 *       maze game. If it is DEFAULT, load the default configuration.
	 */
	public static void main(String[] args) {
		if (args.length == 0) 
		{
			System.out.println("Error: Too few arguments given. Expected 1 argument, found " + args.length + ".");
			System.out.println("Usage: MazeGame [<game configuration file>|DEFAULT]");
			return;
		}
		else if (args.length > 1) 
		{
			System.out.println("Error: Too many arguments given. Expected 1 argument, found " + args.length + ".");
			System.out.println("Usage: MazeGame [<game configuration file>|DEFAULT]");
			return;
		}
		else if (args.length == 1) 
		{
			try 
			{
				initialiseGame(args[0]);
			} catch (IOException e) // fixed it because it needs to handle an io
									// exception
			{
				System.out.println("Error: Could not load the game configuration from '" + args[0] + "'.");
				return;
			}

		}
		
		Scanner scan = new Scanner(System.in);
		while (scan.hasNextLine())
		{
			String action = scan.nextLine().toLowerCase();
			performAction(action); // calling the method for action Perform
									// action
			
			if(isGameEnd())
				return;
		}
		
		if(!scan.hasNextLine() && !isGameEnd())
		{
			System.out.println("You did not complete the game.");
			return;
		}
		scan.close();
	}

}
