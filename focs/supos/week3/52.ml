(* selection sort:
   works by getting min item and then sorting the rest of the list  *)
fun sort(x::[]) = [x]
| sort (x::xs) = fst(select(x::xs)) :: sort(drop(x::xs, snd(select(x::xs))))
(* returns min value and index of the min element *)
fun select(x::xs) = 
    let fun helper([], min, _, index, _) = (min,index )
    | helper(x::xs, min, first, index, current) = if x < min orelse first then helper(xs, x, false, current, current+1) else helper(xs, min, first, index, current+1)
    in helper(x::xs, 0, true, 0, 0)
    end;
(* remove an element in a list at a particular index *)
fun drop(x::xs, 0) = xs
| drop (x::xs, index) = x :: drop(xs , index-1)

(* return first element in tuple *)
fun fst(a,b) = a

(* return second element in tuple *)
fun snd(a,b) = b
