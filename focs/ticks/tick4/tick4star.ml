(* lazy list datatype *)
datatype 'a stream = Cons of 'a * (unit -> 'a stream)
(* merge two sequences *)
fun merge(Cons(x, xs), Cons(y, ys)) = if x < y then Cons(x, fn() => merge(xs(), Cons(y, ys)))
                                else if y < x then Cons(y, fn() => merge(Cons(x,xs), ys()))
                                else Cons(x, fn() => merge(xs(), ys())) 
fun tail (Cons(_, f):'a stream) = f()

(* curried function returning the product of a and b *)
fun mul a b = a * b

(* simple map function for lazy lists *)
fun mapS (f)(Cons(a,b)) = Cons(f(a), fn() => mapS(f)(b()))

(* works by merging the sequence of the nth value multiplied by 3 and the (n+1)th value multiplied by 2 *)
fun pow() = Cons(1,  fn() =>
         Cons(2, fn() => merge(mapS(mul(2))((tail(pow()))), mapS(mul(3))((pow())))))
val pows23 = pow()

(* function that returns a sequence of the multiples of a starting at the kth value *)
fun multiple a k = Cons(a*k, fn() => multiple(a)(k+1))

(* function used to filter the values of a lazy list *)
fun filter (p)(Cons(x, xs)) = if p(x) then Cons(x, fn() => filter(p)(xs())) else filter(p)(xs())

(* test if a is a power of b i.e. a = b ^ k for some integer k *)
fun isPow(0, _) = false
| isPow (1, _) = true
| isPow(a,b) = a mod b = 0 andalso isPow(a div b, b)

(* function to perform repeated division *)
fun repDiv(a,b) = if a mod b = 0 then repDiv(a div b, b) else a

(* test if a value is contained in an increasing sequence *)
fun con(Cons(x,xs), a) = 
    if x = a then true
    else if x > a then false
    else con(xs(), a)
    
(* works by merging the multiples of 5 with pows23 *)
val pows235 = merge(pows23,
    (* test if multiple of 5 can be represented in the 235 form *)
    (* it has to either be a multiple of 2 or 5, or it is a power of 5 *)
    filter(fn x => con(pows23, repDiv(x, 5)) orelse isPow(x,5))
    (multiple(5)(1)))
