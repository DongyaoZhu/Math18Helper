
/*
Pair objects used to represent fraction numbers
e.g. up = 3, down = 4, number is 3/4 = 0.75

METHODS:

value(), returns double value of up/down
4 basic algebra operations
simplify(), e.g. from 4/6 to 2/3 by finding gcd

*/

public class Pair{

	double up;
	double down;

	public Pair(){//	0/1
		up = 0;
		down = 1;
	}
	public Pair(double a, double b){//	a/b
		up = a;
		down = b != 0 ? b : 1;
	}
	public Pair(double a){//	a/1
		up = a;
		down = 1;
	}
	public Pair(Pair p){
		this();
		if(p != null){
			up = p.up;
			down = p.down;
		}
	}
	public double getUp(){
		return up;
	}
	public double getDown(){
		return down;
	}
	public double value(){
		return (double)up / down;
	}
	public Pair add(Pair p){
		Pair addition = new Pair();
		addition.up = up * p.down + p.up*down;
		addition.down = down * p.down;
		addition.simplify();
		return addition;
	}
	public Pair subtract(Pair p){
		Pair subtraction = new Pair();
		subtraction.up = up * p.down - p.up*down;
		subtraction.down = down * p.down;
		subtraction.simplify();
		return subtraction;		
	}
	public Pair multiply(Pair p){
		Pair product = new Pair();
		product.up = up * p.up;
		product.down = down * p.down;
		product.simplify();
		return product;
	}
	public Pair divide(Pair p){
		if(p.up == 0){System.out.println("error: "+this+" / "+p); return this;}
		Pair division = new Pair();
		division.up = up * p.down;
		division.down = down * p.up;
		division.simplify();
		return division;
	}
	public void simplify(){
		double gcd = gcd();
		if(gcd == Math.PI) return;
		gcd = down < 0 ? gcd < 0 ? gcd : gcd * -1 : gcd;
		up /= gcd;
		down /= gcd;
	}

	public double gcd(){
		double a = up;
		double b = down;
		double temp;
		if(a == 0 || b == 0){
			return a != 0 ? a : b != 0 ? b : Math.PI;
		}
		while(a % b != 0){
			temp = a;
			a = b;
			b = temp % b;
		}
		return b;
	}
    public static int factorial(int n){
        if(n == 0 || n == 1){
            return 1;
        }else{
            int result = 1;
            while(n > 1){
                result *= n--;
            }
            return result;
        }
    }
public static void main(String[] args) {
	Pair result = new Pair(4, -3);

	System.out.println(result);

}
	public String toString(){
		simplify();
		return up == 0 ? "0" : down == 1 ? (int)up + "" : (int)up + "/" + (int)down;
	}
	public boolean equals(Pair p){
		simplify();
		p.simplify();
		return up == p.up && down == p.down;
	}

}