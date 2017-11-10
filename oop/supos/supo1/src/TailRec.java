class TailRec{
    public static void main(String[] args){
        System.out.println(new TailRec().power(2, 3));
        System.out.println(new TailRec().powerTail(2,3,1));
    }
    int power(double a, int n){
        return (int)(n==0 ? 1 : a*power(a,n-1));
    }
    int powerTail(double a, int n, int total){
        return (int)(n==0 ? total : powerTail(a, n-1, (int)(total*a)));
    }
}
