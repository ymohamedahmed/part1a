fun digitsToYear(a) = if a >= 50 then 1900 + a else 2000 + a
fun compareYears(a,b) = abs(digitsToYear(a) - digitsToYear(b))
fun abs(x) = if x < 0 then ~1 * x else x
