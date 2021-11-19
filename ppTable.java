package ppPackage;

import java.awt.Color;
import javax.swing.JSlider;
import acm.graphics.*;
import acm.program.GraphicsProgram;
import static ppPackage.ppSimParams.*;
/**
 * The ppTable class contains methods to generate the GRect as ground plane
 * to illustrate a ping-pong table for {@link ppSim}
 * using parameters defined in {@link ppSimParams}
 * <br>
 * <br>{@link ppTable#width}
 * <br>{@link ppTable#playerScore}
 * <br>{@link ppTable#computerScore}
 * <br>{@link ppTable#GProgram}
 * <br>{@link ppTable#ppTable}
 * <br>{@link ppTable#newScreen}
 * <br>{@link ppTable#drawGroundPlane}
 * <br>{@link ppTable#W2S}
 * <br>{@link ppTable#S2W}
 * <br>{@link ppTable#addPlayerScore}
 * <br>{@link ppTable#addComputerScore}
 * @author Shuzhao Feng
 */
public class ppTable{
	/**
	 * Define actual table width regardless of table's margin. Prevents some Window system exceptions.
	 * @author Shuzhao Feng
	 */
	public static int width;
	/**
	 * Keep track of the scores.
	 * @author Shuzhao Feng
	 */
	int playerScore,computerScore;
	/**
	 * Used to control the game speed.
	 * @author Shuzhao Feng
	 */
	JSlider gameSpeed;
	/**
	 * Graphic program for display.
	 * @author Shuzhao Feng
	 */
	GraphicsProgram GProgram;
	/**
	 * The constructor for the ppTable class resizes graphic window to desired size,
	 * creates instances <b>gPlane</b> and <b>gWallL</b>
	 * and adds them to the display.
	 * Modified from original code from  ECSE-202 F2021 previous assignments.
	 * @param GProgram - a reference to the ppSimPaddle class used to manage the display.
	 * @author Shuzhao Feng
	 */
	public ppTable(JSlider gameSpeed,GraphicsProgram GProgram) {
		this.gameSpeed = gameSpeed;
		this.GProgram = GProgram;
		GProgram.resize(ppSimParams.WIDTH,ppSimParams.HEIGHT+OFFSET);
		width = GProgram.getWidth(); // fix some Window system width issues
		// Create ground plane
		drawGroundPlane();
	}
	/**
	 * This method erase everything on the screen and draw a new ground plane.
	 * @author Shuzhao Feng
	 */
	public void newScreen() {
		GProgram.removeAll();
		drawGroundPlane();
	}
	/**
	 * This method is called every time a new ground plane is needed
	 * and creates a new GRect representing the ground plane.
	 * @author Shuzhao Feng
	 */
	public void drawGroundPlane() {
		GRect gPlane = new GRect(0,ppSimParams.HEIGHT+PD,width,wThick);
		gPlane.setColor(Color.BLACK);
		gPlane.setFilled(true);
		GProgram.add(gPlane);
	}
	/**
     * A Method to convert from world to screen coordinates.
     * @param p a point object in world coordinates.
     * @return the corresponding point object in screen coordinates.
     * @author Ferrie
     */
	GPoint W2S(GPoint p) {return new GPoint((p.getX()-Xmin)*Xs,ymax-(p.getY()-Ymin)*Ys);}
	/**
     * A Method to convert from screen to world coordinates.
     * @param p a point object in screen coordinates.
     * @return the corresponding point object in world coordinates.
     * @author Shuzhao Feng
     */
	GPoint S2W(GPoint p) {return new GPoint(p.getX()/Xs+Xmin,(ymax-p.getY())/Ys+Ymin);}
	/**
	 * A simple method to add 1 point for computer whenever computer wins.
	 * @author Shuzhao Feng
	 */
	public void addComputerScore() {computerScore ++;}
	/**
	 * A simple method to add 1 point for player whenever player wins.
	 * @author Shuzhao Feng
	 */
	public void addPlayerScore() {playerScore ++;}
	/**
	 * A simple method to change Scaling parameter for pause()
	 * Used to scale {@link ppSimParams#TICK}.
	 * @see ppSimParams#TICK
	 */
	public double getGameSpeed() {return gameSpeed.getValue();}
	/**
	 * A method to reset scores to 0.
	 * @author Shuzhao Feng
	 */
	public void resetScore() {
		playerScore = 0;
		computerScore = 0;
	}
}
