(* create row in the identity matrix where i represents the index of the row *)
(* set appropriate index to 1 *)
fun row n i = 
    let val row = Array.tabulate(n, (fn x => 0))
    in (Array.update(row, i, 1));
    row
    end;

(* repeatedly apply function row for the entire table *)
fun id n = Array.tabulate(n, row n)

(* transpose a matrix *)
fun trans(ar, m, n) = 
    let val result = Array.tabulate(m, (fn x => Array.tabulate(n, fn x => 0)))
    val row = Array.tabulate(n, fn x => 0)
    val x = ref 0
    val y = ref 0
    in while (!x < m; !y < n) do(
        (Array.update(row, !y, Array.sub(Array.sub(ar, !x), !y)); y:=!y+1;x:=!x+1));
    row
    end;
