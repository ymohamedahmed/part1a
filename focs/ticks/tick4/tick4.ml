(* lazy list datatype *)
datatype 'a stream = Cons of 'a * (unit -> 'a stream)

(* helper function stores the original function and then reapplies it to f^n (x) *)
fun nfold f n = 
     let fun helper _ (f:('a -> 'a)) 0 = (fn x:'a => x)
     | helper _ f2 1 = f2
     | helper f1 f2 n = helper (f1) (fn x => f1 (f2 x)) (n-1)
     in helper f f n
     end;

(* applies f(x) = x+1 n times to give f^n(x) = x+n then subs m in *)
fun sum m n = nfold(fn x => x+1)(n) m 

(* applies f(x) = x + m n times to give f^n(x) = x + mn then subs x = 0 *)
fun product m n = nfold(fn x:int => x + m)(n)(0) 

(* applies f(x) = mx n times to give f^n(x) = m^n * x then subs x = 1 *)
fun power m n = nfold(fn x:int => x*m)(n)(1)    

(* keeps computing next element until nth element is reached *)
fun nth(Cons(a, _), 1) = a 
| nth(s as Cons(_,b), n) =  nth(b(), n-1)

(* define square function and then declare sequence *)
fun square (k:int) = Cons(k*k, fn() => square(k+1))
val squares = square 1

(* applies function to the heads of both streams and updates the anon function *)
fun map2 (f)(Cons(a,b))(Cons(c,d)) = Cons(f(a)(c), fn() => map2 f(b())(d()))
fun tail (Cons(_,f)) = f()
