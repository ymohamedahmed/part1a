fun exch(xr, yr) = (xr := !yr; yr := !xr)

(*fun test() =
    let val a = ref 5
    val b = ref 0
    in while (!a>0; !a < 6) do (b:= !b+1);
        !b
    end;*)
