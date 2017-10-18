(* board : first int is column and second int is row counting from zero starting in bottom left corner *)
(* makerow returns a list containing all the coordinates in a row *)
fun makerow(row, 0) = [(row,0)]
| makerow(row, col) = makerow(row, col-1) @  [(row,col)]

(* makeboard returns a list containing the coordinates of all the squares on the board*)
fun makeboard(x) = 
    let fun helper(0, size) = makerow(0, size)
    | helper(row, size) = helper(row-1,size) @ makerow(row, size)
    in helper(x,x)
    end;

(* function to test if a coordinate is on the board or not *)
fun onboard(a,b)(size) = a > 0 andalso a < size andalso b > 0 andalso b < size

(* having generated all the moves, it checks to see which ones are legal or not *)
fun refine([],_) = []
| refine(x::xs, size) = if onboard(x)(size) then x::refine(xs, size) else refine(xs, size)

(* returns the length of a list *)
fun len([]) = 0 | len(x::xs) = 1 + len(xs)

(* generates all the knight moves using the coordinate system *)
(* floor(Math.sqrt(real(len(x::xs)))) although complicated simply returns the size of the board as an integer value, used so that the problem can be generalised to any size board *)
fun generateMoves(col,row)(x::xs) = refine([(col+1,row+2), (col+1, row-2), (col-1, row+2), (col-1, row-2), (col+2, row+1), (col+2, row-1), (col-2, row+1), (col-2, row-1)], floor(Math.sqrt(real(len(x::xs))))) 
