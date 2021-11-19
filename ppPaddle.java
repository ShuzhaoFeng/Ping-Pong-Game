package ppPackage;

import acm.graphics.*;
import acm.program.GraphicsProgram;
import static ppPackage.ppSimParams.*;

import java.awt.Color;
/**
 * The ppPaddle class generates a GRect as paddle for user interactions
 * using parameters defined in {@link ppSimParams}.
 * The class contains methods to generate the paddle, track the paddle's location
 * and simulate the paddle's collision with {@link ppBall}.
 * It also calculates the velocity of the paddle and thus determines the direction of the bouncing ball
 * once it enters in collision with the paddle.
 * <br>
 * <br>{@link ppPaddle#ppPaddle}
 * <br>{@link ppPaddle#run}
 * <br>{@link ppPaddle#setP}
 * <br>{@link ppPaddle#getP}
 * <br>{@link ppPaddle#getV}
 * <br>{@link ppPaddle#getSgnVy}
 * <br>{@link ppPaddle#contact}
 * @author Shuzhao Feng
 */
public class ppPaddle extends Thread{
	/**
	 * X position of ball.
	 * @author Shuzhao Feng
	 */
	double X;
	/**
	 * Y position of ball.
	 * @author Shuzhao Feng
	 */
	double Y;
	/**
	 * Velocity of ball in X direction.
	 * @author Shuzhao Feng
	 */
	double Vx;
	/**
	 * Velocity of ball in Y direction.
	 * @author Shuzhao Feng
	 */
	double Vy;
	/**
	 * Color of the paddle.
	 * @author Shuzhao Feng
	 */
	Color color;
	/**
	 * Instance for ping-pong table.
	 * @author Shuzhao Feng
	 */
	ppTable table;
	/**
	 * Instance for paddle.
	 * @author Shuzhao Feng
	 */
	GRect paddle;
	/**
	 * Instance for graphics program.
	 * @author Shuzhao Feng
	 */
	GraphicsProgram GProgram;
	/**
	 * The constructor for the ppPaddle class generates a GRect that represents the paddle
	 * which will follow the player's cursor.
	 * @param X - X position for the paddle.
	 * @param Y - Y position for the paddle.
	 * @param color - color of the paddle.
	 * @param table - a reference to the ppTable class used to export coordinate functions.
	 * @param GProgram - a reference to the {@link ppSim} class used to manage the display.
	 * @author Shuzhao Feng
	 */
	public ppPaddle (double X, double Y, Color color, ppTable table, GraphicsProgram GProgram) {
		this.X = X;
		this.Y = Y;
		this.color = color;
		this.table = table;
		this.GProgram = GProgram;
		// define upper-left corner
		double ULX = X-ppPaddleW/2;
		double ULY = Y+ppPaddleH/2;
		// get screen coordinates
		GPoint p = table.W2S(new GPoint(ULX,ULY));
		double ScrX = p.getX(); 
		double ScrY = p.getY();
		this.paddle = new GRect(ScrX,ScrY,ppPaddleW*Xs,ppPaddleH*Xs);
		paddle.setColor(color);
		paddle.setFilled(true);
		GProgram.add(paddle);
	}
	/**
	 * A run method used to calculate the velocity of the paddle with the user's cursor movements.
	 * @author Shuzhao Feng
	 */
	public void run() {
		double lastX = RPaddleXinit;
		double lastY = RPaddleYinit;
		while (true) {
			Vx = (X-lastX)/TICK;
			Vy = (Y-lastY)/TICK;
			lastX = X;
			lastY = Y;
			GProgram.pause(TICK*table.getGameSpeed()); // Time to mS
		}
	}
	/**
	 * A method to set the paddle location on the graphics program using world coordinates.
	 * @param p - the location of the paddle in world coordinate.
	 * @author Shuzhao Feng
	 */
	public void setP(GPoint p) {
		// update instance variables
		this.X = p.getX();
		this.Y = p.getY();
		// define upper-left corner
		double ULX = X-ppPaddleW/2;
		double ULY = Y+ppPaddleH/2;
		// get screen coordinates
		GPoint P = table.W2S(new GPoint(ULX,ULY));
		double ScrX = P.getX(); 
		double ScrY = P.getY();
		this.paddle.setLocation(ScrX,ScrY);
	}
	/**
	 * A simple method to get the paddle location from the graphics program.
	 * @author Shuzhao Feng
	 */
	public GPoint getP() {return new GPoint(X,Y);}
	/**
	 * A simple method to get the paddle location from {@link ppPaddle#run}.
	 * @author Shuzhao Feng
	 */
	public GPoint getV() {return new GPoint(Vx,Vy);}
	/**
	 * A method to return a negative 1 if the paddle is moving down and 1 if it is moving up.
	 * @author Shuzhao Feng
	 */
	public double getSgnVy() {
		if (Vy == 0) return 0; // if paddle is not moving, no directional force will be applied.
		else return (Vy < 0) ? -1 : 1;
	}
	/**
	 * A simple method to return true the ball hits the paddle.
	 * @author Shuzhao Feng
	 */
	public boolean contact (double X,double Y) {return Y <= getP().getY()+ppPaddleH/2 && Y >= getP().getY()-ppPaddleH/2;}
}
