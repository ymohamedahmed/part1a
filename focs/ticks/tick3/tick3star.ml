exception NoMin

datatype 'a tree = Lf
| Br of 'a * 'a tree * 'a tree

fun insert(a:string,Lf) = Br(a, Lf, Lf)
| insert(a, Br(v, t1, t2)) = 
    if a < v then Br(v, insert(a, t1), t2)
    else if a > v then Br(v, t1, insert(a,t2))
    else Br(v, t1, t2)

fun member(a, Lf) = false
| member(a, Br(v, t1, t2)) = 
    if a < v then member(a, t1)
    else if a > v then member(a, t2)
    else true

(* find the minimum element in a tree *)
fun min(Lf) = raise NoMin
| min(Br(x, Lf, Lf)) = x
| min(Br(x, t1, t2)) = min(t1)

(* first case deleting a node with no children *)
fun remove(x1, Lf) = Lf 
| remove(x1, t as Br(x2, Lf, Lf)) = if x1 = x2 then Lf else t
(* second case deleting a node with one child *)
(* consider both subcases i.e. left and right subtrees *)
| remove(x1, Br(x2, t1 as Br(x3, t2, t3),  Lf)) = 
    if x1 < x2 then Br(x2,remove(x1,t1), Lf) 
    else if x1 > x2 then t1 
    else Br(x3, t2, t3)
| remove(x1, Br(x2, Lf, t1 as Br(x3,t2,t3))) = 
    if x1 > x2 then Br(x2, Lf, remove(x1, t1))
    else if x1 < x2 then t1 
    else Br(x3, t2, t3)
(* consider case with two children *)
| remove(x1, Br(x2, t1, t2))=
    if x1 < x2 then Br(x2, remove(x1, t1), t2)
    else if x1 > x2 then Br(x2, t1, remove(x1, t2))
    else Br(min(t2), t1, remove(min(t2), t2))

fun union(Lf, Lf) = Lf
| union(Lf, t) = t
| union(t1 as Br(x, _, _), t2) = union(remove(x, t1), insert(x, t2))

fun inter(Lf, Lf) = Lf 
| inter(Lf, t) = Lf
| inter(t1 as Br(x, _, _), t2) = 
    if member(x, t2) then insert(x, inter(remove(x,t1), t2))
    else inter(remove(x, t1), t2)
