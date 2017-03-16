package MatrixThingDM;

import java.awt.Color;
import java.lang.reflect.Array;
import java.util.Arrays;

import org.opensourcephysics.display.DrawableShape;
import org.opensourcephysics.display.Trail;
import org.opensourcephysics.frames.PlotFrame;

public class TableObjectDM {
	//global variables
	int rows; //number of rows
	int cols; //number of columns
	double [][] blurbs; //stores area of text blurb in each cell
	double [] subwidths; //stores width of each column
	double [] subheights; //stores height of each row


	//constructors:
	public TableObjectDM (int rows, int cols, double [][] blurbs, double [] subwidths, double [] subheights){
		this.rows = rows; 
		this.cols = cols;
		this.blurbs = blurbs;
		this.subwidths = subwidths;
		this.subheights = subheights;

	}
	public TableObjectDM (int rows, int cols){
		this.rows = rows;
		this.cols = cols;
		subwidths = new double [cols];
		subheights = new double [rows];
		blurbs = new double [rows][cols];

		//filling everything with ones
		Arrays.fill(subwidths, 1);
		Arrays.fill(subheights, 1);
		for(int i = 0; i<blurbs.length;i++){
			Arrays.fill(blurbs[i], 1);
		}
	}

	//setters and getters:
	public int getRows() {
		return rows;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}
	public int getCols() {
		return cols;
	}
	public void setCols(int cols) {
		this.cols = cols;
	}
	public double[][] getBlurbs() {
		return blurbs;
	}
	public void setBlurbs(double[][] blurbs) {
		this.blurbs = blurbs;
	}
	public double[] getSubwidths() {
		return subwidths;
	}
	public void setSubwidths(double[] subwidths) {
		this.subwidths = subwidths;
	}
	public double[] getSubheights() {
		return subheights;
	}
	public void setSubheights(double[] subheights) {
		this.subheights = subheights;
	}


	//calculates total width and height of table
	public double getWidth() {
		double width = 0;
		for (int i = 0; i<subwidths.length;i++){
			width+= subwidths[i];
		}
		return width;
	}
	public double getHeight() {
		double height = 0;
		for (int i = 0; i<subheights.length;i++){
			height+= subheights[i];
		}
		return height;
	}

	/**
	 * Sums all doubles from index zero until specified index in single dimensional double array 
	 * @param array the given array
	 * @param cutoff the specified index up until which the function sums the array's doubles
	 * @return
	 */
	public double sumDoubles (double [] array, double cutoff){
		double sum = 0;
		for (int i = 0; i<cutoff; i++){
			sum = sum + array[i];
		}
		return sum;
	}
	/**
	 * 
	 * @return areas of each cell, given heir heights and widths
	 */
	public double [][] getAreas (){
		double [][] areas = new double [rows][cols];
		for (int row = 0; row < rows; row++) {
			for (int col = 0; col< cols; col++){
				areas[row][col] = subheights[row]*subwidths[col];
			}
		}
		return areas;
	}
	public double getArea(int row, int col){
		return  subwidths[col]*subheights[row];
	}
	

	/**
	 * 
	 * @return array of empty space in each cell: area of cell minus the text blurb within
	 */
	public double [][] getEmptySpaces(){
		double [][] areas = getAreas();
		double [][] emptyspaces = new double [rows][cols];
		for (int row = 0; row < rows; row++) {
			for (int col = 0; col< cols; col++){
				emptyspaces[row][col] = areas[row][col] -blurbs[row][col];
			}
		}
		return emptyspaces;
	}
	
	public double allEmptySpace(){
		double sum = 0; 
		double [][] emptyspaces = getEmptySpaces();
		for (int i = 0; i<emptyspaces.length; i++){
			sum += sumDoubles(emptyspaces[i], emptyspaces[0].length);
		}
		return sum;
	}

	public void printTableValues(double [][] tablevalues){
		for (int row = 0; row<rows; row++){
			System.out.println();
			for (int col = 0; col<cols; col++){
				System.out.print("|" + tablevalues[row][col] + "|");
			}
		}
		System.out.println();
	}

	/**
	 * 
	 * @param row
	 * @param col
	 * @return the extra space in a single cell
	 */
	public double getEmptySpace(int row, int col){
		return getAreas()[row][col]- blurbs[row][col];
	}
	public void plotRects(){
		PlotFrame pf = new PlotFrame("rows", "columns", "Rect Table");
		double x = 0; //temp x coord
		double y = 0; //temp y coord
		double height = getHeight();
		double width = getWidth();
		double [] subwidthz = new double [cols+1];
		//initializing
		subwidthz[0] = 0;
		for(int row = 0; row<rows; row++){
			subwidthz[row+1] = subwidths[0];
		}
		double [] subheightz = new double [cols+1];
		//initializing
		subheightz[0] = 0;
		for(int row = 0; row<rows; row++){
			subheightz[row+1] = subwidths[0];
		}


		pf.setPreferredMinMax(-2, width+2, -2, height+2);
		pf.setVisible(true); //set display frame visible

		for (int row = 0; row<rows; row++){
			for (int col = 0; col<cols; col++){
				DrawableShape rec = DrawableShape.createRectangle(sumDoubles(subwidths, col) + subwidths[col]/2,  sumDoubles(subheights, row) + subheights[row]/2, subwidths[col], subheights[row]);


				//indicate whether blurb fits
				if (blurbs[row][col]<=subwidths[col]*subheights[row]){
					rec.setMarkerColor(Color.GREEN, Color.BLUE);
				}
				pf.addDrawable(rec);
			}
		}


	}
	public void plotTable(){
		PlotFrame pf = new PlotFrame("rows", "columns", "Table");
		double x = 0; //temp x coord
		double y = 0; //temp y coord
		double height = getHeight();
		double width = getWidth();
		Trail [] verts = new Trail [cols+1]; //array of vertical boundaries of table
		//initializing
		for(int vert = 0; vert<verts.length; vert++){
			verts[vert] = new Trail();
		}
		Trail [] hors = new Trail [rows+1]; //array of horizontal boundaries of table
		//initializing
		for(int hor = 0; hor<hors.length; hor++){
			hors[hor] = new Trail();
		}

		pf.setPreferredMinMax(-2, width+2, -2, height+2);
		pf.setVisible(true); //set display frame visible

		//Vertical Lines:

		//first vertical line
		verts[0].addPoint(0, 0);
		verts[0].addPoint(0, height);
		pf.addDrawable(verts[0]);
		//every other vertical line
		for (int col = 1; col<= cols; col++){
			x = x + subwidths[col-1];
			verts[col].addPoint(x, 0);
			verts[col].addPoint(x, height);
			pf.addDrawable(verts[col]);
		}

		//Horizontal Lines:
		//first horizontal line
		hors[0].addPoint(0,0);
		hors[0].addPoint(width, 0);
		pf.addDrawable(hors[0]);
		for (int row = 1; row<=rows; row++){
			y = y + subheights[row-1];
			hors[row].addPoint(0, y);
			hors[row].addPoint(width, y);
			hors[row].closeTrail();
			pf.addDrawable(hors[row]);
		}
	}
	//OPTIMIZATION!!!
	/**
	 * 
	 * @return optimized dimensions of each row and col, as to minimize extra space in the table: sets table's dimensions equal to these
	 */
	public void cellByCell (double param){
		//2 BY 2
		//find possible dimensions of cell 0,0 (row,column)
		subwidths[0] = param; //width: variable: subwidths[1]
		subheights[0] = blurbs[0][0]/subwidths[0];

		//from here, find best dimensions of cell 1,0
		//optw[0] //width
		subheights[1] = blurbs[1][0]/subwidths[0]; //height

		//find best dimensions of cell, 0,1
		//opth[0] //height
		subwidths[1] = blurbs[0][1]/subheights[0]; //width

		//dimensions are already set for fourth cell, so if fourth blurb is smaller than or equal to present constraint (optw[1] and opth[1])
		//, we are good. otherwise, we need to find the best possible way to extend the edges of the table to create the least possible blank space
		if (blurbs[1][1]> getArea(1,1)){
			double space = -getEmptySpace(1, 1);
			double extendright = 0;
			double extendup = 0;
			double rspace = 0; //space that is solely extended in one direction
			
			double still_left = 0; //blurb still left after step one, ie threshold is reached
			double secondright = 0; //how much we extend right in step two
			double secondup = 0; //how much we extend up in step two
			

			//for sake of clarity, the term 'threshold' is now used to denote the point at which the area of cell 0,1 = area of cel 1,1
			//Step 1
			//if top left cell has smaller area than bottom right cell, extendup
			if (getArea(1,0)<getArea(0,1)){
				rspace = findThreshArea(0, 1, 1, 0,subwidths[0], subheights[0]); //amount of leftover area that would be extended until height = width if extra blurb provides enough
				//if leftover space is smaller than space it would take to reach this threshold, i.e. for height and width to become equal, just calculate extension needed for the height
				if(space<rspace){
					extendup = space/subheights[0]; //length that height of row is extended up
				}
				//if blurb provides enough empty area to reach threshold
				else{
					extendup = (getArea(0,1)-getArea(1,0))/subwidths[0]; //make areas equal
				}
			}
			//else if cell 0,1 is smaller than cell 1,0: extend to the right
			else if (getArea(0,1)<getArea(1,0)){
				rspace = findThreshArea (1,0,0,1, subheights[0], subheights[1]); //threshold area: the amount that would be added to fourth cell if area cell 0,1 was equalized with 1,0
				//does extra blurb provide enough area to make this extension right? if not...
				if(space<rspace){
					extendright = space/subwidths[0];
				}
				//if extra blurb does provide enough extra area:
				else {
					extendright = (getArea(1,0)-getArea(0,1))/subheights[0]; //extend until width equals height
				}
			}
			//add all extended area
			subwidths[1] += extendup; //adding extension up
			subheights[1] += extendright; //adding extension right
			
			
			//step two: at this point, if there is still leftover blurb (or the height and widths of col and row were equal initially), increase 1,0 and 0,1 areas equally through combined left and right extension until all extra area is displayed
			still_left = space-rspace;
			if (still_left> 0){
				//then extend combined in both directions, preserving equal areas of 1,0 and 0,1 until blurb is exhausted
				
				//still_left = (width+equal)*(height+eaual)-width*height: simplified below
				//equal(width+height) + equal*equal = still_left
				//e^2 + (w+h)e - sl = 0 --> 'equal' is the following
				double h = getHeight();
				double w = getWidth();
				secondup = (-subwidths[1]-subheights[1] + Math.sqrt(Math.pow((subwidths[1]+subheights[1]*subheights[0]/subwidths[0]), 2) + 4*space*(Math.pow(subheights[0]/subwidths[0], 2))))/(2*subheights[0]/subwidths[0]);
				secondright = secondup*subheights[0]/subwidths[0];
				subheights[1] += secondup;
				subwidths[1] += secondright;
			}

		}
		//given this equation, what should param be? five cases: 1) constraints are perfect for 2 by 2, 2) needs only extend up 3) needs only extend right  5) needs extend right and together 6) needs to extend up and together:
//		double [] possEmptySpaces = new double [40];
//		for(int i = 0; i<20; i++){
//			cellByCell(i);
//			possEmptySpaces[i] = allEmptySpace();
//			cellByCell(1/i);
//			possEmptySpaces[40-i] = allEmptySpace();
//		}
//		Arrays.sort(possEmptySpaces);
	//	syso
		
		//I should think about how to simplify this function
	}
	
	/**
	 * finds the area that will be added to fourth cell threshold to be reached, which would mean that 1,0 and 0,1 are equalized
	 * @param rowA row number of larger cell
	 * @param colA col number of larger cell
	 * @param rowB row number of smaller cell
	 * @param colB col number of smaller cell
	 * @param c1 the dimension of the smaller cell that's staying constant as its other dimension extends (its height or width)
	 * @param c2 same dimension type, but of fourth cell, that'll stay constant when it extends in other dimension
	 * @return
	 */
	public double findThreshArea (int rowA, int colA, int rowB, int colB, double c1, double c2){
		//finds the difference between current areas, and finds how much area this will add to the fourth
		return (getArea(rowA, colA) - getArea(rowB, colB))/c1*c2;
	}
	
}
