package MatrixThingDM;

import java.util.Random;

public class TableTestDM {
	public static void main(String[] args) {
		Random ran = new Random();
		
		TableObjectDM table = new TableObjectDM(2, 2);
		table.setBlurbs(new double [][] {{2,1.5},{7.3,8}});
		//table.setBlurbs(new double [][] {{ran.nextInt(10),ran.nextInt(10)},{ran.nextInt(10),ran.nextInt(10)}});
		System.out.println(table.getHeight() + " ; " + table.getWidth());
		table.plotTable();
		table.plotRects();
		table.printTableValues(table.getEmptySpaces());
		table.printTableValues(table.getBlurbs());
		table.printTableValues(table.getAreas());
		
		
		System.out.println("Now, optimized!!");
		table.cellByCell(1);
		table.printTableValues(table.getEmptySpaces());
		table.printTableValues(table.getBlurbs());
		table.printTableValues(table.getAreas());
		table.plotRects();
	}
}
