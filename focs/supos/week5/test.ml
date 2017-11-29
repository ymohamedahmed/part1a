datatype 'a seq = Nil | Cons of 'a * (unit -> 'a seq);
datatype 'a lazyt = Lf | Br of 'a * (unit -> 'a lazyt) * (unit -> 'a lazyt);
fun from k = Br(k, fn() => from(k@[0]), fn() => from(k@[1]));
fun interleave (Cons(x,xf),y) = Cons(x, fn() => interleave(y, xf()));
fun interleaveTree (Br(k,x,y)) = Cons(k, fn() => interleave(interleaveTree(x()), interleaveTree(y())))
fun tail (Cons(_,xf)) = xf()
fun left(Br(_,l, _)) = l()
val list = interleaveTree(from[])
