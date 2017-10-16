fun last (x :: nil) = x
| last (x :: xs) = last(xs)

fun butlast(x::xs) =
    let fun helper(nil,y)=y 
    | helper(x::[],y) = y 
    | helper(x::xs, y) = helper(xs, y @ [x])
    in helper(x::xs, [])
    end;

fun nth(x::xs,0) = x
|nth(x::xs, n) = nth(xs, n-1)
