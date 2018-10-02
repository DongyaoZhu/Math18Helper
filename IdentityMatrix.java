import java.util.Scanner;
import java.io.*;
import java.util.ArrayList;

public class IdentityMatrix extends NbyNMatrix{

   public IdentityMatrix(int size){
      super(size, true);
      for(int i = 0; i < size; i++){
         element[i][i] = new Pair(1);
      }
   }

   public IdentityMatrix(int size, Pair times){//times e.g. 2 0, 0 2
      super(size, true);
      for(int i = 0; i < size; i++){
         element[i][i] = new Pair(times);
      }
   }

   public IdentityMatrix(IdentityMatrix m){
      super(m);
   }

   public IdentityMatrix(String input) throws IOException{
      super(input);
   }

}
