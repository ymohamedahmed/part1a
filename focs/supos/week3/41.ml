fun contains(x, []) = false
| contains(x, y::ys) = if x = y then true else contains(x, ys)

fun union([], ys) = ys
| union (x::xs, ys) = if not(contains(x, ys))  then union(xs, ys @ [x]) else union(xs, ys) 
