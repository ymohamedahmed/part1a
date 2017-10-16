fun allcons(k, x::xs) = [[k]@x] @ allcons(k, xs)
| allcons(k,[]) = []

fun concat(xs, []) = xs
| concat(xs, y::ys) = concat(xs@[y], ys)

fun choose(1, x::xs) =[[x]] @ choose(1,xs)
| choose(_, []) = []
| choose(k, x::xs) = concat(allcons(x, choose(k-1,xs)),choose(k, xs))
