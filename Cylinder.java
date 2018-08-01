import java.awt.*;

public class Cylinder extends Shapes{
	int resol;
	
	public Cylinder(int resol,Matrix m){
		matrix=new Matrix();
		matrix.copyMatrix(m);
		inv_matrix=new Matrix();
		
		this.resol=resol;
		createOutline();
		createRenderFaces();
	}
	
	public Cylinder(int resol, Matrix m, double[] color0, double[] color1, double[] color2){
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
		vertices=new double[10*resol][6];
		int count=0;
		double du=Math.PI*2/resol;
		for(int i=0;i<resol;i++){
			double u=Math.PI*2*i/resol;
			vertices[count]=new double[]{0,1,0,0,1,0};
			count++;
			vertices[count]=new double[]{Math.cos(u),1,Math.sin(u),0,1,0};
			count++;
			vertices[count]=new double[]{Math.cos(u+du),1,Math.sin(u+du),0,1,0};
			count++;
		}
		for(int i=0;i<resol;i++){
			double u=Math.PI*2*i/resol;
			vertices[count]=new double[]{Math.cos(u),1,Math.sin(u),Math.cos(u+du/2),0,Math.sin(u+du/2)};
			count++;
			vertices[count]=new double[]{Math.cos(u+du),1,Math.sin(u+du),Math.cos(u+du/2),0,Math.sin(u+du/2)};
			count++;
			vertices[count]=new double[]{Math.cos(u+du),-1,Math.sin(u+du),Math.cos(u+du/2),0,Math.sin(u+du/2)};
			count++;
			vertices[count]=new double[]{Math.cos(u),-1,Math.sin(u),Math.cos(u+du/2),0,Math.sin(u+du/2)};
			count++;
		}
		for(int i=0;i<resol;i++){
			double u=Math.PI*2*i/resol;
			vertices[count]=new double[]{0,-1,0,0,-1,0};
			count++;
			vertices[count]=new double[]{Math.cos(u),-1,Math.sin(u),0,-1,0};
			count++;
			vertices[count]=new double[]{Math.cos(u+du),-1,Math.sin(u+du),0,-1,0};
			count++;
		}
		
		faces=new double[3*resol][][];
		count=0;
		for(int i=0;i<resol*3;i=i+3){
			faces[count]=new double[][]{vertices[i],vertices[i+1],vertices[i+2]};
			count++;
		}
		for(int i=resol*3;i<resol*7;i=i+4){
			faces[count]=new double[][]{vertices[i],vertices[i+1],vertices[i+2],vertices[i+3]};
			count++;
		}
		for(int i=resol*7;i<resol*10;i=i+3){
			faces[count]=new double[][]{vertices[i],vertices[i+1],vertices[i+2]};
			count++;
		}
		/*vertices[0]=new double[]{0,-1,0,0,-1,0};
		vertices[1]=new double[]{0,1,0,0,1,0};
		int count=2;
		for(int i=0;i<resol;i++){
			double u=Math.PI*2*i/resol;
			vertices[count]=new double[]{Math.cos(u),-1,Math.sin(u),0,-1,0};
			count++;
		}
		for(int i=0;i<resol;i++){
			double u=Math.PI*2*i/resol;
			vertices[count]=new double[]{Math.cos(u),1,Math.sin(u),0,1,0};
			count++;
		}
		for(int i=0;i<resol;i++){
			double u=Math.PI*2*i/resol;
			vertices[count]=new double[]{Math.cos(u),-1,Math.sin(u),Math.cos(u),0,Math.sin(u)};
			count++;
		}
		for(int i=0;i<resol;i++){
			double u=Math.PI*2*i/resol;
			vertices[count]=new double[]{Math.cos(u),1,Math.sin(u),Math.cos(u),0,Math.sin(u)};
			count++;
		}
		
		faces=new double[3*resol][][];
		count=0;
		for(int i=2;i<=resol;i++){			
			faces[count]=new double[][]{vertices[0],vertices[i],vertices[i+1]};
			count++;					
		}
		faces[count]=new double[][]{vertices[0],vertices[resol+1],vertices[2]};		
		count++;		
		for(int i=0;i<=resol-2;i++){
			faces[count]=new double[][]{vertices[2*resol+2+i],vertices[2*resol+3+i],
			vertices[3*resol+3+i],vertices[2*resol+2+i]};
			count++;
		}
		faces[count]=new double[][]{vertices[3*resol+1],vertices[2*resol+2],vertices[3*resol+2],vertices[4*resol+1]};
		count++;		
		for(int i=resol+2;i<=2*resol;i++){			
			faces[count]=new double[][]{vertices[1],vertices[i],vertices[i+1]};
			count++;					
		}
		faces[count]=new double[][]{vertices[1],vertices[2*resol+1],vertices[resol+2]};	*/
	}
}