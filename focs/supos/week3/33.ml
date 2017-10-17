fun evenElements(x::xs) =
    let fun helper([], _) = []
        | helper(x::xs, k) = if k mod 2 = 0 then [x] @ helper(xs, k+1) else helper(xs, k+1)
    in helper(x::xs, 1)
    end;
