type color = int * int * int
type xy = int * int
datatype image = Image of xy * color array array;

fun image (x as (w,h):xy) (c:color) =Image(x, 
    Array.tabulate(h, (fn i => Array.tabulate(w, fn i => c))))

fun size (Image(x,_))  = x

fun drawPixel (Image(_,c)) color ((x,y):xy) = Array.update(Array.sub(c,y), x, color)

fun format4 i = StringCvt.padLeft #" " 4 (Int.toString i);

fun colourToString ((r,g,b):color) = format4 r ^ format4 g ^ format4 b

fun rowColourString (a,0, w) = colourToString(Array.sub(a,w-1))
    | rowColourString (a,i, w) = colourToString(Array.sub(a,w-i-1)) ^ rowColourString(a,i-1,w)
    
fun twoDArrayToString(a, 0, h, w) = rowColourString(Array.sub(a,h-1), w-1, w)
| twoDArrayToString (a, i, h, w) = rowColourString(Array.sub(a, h-i-1), w-1, w) ^ "\n" ^ twoDArrayToString(a,i-1,h, w)

fun toPPM (image as Image(x,c)) filename =
  let val oc = TextIO.openOut filename
      val (w,h) = size image
  in
     TextIO.output(oc,"P3\n" ^ Int.toString w ^ " " ^ Int.toString h ^ "\n255\n" ^ twoDArrayToString(c,h-1,h,w) ^ "\n");
     (* code to output image rows, one per line goes here *)
     TextIO.closeOut oc
end;

fun drawHoriz i col (xy:xy) d =
    let fun helper i col pos 0 _ = ()
         | helper i col (pos as (x,y):xy) d unit = helper(i)(col)(x+1,y)(d-1)(drawPixel i col pos)
    in helper i col xy d ()
    end;

fun drawVert i col (xy:xy) d =
    let fun helper i col pos 0 _ = ()
         | helper i col (pos as (x,y):xy) d unit = helper(i)(col)(x,y+1)(d-1)(drawPixel i col pos)
    in helper i col xy d ()
    end;


fun drawDiag i col (xy:xy) d =
    let fun helper i col pos 0 _ = ()
         | helper i col (pos as (x,y):xy) d unit = helper(i)(col)(x+1,y+1)(d-1)(drawPixel i col pos)
    in helper i col xy d ()
    end;

fun drawLine i col ((x0,y0):xy) ((x1,y1):xy) =
    let fun loop ((x,y):xy) dx dy sx sy err unit =
        if x = x1 andalso y=y1 then drawPixel i col (x,y)
        else loop (if 2 * err >  ~dy then x + sx else x, if 2 * err < dx then y + sy else y)(dx)(dy)(sx)(sy)
            (* consider all different cases of new value of err *)
            (if 2*err < dx andalso 2*err > ~dy then err-dy+dx 
            else if 2*err < dx then err+dx 
            else if 2*err > ~dy then err-dy 
            else err)
            (drawPixel i col (x,y))
    in loop (x0,y0) (Int.abs(x1-x0)) (Int.abs(y1-y0)) (if x0<x1 then 1 else ~1) (if y0<y1 then 1 else ~1) (Int.abs(x1-x0)-Int.abs(y1-y0)) ()
    end;

fun drawAll f (Image((w,h), c)) = Array.tabulate

