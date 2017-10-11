exception YearOutOfRange
(* convert from the digits form to the calendar year *)
fun digitsToYear(a) = if a >= 50 then 1900 + a else 2000 + a

(* converts from the calendar year to the digits form *)
fun yearToDigits(a) =
    if a >= 2000 andalso a <= 2049 then a - 2000 
    else if a < 2000 andalso a >= 1950 then a - 1900
    else raise YearOutOfRange


(* return the difference between two years stored in the two digit form *)
fun compareYears(a,b) = abs(digitsToYear(a) - digitsToYear(b))

(* return the absolute value *)
fun abs(x) = if x < 0 then ~1 * x else x

(* takes a year in the digit form and adds a number of years to it
   raises an exception if the answer is out of the range
 *)

fun addYears(year, x) = yearToDigits(x + digitsToYear(year))
