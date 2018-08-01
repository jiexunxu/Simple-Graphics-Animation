import java.awt.*;

public class Cone extends Shapes{
	int resol;
	
	public Cone(int resol, Matrix m){
		matrix=new Matrix();
		matrix.copyMatrix(m);
		inv_matrix=new Matrix();
		
		this.resol=resol;
		createOutline();
		createRenderFaces();
	}
	
	public Cone(int resol, Matrix m, double[] color0, double[] color1, double[] color2){
		amb_color=copy(color0);
		dif_color=copy(color1);
		spe_color=copy(color2);
		matrix=new Matrix();
		matrix.copyMatrix(m);
		inv_matrix=new Matrix();
				
		this.resol=resol;
		createOutline();
		createRenderFaces();
	}
	
	public void createOutline(){
		vertices=new double[3*resol][6];
		int count=0;
		double du=Math.PI*2/resol;
		for(int i=0;i<resol;i++){
			double u=Math.PI*2*i/resol;
			double xn=Math.cos(u)*0.707;
			double zn=Math.sin(u)*0.707;
			double yn=0.707;
			vertices[count]=new double[]{Math.cos(u),0,Math.sin(u),xn,yn,zn};
			count++;
			vertices[count]=new double[]{Math.cos(u+du),0,Math.sin(u+du),xn,yn,zn};
			count++;
			vertices[count]=new double[]{0,1,0,xn,yn,zn};
			count++;			
		}
		count=0;
		faces=new double[resol][3][6];
		for(int i=0;i<3*resol;i=i+3){
			faces[count]=new double[][]{vertices[i],vertices[i+1],vertices[i+2]};
			count++;
		}	
	}
}