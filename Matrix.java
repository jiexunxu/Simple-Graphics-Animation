public class Matrix{
	public double[][] matrix;
	
	public Matrix(){
		identityMatrix();
	}
	
	public void set(int i,int j,double value){
		matrix[i][j]=value;
	}
	
	public double get(int i,int j){
		return matrix[i][j];
	}
	
	public void identityMatrix(){
		matrix=new double[4][4];
		matrix[0][0]=1.0;
		matrix[1][1]=1.0;
		matrix[2][2]=1.0;
		matrix[3][3]=1.0;
	}
	
	public void transformationMatrix(double a,double b,double c){
		identityMatrix();
		matrix[0][3]=a;
		matrix[1][3]=b;
		matrix[2][3]=c;
	}
	
	public void xRotationMatrix(double angle){
		identityMatrix();
		matrix[1][1]=Math.cos(angle);
		matrix[2][2]=Math.cos(angle);
		matrix[1][2]=-Math.sin(angle);
		matrix[2][1]=Math.sin(angle);
	}
	
	public void yRotationMatrix(double angle){
		identityMatrix();
		matrix[0][0]=Math.cos(angle);
		matrix[2][2]=Math.cos(angle);
		matrix[2][0]=-Math.sin(angle);
		matrix[0][2]=Math.sin(angle);
	}
	
	public void zRotationMatrix(double angle){
		identityMatrix();
		matrix[1][1]=Math.cos(angle);
		matrix[0][0]=Math.cos(angle);
		matrix[0][1]=-Math.sin(angle);
		matrix[1][0]=Math.sin(angle);		
	}
	
	public void xShearMatrix(double ratio){
		identityMatrix();
		matrix[0][1]=ratio;
		matrix[0][2]=ratio;
	}
	
	public void yShearMatrix(double ratio){
		identityMatrix();
		matrix[1][0]=ratio;
		matrix[1][2]=ratio;
	}
	
	public void zShearMatrix(double ratio){
		identityMatrix();
		matrix[2][0]=ratio;
		matrix[2][1]=ratio;
	}
	
	public void scaleMatrix(double a,double b,double c){
		identityMatrix();
		matrix[0][0]=a;
		matrix[1][1]=b;
		matrix[2][2]=c;
		matrix[3][3]=1.0;
	}
	
	public void transposeMatrix(){
		swap(1,0,0,1);
		swap(2,0,0,2);
		swap(3,0,0,3);
		swap(2,1,1,2);
		swap(3,1,1,3);
		swap(3,2,2,3);
	}
	
		private void swap(int r1, int c1, int r2, int c2){
			double temp=matrix[r1][c1];
			matrix[r1][c1]=matrix[r2][c2];
			matrix[r2][c2]=temp;
		}
	
	public void transformCoordinate(double[] input,double[] output){
		for(int i=0;i<3;i++){
			output[i]=input[0]*matrix[i][0]+input[1]*matrix[i][1]+input[2]*matrix[i][2]+matrix[i][3];
		}
	}
	
	public void copyMatrix(Matrix m){
		for(int i=0;i<4;i++){
			for(int j=0;j<4;j++){
				matrix[i][j]=m.get(i,j);
			}
		}
	}
	
	public void multiply(Matrix m){
		Matrix temp=new Matrix();
		temp.copyMatrix(this);
		for(int i=0;i<4;i++){
			for(int j=0;j<4;j++){
				double sum=0.0;
				for(int k=0;k<4;k++){
					sum=sum+temp.matrix[i][k]*m.matrix[k][j];
				}
				matrix[i][j]=sum;
			}
		}
	}
	
	public void copyInverseTo(Matrix m){
		for (int i = 0 ; i < 3 ; i++)
		for (int j = 0 ; j < 3 ; j++) {
		 int iu = (i + 1) % 3, iv = (i + 2) % 3;
		 int ju = (j + 1) % 3, jv = (j + 2) % 3;
		 m.matrix[j][i]=matrix[iu][ju]*matrix[iv][jv]-matrix[iu][jv]*matrix[iv][ju];
		}
				
		double det = matrix[0][0]*m.get(0,0)+matrix[1][0]*m.get(0,1)+matrix[2][0]*m.get(0,2);
		for (int i = 0 ; i < 3 ; i++)
		for (int j = 0 ; j < 3 ; j++)
		 m.matrix[i][j] /= det;
				
		for (int i = 0 ; i < 3 ; i++)
		 m.matrix[i][3] = m.get(i,0)*matrix[0][3] - m.get(i,1)*matrix[1][3] - m.get(i,2)*matrix[2][3];
				
		for (int i = 0 ; i < 4 ; i++)
		 m.matrix[3][i] = i < 3 ? 0 : 1;

	}
	
	public void print(){
		for(int i=0;i<4;i++){
			for(int j=0;j<4;j++){
				System.out.print(matrix[i][j]+" ");
			}
			System.out.println();
		}
		System.out.println();
	}		
}