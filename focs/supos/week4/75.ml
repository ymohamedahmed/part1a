exception InvalidDelete
datatype 'a tree = Lf 
| Br of 'a * 'a tree * 'a tree

(* find the minimum element in a tree *)
fun min(Br(x, Lf, Lf)) = x
| min(Br(x, t1, t2)) = min(t1)

(* first case deleting a node with no children *)
fun delete(x1, Br(x2, Lf, Lf)) = if x1 = x2 then Lf else raise InvalidDelete
(* second case deleting a node with one child *)
(* consider both subcases i.e. left and right subtrees *)
| delete(x1, Br(x2, t1 as Br(x3, t2, t3),  Lf)) = 
    if x1 < x2 then Br(x2,delete(x1,t1), Lf) 
    else if x1 > x2 then raise InvalidDelete
    else Br(x3, t2, t3)
| delete(x1, Br(x2, Lf, t1 as Br(x3,t2,t3))) = 
    if x1 > x2 then Br(x2, Lf, delete(x1, t1))
    else if x1 < x2 then raise InvalidDelete
    else Br(x3, t2, t3)
(* consider case with two children *)
| delete(x1, Br(x2, t1, t2))=
    if x1 < x2 then Br(x2, delete(x1, t1), t2)
    else if x1 > x2 then Br(x2, t1, delete(x1, t2))
    else Br(min(t2), t1, delete(min(t2), t2))
                            
