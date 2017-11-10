fun length [] = 0 | length (_::xs) = 1 + length(xs);
fun isPrime(x) = 
    let fun eval (1) = true 
        | eval (n) = if x mod n = 0 then false else eval(n-1) 
    in eval (x div 2)
    end;
fun genPrimes(n) =     
    let fun checkPrime(x, []) = [x] 
        | checkPrime(x, y::ys) = if x mod y = 0 then [] 
        else checkPrime(x, ys)
    fun generate (x, primes) = if x = n then primes  
        else generate(x+1,checkPrime(x, primes) & primes)
in 
    generate(3, [2])
end;
val primesist = genPrimes(50);

