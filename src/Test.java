import java.util.*;
import java3dpipeline.*;

public class Test {
	public static void main (String[] args) {
		Matrix m = new Matrix (new Rotation (45, 45, 0));
		Matrix m1 = Matrix.rotationXYZ(new Rotation (45, 45, 0));
		m.print();
		System.out.println("");
		m1.print();
	}
}