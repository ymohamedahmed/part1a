(*
Design: 
    -Rationals are stored as a two-tuple of the form (a,b) where a is the numerator and b is the denominator
    -To complete the problem only two base algorithms are needed addition and multiplication. Since both subtraction and division can be written in terms of these two
    -There also has to be a function to reduce a rational number to its canonical form
    -This will work by dividing the numerator and denominator by the greatest common divisor of the two
    -The GCD is calculated using Euclid's algorithm
*)
exception InvalidFraction

(*Euclid' algorithm for the greatest common divisor*)
fun gcd(a,b) = if b = 0 then a else gcd(b, a mod b)

(*Returns the reduced version of a fraction as a tuple only need to check for invalid fractions here since all operators on fractions pass through here*)
fun canonicalise(a,b) = 
    if b = 0 then raise InvalidFraction
    else (a div gcd(a,b), b div gcd(a,b))

(*Adds two fractions and then reduces the result*)
fun add (a1,b1) (a2,b2) = canonicalise((a1*b2) + (a2*b1), b1*b2)

(*Subtracts two fractions using the addition algorithm*)
fun subtract (a1,b1) (a2,b2) = add (a1,b1) (~a2,b2)

(*Multiply two fractions and then reduce the result*)
fun multiply (a1,b1) (a2,b2) = canonicalise(a1*a2, b1*b2)

(*Divide two fractions using multiplication and the reciprocal of the second argument*)
fun divide (a1,b1) (a2,b2) = multiply (a1,b1) (b2,a2)

(*Test cases*)
val x = canonicalise(1,2)
val y = canonicalise(2,4)

