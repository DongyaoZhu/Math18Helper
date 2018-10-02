import java.util.Scanner;
import java.io.*;
import java.util.ArrayList;

public class Matrix{

   //fields
   protected int row;
   protected int column;
   protected Pair[][] element;
   protected ArrayList<Pair> refStep;
   private Pair[][] ref;
   private boolean isRREF;
   private int dimCol;

   public Matrix(int r, int c, boolean empty){
      row = r;
      column = c;
      element = new Pair[r][c];
      refStep = new ArrayList<Pair>();
      isRREF = false;
      dimCol = -1;
      if(empty){
         for(int i = 0; i < r; i++){
            for(int j = 0; j < c; j++){
               element[i][j] = new Pair();
            }
         }
      }else{
         Scanner input = new Scanner(System.in);
         for(int i = 0; i < r; i++){
            System.out.println("enter elements in row " + (i + 1) + " :");
            for(int j = 0; j < c; j++){
               int in = input.nextInt();
               element[i][j] = new Pair(in);
            }
         }
      }
   }

   //matrices created by this should be only used temporarily for calculation
   public Matrix(Pair[][] p){
      row = p.length;
      column = p[0].length;
      element = new Pair[row][column];
      refStep = new ArrayList<Pair>();
      isRREF = false;
      dimCol = -1;
      for(int i = 0; i < row; i++){
         for(int j = 0; j < column; j++){
            element[i][j] = new Pair(p[i][j]);
         }
      }
   }

   public Matrix(Matrix m){
      row = m.element.length;
      column = m.element[0].length;
      element = new Pair[row][column];
      refStep = new ArrayList<Pair>(m.refStep);
      isRREF = m.isRREF;
      dimCol = m.dimCol;
      for(int i = 0; i < row; i++){
         for(int j = 0; j < column; j++){
            element[i][j] = new Pair(m.element[i][j]);
         }
      }
   }

   public Matrix(String input) throws IOException{
      File file = new File(input);
      if(!file.exists()){
         System.out.println("no such file. Exit.");
         System.exit(0);
      }
      int[] size = countSize(input);
      row = size[0];
      column = size[1];
      element = new Pair[row][column];
      refStep = new ArrayList<Pair>();
      isRREF = false;
      dimCol = -1;
      Scanner row = new Scanner(file);
      Scanner col;
      int rowCount = 0;
      while(row.hasNextLine()){
         col = new Scanner(row.nextLine());
         int colCount = 0;
         while(col.hasNext()){
            String s = col.next();
            if(s.equals("/")){
               element[rowCount][colCount - 1] = new Pair
               (element[rowCount][colCount++ - 2].divide(new Pair(Integer.parseInt(col.next()))));
            }else{
               element[rowCount][colCount++] = new Pair(Integer.parseInt(s));
            }            
         }
         rowCount++;
      }
   }

   public static int[] countSize(String input) throws IOException{
      File file = new File(input);
      if(!file.exists()){
         System.out.println("no such file. Exit.");
         System.exit(0);
      }
      int r = 0, c = 0;
      try{
         Scanner scan = new Scanner(file);
         Scanner scanCol;
         while(scan.hasNextLine()){
            scanCol = new Scanner(scan.nextLine());
            while(scanCol.hasNext()){
               String s = scanCol.next();
               if(s.equals("/")){
                  c--;
                  continue;
               }
               c++;
            }
            r++;
         }
      }catch(IOException e){
         System.out.println(e.getMessage());
      }
      return new int[] {r, c / r};
   }

   public boolean isRREF(){
      rref();
      return isRREF;
   }

   public Matrix rref(){
      if(isRREF){
         System.out.println("is already rref");
         return this;
      }else if(ref != null){
         return new Matrix(ref);
      }
      ref = new Pair[row][column];
      for(int i = 0; i < row; i++){
         for(int j = 0; j < column; j++){
            ref[i][j] = new Pair(element[i][j]);
         }
      }
      int pivot;
      int step = 1;
      dimCol = 0;
      for(int baseR = 0; baseR < row; baseR++){
         //scale1 for scaling down pivot row
         //scale2 for reducing other row to most 0s
         Pair scale1, scale2;
         pivot = 0;
         scale1 = ref[baseR][0];
         //in case this row all zero
         while(scale1.value() == 0 && ++pivot < column){
            scale1 = ref[baseR][pivot];
         }
         if(scale1.value() == 0) continue;
         dimCol++;
         for(int curR = 0; curR < row; curR++){//for every row
            scale1 = ref[baseR][pivot];
            scale2 = ref[curR][pivot].divide(scale1);//current scale 2
            if(curR == baseR){//scaling down pivot row
               refStep.add(new Pair(scale1));
               for(int curC = 0; curC < column; curC++){//for every column
                  ref[curR][curC] = ref[curR][curC].divide(scale1);
               }//end every column
            }else{
               for(int curC = 0; curC < column; curC++){//for every column
                  ref[curR][curC] = ref[curR][curC].subtract
                     (ref[baseR][curC].multiply(scale2));
               }//end every column
            }
         }//end every operated row
      }
      Matrix temp = new Matrix(ref);
      isRREF = equals(temp);
      return temp;
   }

   public Matrix add(Matrix m){
      if(row != m.row || column != m.column){
         System.out.println("addition not applicable");
         return null;
      }
      Matrix temp = new Matrix(this);
      for(int i = 0; i < row; i++){
         for(int j = 0; j < column; j++){
            temp.element[i][j] = temp.element[i][j].add(m.element[i][j]);
         }
      }
      return temp;
   }

   public Matrix subtract(Matrix m){
      if(row != m.row || column != m.column){
         System.out.println("subtraction not applicable");
         return null;
      }
      Matrix temp = new Matrix(this);
      for(int i = 0; i < row; i++){
         for(int j = 0; j < column; j++){
            temp.element[i][j] = temp.element[i][j].subtract(m.element[i][j]);
         }
      }
      return temp; 
   }

   public Matrix multiply(Matrix b){
      if(column != b.row){
         System.out.println("multiplication not applicable");
         return null;
      }
      Pair[][] newPair = new Pair[row][b.column];
      for(int i = 0; i < b.column; i++){
         for(int j = 0; j < row; j++){
            newPair[j][i] = new Pair();
            for(int k = 0; k < column; k++){
               newPair[j][i] = newPair[j][i].add
                  (element[j][k].multiply(b.element[k][i]));
               newPair[j][i].simplify();
            }
         }
      }
      return new Matrix(newPair);
   }

   public Matrix multiply(Pair p){
      return multiply(new IdentityMatrix(column, p));
   }

   public Matrix transpose(){
      Matrix temp = new Matrix(column, row, true);
      for(int i = 0; i < column; i++){
         for(int j = 0; j < row; j++){
            temp.element[i][j] = new Pair(element[j][i]);
         }
      }
      return temp;
   }

   public int dimCol(){
      if(dimCol < 0) rref();
      return dimCol;
   }

   public int dimNul(){
      return column - dimCol();
   }

   public String toString(){
      String s = "";	
      for(Pair p[] : element){
         for(Pair pp : p){
            s += pp.toString() + " ";
         }
         s += "\n";
      }
      return s;
   }

   public boolean equals(Matrix m){
      if(row != m.row || column != m.column) return false;
      for(int i = 0; i < row; i++){
         for(int j = 0; j < column; j++){
            if(element[i][j] != m.element[i][j]) return false;
         }
      }
      return true;
   }

   public Pair dot(Matrix m){
      Pair dp = new Pair();
      for(int i = 0; i < row; i++){
         dp = dp.add(element[i][0].multiply(m.element[i][0]));
      }
      return dp;
   }

   //projection of this onto columns of m
   public Matrix projection(Matrix m){
      Matrix proj = new Matrix(m.row, 1, true);
      Matrix mCol;
      for(int i = 0; i < m.column; i++){
         mCol = m.colVector(i);
         Pair yu = this.dot(mCol);
         Pair uu = mCol.dot(mCol);
         proj = proj.add(mCol.multiply(yu.divide(uu)));
      }
      return proj;
   }

   public Matrix colVector(int c){
      Matrix cv = new Matrix(row, 1, true);
      int temp = 0;
      while(temp < row){
         cv.element[temp][0] = element[temp++][c];
      }
      return cv;
   }

   //TODO
   public Matrix[] /*or Matrix */ orthBasisColSpace(){
      int num = column;
      Matrix ob = new Matrix(row, dimCol(), true);
      /*
       * for i -> ob.column
       *    for j -> ob.row
       *       subColVector = subColVector
       *
       *    subColVector = subColVector.subtract(sub.multiply(previousSCV));
       *
       * */
      return null;
   }

   public static void main(String[] args) throws IOException{

      Matrix a = new Matrix(1, 1, true);
      Matrix b = new Matrix(1, 1,true);
      for(int i = 0; i < args.length; i++){
         if(args[i].equals("-proj")){
            if(args[i + 1].equals("-a") && args[i + 3].equals("-b")){
               a = new Matrix(args[i + 2]);
               b = new Matrix(args[i + 4]);
            }
         }
      }
      Matrix proj = a.projection(b);
      System.out.println(proj);
      System.out.println("z\n"+a.subtract(proj));
   }

}//end of class
