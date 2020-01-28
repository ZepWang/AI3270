/*I certify that I wrote the code I am submitting. I did not copy whole or parts of it from 
another student or have another person write the code for me. Any code I am reusing in my 
program is clearly marked as such with its source clearly identified in comme
package sortingredo2*/

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This program will give the Maximun sum contiguous subverctor (MSCS) of given array. 0 if all negative.
 * 
 * @author 
 * 
 *
 */
public class Al3270MSCS {
	
	// "algorithm-1,algorithm-2,algorithm-3,algorithm-4,T1(n)=n^3,T2(n)=n^2,T3(n)=n^2, T4(n)=n"
   public static void main(String[] args) throws FileNotFoundException  {
   
   	/*
   	 *  1, read from phw_input.txt (10 ',' saparated data)
   	 *	2, run al1 - al4 for this array
   	 *	3, create 19 arrays length from (10, 15 ... 100)
   	 *	4, use the 19 arrays to execute the 4 algorithm and take the record of time
   	 *  5, create the 19 x 8 2 d array, 
   	 *  	(1, 4 to hold the average time, 5, 8 hold the ceiling of 1->4 )
   	 */
   	
      String filePath = new String("phw_input.txt");
   	
   	// 1, 2
      step1(filePath);
   	
   	// 3
      int[][] as = new int[19][];
      randomization(as);
   	
      System.out.println(" ---------------- Unit is : 10-6s ---------------- ");
   	// 4
      final int TIMES = 10000; 
      int[][] results = new int[19][8];
      timing(as, TIMES, results);
   	
      for(int i=0; i<results.length; i++) {
         for(int j=0; j<results[i].length; j++) {
            System.out.print(results[i][j] + "   ");
         }
         System.out.println();
      }
   	
   
   	
   }
	
	/**
	 * Timing the data and store the resulte in [1,4] fields. [5, 6] fields
	 * @param TIMES
	 */
   public static void timing(int[][] data, int TIMES, int [][] results) {
   	
      for(int i=0; i<data.length; i++) { // 19
         int avgTime = 0;
         for(int j=1; j<=4; j++) { // timing [al1, al4] separately
            avgTime = timing_aux(j, data[i], TIMES); /// ***********
            results[i][j-1] = avgTime;
         }
         for(int j=5; j<=8; j++) {
            results[i][j-1] = results[i][j-5];
         }
      //			System.out.println();
      }
   	
   }
	
	/**
	 * This method will calculate the average time calling TIMES of specified function
	 * @param methodSpecifier specifier which function to call [1,4] -> [al1,al4]
	 * @param data linear data to call
	 * @param TIMES times to run
	 */
   public static int timing_aux(int methodSpecifier, int[] data, int TIMES) {
      long t1 = System.currentTimeMillis();
   	
      for(int i=0; i<TIMES; i++) {
         switch(methodSpecifier) {
            case 1: 
               al1(data);
               break;
            case 2: 
               al2(data);
               break;
            case 3: 
               al3(data, 0, data.length-1);
               break;
            case 4: 
               al4(data);
               break;
         }
      }
      long t2 = System.currentTimeMillis();
   	
   //		System.out.println("time is : " + (t2-t1));
   	
      int result = (int)(t2-t1);
   	
      return (int)(t2-t1)*1000000  / (TIMES);
   }
	
	
	/**
	 * This will make the data in as random from [-5,5]
	 * @param as the 2D array we need to randomized
	 */
   public static void randomization(int [][] as) {
   	
      for(int i=0; i<as.length; i++) {
         int currentLength = 10 + 5 * i;
         as[i] = new int[currentLength];
      	
         for(int j=0; j<currentLength; j++) {
            as[i][j] = (int)(Math.random() * 11) - 5; // [0, 1) -> [0, 10] -> [-5, 5]
         }
      	
      }
   }
	
	
	/**
	 * Enter the path for the file name to run the 
	 * @param filePath the file name to get the 10 ',' sarated data 
	 */
   public static void step1(String filePath) {
   	
      File f = new File(filePath);
      int[] data = new int[10];
      Scanner fileInput;
      try {
         fileInput = new Scanner(f);
      
      	// Regex construct
         Pattern p = Pattern.compile("(-?[0-9]+),?"); // extract data by using the regex
         Matcher m = null;
      	
      	
         int size = 0;
      	
         while(fileInput.hasNext()) {
            String next = fileInput.next();
         	
            m = p.matcher(next);
            if(m.find()) { 
               data[size++] = Integer.parseInt(m.group(1));
            }
         }
      
      } catch (FileNotFoundException e) {
         e.printStackTrace();
      }
   	// a1 - a4
   //		algorithm-1: <answer>; algorithm-2:<answer>; algorithm-3:<answer>; algorithm-4:<answer>
   	
      System.out.println("algorithm-1 : " + al1(data));
      System.out.println("algorithm-2 : " + al2(data));
      System.out.println("algorithm-3 : " + al3(data, 0, data.length-1));
      System.out.println("algorithm-4 : " + al4(data));
   }
	
	
	
	
   public static int al1(int[] a) {
   	
      int maxSoFar = 0; // zero can be the default value
   	
      for(int i=0; i<1; i++) {
         for(int j=i; j<a.length; j++) {
            int sum = 0;
            for(int k=j; k<a.length; k++) {
               sum += a[k];
               maxSoFar = max(maxSoFar, sum);
            }
         }
      }
      return maxSoFar;
   }
	


   public static int al2(int[] a) {
      int maxSoFar = 0;
   	
      for(int i=0; i<a.length; i++) {
         int sum = 0;
         for(int j=i; j<a.length; j++) {
            sum += a[j];
            maxSoFar = max(maxSoFar, sum);
         }
      }
      return maxSoFar;
   }
	
	
	
	
   public static int al3(int[] a, int l, int u) {
   	
      if(l > u) {
         return 0;
      }
      if(l == u) {
         return max(0, a[l]);
      }
      int m = (l+u) / 2;
   	
      int sum = 0, maxToLeft = 0;
      for(int i=m; i>=l; i--) {
         sum +=a[i];
         maxToLeft = max(maxToLeft, sum);
      }
   	
      sum = 0;
      int maxToRight = 0;
      for(int i=m+1; i<=u; i++) {
         sum += a[i];
         maxToRight = max(maxToRight, sum);
      }
   	
      int maxCrossing = maxToLeft + maxToRight;
   	
      int maxInA = al3(a, l, m);
      int maxInB = al3(a, m+1, u);
      return max(maxCrossing, maxInA, maxInB);
   }
	
	
	
   public static int al4(int[] a) {
      int maxSoFar = 0;
      int maxEndingHere = 0;
      for(int i=0; i<a.length; i++) {
         maxEndingHere = max(0, maxEndingHere + a[i]);
         maxSoFar = max(maxSoFar, maxEndingHere);
      }
      return maxSoFar;
   }
	
	
   public static int max(int a, int b) {
      return (a > b ? a : b);
   }
	
   public static int max(int a, int b, int c) {
   	// get the maximum value from 3 values
      return ((a > b ? a : b) > c ? (a > b ? a : b) : c);
   }
	
}
