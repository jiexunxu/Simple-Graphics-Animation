public class Chair{
	public Cylinder base;
	public Cylinder back;
	public Cube support1;
	public Cube support2;
	public Cube support3;
	public Cube support4;
	
	public Chair(){
		double[] amb={0.1,0,0.1};
		double[] dif={0.88,0,0.88};
		double[] spe={0.1,0,0.1};
		base=new Cylinder(16,new Matrix(),amb,dif,spe);
		
		amb=new double[]{0.063,0,0.063};
		dif=new double[]{0.58,0,0.58};
		spe=new double[]{0.08,0,0.08};
		back=new Cylinder(16,new Matrix(),amb,dif,spe);
		
		amb=new double[]{0,0,0.05};
		dif=new double[]{0,0,0.1};
		spe=new double[3];
		support1=new Cube(new Matrix(),amb,dif,spe);
		support2=new Cube(new Matrix(),amb,dif,spe);
		support3=new Cube(new Matrix(),amb,dif,spe);
		support4=new Cube(new Matrix(),amb,dif,spe);
	}
	
	public void rendering(Matrix m, Light t, Camera cam){
		Matrix m1=new Matrix();
		Matrix m2=new Matrix();
		
		m2.copyMatrix(m);
		m1.scaleMatrix(20,1,20);
		m2.multiply(m1);
		base.rendering(m2,t,cam);
		m2.identityMatrix();
		
		m2.multiply(m);
		m1.transformationMatrix(-34.66,-19.66,0);
		m2.multiply(m1);
		m1.zRotationMatrix(Math.PI/4);
		m2.multiply(m1);
		m1.scaleMatrix(8,1,21);
		m2.multiply(m1);
		back.rendering(m2,t,cam);
		m2.identityMatrix();
		
		m2.multiply(m);
		m1.transformationMatrix(-10,5,13.2);
		m2.multiply(m1);
		m1.zRotationMatrix(Math.PI/4);
		m2.multiply(m1);
		m1.scaleMatrix(35.4,0.8,0.8);
		m2.multiply(m1);
		support1.rendering(m2,t,cam);
		m2.identityMatrix();
		
		m2.multiply(m);
		m1.transformationMatrix(-10,5,-13.2);
		m2.multiply(m1);
		m1.zRotationMatrix(Math.PI/4);
		m2.multiply(m1);
		m1.scaleMatrix(35.4,0.8,0.8);
		m2.multiply(m1);
		support2.rendering(m2,t,cam);
		m2.identityMatrix();
		
		m2.multiply(m);
		m1.transformationMatrix(0,15,13.2);
		m2.multiply(m1);
		m1.zRotationMatrix(-Math.PI/4);
		m2.multiply(m1);
		m1.scaleMatrix(21.21,0.8,0.8);
		m2.multiply(m1);
		support3.rendering(m2,t,cam);
		m2.identityMatrix();
		
		m2.multiply(m);
		m1.transformationMatrix(0,15,-13.2);
		m2.multiply(m1);
		m1.zRotationMatrix(-Math.PI/4);
		m2.multiply(m1);
		m1.scaleMatrix(21.21,0.8,0.8);
		m2.multiply(m1);
		support4.rendering(m2,t,cam);
	}
	
	public doublyLinkedList<Shapes> getAllElements(){
		doublyLinkedList<Shapes> list=new doublyLinkedList<Shapes>();
		list.insertAtFront(new DNode<Shapes>(base));
		list.insertAtFront(new DNode<Shapes>(back));
		list.insertAtFront(new DNode<Shapes>(support1));
		list.insertAtFront(new DNode<Shapes>(support2));
		list.insertAtFront(new DNode<Shapes>(support3));
		list.insertAtFront(new DNode<Shapes>(support4));
		return list;
	}
}