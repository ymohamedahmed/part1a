fun member(x, []) = false
| member(x, y::ys) = x = y orelse (x > y andalso member(x,ys))

fun subset([], ys) = true
| subset(x::xs, ys) = member(x,ys) andalso subset(xs, ys)

fun union([], ys) = ys 
| union(xs, []) = xs
| union(x::xs, y::ys) = 
    if x < y then x :: union(xs, y::ys)
    else if x > y then y :: union(x::xs, ys)
    else x::union(xs, ys)

fun inter([], ys) = []
| inter(xs, []) = []
| inter(x::xs, y::ys) =
    if x < y then inter(xs,y::ys)
    else if x > y then inter(x::xs, ys)
    else x::inter(xs, ys)
