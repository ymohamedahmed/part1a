fun power(x,n) =
    let fun helper(x,n,total) =
        if n = 0 then total
        else helper(x, n-1, total*x)
    in helper(x,n,1)
    end;
