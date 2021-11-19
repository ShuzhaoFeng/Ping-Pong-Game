package ppPackage;

import java.awt.Color;
import javax.swing.JSlider;
import acm.graphics.GPoint;
import acm.program.GraphicsProgram;
import static ppPackage.ppSimParams.*;
/**
 * ppPaddle Agent class defines the left paddle controlled by the computer
 * and implements method to create lag on the paddle so that the player has a greater chance to win.
 * <br> 
 * <br> {@link ppPaddleAgent#ball}
 * <br> {@link ppPaddleAgent#lag}
 * <br> {@link ppPaddleAgent#ppPaddleAgent}
 * <br> {@link ppPaddleAgent#run}
 * <br> {@link ppPaddleAgent#attachBall}
 * @author Shuzhao Feng
 */
public class ppPaddleAgent extends ppPaddle {
	/**
	 * Reference to the ball.
	 * @author Shuzhao Feng
	 */
	ppBall ball;
	/**
	 * Reference to the slider.
	 * @author Shuzhao Feng
	 */
	JSlider lag;
	/**
	 * The constructor method for the agent paddle controlled by computer.
	 * @param X - X position for the paddle.
	 * @param Y - Y position for the paddle.
	 * @param color - color of the paddle.
	 * @param table - a reference to the ppTable class used to export coordinate functions.
	 * @param GProgram - a reference to the {@link ppSim} class used to manage the display.
	 * @author Shuzhao Feng
	 */
	public ppPaddleAgent(double X,double Y,Color color,ppTable table,JSlider lag,GraphicsProgram GProgram) {
		super(X,Y,color,table,GProgram);	
		this.lag=lag;
	}
	/**
	 * The run methods includes a set of operations to intentionally increase the paddle's lag.
	 * The paddle will search for the ball's position every once in a while and be set to the same Y position as the ball.
	 * @author Shuzhao Feng
	 */
	public void run() {
		int ballSkip = 0;
		while(true) {
			int agentLag = lag.getValue(); // lag value from silder
			// get ball position in Y
			double ballY = ball.getP().getY();		
			if (ballSkip ++ >= agentLag) {
				if (ballY-ppPaddleH/2 < Ymin) this.setP(new GPoint(X,Ymin+ppPaddleH/2)); // set paddle lower bound.
				else if (ballY+ppPaddleH/2 > Ymax) this.setP(new GPoint(X,Ymax-ppPaddleH/2)); // set paddle upper bound.
				else this.setP(new GPoint(this.X,ballY));
				ballSkip = 0;
			}
			GProgram.pause(TICK*table.getGameSpeed()); // Time to mS
		}
	}
	/**
	 * A simple method to attach the ball to the paddle.
	 * @param ball - reference to the ball.
	 * @author Shuzhao Feng
	 */
	public void attachBall(ppBall ball) {this.ball = ball;}
}
