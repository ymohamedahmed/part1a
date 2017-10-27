datatype arc = A of int * int
datatype 'a queue = Q of 'a list * 'a list

fun norm(Q([], tls)) = Q(rev tls, [])
| norm q = q

fun qnull (Q([],[])) = true | qnull _ = false

fun enq(Q(hds,tls), x) = norm(Q(hds, x::tls))

fun deq(Q(x::hds, tls)) = norm(Q(hds, tls))

