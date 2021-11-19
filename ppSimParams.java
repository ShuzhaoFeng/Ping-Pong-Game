package ppPackage;
/**
 * A list of all static parameters used by the program.
 * Modified from original code in student's ECSE-202 F2021 previous assignments.
 * <br>
 * <br>{@link ppSimParams#WIDTH}
 * <br>{@link ppSimParams#HEIGHT}
 * <br>{@link ppSimParams#OFFSET}
 * <br>{@link ppSimParams#ppTableXlen}
 * <br>{@link ppSimParams#ppTableHgt}
 * <br>{@link ppSimParams#XwallL}
 * <br>{@link ppSimParams#XwallR}
 * <br>{@link ppSimParams#g}
 * <br>{@link ppSimParams#k}
 * <br>{@link ppSimParams#Pi}
 * <br>{@link ppSimParams#bSize}
 * <br>{@link ppSimParams#bMass}
 * <br>{@link ppSimParams#ETHR}
 * <br>{@link ppSimParams#TICK}
 * <br>{@link ppSimParams#MS2S}
 * <br>{@link ppSimParams#Xmin}
 * <br>{@link ppSimParams#Xmax}
 * <br>{@link ppSimParams#Ymin}
 * <br>{@link ppSimParams#Ymax}
 * <br>{@link ppSimParams#xmin}
 * <br>{@link ppSimParams#xmax}
 * <br>{@link ppSimParams#ymin}
 * <br>{@link ppSimParams#ymax}
 * <br>{@link ppSimParams#Xs}
 * <br>{@link ppSimParams#Ys}
 * <br>{@link ppSimParams#PD}
 * <br>{@link ppSimParams#wThick}
 * <br>{@link ppSimParams#lagMIN}
 * <br>{@link ppSimParams#lagMAX}
 * <br>{@link ppSimParams#lagInit}
 * <br>{@link ppSimParams#gameSpeedMIN}
 * <br>{@link ppSimParams#gameSpeedMAX}
 * <br>{@link ppSimParams#gameSpeedInit}
 * <br>{@link ppSimParams#Xinit}
 * <br>{@link ppSimParams#Xinit2}
 * <br>{@link ppSimParams#Yinit}
 * <br>{@link ppSimParams#ppPaddleH}
 * <br>{@link ppSimParams#ppPaddleW}
 * <br>{@link ppSimParams#ppPaddleXinit}
 * <br>{@link ppSimParams#ppPaddleYinit}
 * <br>{@link ppSimParams#ppPaddleXgain}
 * <br>{@link ppSimParams#ppPaddleYgain}
 * <br>{@link ppSimParams#YinitMAX}
 * <br>{@link ppSimParams#YinitMIN}
 * <br>{@link ppSimParams#EMAX}
 * <br>{@link ppSimParams#EMIN}
 * <br>{@link ppSimParams#VoMAX}
 * <br>{@link ppSimParams#VoMIN}
 * <br>{@link ppSimParams#thetaMAX}
 * <br>{@link ppSimParams#thetaMIN}
 * <br>{@link ppSimParams#RSEED}
 * <br>{@link ppSimParams#STARTDELAY}
 * @author Shuzhao Feng
 */
public class ppSimParams {
	/**Width of the screen in pixels.*/
	public static final int WIDTH = 1080;
	/**Distance from top of screen to ground plane in pixels.*/
	public static final int HEIGHT = 600;
	/**Distance from bottom of screen to ground plane in pixels.*/
	public static final int OFFSET = 200;
	/**Total length of the table in meters.*/
	public static final double ppTableXlen = 2.74;
	/**Height of the ceiling in meters.*/
	public static final double ppTableHgt = 1.52;
	/**Position of left wall in meters.*/
	public static final double XwallL = 0.05;
	/**Position of right wall in meters.*/
	public static final double XwallR = 2.69;
	/**Gravitational constant in m/s<sup>2</sup> rounded to 1 decimal place.*/
	public static final double g = 9.8;
	/**
	 * Terminal velocity constant provided by Prof. Ferrie with unknown unit.
	 */
	public static final double k = 0.1316;
	/**Irrational constant pi rounded to 4 decimal places.*/
	public static final double Pi = 3.1416;
	/**ppBall radius in meter.*/
	public static final double bSize = 0.02;
	/**ppBall mass in kilogram.*/
	public static final double bMass = 0.0027;
	/**
	 * Minimum energy in joule.
	 * Used to stop simulation when the ball's energy falls below the threshold.
	 */
	public static final double ETHR = 0.001;
	/**Unit clock increment at each iteration in second.*/
	public static final double TICK = 0.01;
	/**Minimum value of X in meter.*/
	public static final double Xmin = 0.0;
	/**Maximum value of X in meters.*/
	public static final double Xmax = ppTableXlen;
	/**Minimum value of Y in meter.*/
	public static final double Ymin = 0.0;
	/**Maximum value of Y in meters.*/
	public static final double Ymax = ppTableHgt;
	/**Minimum value of x in pixel.*/
	public static final int xmin = 0;
	/** Maximum value of x in pixels.*/
	public static final int xmax = ppTable.width;
	/**Minimum value of y in pixel.*/
	public static final int ymin = 0;
	/**Minimum value of y in pixels.*/
	public static final int ymax = HEIGHT;
	/**Scale factor X. Used for conversion between world and screen coordinates.*/
	public static final double Xs = (xmax-xmin)/(Xmax-Xmin);
	/**Scale factor Y. Used for conversion between world and screen coordinates.*/
	public static final double Ys = (ymax-ymin)/(Ymax-Ymin);
	/**
	 * Pixel diameter. Used for trace point radius and marginal correction.
	 * <br>For example, if the table width is 1280, the rightmost wall should start at the 1281st pixel instead of the 1280th pixel.
	 * This can be achieved by adding <b>PD/Xs</b> to the right wall position.
	 * @see ppTable#ppTable
	 */
	public static final double PD = 1;
	/**Wall thickness in pixel.*/
	public static final int wThick = 3;
	/**Minimum lag of the paddle.*/
	public static final int lagMIN = 0;
	/**Maximum lag of the paddle.*/
	public static final int lagMAX = 10;
	/**Initial lag of the paddle.*/
	public static final int lagInit = 5;
	/**Minimum game speed.*/
	public static final int gameSpeedMIN = 1000;
	/**Maximum game speed.*/
	public static final int gameSpeedMAX = 5000;
	/**Initial game speed.*/
	public static final int gameSpeedInit = 2000;
	/**Paddle height in meter.*/
	public static final double ppPaddleH = 8*2.54/100;
	/**Paddle width in meter.*/
	public static final double ppPaddleW = 0.5*2.54/100;
	/**Initial left paddle X position in meter.*/
	public static final double LPaddleXinit = XwallL-ppPaddleW/2;
	/**Initial right paddle X position in meter.*/
	public static final double RPaddleXinit = XwallR-ppPaddleW/2;
	/**Initial paddle Y position in meter.*/
	public static final double RPaddleYinit = Ymax/2+bSize;
	/**X velocity gain on left paddle hit.*/
	public static final double LPaddleXgain = 2.0;
	/**Y velocity gain on left paddle hit.*/
	public static final double LPaddleYgain = 2.0;
	/**X velocity gain on right paddle hit.*/
	public static final double RPaddleXgain = 2.0;
	/**Y velocity gain on right paddle hit.*/
	public static final double RPaddleYgain = 2.0;
	/**Initial ball location in meters for the first ball (X).*/
	public static final double Xinit = LPaddleXinit+ppPaddleW/2+PD/Xs;
	/**Maximum initial Y position in meter, used to control the range of the random generator.*/
	public static final double YinitMAX = 0.75*Ymax;
	/**Minimum initial Y position in meter, used to control the range of the random generator.*/
	public static final double YinitMIN = 0.25*Ymax;
	/**Maximum energy loss per collision, used to control the range of the random generator.*/
	public static final double EMAX = 0.2;
	/**Minimum energy loss per collision, used to control the range of the random generator.*/ 
	public static final double EMIN = 0.2;
	/**Maximum initial velocity in meter per second, used to control the range of the random generator.*/
	public static final double VoMAX = 5.0;
	/**Minimum initial velocity in meter per second, used to control the range of the random generator.*/ 
	public static final double VoMIN = 5.0;
	/**Maximum launch angle in degree, used to control the range of the random generator.*/ 
	public static final double thetaMAX = 20.0;
	/**Minimum launch angle in degree, used to control the range of the random generator.*/
	public static final double thetaMIN = 0.0;
	/** Arbitrary random number generator seed value.*/
	public static final long RSEED = 8976232;
	/**Delay in millisecond between setup and start.*/
	public static final int STARTDELAY = 1000;
}
