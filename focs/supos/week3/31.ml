fun sumr(x::[]) = x
| sumr(x::xs) x + sumr(xs)

fun sumi(x::[], total) = x+total
| sumi(x::xs, total) = sumi(xs, x+total)
