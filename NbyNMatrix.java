import java.util.Scanner;
import java.io.*;
import java.util.ArrayList;

public class NbyNMatrix extends Matrix{
   protected int size;
   private Pair determinant = new Pair(1);
   private boolean detDone = false;

   public NbyNMatrix(int size, boolean empty){
      super(size, size, empty);
      this.size = size;
   }

   public NbyNMatrix(NbyNMatrix m){
      super(m.element);
      size = m.size;
      if(m.detDone){
         detDone = true;
         determinant = m.determinant;
      }
   }

   public NbyNMatrix(Matrix m){
      super(m.element);
      size = m.row;
   }

   public NbyNMatrix(String input) throws IOException{
      super(input);
      size = element.length;
   }

   public NbyNMatrix inverse(){
      if(det().value() == 0){
         System.out.println("this matrix is not invertible");
         return null;
      }
      Pair[][] aipair = new Pair[size][2 * size];
      for(int i = 0; i < size; i++){//make matrix A I ~ I A⁻¹
         for(int j = 0; j < size; j++){
            aipair[i][j] = new Pair(element[i][j]);
            aipair[i][j + size] = new Pair(i == j ? 1 : 0);
         }
      }
      Matrix ai = new Matrix(aipair).rref();
      NbyNMatrix inverse = new NbyNMatrix(size, true);
      for(int i = 0; i < size; i++){
         for(int j = 0; j < size; j++){
            inverse.element[i][j] = ai.element[i][j + size];
         }
      }
      return inverse;
   }

   public Pair det(){
      if(detDone) return determinant;
      Pair[][] ref = rref().element;
      for(int i = 0; i < size; i++){
         determinant = determinant.multiply(ref[i][i]);
      }
      for(Pair p : refStep){
         determinant = determinant.multiply(p);
      }
      detDone = true;
      return determinant;
   }

   public boolean isEigenValue(int eig){
      Matrix im = subtract(new IdentityMatrix(size).multiply(new Pair(eig)));
      System.out.printf("A-%dI = \n"+im, eig);
      System.out.println("rref: \n"+im.rref());
      return im.dimNul() != 0;
   }

   public boolean isEigenVector(Matrix vector){//n x 1 vector for now
      if(vector.row != size) return false;
      Matrix ax = multiply(vector);
      Pair k = ax.element[0][0].divide(vector.element[0][0]);
      for(int i = 1; i < size; i++){
         if(!ax.element[0][0].divide(vector.element[0][0]).equals(k)){
            System.out.println("not eigen vector");
            return false;
         }
      }
      System.out.printf("is eigen vector. eigen value is %s\n", k);
      return true;
   }

   public static void main(String[] args) throws IOException{
      NbyNMatrix a = new NbyNMatrix("A.txt");
      // NbyNMatrix b = new NbyNMatrix("B.txt");
      /* System.out.println(a.multiply(b));
      System.out.println(a.inverse());
      System.out.println(b.inverse()); */
      System.out.println("A\n"+a);
      System.out.println("A rref\n"+a.rref());
      System.out.println("det a\n"+a.det());
      //System.out.println(a.isEigenValue(1));
      //Matrix b = new Matrix("B.txt");
      //System.out.println(a.isEigenVector(b));
      //System.out.println(a.multiply(a.inverse()));
   }

}
