public class TriCylinder extends Shapes{
	public double x1;
	public double z1;
	public double x2;
	public double z2;
	
	public TriCylinder(Matrix m, double[] color0, double[] color1, double[] color2, double x1, double z1, double x2, double z2){
		amb_color=copy(color0);
		dif_color=copy(color1);
		spe_color=copy(color2);
		matrix=new Matrix();
		matrix.copyMatrix(m);
		inv_matrix=new Matrix();
		this.x1=x1;
		this.z1=z1;
		this.x2=x2;
		this.z2=z2;
		
		createOutline();
		createRenderFaces();
	}
	
	public void createOutline(){
		vertices=new double[18][6];
		double L1=Math.sqrt(Math.pow((1-z1)/x1,2)+1);
		double L2=Math.sqrt(Math.pow((1-z2)/x2,2)+1);
		double nx1=(1-z1)/(x1*L1);
		double nz1=1/L1;
		double nx2=(1-z2)/(x2*L2);
		double nz2=1/L2;
		vertices[0]=new double[]{x1,1,z1,0,0,-1};
		vertices[1]=new double[]{x1,1,z1,nx1,0,nz1};
		vertices[2]=new double[]{x1,1,z1,0,1,0};
				
		vertices[3]=new double[]{x2,1,z2,0,0,-1};
		vertices[4]=new double[]{x2,1,z2,nx2,0,nz2};
		vertices[5]=new double[]{x2,1,z2,0,1,0};
		
		vertices[6]=new double[]{0,1,1,nx1,0,nz1};
		vertices[7]=new double[]{0,1,1,nx2,0,nz2};
		vertices[8]=new double[]{0,1,1,0,1,0};
		
		vertices[9]=new double[]{x1,-1,z1,0,0,-1};
		vertices[10]=new double[]{x1,-1,z1,nx1,0,nz1};
		vertices[11]=new double[]{x1,-1,z1,0,-1,0};
		
		vertices[12]=new double[]{x2,-1,z2,0,0,-1};
		vertices[13]=new double[]{x2,-1,z2,nx2,0,nz2};
		vertices[14]=new double[]{x2,-1,z2,0,-1,0};
		
		vertices[15]=new double[]{0,-1,1,nx1,0,nz1};
		vertices[16]=new double[]{0,-1,1,nx2,0,nx2};
		vertices[17]=new double[]{0,-1,1,0,-1,0};
		
		faces=new double[][][]{
			{vertices[0],vertices[3],vertices[12],vertices[9]},
			{vertices[2],vertices[5],vertices[8]},
			{vertices[1],vertices[6],vertices[15],vertices[10]},
			{vertices[4],vertices[7],vertices[16],vertices[13]},
			{vertices[11],vertices[14],vertices[17]}
		};
	}
}