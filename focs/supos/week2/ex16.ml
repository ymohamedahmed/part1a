(* Function to compute the nth gamma value using the golden ratio *)
fun gamma(0) = (1.0 + Math.sqrt(5.0))/2.0
| gamma(n) = 1.0/(gamma(n-1)-1.0)
