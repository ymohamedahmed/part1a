exception Collision

datatype 'a tree = Lf 
| Br of 'a * 'a tree * 'a tree

fun insert(a,b)(Lf) = Br((a,b), Lf, Lf)
| insert(a1,b1)(Br((a2,b2), t1, t2)) = 
    if a1 = a2 andalso b1 = b2 then raise Collision
    else if a1 < a2 then insert(a1,b1)(t1)
    else insert(a1,b1)(t2)
