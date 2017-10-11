fun fact(n)= if n = 1 then 1 else n * fact(n-1)
fun eapprox(n) = if n = 1 then 1.0 else 1.0/real(fact(n-1)) + eapprox(n-1) 
fun pow(a,b) = if b = 1 then a else a * pow(a,b-1)
fun exp(z:real,n) = if n = 1 then 1.0 else pow(z,n-1)/real(fact(n-1)) + exp(z,n-1)
fun gcd(a,b) = 
          (* base case *)
          if a > b andalso a mod b = 0 then b 
          else if b > a andalso b mod a = 0 then a
          else if a = b then a
          else if a mod 2 = 0 then
             (* a is even *)
             (* a and b are even *)
             if b mod 2 = 0 then 2 * gcd(a div 2, b div 2)
             (* a is even and b is odd *)
             else gcd(a div 2, b)
          (* a is odd *)
          (* a is odd and b is even *)
          else if b mod 2 = 0 then gcd(a, b div 2) 
          (* a is odd and b is odd *)
          else gcd(b, abs((a-b)div 2))
fun abs(x) = if x < 0 then ~1 * x else x
