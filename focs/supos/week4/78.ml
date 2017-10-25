datatype 'a tree = Lf
| Br of 'a * 'a tree * 'a tree

(* Moves the left most nodes up one level, repeatedly *)
fun upNode(Lf) = Lf
| upNode(Br(x, Lf, Lf)) = Lf
| upNode(Br(x, t as Br(y, _,_), t2)) = Br(y, upNode(t), t2)

(* Conducts the necessary inversion, effectively decrementing the indices *)
fun flip(Lf) = Lf
| flip(Br(x, l, r)) = Br(x, r, flip(l))

(* Combines both methods to remove the first element *)
fun delFirst(t) = flip(upNode(t))
