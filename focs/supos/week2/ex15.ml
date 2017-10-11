(* function to add the value x, n times *)
fun add(x:real,1) = x
    | add(x,n) = x + add(x,n-1)
