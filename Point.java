package fr.umlv.tools;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
/**
 * This class is used in order to represent a point.
 * @author natew
 *
 */
public class Point {
	/* this field represents the value depending on abscissa */
	private int x;
	/* this field represents the value depending on ordinate */
	private int y;
	/* this field represents the maximum value for ordinate */
	private static final int maxY = 8;
	/* this field represents the maximum value for abscissa */
	private static final int maxX = 8 ;
	/**
	 * The constructor for a point
	 * @param x1
	 * the initial value for "x" in abscissa
	 * @param y1
	 * the initial value for "y" in ordinate
	 */
	public Point(int x1, int y1){
	  if(x1 < 0 || x1 > maxY) {
		  throw new IllegalArgumentException
		  ("Lookout your coordinate 'x' must be between 0 and 8");
	  }
	  if(y1 < 0 || y1 > maxX) {
		  throw new IllegalArgumentException
		  ("Lookout your coordinate 'y' must be between 0 and 8");
	  }
	  x = x1;
	  y = y1;
	}
	
	@Override
	/**
	 * Thiss method enables to compare two points, in order to know if they're equals or not.
	 * @param o
	 * it represents an object with which one wants to compare their points.
	 * 
	 * @return boolean
	 * it indicates either two points are equals or not.
	 * 1 means equals.
	 * 0 means different.
	 */
	public boolean equals(Object o1) {
		Objects.requireNonNull(o1,"You cannot compare a point with an null object");
		return o1 instanceof Point p1 && (p1.x == x) && (p1.y == y);
		
	}
	/**
	 * This method express with words a point coordinates
	 * @return String
	 * it is a litteral representation of a point
	 */
	@Override
	public String toString(){
		return " [x = "+x+", y= " +y+"]";
	}
	
	@Override
	/**
	 * This method represents a point hashcode
	 * @return int
	 * the return value is an int which is the item hashCode.
	 */
	public int hashCode(){
		return Integer.hashCode(x)^Integer.hashCode(y);
	}
	
	/** 
	 * this method enable to update the cordinate "X" of
	 * an element
	 * @param x1
	 * the new value for x
	 */
	public void updateX(int x1) {
		if(x1 < 0 || x1 >= maxX) {
			  throw new IllegalArgumentException
			  ("Lookout your coordinate 'x' must be between 0 and 20");
		  }
		x = x1;
	}
	
	/**
	 * 
	 * this method enable to update the coordinate "Y" of
	 * an element
	 * @param y1
	 * the new value for y
	 * 
	 */
	public void updateY(int y1) {
		  if(y1 < 0 || y1 >= maxY) {
			  throw new IllegalArgumentException
			  ("Lookout your coordinate 'y' must be between 0 and 20");
		  }
		y = y1;
	}
	
	/**This function do a deep copy of a point coordinates
	 * 
	 * @return the copy which has its own references.
	 */
	@Override
	public Object clone(){
		
		var clonePoint = new Point(this.x, this.y);
		return clonePoint;	
	}
	/**
	 * 
	 * An accessor for the x position.
	 * @return 
	 * an int which represents the point position according to the "x" axis.
	 */
	public int x() {
		return x;
	}
	
	/**
	 * 
	 * An accessor for the y position.
	 * @return 
	 * an int which represents the point position according to the "y" axis.
	 */
	public int y() {
		return y;
	}
	
	/**
	 * @param text
	 * It's the text that represents the Point coordinates
	 * (x,y)
	 * @return
	 * A point which is the point chosen.
	 */
	public static Point fromTextToPoint(String text){
		
		ArrayList <String> items = new ArrayList<String>();
		items.addAll(Arrays.asList(text.split(",")));
		
		if(items.size()>0) {
			/* on retire les parenthèses qui sont autour des coordonnées*/
			String nouv = items.get(0).replace("(", "");
			items.set(0, nouv);
		}
		if(items.size()>1) {
			/* on retire les parenthèses qui sont autour des coordonnées*/
			String nouv2 = items.get(items.size()-1).replace(")", "");
			items.set(items.size()-1, nouv2);
			
		}
		/* on transforme les coordonnées en point*/
		var p1 = new Point(Integer.parseInt(items.get(0)), Integer.parseInt(items.get(1)));
		
		return p1;
		
		
	}
	
	/**
	 * @param time
	 * the time that one wants to convert into coordinates.
	 * This method converts the player coordinates into their opposite time.
	 * @return
	 * A point which represents the coordinates.
	 */
	public static Point timeToOppositeCoordinates(int temps){
		int y = temps/maxY;//on détermine la valeur de y
		int x = temps%maxX; //on détermine la valeur de x
		return new Point(x,y); // faux à compléter
	}
	/**
	 * 
	 * This method substract one point to "this" object.
	 * @param p2
	 * The point that will be substract to "this".
	 * @return
	 * The new point created by substraction.
	 */
	public Point soustractionPoint(Point p2) {
		return new Point(this.x-p2.x(), this.y()-p2.y());
	}
	
	/**
	 * This method converts time into coordinates.
	 * @param time
	 * the time that one wants to convert into coordinates.
	 * @return
	 * A point which represents the coordinates.
	 */
	public static Point timeToCoordinates(int time){
		return new Point((maxX-1),(maxY-1)).soustractionPoint(timeToOppositeCoordinates(time)); // faux à compléter
	}
	
	
	

}
