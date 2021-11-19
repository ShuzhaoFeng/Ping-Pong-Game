package ppPackage;

import java.awt.Color;
import javax.swing.JToggleButton;
import acm.graphics.*;
import acm.program.GraphicsProgram;
import static ppPackage.ppSimParams.*;
/**
 * The ppBall class generates a GOval as ping-pong ball
 * and simulates two-dimensional motion of the ball for {@link ppSim}
 * using parameters defined in {@link ppSimParams}.
 * The class contains methods to define a GOval, to calculate position and velocity of the ball
 * using inputs from {@link ppSim}
 * and constants from {@link ppSimParams}, display the result as plain text and as graphics,
 * and track the motion of the ball with black dots on its trajectory.
 * <br>
 * <br>{@link ppBall#ppBall}
 * <br>{@link ppBall#run}
 * <br>{@link ppBall#trace}
 * <br>{@link ppBall#getP}
 * <br>{@link ppBall#setRightPaddle}
 * <br>{@link ppBall#setLeftPaddle}
 * <br>{@link ppBall#kill}
 * @author Shuzhao Feng
 */
public class ppBall extends Thread{
	/**
	 * Graphics object representing ball
	 * @author Shuzhao Feng
	 */
	GOval ball;
	/**
	 * Initial X position of ball.
	 * @author Shuzhao Feng
	 */
	private double Xinit;
	/**
	 * Initial Y position of ball.
	 * @author Shuzhao Feng
	 */
	private double Yinit;
	/**
	 * Initial velocity magnitude in meter per second.
	 * @see {@link ppSim#run()}
	 * @author Shuzhao Feng
	 */
	private double Vo;
	/**
	 * Initial direction in degrees, with 0 defined as horizontal-right position.
	 * @see {@link ppSim#run()}
	 * @author Shuzhao Feng
	 */
	private double theta;
	/**
	 * Energy loss on collision, range from 0 to 1.
	 * @see {@link ppSim#run()}
	 * @author Shuzhao Feng
	 */
	private double loss;
	/**
	 * Color of ball
	 * @author Shuzhao Feng
	 */
	private Color color;
	/**
	 * Instance for graphic program.
	 * @author Shuzhao Feng
	 */
	private GraphicsProgram GProgram;
	/**
	 * Instance for table.
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
	 * Trace the ball if the toggle button is selected.
	 * @author Shuzhao Feng
	 */
	JToggleButton trace;
	/**
	 * Print motion parameters in console if the toggle button is selected.
	 * @author Shuzhao Feng
	 */
	JToggleButton test;
	/**
	 * X and Y velocities and positions of the ball.
	 * @author Shuzhao Feng
	 */
	double X,Y,Xo,Yo,Vx,Vy;
	/**
	 * Run the program if true.
	 * @author Shuzhao Feng
	 */
	boolean running;
	/**
	 * Check if the ball was last hit by player or by computer.
	 * @author Shuzhao Feng
	 */
	boolean playerhit;
	/**
	* The constructor for the ppBall class copies parameters to instance variables, creates an
	* instance of a GOval to represent the ping-pong ball, and adds it to the display.
	* @param Xinit - starting position of the ball X (meters)
	* @param Yinit - starting position of the ball Y (meters)
	* @param Vo - initial velocity (meters/second)
	* @param theta - initial angle to the horizontal (degrees)
	* @param loss - loss on collision ([0,1])
	* @param color - ball color (Color)
	* @param GProgram - a reference to the ppSimPaddle class used to manage the display
	* @author Shuzhao Feng
	*/
	public ppBall(double Xinit,double Yinit,double Vo,double theta,double loss,Color color,ppTable table,JToggleButton trace,JToggleButton test,GraphicsProgram GProgram) {
		this.Xinit=Xinit; // Copy constructor parameters to instance variables
		this.Yinit=Yinit;
		this.Vo=Vo;
		this.theta=theta;
		this.loss=loss;
		this.color=color;
		this.table=table;
		this.trace=trace;
		this.test=test;
		this.GProgram=GProgram;
		// trace ball
		GPoint p = table.W2S(new GPoint(Xinit,Yinit)); // Get initial position in screen coordinates
		double ScrX = p.getX(); 
		double ScrY = p.getY();
		// create ball
		this.ball = new GOval(ScrX,ScrY,2*bSize*Xs,2*bSize*Ys);
		this.ball.setColor(color);
		this.ball.setFilled(true);
		this.ball.setLocation(ScrX,ScrY);
		GProgram.add(ball);
	}
	/**
	 * A <b>run</b> method that contains while loop to update ball's position and velocity at regular time interval
	 * and print these results in the console.
	 * It also update the GOval position in the graphics program using the ball's coordinates.
	 * <br>Modified from original code from student's ECSE-202 F2021 Assignment 1.
	 * @author Shuzhao Feng
	 */
	public void run() {
		// declare simulation parameters
		Xo = Xinit+bSize; // Set initial X position
		Yo = Yinit-bSize; // Set initial Y position
		double time = 0; // Time starts at 0 and counts up
		double Ptime = 0; // partial time at each iteration
		double Vt = bMass*g/(4*Pi*bSize*bSize*k); // Terminal velocity
		double Vox = Vo*Math.cos(theta*Pi/180); // X component of velocity
		double Voy = Vo*Math.sin(theta*Pi/180); // Y component of velocity
		// Simulation loop. Calculate position and velocity, print, increment time.
		running = true; // Initial state = running
		// X and Y are relative to the initial starting position Xo, Yo.
		// Print out a header line for the displayed values.
		if(test.isSelected())System.out.printf("\t\t\t Ball Position and Velocity\n");
		// main simulation loop
		while (running) {			
			X = Vox*Vt/g*(1-Math.exp(-g*Ptime/Vt)); // Update relative position
			Y = Vt/g*(Voy+Vt)*(1-Math.exp(-g*Ptime/Vt))-Vt*Ptime;
			Vx = Vox*Math.exp(-g*Ptime/Vt); // Update velocity
			Vy = (Vt+Voy)*Math.exp(-g*Ptime/Vt)-Vt;
			// Display current values
			if(test.isSelected())System.out.printf("t: %.2f\t\t X: %.2f\t Y: %.2f\t Vx: %.2f\t Vy: %.2f\n",Ptime+time,X+Xo-XwallL,Y+Yo,Vx,Vy); // Print motion parameters
			// Check to see if we hit the ground yet. When the ball hits the ground, the height of the center is the radius of the ball.
			if (Vy < 0 && Y+Yo <= bSize) { // Ball hits the ground
				time += Ptime; // Archive time value
				Ptime = 0; // Reset partial time
				Xo += X;
				Yo = bSize;
				X = 0;
				Y = 0;
				double KEx = 0.5*bMass*Vx*Vx*(1-loss);
				double KEy = 0.5*bMass*Vy*Vy*(1-loss);
				double PE = 0; // Potential energy is always 0 at ground level.
				Vox=Math.sqrt(2*KEx/bMass);
				Voy=Math.sqrt(2*KEy/bMass);
				if (Vx < 0) Vox = -Vox;
				if (KEx+KEy+PE < ETHR) running = false;
			}
			if (Vx < 0 && X+Xo-bSize <= LPaddle.getP().getX()+ppPaddleW/2) { // Ball hits the left paddle
				if (LPaddle.contact(Xo+X,Yo+Y)) { // check paddle contact
					time += Ptime; // Archive time value
					Ptime = 0; // Reset partial time
					Xo = LPaddleXinit+ppPaddleW/2+bSize+PD/Xs;
					Yo += Y;
					X = 0;
					Y = 0;
					// compute energy loss
					double KEx = 0.5*bMass*Vx*Vx*(1-loss);
					double KEy = 0.5*bMass*Vy*Vy*(1-loss);
					Vox=Math.sqrt(2*KEx/bMass);
					Voy=Math.sqrt(2*KEy/bMass);
					Vox=Vox*LPaddleXgain; // Scale X component of velocity
					Voy=Voy*LPaddleYgain; // Scale Y component of velocity
					if (Vy < 0) Voy=-Voy;
					playerhit = false; // ball was hit by computer
				} else {
					if(test.isSelected()) System.out.println("You win!"); // print end message
					table.addPlayerScore(); // +1 point for player
					kill();
				}
			}
			if (Vx > 0 && X+Xo+bSize >= RPaddle.getP().getX()-ppPaddleW/2) { // Ball hits the right paddle.
				if (RPaddle.contact(Xo+X,Yo+Y)) { // check paddle contact
					time += Ptime; // Archive time value
					Ptime = 0; // Reset partial time
					Xo = RPaddleXinit-ppPaddleW/2-bSize-PD/Xs;
					Yo += Y;
					X = 0;
					Y = 0;
					// compute energy loss
					double KEx = 0.5*bMass*Vx*Vx*(1-loss);
					double KEy = 0.5*bMass*Vy*Vy*(1-loss);
					Vox=Math.sqrt(2*KEx/bMass);
					Vox = -Vox;
					Voy=Math.sqrt(2*KEy/bMass);
					Vox=Vox*RPaddleXgain; // Scale X component of velocity
					Voy=Voy*RPaddleYgain; // Scale Y component of velocity
					if (Vy < 0) Voy=-Voy;
					if (RPaddle.getSgnVy() != 0) Voy=Math.abs(Voy)*RPaddle.getSgnVy(); // if paddle is not moving, no directional force will be applied. Otherwise ball moves the same direction as paddle.
					playerhit = true; // ball was hit by player
				} else {
					if(test.isSelected()) System.out.println("You lose!"); // print end message
					table.addComputerScore(); // +1 point for computer
					kill();
				}
			}
			if (Vy > 0 && Y+Yo >= Ymax+bSize) { // ball is completely out of upper bound
				if(test.isSelected()) System.out.println(playerhit ? "You lose!" : "You win!");
				if (playerhit) table.addComputerScore(); // point goes to whoever didn't last hit the ball
				else table.addPlayerScore();;
				kill();
			}
			if (Vox*Vox+Voy*Voy > 4*VoMAX*VoMAX) { // check if velocity exceeds threshold (2*VoMAX)
				// reduce velocity to the threshold by reducing both Vox and Voy proportionally
				double YXScale = Voy/Vox;
				if (Vox >= 0) Vox = Math.sqrt(4*VoMAX*VoMAX/(1+YXScale*YXScale));
				else Vox = -Math.sqrt(4*VoMAX*VoMAX/(1+YXScale*YXScale));
				Voy = Vox*YXScale;
			}
			// Update the position of the ball, Plot a tick mark at current location.
			GPoint p = table.W2S(new GPoint(Xo+X-bSize,Yo+Y+bSize)); // Get current position in screen coordinates
			double ScrX = p.getX(); 
			double ScrY = p.getY();
			ball.setLocation(ScrX,ScrY);
			if (trace.isSelected()) trace(ScrX,ScrY);
			// update cycle count
			Ptime += TICK;
			// pause display
			this.GProgram.pause(TICK*table.getGameSpeed());
		}
	}
	/**
     * A method to plot a dot at the current location in screen coordinates
     * @param ScrX - horizontal position of the ball in screen coordinates
     * @param ScrY - vertical position of the ball in screen coordinates
     * @author Shuzhao Feng
     */
	private void trace(double ScrX, double ScrY) {
		// Plot a black dot at each tick position
		GOval tracepoint = new GOval(ScrX+bSize*Xs,ScrY+bSize*Ys,PD,PD);
		tracepoint.setColor(Color.BLACK);
		GProgram.add(tracepoint);
	}
	/**
	 * A simple method to get the coordinates of the ball.
	 * @return GPoint - The location point of the ball.
	 * @author Shuzhao Feng
	 */
	public GPoint getP() {return new GPoint(Xo+X,Yo+Y);}
	/**
     * A method to set paddle for {@link ppBall} class for collisions.
     * @param RPaddle - user paddle instance created by {@link ppPaddle}.
     * @author Shuzhao Feng
     */
	public void setRightPaddle(ppPaddle paddle) {this.RPaddle = paddle;}
	/**
     * A method to set paddle for {@link ppBall} class for collisions.
     * @param LPaddle - computer paddle instance created by {@link ppPaddle}.
     * @author Shuzhao Feng
     */
	public void setLeftPaddle(ppPaddleAgent paddle) {this.LPaddle = paddle;}
	/**
	 * Kill the program by setting the running boolean to false.
	 * @author Shuzhao Feng
	 */
	void kill() {running = false;}
}
