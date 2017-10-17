fun splitIntList(xs) = 
    let fun helper([], l, r) = (l,r) 
        | helper(x::xs, l, r) = if x >=0 then helper(xs, l@[x], r) else helper(xs, l, r@[x])
    in helper(xs, [],[])
    end;
        
