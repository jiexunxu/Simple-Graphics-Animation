public class Sakura_Tree{
	public Cylinder base;
	public Tree_Branch_Main mainbody;
	public boolean hasFlower;
	public boolean hasLeaf;
	
	public Sakura_Tree(boolean hasFlower, boolean hasLeaf){
		this.hasFlower=hasFlower;
		this.hasLeaf=hasLeaf;
		
		createMainStructure();
	}
	
	public void createMainStructure(){
		double[] amb_color={0.06,0.045,0.03};
		double[] dif_color={0.03,0.02,0.015};
		double[] spe_color=new double[3];		
		base=new Cylinder(9,new Matrix(), amb_color,dif_color,spe_color);						
		mainbody=new Tree_Branch_Main(hasFlower, hasLeaf);
	}
	
	public void rendering(Matrix matrix, Light t, Camera cam, double wind, Matrix body_matrix){
		Matrix m1=new Matrix();
		Matrix m2=new Matrix();
		
		m2.multiply(matrix);
		m1.scaleMatrix(30,220,30);
		m2.multiply(m1);
		base.rendering(m2,t,cam);
		m2.identityMatrix();
		
		m2.multiply(matrix);
		m1.transformationMatrix(0,220,0);
		m2.multiply(m1);
		m2.multiply(body_matrix);
		mainbody.rendering(m2,t,cam,wind);
	}
	
	public doublyLinkedList<Shapes> getAllElements(){
		doublyLinkedList<Shapes> list=new doublyLinkedList<Shapes>();
		list.insertAtFront(new DNode<Shapes>(base));
		list.append(mainbody.getAllElements());
		return list;
	}
}

class Tree_Branch_Main{
	public Tree_Branch_Long b1;
	public Tree_Branch_Long b2;
	public Tree_Branch_Long b3;
	public Tree_Branch_Long b4;
	public Tree_Branch_Long b5;
	public Tree_Branch_Long b6;
	public boolean hasFlower;
	public boolean hasLeaf;
	
	public Tree_Branch_Main(boolean hasFlower, boolean hasLeaf){
		this.hasFlower=hasFlower;
		this.hasLeaf=hasLeaf;
		
		createMainStructure();
	}
	
	public void createMainStructure(){
		b1=new Tree_Branch_Long(hasFlower,hasLeaf);
		b2=new Tree_Branch_Long(hasFlower,hasLeaf);
		b3=new Tree_Branch_Long(hasFlower,hasLeaf);
		b4=new Tree_Branch_Long(hasFlower,hasLeaf);
		b5=new Tree_Branch_Long(hasFlower,hasLeaf);
		b6=new Tree_Branch_Long(hasFlower,hasLeaf);
	}
	
	public void rendering(Matrix matrix, Light t, Camera cam, double wind){
		Matrix m1=new Matrix();
		Matrix m2=new Matrix();
		
		m2.multiply(matrix);
		m1.yRotationMatrix(0.523);
		m2.multiply(m1);
		m1.zRotationMatrix(Math.PI/4);
		m2.multiply(m1);
		m1.xRotationMatrix(Math.random()*wind*Math.PI/60-Math.PI*wind/120);
		m2.multiply(m1);
		m1.zRotationMatrix(Math.random()*wind*Math.PI/60-Math.PI*wind/120);
		m2.multiply(m1);
		b1.rendering(m2,t,cam,wind);
		m2.identityMatrix();
		
		m2.multiply(matrix);
		m1.yRotationMatrix(1.779);
		m2.multiply(m1);
		m1.zRotationMatrix(Math.PI/4);
		m2.multiply(m1);
		m1.xRotationMatrix(Math.random()*wind*Math.PI/60-Math.PI*wind/120);
		m2.multiply(m1);
		m1.zRotationMatrix(Math.random()*wind*Math.PI/60-Math.PI*wind/120);
		m2.multiply(m1);
		b2.rendering(m2,t,cam,wind);
		m2.identityMatrix();
		
		m2.multiply(matrix);
		m1.yRotationMatrix(3.035);
		m2.multiply(m1);
		m1.zRotationMatrix(Math.PI/4);
		m2.multiply(m1);
		m1.xRotationMatrix(Math.random()*wind*Math.PI/60-Math.PI*wind/120);
		m2.multiply(m1);
		m1.zRotationMatrix(Math.random()*wind*Math.PI/60-Math.PI*wind/120);
		m2.multiply(m1);
		b3.rendering(m2,t,cam,wind);
		m2.identityMatrix();
		
		m2.multiply(matrix);
		m1.yRotationMatrix(4.291);
		m2.multiply(m1);
		m1.zRotationMatrix(Math.PI/4);
		m2.multiply(m1);
		m1.xRotationMatrix(Math.random()*wind*Math.PI/60-Math.PI*wind/120);
		m2.multiply(m1);
		m1.zRotationMatrix(Math.random()*wind*Math.PI/60-Math.PI*wind/120);
		m2.multiply(m1);
		b4.rendering(m2,t,cam,wind);
		m2.identityMatrix();
		
		m2.multiply(matrix);
		m1.yRotationMatrix(5.547);
		m2.multiply(m1);
		m1.zRotationMatrix(Math.PI/4);
		m2.multiply(m1);
		m1.xRotationMatrix(Math.random()*wind*Math.PI/60-Math.PI*wind/120);
		m2.multiply(m1);
		m1.zRotationMatrix(Math.random()*wind*Math.PI/60-Math.PI*wind/120);
		m2.multiply(m1);
		b5.rendering(m2,t,cam,wind);
		m2.identityMatrix();
		
		m2.multiply(matrix);
		m1.zRotationMatrix(Math.PI/24);
		m2.multiply(m1);
		m1.xRotationMatrix(Math.random()*wind*Math.PI/60-Math.PI*wind/120);
		m2.multiply(m1);
		m1.zRotationMatrix(Math.random()*wind*Math.PI/60-Math.PI*wind/120);
		m2.multiply(m1);
		b6.rendering(m2,t,cam,wind);
	}
	
	public doublyLinkedList<Shapes> getAllElements(){
		doublyLinkedList<Shapes> list=new doublyLinkedList<Shapes>();
		list.append(b1.getAllElements());
		list.append(b2.getAllElements());
		list.append(b3.getAllElements());
		list.append(b4.getAllElements());
		list.append(b5.getAllElements());
		list.append(b6.getAllElements());
		return list;
	}
}

class Tree_Branch_Long{
	public Cone branch;
	public Tree_Branch_Short sbranch;
	public Tree_Branch_Medium mbranch;
	public Tree_End end1;
	public Tree_End end2;
	public Tree_End end3;
	public boolean hasFlower;
	public boolean hasLeaf;
	
	public Tree_Branch_Long(boolean hasFlower, boolean hasLeaf){
		this.hasFlower=hasFlower;
		this.hasLeaf=hasLeaf;
		
		createMainStructure();
	}
	
	public void createMainStructure(){
		double[] amb_color={0.06,0.045,0.03};
		double[] dif_color={0.03,0.02,0.015};
		double[] spe_color=new double[3];
		branch=new Cone(7,new Matrix(),amb_color,dif_color,spe_color);
		sbranch=new Tree_Branch_Short(hasFlower,hasLeaf);
		mbranch=new Tree_Branch_Medium(hasFlower,hasLeaf);
		end1=new Tree_End(0.8,hasFlower,hasLeaf);
		end2=new Tree_End(0.85,hasFlower,hasLeaf);
		end3=new Tree_End(0.85,hasFlower,hasLeaf);
	}
	
	public void rendering(Matrix matrix, Light t, Camera cam, double wind){
		Matrix m1=new Matrix();
		Matrix m2=new Matrix();
		m2.multiply(matrix);
		m1.scaleMatrix(30,600,30);
		m2.multiply(m1);
		branch.rendering(m2,t,cam);
		m2.identityMatrix();
		
		m2.multiply(matrix);
		m1.transformationMatrix(0,570,0);
		m2.multiply(m1);
		end1.rendering(m2,t,cam,wind);
		m2.identityMatrix();
		
		m2.multiply(matrix);
		m1.transformationMatrix(0,490,0);
		m2.multiply(m1);
		m1.yRotationMatrix(Math.PI/4);
		m2.multiply(m1);
		m1.zRotationMatrix(-Math.PI/4);
		m2.multiply(m1);
		end2.rendering(m2,t,cam,wind);
		m2.identityMatrix();
		
		m2.multiply(matrix);
		m1.transformationMatrix(0,190,0);
		m2.multiply(m1);
		m1.yRotationMatrix(Math.PI/4);
		m2.multiply(m1);
		m1.zRotationMatrix(Math.PI/3);
		m2.multiply(m1);
		end3.rendering(m2,t,cam,wind);
		m2.identityMatrix();
		
		m2.multiply(matrix);
		m1.transformationMatrix(0,300,0);
		m2.multiply(m1);
		m1.zRotationMatrix(Math.PI/6);
		m2.multiply(m1);
		m1.xRotationMatrix(Math.random()*wind*Math.PI/50-Math.PI*wind/100);
		m2.multiply(m1);
		m1.zRotationMatrix(Math.random()*wind*Math.PI/50-Math.PI*wind/100);
		m2.multiply(m1);
		sbranch.rendering(m2,t,cam,wind);
		m2.identityMatrix();
		
		m2.multiply(matrix);
		m1.transformationMatrix(0,200,0);
		m2.multiply(m1);
		m1.zRotationMatrix(-Math.PI/6);
		m2.multiply(m1);
		m1.xRotationMatrix(Math.random()*wind*Math.PI/50-Math.PI*wind/100);
		m2.multiply(m1);
		m1.zRotationMatrix(Math.random()*wind*Math.PI/50-Math.PI*wind/100);
		m2.multiply(m1);
		mbranch.rendering(m2,t,cam,wind);
	}
	
	public doublyLinkedList<Shapes> getAllElements(){
		doublyLinkedList<Shapes> list=new doublyLinkedList<Shapes>();
		list.insertAtFront(new DNode<Shapes>(branch));
		list.append(end1.getAllElements());
		list.append(end2.getAllElements());
		list.append(end3.getAllElements());
		list.append(sbranch.getAllElements());
		list.append(mbranch.getAllElements());
		return list;
	}
}

class Tree_Branch_Short{
	public Cone branch;
	public Tree_End end1;
	public Tree_End end2;
	public boolean hasFlower;
	public boolean hasLeaf;
	
	public Tree_Branch_Short(boolean hasFlower, boolean hasLeaf){
		this.hasFlower=hasFlower;
		this.hasLeaf=hasLeaf;
		
		createMainStructure();
	}
	
	public void createMainStructure(){
		double[] amb_color={0.06,0.045,0.03};
		double[] dif_color={0.03,0.02,0.015};
		double[] spe_color=new double[3];		
		branch=new Cone(7,new Matrix(),amb_color,dif_color,spe_color);				
		end1=new Tree_End(0.8,hasFlower,hasLeaf);				
		end2=new Tree_End(0.85,hasFlower,hasLeaf);
	}
	
	public void rendering(Matrix matrix, Light t, Camera cam, double wind){
		Matrix m1=new Matrix();
		Matrix m2=new Matrix();
		
		m2.multiply(matrix);
		m1.scaleMatrix(15,300,15);
		m2.multiply(m1);
		branch.rendering(m2,t,cam);
		m2.identityMatrix();
		
		m2.multiply(matrix);
		m1.transformationMatrix(0,290,0);
		m2.multiply(m1);
		end1.rendering(m2,t,cam,wind);
		m2.identityMatrix();
		
		m2.multiply(matrix);
		m1.transformationMatrix(0,190,0);
		m2.multiply(m1);
		m1.zRotationMatrix(Math.PI/2.5);
		m2.multiply(m1);
		end2.rendering(m2,t,cam,wind);
	}
	
	public doublyLinkedList<Shapes> getAllElements(){
		doublyLinkedList<Shapes> list=new doublyLinkedList<Shapes>();
		list.insertAtFront(new DNode<Shapes>(branch));
		list.append(end1.getAllElements());
		list.append(end2.getAllElements());
		return list;
	}
}

class Tree_Branch_Medium{
	public Cone branch;
	public Tree_End end1;
	public Tree_End end2;
	public Tree_End end3;
	public boolean hasFlower;
	public boolean hasLeaf;
	
	public Tree_Branch_Medium(boolean hasFlower, boolean hasLeaf){
		this.hasFlower=hasFlower;
		this.hasLeaf=hasLeaf;
		
		createMainStructure();
	}
	
	public void createMainStructure(){
		double[] amb_color={0.06,0.045,0.03};
		double[] dif_color={0.03,0.02,0.015};
		double[] spe_color=new double[3];
		
		branch=new Cone(7,new Matrix(),amb_color,dif_color,spe_color);
				
		end1=new Tree_End(0.8,hasFlower,hasLeaf);				
		end2=new Tree_End(0.85,hasFlower,hasLeaf);						
		end3=new Tree_End(0.9,hasFlower,hasLeaf);
	}
	
	public void rendering(Matrix matrix, Light t, Camera cam, double wind){
		Matrix m1=new Matrix();
		Matrix m2=new Matrix();
		
		m2.multiply(matrix);
		m1.scaleMatrix(20,400,20);
		m2.multiply(m1);
		branch.rendering(m2,t,cam);
		m2.identityMatrix();

		m2.multiply(matrix);
		m1.transformationMatrix(0,390,0);
		m2.multiply(m1);
		end1.rendering(m2,t,cam,wind);
		m2.identityMatrix();
		
		m2.multiply(matrix);
		m1.transformationMatrix(0,280,0);
		m2.multiply(m1);
		m1.zRotationMatrix(Math.PI/2.5);
		m2.multiply(m1);
		end2.rendering(m2,t,cam,wind);
		m2.identityMatrix();
		
		m2.multiply(matrix);
		m1.transformationMatrix(0,170,0);
		m2.multiply(m1);
		m1.zRotationMatrix(-Math.PI/2.5);
		m2.multiply(m1);
		end3.rendering(m2,t,cam,wind);
	}
	
	public doublyLinkedList<Shapes> getAllElements(){
		doublyLinkedList<Shapes> list=new doublyLinkedList<Shapes>();
		list.insertAtFront(new DNode<Shapes>(branch));
		list.append(end1.getAllElements());
		list.append(end2.getAllElements());
		list.append(end3.getAllElements());
		return list;
	}
}

class Tree_End{
	public Cone branch;
	public Tree_Terminal ter1;
	public Tree_Terminal ter2;
	public Tree_Terminal ter3;
	public Sphere flower;
	public double mag;
	public boolean hasFlower;
	public boolean hasLeaf;
	
	public Tree_End(double magnification, boolean hasFlower, boolean hasLeaf){
		mag=magnification;
		this.hasFlower=hasFlower;
		this.hasLeaf=hasLeaf;
		
		createMainStructure();
	}
	
	public void createMainStructure(){
		double[] amb_color={0.06,0.045,0.03};
		double[] dif_color={0.03,0.02,0.015};
		double[] spe_color=new double[3];		
		branch=new Cone(5,new Matrix(),amb_color,dif_color,spe_color);
				
		if(hasFlower){
			amb_color=new double[]{0.096,0.034,0.048};
			dif_color=new double[]{0.92,0.32,0.46};
			spe_color=new double[]{0.7,0.27,0.38};
			flower=new Sphere(5,5,new Matrix(),amb_color,dif_color,spe_color);
		}						
						
		ter1=new Tree_Terminal(mag,hasLeaf);				
		ter2=new Tree_Terminal(mag,hasLeaf);				
		ter3=new Tree_Terminal(mag,hasLeaf);
	}
	
	public void rendering(Matrix matrix, Light t, Camera cam, double wind){
		Matrix m1=new Matrix();
		Matrix m2=new Matrix();
		
		m2.multiply(matrix);
		m1.scaleMatrix(1.2*mag,70*mag,1.2*mag);
		m2.multiply(m1);
		branch.rendering(m2,t,cam);
		m2.identityMatrix();
		
		if(hasFlower){			
			m2.multiply(matrix);
			m1.transformationMatrix(0,70*mag,0);
			m2.multiply(m1);
			m1.scaleMatrix(15*mag,15*mag,15*mag);
			m2.multiply(m1);
			flower.rendering(m2,t,cam);
			m2.identityMatrix();
		}
		
		m2.multiply(matrix);
		m1.transformationMatrix(0,25*mag,0);
		m2.multiply(m1);				
		Matrix temp=new Matrix();
		temp.copyMatrix(m2);
				
		m1.zRotationMatrix(Math.PI/4);
		m2.multiply(m1);
		m1.xRotationMatrix(Math.random()*wind*Math.PI/10-Math.PI*wind/20);
		m2.multiply(m1);
		ter1.rendering(m2,t,cam);
		
		m2.copyMatrix(temp);
		m1.yRotationMatrix(Math.PI*2/3);
		m2.multiply(m1);
		m1.zRotationMatrix(Math.PI/4);
		m2.multiply(m1);
		m1.xRotationMatrix(Math.random()*wind*Math.PI/10-Math.PI*wind/20);
		m2.multiply(m1);
		ter2.rendering(m2,t,cam);
		
		m2.copyMatrix(temp);
		m1.yRotationMatrix(-Math.PI*2/3);
		temp.multiply(m1);
		m1.zRotationMatrix(Math.PI/4);
		temp.multiply(m1);
		m1.xRotationMatrix(Math.random()*wind*Math.PI/10-Math.PI*wind/20);
		m2.multiply(m1);
		ter3.rendering(temp,t,cam);
	}
	
	public doublyLinkedList<Shapes> getAllElements(){
		doublyLinkedList<Shapes> list=new doublyLinkedList<Shapes>();
		list.insertAtFront(new DNode<Shapes>(branch));
		if(hasFlower){
			list.insertAtFront(new DNode<Shapes>(flower));
		}
		list.append(ter1.getAllElements());
		list.append(ter2.getAllElements());
		list.append(ter3.getAllElements());
		return list;
	}
}

class Tree_Terminal{
	public Cube branch;
	public Sphere leaf;
	public double magnification;
	public boolean hasLeaf;
	
	public Tree_Terminal(double mag, boolean hasLeaf){
		magnification=mag;
		this.hasLeaf=hasLeaf;
		
		createMainStructure();		
	}
	
	public void createMainStructure(){
		double[] amb_color={0.014,0.085,0.014};
		double[] dif_color={0.11,0.75,0.11};
		double[] spe_color={0.1,0.55,0.1};
		if(hasLeaf){
			leaf=new Sphere(6,6,new Matrix(),amb_color,dif_color,spe_color);
		}
				
		amb_color=new double[]{0.06,0.045,0.03};
		dif_color=new double[]{0.03,0.02,0.015};
		spe_color=new double[3];
		branch=new Cube(new Matrix(),amb_color,dif_color,spe_color);		
	}
	
	public void rendering(Matrix matrix, Light t, Camera cam){	
		Matrix m1=new Matrix();
		Matrix m2=new Matrix();	
		if(hasLeaf){
			m2.multiply(matrix);
			m1.transformationMatrix(0,50*magnification,0);
			m2.multiply(m1);
			m1.scaleMatrix(10*magnification,30*magnification,4*magnification);
			m2.multiply(m1);
			leaf.rendering(m2,t,cam);
			m2.identityMatrix();
		}
		
		m2.multiply(matrix);
		m1.transformationMatrix(0,20*magnification,0);
		m2.multiply(m1);
		m1.scaleMatrix(1*magnification,20*magnification,1*magnification);
		m2.multiply(m1);
		branch.rendering(m2,t,cam);		
	}
	
	public doublyLinkedList<Shapes> getAllElements(){
		doublyLinkedList<Shapes> list=new doublyLinkedList<Shapes>();
		list.insertAtFront(new DNode<Shapes>(branch));
		if(hasLeaf){
			list.insertAtFront(new DNode<Shapes>(leaf));		
		}
		return list;
	}
}