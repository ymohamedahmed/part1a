fun map2 (f,x::xs) = f (f (x)) :: map2(f,xs)
| map2 (_,[]) = []
