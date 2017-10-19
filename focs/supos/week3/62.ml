datatype 'a tree = Lf 
| Br of 'a * 'a tree * 'a tree

fun sum(Lf) = 0
| sum(Br(k, t1, t2)) = k + sum(t1) + sum(t2)
