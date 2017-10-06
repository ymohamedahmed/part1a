fun genPrimes(n) =     
    let fun checkPrime(x, []) = [x] 
        | checkPrime(x, y::ys) = if x mod y = 0 then [] 
        else checkPrime(x, ys)
    fun generate (x, primes) = if x = n then primes  
        else generate(x+1,checkPrime(x, primes) @ primes)
in 
    generate(3, [2])
end;
val primeList = genPrimes(50);

fun primeFactors(x, []) = []
    | primeFactors(x, y::ys) = if x mod y = 0 then y::primeFactors(x div y, y::ys)
    else primeFactors(x, ys)

fun length(x::xs) = [] = 0 | 1 + length(xs)
fun getPair(0) = (0,[])
 | getPair(n)  = if length(primeFactors(n, primeList)) = 2 then (n, primeFactors(n, primeList))
 else getPair(n-1)
