(* function that tests if knight move is valid *)
fun validmove(a1,b1)(a2,b2) = (abs(a1-a2) = 1 andalso abs(b1-b2) = 2) orelse (abs(a1-a2) = 2 andalso abs(b1-b2) = 1)
(* filter function *)
fun filter p [] = []
| filter p (x::xs) =
    if p x then x::filter p xs
    else filter p xs;

(* board : first int is column and second int is row counting from zero starting in bottom left corner *)
(* makerow returns a list containing all the coordinates in a row *)
(* Time: O(n), Space: O(n) *)
fun makerow(row, 0) = [(row,0)]
| makerow(row, col) = makerow(row, col-1) @  [(row,col)]

(* makeboard returns a list containing the coordinates of all the squares on the board *)
(* Time: O(n^2), Space: O(n^2) *)
fun makeboard(x) = 
    let fun helper(0, size) = makerow(0, size)
    | helper(row, size) = helper(row-1,size) @ makerow(row, size)
    in helper(x,x)
    end;

(* function to test if a coordinate is on the board or not *)
(* Time: O(1), Space: O(1) *)
fun onboard(a,b)(size) = a > 0 andalso a < size andalso b > 0 andalso b < size

(* returns the length of a list *)
(* Time: O(n), Space: O(n) *)
fun len([]) = 0 | len(x::xs) = 1 + len(xs)

(* generates all the knight moves using the coordinate system *)
(* floor(Math.sqrt(real(len(x::xs)))) although complicated simply returns the size of the board as an integer value, used so that the problem can be generalised to any size board *)
(* Time: O(n) , Space: O(1) *)
(* generate moves using filter *)
fun genMoves(col,row)(board) = filter (validmove(col,row)) board 

(* test cases *)
val test1 = makerow(0,0) 
val test2 = makerow(1,0)
val test3 = makeboard(8)
val test4 = onboard(~1,~1)(8)
val test5 = onboard(1,1)(8)
val test6 = ([(~1,~1), (1,1)],8)
val test7 = ([],8)
val test8 = len([])
val test9 = len([1])
val test10 = genMoves(0,0)(makeboard(8))
