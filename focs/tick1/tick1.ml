fun evalquad (a,b,c,x : real) = (a * x * x) + (b*x) + c;
fun facr(n) = if n = 0
            then 1
            else if n < 0
            then 0 
            else n * facr(n-1)
fun faci(n) = 
    let fun helper(n, total) =
        if n = 1
        then total
        else if n < 0
        then 0
        else helper(n-1, total*n)
    in
        helper(n,1)
    end;
fun sumt(n) = 
    let fun helper(x:real,n:int) =
        if n = 1
        then x
        else x + helper(x/2.0, n-1)
    in
        helper(1.0, n)
    end;   
