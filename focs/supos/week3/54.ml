fun sorted([]) = true
| sorted([x]) = true
| sorted(x::xs) = if x < hd(xs) then sorted(xs) else false

fun bubble([]) = []
| bubble([y]) = [y]
| bubble(y::z::ys) = if y > z then bubble(z::y::ys) else y :: bubble(z::ys)

fun sort(xs) = if sorted(xs) then xs else sort(bubble(xs))
