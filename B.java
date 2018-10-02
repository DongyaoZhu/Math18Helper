import java.util.Scanner;
import java.io.*;
public class B{

	public static void printUsage(){
		System.out.println("java B -n NumberOfMatrices");
	}

	public static void main(String[] args) throws IOException{

/*		//QS process
		Matrix a = new Matrix("A.txt");
		System.out.println("v1\n\n"+a);
		
		Matrix b = new Matrix("B.txt");
		Matrix v2 = b.subtract(b.projection(a));
		System.out.println("v2\n\n"+v2);

		Matrix c = new Matrix("C.txt");
		Matrix v3 = c.subtract(c.projection(a)).subtract(c.projection(v2));
		System.out.println("v3\n\n"+v3);

		Matrix d = new Matrix("D.txt");
		Matrix v4 = d.subtract(d.projection(a)).subtract(d.projection(v2)).subtract(d.projection(v3));
		System.out.println("v4\n\n"+v4);
*/
/*		Scanner scan = new Scanner(System.in);
		Matrix[] matrix = new Matrix[0];
		for(int i = 0; i < args.length; i++){
			if(args[i].equals("-n")){
				int n = 0;
				try{
    				n = Integer.parseInt(args[i + 1]);  
    			}catch(NumberFormatException e){
    				System.out.println(e.getMessage());
    				printUsage();
    			}
    			if(n < 1) System.exit(1);
    			matrix = new Matrix[n];
    			for(int j = 0; j < n; j++){
					System.out.printf("Enter file name of matrix %c:\n", (char)n);
					String s = scan.next();
					int[] size = Matrix.countSize(s);
					if(size[0] == size[1]){
						matrix[j] = new NbyNMatrix(s);
					}else{
						matrix[j] = new Matrix(s);
					}
					processMatrix(matrix[i]);
				}
			}
		}
		if (matrix.length > 1)
		System.out.println("all multiply\n" + several(matrix, matrix.length - 2));*/
	}

	public static Matrix several(Matrix[] matrix, int i){
		if (i == matrix.length - 1) return matrix[i];
		return matrix[i].multiply(several(matrix, i + 1));
	}

	public static void processMatrix(Matrix matrix){
		if(matrix instanceof NbyNMatrix){
			NbyNMatrix nbnm = (NbyNMatrix)matrix;
			System.out.print("original\n"+nbnm);
			System.out.print("rref\n"+nbnm.rref());
			System.out.println("dimension of column space\n"+nbnm.dimCol());
			System.out.println("dimension of null space\n"+nbnm.dimNul());
			System.out.println("determinant\n"+nbnm.det());
			System.out.print("inverse\n"+nbnm.inverse());
		}else{
			System.out.print("original\n"+matrix);
			System.out.print("rref\n"+matrix.rref());
			System.out.println("dimension of column space\n"+matrix.dimCol());
			System.out.println("dimension of null space\n"+matrix.dimNul());
		}
	}

}