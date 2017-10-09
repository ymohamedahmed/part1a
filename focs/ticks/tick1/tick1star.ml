fun fact(n)= if n = 1 then 1 else n * fact(n-1)
fun eapprox(n) = if n = 1 then 1.0 else 1.0/real(fact(n-1)) + eapprox(n-1) 
fun pow(a,b) = if b = 1 then a else a * pow(a,b-1)
fun exp(z:real,n) = if n = 1 then 1.0 else pow(z,n-1)/real(fact(n-1)) + exp(z,n-1)
fun gcdEuclid(a,b) = if a mod b = 0 then b else gcdEuclid(b, a mod b)
fun gcd(a,b) = 
    if a = 1 orelse b = 1 then 1 
    else if a = b then a
    else if a mod 2 = 0 andalso b mod 2 = 0 then 2 * gcd(a div 2, b div 2)
    else if a mod 2 = 0 andalso ((b - 1) mod 2) = 0 then gcd(a div 2, b) 
    else if ((a-1) mod 2) = 0 andalso b mod 2 = 0 then gcd(a, b div 2)
    else if ((a-1) mod 2) = 0 andalso ((b - 1) mod 2) = 0 then gcd(b, ((a-1)div 2) - ((b-1)div 2))
    else ~1
