datatype 'a seq = Nil
| Cons of 'a * (unit -> 'a seq);

fun cons (a)(cs) = a::cs 

fun change (till, 0)    = [[]]
| change ([], amt)    = []
| change (c::till, amt) =
    if amt<c
    then change(till, amt)
    else map (cons(c))(change(c::till, amt-c)) @ change(till, amt)
