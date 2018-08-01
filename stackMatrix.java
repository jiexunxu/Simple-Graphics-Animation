public class stackMatrix{
	private Matrix[] stack;
	private int peek;
	
	public stackMatrix(){
		stack=new Matrix[100];
		peek=-1;
	}
	
	public stackMatrix(int c){
		stack=new Matrix[c];
		peek=-1;
	}
	
	public void push(Matrix m){	
		peek++;
		Matrix n=new Matrix();
		n.copyMatrix(m);						
		stack[peek]=n;						
	}
	
	public void pop(){
		stack[peek]=null;
		peek--;			
	}
	
	public Matrix peek(){
		Matrix n=new Matrix();
		n.copyMatrix(stack[peek]);
		return n;
	}
	
	public int size(){
		return peek+1;
	}
	
	public void print(){
		System.out.println("**********start***********");
		for(int i=0;i<=peek;i++){
			stack[i].print();
			
		}
		System.out.println("***********end************");
	}
}