import java.awt.*;

public class Cube extends Shapes{
	public Cube(Matrix m){
		matrix=new Matrix();
		matrix.copyMatrix(m);
		inv_matrix=new Matrix();
				
		createOutline();
		createRenderFaces();
	}
	
	public Cube(Matrix m, double[] color0, double[] color1, double[] color2){
		amb_color=copy(color0);
		dif_color=copy(color1);
		spe_color=copy(color2);
		matrix=new Matrix();
		matrix.copyMatrix(m);
		inv_matrix=new Matrix();
				
		createOutline();
		createRenderFaces();
	}
		
	public void createOutline(){
		vertices=new double[24][6];	
		vertices[0]=new double[]{-1,-1,-1,-1,0,0};
		vertices[1]=new double[]{-1,-1,-1,0,-1,0};
		vertices[2]=new double[]{-1,-1,-1,0,0,-1};
		
		vertices[3]=new double[]{-1,-1,1,-1,0,0};
		vertices[4]=new double[]{-1,-1,1,0,-1,0};
		vertices[5]=new double[]{-1,-1,1,0,0,1};
		
		vertices[6]=new double[]{-1,1,1,-1,0,0};
		vertices[7]=new double[]{-1,1,1,0,0,1};
		vertices[8]=new double[]{-1,1,1,0,1,0};
		
		vertices[9]=new double[]{-1,1,-1,-1,0,0};
		vertices[10]=new double[]{-1,1,-1,0,0,-1};
		vertices[11]=new double[]{-1,1,-1,0,1,0};
		
		vertices[12]=new double[]{1,-1,-1,1,0,0};
		vertices[13]=new double[]{1,-1,-1,0,0,-1};
		vertices[14]=new double[]{1,-1,-1,0,-1,0};
		
		vertices[15]=new double[]{1,-1,1,1,0,0};
		vertices[16]=new double[]{1,-1,1,0,0,1};
		vertices[17]=new double[]{1,-1,1,0,-1,0};
		
		vertices[18]=new double[]{1,1,1,0,0,1};
		vertices[19]=new double[]{1,1,1,1,0,0};
		vertices[20]=new double[]{1,1,1,0,1,0};
		
		vertices[21]=new double[]{1,1,-1,0,0,-1};
		vertices[22]=new double[]{1,1,-1,1,0,0};
		vertices[23]=new double[]{1,1,-1,0,1,0};
		faces=new double[][][]
			  {{vertices[0],vertices[3],vertices[6],vertices[9]},
			   {vertices[1],vertices[4],vertices[17],vertices[14]},
			   {vertices[5],vertices[16],vertices[18],vertices[7]},
			   {vertices[2],vertices[13],vertices[21],vertices[10]},
		   	   {vertices[15],vertices[12],vertices[22],vertices[19]},
		   	   {vertices[20],vertices[23],vertices[11],vertices[8]}};
	}
}