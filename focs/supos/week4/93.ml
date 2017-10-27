datatype 'a seq = Nil
| Cons of 'a * (unit -> 'a seq);

fun head(Cons(x, _)) = x

fun tail (Cons(_, xf)) = xf()

fun cons (a)(cs) = a::cs 

fun change (till, 0)    = []
| change ([], amt)    = []
| change (c::till, amt) =
    if amt<c
    then change(till, amt)
    else cons(c)(change(c::till, amt-c))

exception Change;
fun change(till, 0) = []
| change([], amt) = raise Change;

fun ch (c::till, amt) = Cons(change(c::till ,amt) , fn() => ch(c::till, amt-c)) 
