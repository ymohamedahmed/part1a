package uk.ac.cam.ym346.prejava.ex2;
public class PackedLong {
            
   /*
    * Un<Plug>yankstack_after_pastepack and return the nth bit from the packed number at index position;
    * position counts from zero (representing the least significant bit)
    * up to 63 (representing the most significant bit).
    */
   public static boolean get(long packed, int position) {
   // set "check" to equal 1 if the "position" bit in "packed" is set to 1
   // you should use bitwise operators (not % or similar)
   long check = (packed >>> position) & 1;//TODO: complete this statement

   return (check == 1);
   }

   /*
    * Set the nth bit in the packed number to the value given
    * and return the new packed number
    */
   public static long set(long packed, int position, boolean value) {
      if (value) {
         // TODO: complete this using bitwise operators
         // update the value "packed" with the bit at "position" set to 1
         packed |= (1L << position); 
      }
      else {
         // TODO: complete this using bitwise operators
         // update the value "packed" with the bit a "position" set to 0 
         packed &= ~(1L << position);
      }
      return packed;
   }
}
