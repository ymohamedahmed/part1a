fun med(x,y,z) = 
    if x > y then 
        if x < z then x
        else if z<y then y
        else z
    else if y < z then y
    else if z < x then x
    else z
