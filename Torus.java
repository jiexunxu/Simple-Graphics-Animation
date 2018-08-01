import java.awt.*;

public class Torus extends Shapes{
	int hresol;
	int vresol;
	double ratio;
	
	Torus(int hresol, int vresol, Matrix m, double ratio){
		matrix=m;

		this.hresol=hresol;
		this.vresol=vresol;
		this.ratio=ratio;
		createOutline();
//		transformCoordinates();
	}
	
	public void createOutline(){
		vertices=new double[hresol*vresol][3];
		Matrix ma=new Matrix();
		int count=0;
		for(int i=0;i<hresol;i++){
			double u=Math.PI*2*i/hresol;
			for(int j=0;j<vresol;j++){
				double v=Math.PI*2*j/vresol;
				ma.yRotationMatrix(u);
				double[] temp={ratio*Math.cos(v)+1,ratio*Math.sin(v),0};
				ma.transformCoordinate(temp,vertices[count]);
				count++;
			}
		}
		
		faces=new double[hresol*vresol][4][3];
		count=0;
		for(int i=0;i<=hresol-2;i++){
			for(int j=0;j<=vresol-2;j++){
				faces[count]=new double[][]{vertices[i*vresol+j],vertices[i*vresol+j+1],
				vertices[i*vresol+j+vresol+1],vertices[i*vresol+j+vresol]};
				count++;
			}
			faces[count]=new double[][]{vertices[i*vresol+vresol-1],vertices[i*vresol],
			vertices[i*vresol+vresol],vertices[i*vresol+vresol*2-1]};
			count++;
		}
		for(int j=0;j<vresol-2;j++){
			faces[count]=new double[][]{vertices[(hresol-1)*vresol+j],vertices[(hresol-1)*vresol+j+1],
			vertices[j+1],vertices[j]};
			count++;
		}
		faces[count]=new double[][]{vertices[(hresol-1)*vresol+vresol-1],vertices[(hresol-1)*vresol],
		vertices[0],vertices[vresol-1]};
	}
}