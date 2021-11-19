package ppPackage;

import acm.graphics.GPoint;
import acm.program.GraphicsProgram;
import acm.util.RandomGenerator;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import static ppPackage.ppSimParams.*;
/**
 * The class ppSimPaddle is the starting class of the simulation.
 * It contains methods necessary to generate the graphics and link to other classes.
 * <br>
 * <br>{@link ppSim#main}
 * <br>{@link ppSim#init}
 * <br>{@link ppSim#newGame}
 * <br>{@link ppSim#newBall}
 * <br>{@link ppSim#actionPerformed}
 * <br>{@link ppSim#mouseMoved}
 * <br>{@link ppSim#updateScore}
 * @author Shuzhao Feng
 */
public class ppSim extends GraphicsProgram{
	/**
	 * Reference to {@link ppTable} class. 
	 * @author Shuzhao Feng
	 */
	ppTable table;
	/**
	 * Instance for the left paddle.
	 * @author Shuzhao Feng
	 */
	ppPaddleAgent LPaddle;
	/**
	 * Instance for the right paddle.
	 * @author Shuzhao Feng
	 */
	ppPaddle RPaddle;
	/**
	 * Reference to {@link ppBall} class. 
	 * @author Shuzhao Feng
	 */
	ppBall ball;
	/**
	 * Random generator.
	 * @author Shuzhao Feng
	 */
	RandomGenerator rgen;
	/**
	 * Player versus computer scoreBoard.
	 * @author Shuzhao Feng
	 */
	JLabel computerScoreBoard,playerScoreBoard,scoreBoardBreak;
	/**
	 * Player and computer names.
	 * @author Shuzhao Feng
	 */
	JTextArea computerName,playerName;
	/**
	 * Trace button allows user to turn on and off the trace and test options.
	 * @author Shuzhao Feng
	 */
	JToggleButton trace,test;
	/**
	 * Silder to control computer strength.
	 * @author Shuzhao Feng
	 */
	JSlider lag;
	/**
	 * Silder to control game speed.
	 * @author Shuzhao Feng
	 */
	JSlider gameSpeed;
	/**
	 * Initial Y position of the ball, randomly generated.
	 * @author Shuzhao Feng
	 */
	double Yinit;
	/**
	 * Computer and player's scores.
	 * @author Shuzhao Feng
	 */
	int computerScore,playerScore;
	/**
	 * Java main method used for the system to locate the initial class.
	 * Calls {@link ppSim#init} to start simulation. 
	 * @param args - Java default argument for main.
	 */
	public static void main(String[] args) {
		new ppSim().start(args);
	}
	/**
	 * The init method contains all major components of the simulation.
	 * In programming order, it generates buttons, score panel, a random generator used to generate values,
	 * the ping-pong table and the listeners. It then starts the {@link ppSim#newGame} method to generate the paddles and the ball.
	 * @author Shuzhao Feng
	 */
	public void init() {
		// create buttons
		JButton newServe = new JButton("New Serve");
		JButton quit = new JButton("Quit");
		JButton clear = new JButton("Clear");
		trace = new JToggleButton("Trace",false);
		test = new JToggleButton("Test",false);
		JLabel lagLabel = new JLabel("Computer Lag: ");
		lag = new JSlider(lagMIN,lagMAX,lagInit); // create slider
		lag.setMinorTickSpacing((lagMAX-lagMIN)/10); // set printed minor spacing
		lag.setMajorTickSpacing((lagMAX-lagMIN)/2); // set printed major spacing
		lag.setPaintTicks(true);  // print spacing
		lag.setPaintLabels(true); // print labels
		JLabel gameSpeedLabel = new JLabel("Game Delay: ");
		gameSpeed = new JSlider(gameSpeedMIN,gameSpeedMAX,gameSpeedInit); // create slider
		gameSpeed.setMinorTickSpacing((gameSpeedMAX-gameSpeedMIN)/10); // set printed minor spacing
		gameSpeed.setMajorTickSpacing((gameSpeedMAX-gameSpeedMIN)/2); // set printed major spacing
		gameSpeed.setPaintTicks(true);  // print spacing
		gameSpeed.setPaintLabels(true); // print labels
		computerScoreBoard = new JLabel(": "+computerScore); // create scoreboard
		computerScoreBoard.setFont(new Font("Arial",Font.BOLD,20));
		playerScoreBoard = new JLabel(": "+playerScore);
		playerScoreBoard.setFont(new Font("Arial",Font.BOLD,20));
		scoreBoardBreak = new JLabel("|");
		scoreBoardBreak.setFont(new Font("Arial",Font.BOLD,20));
		playerName = new JTextArea("Player");
		playerName.setFont(new Font("Arial",Font.BOLD,20));
		computerName = new JTextArea("Computer");
		computerName.setFont(new Font("Arial",Font.BOLD,20));
		add(newServe,SOUTH);
		add(quit,SOUTH);
		add(clear,SOUTH);
		add(trace,SOUTH);
		add(test,SOUTH);
		add(lagLabel,SOUTH);
		add(lag,SOUTH);
		add(gameSpeedLabel,SOUTH);
		add(gameSpeed,SOUTH);
		add(computerName,NORTH);
		add(computerScoreBoard,NORTH);
		add(scoreBoardBreak,NORTH);
		add(playerName,NORTH);
		add(playerScoreBoard,NORTH);
		// create random generator
		rgen = RandomGenerator.getInstance();
		// create graphics and objects
		table = new ppTable(gameSpeed,this);
		// add listeners
		addMouseListeners();
		addActionListeners();
		// start game
		newGame();
		while (true) updateScore();
	}
	/**
	 * The newGame method is called every time a new game is needed.
	 * It clears everything on the screen and then recreates the ball and the paddles.
	 * @author Shuzhao Feng
	 */
	public void newGame() {
		if (ball != null) ball.kill(); // stop current game in play
		table.newScreen();
		rgen.setSeed(RSEED); // set random generator seed.
		ball = newBall(); // create ball
		RPaddle = new ppPaddle(RPaddleXinit,RPaddleYinit,Color.GREEN,table,this); // generate right (player) paddle
		LPaddle = new ppPaddleAgent(LPaddleXinit,Yinit-bSize,Color.BLUE,table,lag,this); // generate left (computer) paddle
		LPaddle.attachBall(ball); // attach the left paddle to the Y position of the ball
		ball.setRightPaddle(RPaddle); // set right paddle for player
		ball.setLeftPaddle(LPaddle); // set left paddle for computer
		pause(STARTDELAY);
		ball.start();
		LPaddle.start();
		RPaddle.start();
	}
	/**
	 * The newBall method creates a new ball with random Y position, velocity, angle and energy loss upon collision.
	 * @author Shuzhao Feng
	 */
	public ppBall newBall() {
		// generate variables
		Color color = Color.RED;
		Yinit = rgen.nextDouble(YinitMIN,YinitMAX);
		double Vo = rgen.nextDouble(VoMIN,VoMAX);
		double theta = rgen.nextDouble(thetaMIN,thetaMAX);
		double loss = rgen.nextDouble(EMIN,EMAX);
		return new ppBall(Xinit,Yinit,Vo,theta,loss,color,table,trace,test,this);
	}
	/**
	 * Triggered when a button is pressed and chooses the right action consequently.
	 * @author Shuzhao Feng
	 */
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		if (command.equals("New Serve")) newGame();
		else if (command.equals("Quit")) System.exit(0);
		else if (command.equals("Clear")) table.resetScore();
	}
	/**
	* Mouse handler, bounded within the table range.
	* @param e - a moved event moves the paddle up and down in Y
	* @author Ferrie
	*/
	public void mouseMoved(MouseEvent e) {
		if (table == null || RPaddle == null) return;
		GPoint p = table.S2W(new GPoint(e.getX(),e.getY()));
		double X = RPaddle.getP().getX();
		double Y = p.getY();
		if (Y+ppPaddleH/2 > Ymax) RPaddle.setP(new GPoint(X,Ymax-ppPaddleH/2)); // set paddle upper bound.
		else if (Y-ppPaddleH/2 < Ymin) RPaddle.setP(new GPoint(X,Ymin+ppPaddleH/2)); // set paddle lower bound.
		else RPaddle.setP(new GPoint(X,Y));
	}
	/**
	 * A simple method to get scores from {@link ppTable} class and update the scores on the score panel.
	 * @author Shuzhao Feng
	 */
	public void updateScore() {
			computerScore = table.computerScore;
			playerScore = table.playerScore;
			computerScoreBoard.setText(": "+computerScore);
			playerScoreBoard.setText(": "+playerScore);
	}
}