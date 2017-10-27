datatype 'a seq = Nil
| Cons of 'a * (unit -> 'a seq);

fun map (f)(Nil) = Nil
| map (f)(Cons(x, xf)) = Cons( f(x), fn() => map(f)(xf()))

fun tail(Cons(_, xf)) = xf();
