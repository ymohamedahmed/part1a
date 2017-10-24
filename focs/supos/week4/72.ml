exception Collision

datatype 'a tree = Lf 
| Br of 'a * 'a tree * 'a tree

fun insert(a)(Lf) = Br(a, Lf, Lf)
| insert(a1)(Br(a2, t1, t2)) = 
    if a1 = a2 then raise Collision
    else if a1 < a2 then insert(a1)(t1)
    else insert(a1)(t2)
