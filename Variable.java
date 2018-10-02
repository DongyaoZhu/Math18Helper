public class Variable extends Pair{

	public Pair constant;
	public Pair coefficient;
	public String name;
	public Pair power;

	public Variable(){
		constant = new Pair();//equals 0
		coefficient = new Pair(1);
		name = "x";
		power = new Pair(1);
	}
	public Variable(Pair cons, Pair c, String x, Pair p){
		this();
		if(c != null && p != null){
			constant = new Pair(cons);
			coefficient = new Pair(c);
			name = x;
			power = new Pair(p);
		}
	}
	public Variable(Variable v){
		this();
		if(v != null){
			constant = new Pair(v.constant);
			coefficient = new Pair(v.coefficient);
			name = v.name;
			power = new Pair(v.power);
		}
	}

	public boolean similarTerm(Variable v){
		return name == v.name && power == v.power;
	}

	public Variable add(Variable v){
		//assuming name equals name
		if(similarTerm(v)){
			return new Variable(constant.add(v.constant), 
				coefficient.add(v.coefficient), name, power);
		}
		return null;
	}



}