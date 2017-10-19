exception SingleVariable of string
exception DoubleVariable of string * string
(* data type for value i.e. E in the question *)
(* r represents real, while v represents string *)
datatype value = r of real
| v of string

(* data type for expressions *)
datatype expressions = Neg of value
| Add of value * value 
| Mul of value * value

(* function to evaluate expressions 
   deals with all the possible permutations of errors cause by variables 
 *)
fun eval(Add(r(x), r(y))) = x + y 
| eval(Neg(r(x))) = ~x
| eval(Mul(r(x),r(y))) = x * y
| eval(Neg(v(x))) = raise SingleVariable(x)
| eval(Add(r(x), v(y))) = raise SingleVariable(y)
| eval(Add(v(x), r(y))) = raise SingleVariable(x)
| eval(Add(v(x), v(y))) = raise DoubleVariable(x,y)
| eval(Mul(r(x), v(y))) = raise SingleVariable(y)
| eval(Mul(v(x), r(y))) = raise SingleVariable(x)
| eval(Mul(v(x), v(y))) = raise DoubleVariable(x,y)
