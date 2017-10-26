datatype 'a option = NONE | SOME of 'a;
fun map (f, NONE) = NONE
| map(f, SOME(a)) = SOME(f(a))
