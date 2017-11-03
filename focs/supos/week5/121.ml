fun power(a,b) = 
    let val total = ref 1
    in 
        while !b > 0 do (total:= !total * !a;
        b := !b - 1);
    !total
    end;
