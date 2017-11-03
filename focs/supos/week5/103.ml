datatype 'a queue = Q of 'a list * 'a list
datatype 'a tree = Lf | Br of 'a * 'a tree * 'a tree
fun norm(Q([],tls)) = Q(rev tls, [])
| norm q          = q
fun qnull(Q([],[])) = true  |  qnull _ = false
fun enq(Q(hds,tls), x)  = norm(Q(hds, x::tls))
fun deq(Q(x::hds, tls)) = norm(Q(hds, tls))
fun qhd(Q(x::_,_)) = x
fun breadth q = 
    if qnull q then []
    else 
        let fun helper(Lf) = breadth(deq q)
        | helper(Br(v,t,u)) = v::breadth(enq(enq(deq q, t),u))
        in helper(qhd q)
        end;
