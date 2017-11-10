package uk.ac.cam.ym346.prejava.ex4;

public class FibonacciCache {

   // Uninitialised array
   public static long[] fib = null;

   public static void store() throws Exception {
      //TODO: throw an Exception with a suitable message if fib is null
      if(fib==null){
          throw new Exception("Fibonacci array is null");
      }
      //TODO: using a for loop, fill "fib" with the Fibonacci numbers 
      //      e.g. if length of "fib" is zero, store nothing; and
      //           if length is six, store 1,1,2,3,5,8 in "fib"
      for(int i = 0; i < fib.length; i++){
          if(i < 2){
              fib[i] = 1;
          }else{
              fib[i] = fib[i-1] + fib[i-2];
         }
      }
   }

   public static void reset(int cachesize) {
      //TODO: make fib point to a new array of length cachesize
      //TODO: using a for loop, set all the elements of fib to zero
        fib = new long[cachesize];
        for(int i = 0; i < fib.length; i++){
            fib[i] = 0;
        }
   }
 
   public static long get(int i) /*TODO: declare the exception*/ throws Exception {
      //TODO: throw an Exception with a suitable message if fib is null
      if(fib == null){
          throw new Exception("Fibonacci array is null");
      }
      //TODO: return the value of the element in fib found at index i
      //      e.g. "get(3)" should return the fourth element of fib
      if(i > fib.length - 1){
        throw new Exception("Index is outside of range");
      }
      return fib[i];
      //TODO: your code should throw an Exception with a suitable message
      // if the value requested is out of bounds of the array
   }

   public static void main(String[] args) throws Exception{
      //TODO: catch exceptions as appropriate and print error messages
      try{
      reset(20);
      store();
      int i = Integer.decode(args[0]);
      System.out.println(get(i));
      }catch(Exception e){
          e.printStackTrace();
      }
   }
  }
