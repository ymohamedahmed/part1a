(* where a and b are the comparison and equality operators *)
fun lex(a,b)(x1,y1)(x2,y2) = a(x1,x2) orelse (b(x1,x2) andalso a(y1, y2))
