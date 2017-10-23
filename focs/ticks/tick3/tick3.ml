(* datatype to store trees *)
datatype 'a tree = Lf
| Br of 'a * 'a tree * 'a tree

(* provided function *)
fun tcons v Lf = Br(v, Lf, Lf)
| tcons v (Br(w, t1,t2)) = Br(v, tcons w t2, t1)

(* function to convert list to functional array, works by working through list and appending value to top of tree and then working downwards *)
fun arrayoflist([]) = Lf 
    | arrayoflist(x::xs) = tcons(x)(arrayoflist(xs))

(* merges two lists into a single list, interweaving the elements *)
fun merge([], []) = []
| merge([], ys) = ys
| merge(xs, []) = xs
| merge(x::xs, y::ys) = x::y::merge(xs,ys)

(* merges the list of the two subtrees and appends the root node *)
fun listofarray(Lf) = []
| listofarray(Br(x, t1, t2)) = x::merge(listofarray(t1), listofarray(t2))

(* computes n^k *)
fun pow(_,0) = 1
| pow(n, k) = n * pow(n,k-1)

(* k represents the index of the node, d is depth of the tree traversed *)
fun getSubsOfEvens(t)  = 
    (* base case, leaf node so return empty list *)
    let fun helper(Lf, _, _)= []
    | helper(Br(x, t1, t2), k, d) =
        (* apply function to left subtree and append right subtree *)
        (* add current index if it is even *)
        (* compute new index using power function *)
        if x mod 2 = 0 then k :: helper(t1,k + pow(2,d),d+1)  @ helper(t2, k + pow(2, d+1), d+1)
        else helper(t1,k + pow(2,d),d+1 )  @ helper(t2, k + pow(2, d+1) , d+1)
    in helper(t, 1, 0)
    end;

