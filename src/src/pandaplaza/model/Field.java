/*
 * Kovács Ákos
 * 2019.03.07
 */

package pandaplaza.model;

import java.util.*;
import javafx.geometry.Point2D;

public class Field {

	/* member fields */
	
	/**
	 *  Store the fields neigbhours. 
	 */
	private List<Field> neighbours = new ArrayList<>();
	/**
	 * Center of the field. 
	 * We need that for calculations to determine a the neogbour field that is looked by an animal.
	 */
	protected Point2D center;
	/**
	 * Vertices of the field.
	 * We need that for calculations to determine a the neogbour field that is looked by an animal.
	 */
	private ArrayList<Point2D> verts;
	public void setVerts(ArrayList<Point2D> v) {verts = v;}
	public ArrayList<Point2D> getVerts() {return verts;}
	
	/**
	 * Border width of the field.
	 */
	protected double borderWidth = 1;
	public void setBorderWidth(double bw) { borderWidth = bw;}
	public double getBorderWidth() { return borderWidth; }
	
	/**
	 * The animal which stands on the field.
	 */
	private Animal a = null;
	
	/*
	 * The field's name, used in test cases to identify to the field.
	 */
	private String name;

	public Point2D getCenter()            { return center; }
	public void setCenter(Point2D center) { this.center = center;  }
	public String getName() 	          {return name;}
	public void setName(String n)         {name = n;}
	public List<Field> getNeighbours()    { return neighbours;  }
	
	public void setNeighbours(List<Field> neighbours) {
		this.neighbours = neighbours;
	}
	public void addNeigbour(Field f) { neighbours.add(f); }
	
	public Animal getAnimal()       { return a;  }
	public void setAnimal(Animal a) { this.a = a; }

	/* methods */
	public Field() {}
	
	/**
	 * Returns the neighbour field that is corresponds to the angle.
	 * @param angle in degrees
	 * @return
	 */
	public Field getNeighbourByAngle(double angle) {
		// Field center
		Point2D c = center;

		// New point initial coordinates. Y axis
		double x = 0; double y = +1;
		
		// Rotate the point by the angle
		Point2D rotatedPoint = new Point2D(
				-(x*Math.cos(Math.toRadians(angle)) - y*Math.sin(Math.toRadians(angle))), 
				-(y*Math.cos(Math.toRadians(angle)) + x*Math.sin(Math.toRadians(angle))  ));
		
		rotatedPoint= rotatedPoint.add(c);
		
		// Get the vector from the center to the roteted point.
		// With these calculations above we get the direction vector of the animal.
		Point2D v = rotatedPoint.subtract(c);
		
		// Select the vertex that has the minimum angle with the direction vector.
		double minAngle = Double.MAX_VALUE;
		int minIdx = -1;
		
		for (int i = 0; i<verts.size(); i++) {
			// rotetedpoint <-> c <-> currentVertex angle
			double currangle = c.angle(rotatedPoint, verts.get(i));
			if (currangle < minAngle) {
				minAngle = currangle;
				minIdx = i;
			}
		}
		
		// Now we have the closest vertex
		// We have to tell which one is the other vertex from the two neigbour vertex.
		//
		//              X next
		//
		//        X p1
		//
		//                X prev
		//
		// We have to decide which side is the one we are looking for: p1 <-> next  or p1 <-> prev
		Point2D p1 = verts.get(minIdx);
		Point2D p2 = null;
		
		// Get the next and prev values
		Point2D next, prev;
		if (minIdx == 0) {
			prev = verts.get(verts.size()-1);
		} else {prev = verts.get(minIdx-1); }
		
		if (minIdx == verts.size() - 1) {
			next = verts.get(0);
		} else {next = verts.get(minIdx + 1); }
		
		
		// A is the center  -> p1 vector
		Point2D A = p1.subtract(c);
		
		// B is the center
		Point2D B = v;
		
		// C1 and C2 are center -> next and center -> prev
		Point2D C1 = next.subtract(c);
		Point2D C2 = prev.subtract(c);
		
		// To decide between prev and next, we know that 
		// the v vector is between the other two.
		if (A.crossProduct(B).dotProduct( A.crossProduct(C1) ) >= 0 &&
			C1.crossProduct(B).dotProduct( C1.crossProduct(A)) >= 0) {
			p2 = next;
		}
		if (A.crossProduct(B).dotProduct( A.crossProduct(C2) ) >= 0 &&
				C2.crossProduct(B).dotProduct( C2.crossProduct(A)) >= 0) {
				p2 = prev;
		}
		
		for (Field nb : neighbours) {
			if (nb.getVerts().contains(p1) &&
				nb.getVerts().contains(p2)) {
				return nb;
			}
		}
		return null;
	}


	public void accept(Animal a) {
		a.steppedOnField(this);
	}

	
	/** Used only in WeakTile, we do nothing here */
	public void animalJumped(Animal a) {
		return;
	}


	/** Used only in WeakTile, we do nothing here */
	public void wantToSitDown(LazyPanda lp) {
		return;
	}
	
	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();//the result string
		String newline = System.lineSeparator();//new line string
		//building string
		sb.append("Field:");
		sb.append(newline);
		sb.append(name);
		sb.append(newline);
		sb.append("a: ");
		if(a == null) {sb.append("null");}
		else {sb.append(a.getName());}
		sb.append(newline);
		sb.append("n:");
		for(Field f : neighbours) { sb.append(" "); sb.append(f.getName()); }
		sb.append(newline); sb.append(newline);
		return sb.toString();
	}
	
}