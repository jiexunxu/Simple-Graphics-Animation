import java.awt.*;

public class Sphere extends Shapes{
	int hresol;
	int vresol;
	
	public Sphere(int hresol, int vresol, Matrix m, double[] color){
		matrix=new Matrix();
		matrix.copyMatrix(m);
		inv_matrix=new Matrix();
		dif_color=color;
		
		this.hresol=hresol;
		this.vresol=vresol;
		createOutline();
		createRenderFaces();	
	}
	
	public Sphere(int hresol, int vresol, Matrix m, double[] color0, double[] color1, double[] color2){
		matrix=new Matrix();
		matrix.copyMatrix(m);
		inv_matrix=new Matrix();
		amb_color=copy(color0);
		dif_color=copy(color1);
		spe_color=copy(color2);
		this.hresol=hresol;
		this.vresol=vresol;
		createOutline();
		createRenderFaces();	
	}
	
	public Sphere(int hresol, int vresol, Matrix m){
		matrix=new Matrix();
		matrix.copyMatrix(m);
		inv_matrix=new Matrix();
		
		this.hresol=hresol;
		this.vresol=vresol;
		createOutline();
		createRenderFaces();	
	}
	
	public void createOutline(){
		vertices=new double[hresol*vresol*4][6];		
		int count=0;
		double du=Math.PI/vresol;
		double dv=Math.PI*2/hresol;
		for(int i=0;i<vresol;i++){
			double u=Math.PI*i/vresol-Math.PI/2;						
			for(int j=0;j<hresol;j++){				
				double v=Math.PI*2*j/hresol;
				double nx=Math.cos(u+du/2)*Math.cos(v+dv/2);
				double ny=Math.cos(u+du/2)*Math.sin(v+dv/2);
				double nz=Math.sin(u+du/2);
				vertices[count]=new double[]{Math.cos(u)*Math.cos(v),Math.cos(u)*Math.sin(v),
				Math.sin(u),nx,ny,nz};				
				count++;
				vertices[count]=new double[]{Math.cos(u+du)*Math.cos(v),Math.cos(u+du)*Math.sin(v),
				Math.sin(u+du),nx,ny,nz};	
				count++;
				vertices[count]=new double[]{Math.cos(u+du)*Math.cos(v+dv),Math.cos(u+du)*Math.sin(v+dv),
				Math.sin(u+du),nx,ny,nz};	
				count++;
				vertices[count]=new double[]{Math.cos(u)*Math.cos(v+dv),Math.cos(u)*Math.sin(v+dv),
				Math.sin(u),nx,ny,nz};	
				count++;
			}			
		}
		
		faces=new double[hresol*vresol][4][6];						
		count=0;
		for(int i=0;i<vertices.length;i=i+4){
			faces[count]=new double[][]{vertices[i],vertices[i+1],vertices[i+2],vertices[i+3]};
			count++;
		}
	}						
}