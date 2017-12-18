type color = int * int * int
type xy = int * int
datatype image = Image of xy * color array array;

fun image (x as (w,h):xy) (c:color) =Image(x, 
    Array.tabulate(h, (fn i => Array.tabulate(w, fn i => c))))

fun size (Image(x,_))  = x

fun drawPixel (Image(_,c)) color ((x,y):xy) = Array.update(Array.sub(c,y), x, color)

fun format4 i = StringCvt.padLeft #" " 4 (Int.toString i);

fun colourToString oc ((r,g,b):color) = TextIO.output(oc, format4 r ^ format4 g ^ format4 b)

fun rowColourString oc (a,0, w)() = colourToString oc (Array.sub(a,w-1))
    | rowColourString oc (a,i, w)() =  rowColourString oc (a,i-1,w)(colourToString oc (Array.sub(a,w-i-1)))
    
fun twoDArrayToString oc (a, 0, h, w)()() = rowColourString oc (Array.sub(a,h-1), w-1, w)(TextIO.output(oc, "\n"))
| twoDArrayToString oc (a, i, h, w)()() =  twoDArrayToString oc (a,i-1,h, w)(rowColourString oc (Array.sub(a, h-i-1), w-1, w)())(TextIO.output(oc, "\n"))

fun writeByLine oc [] () = TextIO.closeOut oc
| writeByLine oc (x::xs) () = writeByLine(oc)(xs)(TextIO.output(oc, x ^ "\n"))

fun toPPM (image as Image(x,c)) filename =
  let val oc = TextIO.openOut filename
      val (w,h) = size image
  in
     TextIO.output(oc,"P3\n" ^ Int.toString w ^ " " ^ Int.toString h ^ "\n255\n");
     twoDArrayToString oc (c,h-1,h,w) () ();
     TextIO.closeOut oc
     (* code to output image rows, one per line goes here *)
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

fun drawRow i f (p as (0,y):xy) () = drawPixel i (f p) p
| drawRow i f (p as (x,y):xy) () = drawRow(i)(f)((x-1,y))(drawPixel i (f p) p)

fun drawAll f (i as Image((w,h), c)) = 
    let fun helper (p as (x,0):xy) () = drawRow (i)(f)(x,0)()
    | helper(p as (x,y):xy)() = helper(x,y-1)(drawRow i f (x,y)())
    in helper (w-1,h-1) ()
    end;


fun gradient ((x,y):xy) : color =
(((x div 30) * 30) mod 256, 0, ((y div 30) * 30) mod 256);

fun gradImage() =
    let val i = Image((640,480):xy, Array.tabulate(480, fn i => Array.tabulate(640, fn i => (0,0,0):color)))
    in  
        drawAll (gradient)(i);
        toPPM (i)("gradient.ppm")
    end;

fun mandelbrot maxIter (x,y) =
  let fun solve (a,b) c = 
      if c = maxIter then 1.0
      else
        if (a*a + b*b <= 4.0) then 
          solve (a*a - b*b + x,2.0*a*b + y) (c+1)
        else (real c)/(real maxIter)
  in
    solve (x,y) 0
  end;

  fun chooseColour (n:real) : color=
  let
    val r = round ((Math.cos n) * 255.0)
    val g = round ((Math.cos n) * 255.0)
    val b = round ((Math.sin n) * 255.0)
  in
    (r,g,b)
  end;

  fun rescale ((w,h):xy) (cx, cy, s) ((x,y):xy) = 
      let val p = s*(real(x)/real(w) -0.5)+cx
      val q = s*(real(y)/real(h) - 0.5) + cy
      in (p,q)
      end;
 
fun compute (size as (w,h):xy)(cx,cy,s) maxIter = 
    let val i = Image(size, Array.tabulate(h, fn y => Array.tabulate(w, fn x => chooseColour(mandelbrot(maxIter)(#1(rescale(size)(cx,cy,s)((x,y):xy)),#2(rescale(size)(cx,cy,s)((x,y):xy)))))))
    in toPPM(i)("mandelbrot.ppm")
    end;
