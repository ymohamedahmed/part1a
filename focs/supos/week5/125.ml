(* create row in the identity matrix where i represents the index of the row *)
(* set appropriate index to 1 *)
fun row n i = 
    let val row = Array.tabulate(n, (fn x => 0))
    in (Array.update(row, i, 1));
    row
    end;

(* repeatedly apply function row for the entire table *)
fun id n = Array.tabulate(n, row n)
