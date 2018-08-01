import java.awt.*;
import java.util.*;

public class Shapes{
	public double[][] vertices;
	public double[][][] faces;
	public doublyLinkedList<double[][]> render_faces;
	public doublyLinkedList<double[][]> render_faces_original;
	public doublyLinkedList<int[][]> project_faces;
	public double[] amb_color;
	public double[] dif_color;
	public double[] spe_color;
	public Matrix matrix;
	public Matrix inv_matrix;
	public double a;
	public double b;
	public double c;
	public double d;
		
	// The followings are methods for updating shapes	
	public void setClippingPlane(double x1,double x2,double x3,double x4){
		a=x1;b=x2;c=x3;d=x4;
	}
	
	public void setMatrix(Matrix m){
		matrix.copyMatrix(m);
	}
	
	public void setColor(double[] c1, double[] c2, double[] c3){
		amb_color=copy(c1);
		dif_color=copy(c2);
		spe_color=copy(c3);
	}		
	//end
	
	
	
	public void rendering(Matrix m, Light t, Camera cam){
		matrix.copyMatrix(m);
		transformCoordinates();
		colorVertex(t);
		projectTransformation(cam);
		createTrapezoid();
	}
	
	public void rendering(Light t, Camera cam){	
		transformCoordinates();
		colorVertex(t);
		projectTransformation(cam);
		createTrapezoid();
		
	/*	transformCoordinates(cam);
		colorVertex(t);
		createRenderFaces();
		projectTransformation(cam);
		createTrapezoid();*/
	}
	
	//The followings are methods for rendering pipeline	
	public void createRenderFaces(){		
		render_faces=new doublyLinkedList<double[][]>();
		render_faces_original=new doublyLinkedList<double[][]>();
		DNode<double[][]> n;
		for(int i=0;i<faces.length;i++){
			if(faces[i].length==4){			
				n=new DNode<double[][]>(new double[][]{copy(faces[i][0]),copy(faces[i][1]),copy(faces[i][2])});
				render_faces_original.insertAtBack(n);
				n=new DNode<double[][]>(new double[][]{copy(faces[i][0]),copy(faces[i][1]),copy(faces[i][2])});
				render_faces.insertAtBack(n);
				n=new DNode<double[][]>(new double[][]{copy(faces[i][0]),copy(faces[i][2]),copy(faces[i][3])});
				render_faces_original.insertAtBack(n);
				n=new DNode<double[][]>(new double[][]{copy(faces[i][0]),copy(faces[i][2]),copy(faces[i][3])});
				render_faces.insertAtBack(n);																
			}else{
				n=new DNode<double[][]>(copy(faces[i]));
				render_faces_original.insertAtBack(n);
				n=new DNode<double[][]>(copy(faces[i]));
				render_faces.insertAtBack(n);
			}						
		}
	}
	
	public void transformCoordinates(){
		matrix.copyInverseTo(inv_matrix);
		inv_matrix.transposeMatrix();
		double[] temp2=new double[3];
		
		listIterator<double[][]> I1=render_faces_original.getIterator();
		listIterator<double[][]> I2=render_faces.getIterator();
		while(I1.isValid()){
			double[][] face=copy(I1.getNode().element());
			for(int j=0;j<face.length;j++){
				double[] temp={face[j][0],face[j][1],face[j][2]};				
				matrix.transformCoordinate(temp,temp2);
				face[j][0]=temp2[0];
				face[j][1]=temp2[1];
				face[j][2]=temp2[2];
				
				temp=new double[]{face[j][3],face[j][4],face[j][5]};
				inv_matrix.transformCoordinate(temp,temp2);
				double L=Math.sqrt(temp2[0]*temp2[0]+temp2[1]*temp2[1]+temp2[2]*temp2[2]);
				face[j][3]=temp2[0]/L;
				face[j][4]=temp2[1]/L;
				face[j][5]=temp2[2]/L;				
			}
			I2.getNode().setElement(face);
			I1.moveForward();
			I2.moveForward();
		}
	}	
	
	public void colorVertex(Light t){
		int p=5;
		listIterator<double[][]> I=render_faces.getIterator();
		while(I.isValid()){
			double[][] face=copy(I.getNode().element());
			for(int j=0;j<face.length;j++){
				double[] dir=t.getDirection(face[j][0],face[j][1],face[j][2]);
				double dif_const=Math.max(0.0,dir[0]*face[j][3]+dir[1]*face[j][4]+dir[2]*face[j][5]);
				double[] ref=new double[3];
				double ln=-dir[0]*face[j][3]-dir[1]*face[j][4]-dir[2]*face[j][5];
				ref[0]=2*ln*face[j][3]+dir[0];
				ref[1]=2*ln*face[j][4]+dir[1];
				ref[2]=2*ln*face[j][5]+dir[2];
				double L=Math.sqrt(Math.pow(face[j][0],2)+Math.pow(face[j][1],2)+Math.pow(face[j][2],2));
				double Ls2=Math.max(0.0,-ref[0]*face[j][0]/L-ref[1]*face[j][1]/L-ref[2]*face[j][2]/L);
				double Ls=Math.pow(Ls2,p);
				face[j][3]=(int)((amb_color[0]*3+dif_const*dif_color[0]+Ls*spe_color[0])*t.intensity*255);
				face[j][4]=(int)((amb_color[1]*3+dif_const*dif_color[1]+Ls*spe_color[1])*t.intensity*255);
				face[j][5]=(int)((amb_color[2]*3+dif_const*dif_color[2]+Ls*spe_color[2])*t.intensity*255);
			}
			I.getNode().setElement(face);
			I.moveForward();
		}
	}
	
/*	public void transformCoordinates(Camera cam){
		Matrix aux=new Matrix();		
		aux.transformationMatrix(-cam.cam_pos[0],-cam.cam_pos[1],-cam.cam_pos[2]);		
		matrix.multiply(aux);
		
		matrix.copyInverseTo(inv_matrix);
//		matrix.print();
		inv_matrix.transposeMatrix();
		double[] temp2=new double[3];
//		inv_matrix.print();
	//	double[] temp=new double[3];
	//	double[] temp2=new double[3];
		for(int i=0;i<faces.length;i++){
			for(int j=0;j<faces[i].length;j++){
				double[] temp={faces[i][j][0],faces[i][j][1],faces[i][j][2]};				
				matrix.transformCoordinate(temp,temp2);
		//		System.out.println("temp2="+temp2[0]+" "+temp2[1]+" "+temp2[2]);
				faces[i][j][0]=temp2[0];
				faces[i][j][1]=temp2[1];
				faces[i][j][2]=temp2[2];
				
				temp=new double[]{faces[i][j][3],faces[i][j][4],faces[i][j][5]};
				inv_matrix.transformCoordinate(temp,temp2);
				double L=Math.sqrt(temp2[0]*temp2[0]+temp2[1]*temp2[1]+temp2[2]*temp2[2]);
		//		System.out.println("temp2="+temp2[0]+" "+temp2[1]+" "+temp2[2]);
				faces[i][j][3]=temp2[0]/L;
				faces[i][j][4]=temp2[1]/L;
				faces[i][j][5]=temp2[2]/L;
		//		System.out.println(faces[i][j][3]+" "+faces[i][j][4]+" "+faces[i][j][5]);
			}
		}
		/*for(int i=0;i<faces.length;i++){
			for(int j=0;j<faces[i].length;j++){
				temp=new double[3];
				temp2=new double[3];
				
				matrix.transformCoordinate(faces[i][j],temp);
				double[] temp3={faces[i][j][3],faces[i][j][4],faces[i][j][5]};
				System.out.println("temp3="+temp3[0]+" "+temp3[1]+" "+temp3[2]);						
				inv_matrix.transformCoordinate(temp3,temp2);
				System.out.println("temp2="+temp2[0]+" "+temp2[1]+" "+temp2[2]);
				double L=Math.sqrt(temp2[0]*temp2[0]+temp2[1]*temp2[1]+temp2[2]*temp2[2]);
				
				System.out.println("L="+L);
				System.out.println("temp2="+temp2[0]+" "+temp2[1]+" "+tempf2[2]);
				//double L=Math.sqrt(temp2[0]*temp2[0]+temp2[1]*temp2[1]+temp2[2]*temp2[2]);
				
				faces_helper[i][j][0]=temp[0];
				faces_helper[i][j][1]=temp[1];
				faces_helper[i][j][2]=temp[2];
				faces_helper[i][j][3]=temp2[0];
				faces_helper[i][j][4]=temp2[1];
				faces_helper[i][j][5]=temp2[2];				
			}			
		}
		
		for(int i=0;i<faces.length;i++){
			for(int j=0;j<faces[i].length;j++){
				double L=Math.sqrt(Math.pow(faces_helper[i][j][3],2)+
				Math.pow(faces_helper[i][j][4],2)+Math.pow(faces_helper[i][j][5],2));
	//			System.out.println("L="+L);
				double temp1n=faces_helper[i][j][3]/L;
				double temp2n=faces_helper[i][j][4]/L;
				double temp3n=faces_helper[i][j][5]/L;
	//			System.out.println("temp1n="+temp1n);
				faces[i][j][0]=faces_helper[i][j][0];
				faces[i][j][1]=faces_helper[i][j][1];
				faces[i][j][2]=faces_helper[i][j][2];
				faces[i][j][3]=temp1n;
				faces[i][j][4]=temp2n;
				faces[i][j][5]=temp3n;
	//			System.out.println("faces["+i+"]["+j+"][3]="+faces[i][j][3]);
			}
		}
	//	System.out.println("***faces["+3+"]["+1+"][3]="+faces[3][1][3]);
	}
	
	public void colorVertex(Light t){
		int p=5;
		for(int i=0;i<faces.length;i++){
			for(int j=0;j<faces[i].length;j++){
				double[] dir=t.getDirection(faces[i][j][0],faces[i][j][1],faces[i][j][2]);
				double dif_const=Math.max(0.0,dir[0]*faces[i][j][3]+dir[1]*faces[i][j][4]+dir[2]*faces[i][j][5]);
				double[] ref=new double[3];
				double ln=-dir[0]*faces[i][j][3]-dir[1]*faces[i][j][4]-dir[2]*faces[i][j][5];
				ref[0]=2*ln*faces[i][j][3]+dir[0];
				ref[1]=2*ln*faces[i][j][4]+dir[1];
				ref[2]=2*ln*faces[i][j][5]+dir[2];
				double L=Math.sqrt(Math.pow(faces[i][j][0],2)+Math.pow(faces[i][j][1],2)+Math.pow(faces[i][j][2],2));
				double Ls2=Math.max(0.0,-ref[0]*faces[i][j][0]/L-ref[1]*faces[i][j][1]/L-ref[2]*faces[i][j][2]/L);
				double Ls=Math.pow(Ls2,p);
				faces[i][j][3]=(int)((amb_color[0]*3+dif_const*dif_color[0]+Ls*spe_color[0])*t.intensity*255);
				faces[i][j][4]=(int)((amb_color[1]*3+dif_const*dif_color[1]+Ls*spe_color[1])*t.intensity*255);
				faces[i][j][5]=(int)((amb_color[2]*3+dif_const*dif_color[2]+Ls*spe_color[2])*t.intensity*255);
			}
		}
	}*/
	
	public void clipping(){
		listIterator<double[][]> I=render_faces.getIterator();
		while(I.isValid()){
			DNode<double[][]> currentNode=I.getNode();
			double[][] face=currentNode.element();
			doublyLinkedList<double[]> evil=new doublyLinkedList<double[]>();
			doublyLinkedList<double[]> good=new doublyLinkedList<double[]>();
			for(int i=0;i<=2;i++){
				if(!shouldDraw(a,b,c,d,face[i])){
					evil.insertAtBack(new DNode<double[]>(face[i]));
				}else{
					good.insertAtBack(new DNode<double[]>(face[i]));
				}
			}
			switch(evil.getSize()){
				case 3:
					I.moveForward();
					render_faces.removeNode(currentNode);
					break;
				case 2:
					listIterator<double[]> I2=evil.getIterator();
					double[] goodV=good.getFront().element();
					double[] v1=calculateVertice(I2.getNode().element(),goodV,a,b,c,d);				
					I2.moveForward();
					double[] v2=calculateVertice(I2.getNode().element(),goodV,a,b,c,d);				
					double[][] newFace={v1,v2,goodV};
					currentNode.setElement(newFace);
					I.moveForward();
					break;
				case 1:
					I2=good.getIterator();
					double[] evilV=evil.getFront().element();
					double[] goodV1=I2.getNode().element();
					I2.moveForward();
					double[] goodV2=I2.getNode().element();
					v1=calculateVertice(evilV,goodV1,a,b,c,d);				
					v2=calculateVertice(evilV,goodV2,a,b,c,d);	
					double[][] newFace1={goodV1,v1,v2};
					double[][] newFace2={v1,v2,goodV2};
					currentNode.setElement(newFace2);
					DNode<double[][]> n=new DNode<double[][]>(newFace1);
					render_faces.insertBefore(currentNode,n);
					I.moveForward();
					break;
				default:
					I.moveForward();
					break;
			}
								
		}
	}
		
		//accept all vertices that are behind the plane(<=0)	 
		private boolean shouldDraw(double a,double b,double c,double d,double[] v){
			return (v[0]*a+v[1]*b+v[2]*c+d)<=0;
		}
		
		private double[] calculateVertice(double[] v1,double[] v2,double a,double b,double c,double d){
			double s1=a*v1[0]+b*v1[1]+c*v1[2];
			double s2=a*v2[0]+b*v2[1]+c*v2[2];
			double t=-(d+s2)/(s1-s2);
			double[] x=new double[6];
			x[0]=t*v1[0]+(1-t)*v2[0];
			x[1]=t*v1[1]+(1-t)*v2[1];
			x[2]=t*v1[2]+(1-t)*v2[2];
			x[3]=v1[3];
			x[4]=v1[4];
			x[5]=v1[5];
			return x;
		} 	
		
	public void projectTransformation(Camera cam){
		project_faces=new doublyLinkedList<int[][]>();
		listIterator<double[][]> I=render_faces.getIterator();
		while(I.isValid()){
			double[][] face=I.getNode().element();
			int[][] temp=new int[3][6];
			for(int i=0;i<=2;i++){				
				temp[i][0]=(int)(cam.getFocus()*face[i][0]/face[i][2])+cam.width/2;
				temp[i][1]=(int)(cam.getFocus()*face[i][1]/face[i][2])+cam.height/2;
				temp[i][2]=(int)face[i][2];
				temp[i][3]=(int)(face[i][3]);
				temp[i][4]=(int)(face[i][4]);
				temp[i][5]=(int)(face[i][5]);				
			}
			DNode<int[][]> n=new DNode<int[][]>(temp);
			project_faces.insertAtBack(n);
			I.moveForward();
		}
	}
	
	public void createTrapezoid(){
		listIterator<int[][]> I=project_faces.getIterator();
		while(I.isValid()){
			DNode<int[][]> node=I.getNode();
			int[][] face=node.element();
			//vertices in temp are in sorted order. The bigger the index, the smaller the py
			int[][] temp=sortVertices(face);
			int[] new_v=calculateMid(temp[0],temp[2],temp[1]);
			int[][] new_face1;
			int[][] new_face2;
			if(new_v[0]>temp[1][0]){
				new_face1=new int[][]{temp[0],temp[0],temp[1],new_v};
				new_face2=new int[][]{temp[1],new_v,temp[2],temp[2]};
			}else{
				new_face1=new int[][]{temp[0],temp[0],new_v,temp[1]};
				new_face2=new int[][]{new_v,temp[1],temp[2],temp[2]};
			}
			node.setElement(new_face1);
			DNode<int[][]> n=new DNode<int[][]>(new_face2);
			project_faces.insertBefore(node,n);						
			I.moveForward();
		}
	}
		
		private int[] calculateMid(int[] v1,int[] v2,int[] mid){
			double t=((double)(v2[1]-mid[1]))/(v2[1]-v1[1]);
			int x=(int)(v1[0]*t+v2[0]*(1-t));
			int z=(int)(v1[2]*t+v2[2]*(1-t));
			return new int[]{x,mid[1],z,mid[3],mid[4],mid[5]};			
		}	
		
		private int getMaxYVertex(int[][] face){
			if((face[0][1]>=face[1][1])&&(face[0][1]>=face[2][1])){
				return 0;
			}
			if((face[1][1]>=face[0][1])&&(face[1][1]>=face[2][1])){
				return 1;
			}
			return 2;			
		}
		
		private int[][] sortVertices(int[][] face){
			int maxIndex=getMaxYVertex(face);
			int[][] temp=new int[3][6];
			temp[0]=face[maxIndex];
			int y1=face[(maxIndex+1)%3][1];
			int y2=face[(maxIndex+2)%3][1];
			if(y1>=y2){
				temp[1]=face[(maxIndex+1)%3];
				temp[2]=face[(maxIndex+2)%3];
			}else{
				temp[1]=face[(maxIndex+2)%3];
				temp[2]=face[(maxIndex+1)%3];
			}			
			return temp;
		}
	//end
	
	public void drawShape(Camera cam, Graphics g, Color color){
	//	createRenderFaces();
	//	clipping(0,0,1,150);
		g.setColor(color);
		int[] projectedCoor1=new int[2];
		int[] projectedCoor2=new int[2];
		listIterator<int[][]> I=project_faces.getIterator();
		while(I.isValid()){
			int[][] face=I.getNode().element();						
			g.drawLine(face[0][0],face[0][1],face[1][0],face[1][1]);
			g.drawLine(face[1][0],face[1][1],face[2][0],face[2][1]);
			g.drawLine(face[2][0],face[2][1],face[0][0],face[0][1]);			
			I.moveForward();
		}	
	}
				
	//Below are supplementary methods, not necessary for this object	
	public void printVertices(){
		for(int i=0;i<vertices.length;i++){
        	System.out.println((int)(vertices[i][0]*10000)/10000.0+" "
        	+(int)(vertices[i][1]*10000)/10000.0+" "+(int)(vertices[i][2]*10000)/10000.0+" ");
        }
	}
	
	public void printFaces(){
	//	System.out.println("***faces["+3+"]["+1+"][3]="+faces[3][1][3]);
		for(int i=0;i<faces.length;i++){
				System.out.println("******Face******");
        	for(int j=0;j<faces[i].length;j++){
        		System.out.println((int)(faces[i][j][0]*1000)/1000.0+","+(int)(faces[i][j][1]*1000)/1000.0+","+
        		(int)(faces[i][j][2]*1000)/1000.0+","+(int)(faces[i][j][3]*1000)/1000.0+","+
        		(int)(faces[i][j][4]*1000)/1000.0+","+(int)(faces[i][j][5]*1000)/1000.0);
        	}
        	System.out.println();
        }
	}
	
	public void printRenderFaces(){
		listIterator<double[][]> I=render_faces.getIterator();
		while(I.isValid()){
			double[][] face=I.getNode().element();
			System.out.println("*********Face*********");
			for(int j=0;j<3;j++){
        		System.out.println((int)(face[j][0]*100)/100.0+","+(int)(face[j][1]*100)/100.0+","+
        		(int)(face[j][2]*100)/100.0+","+face[j][3]+","+face[j][4]+","+face[j][5]);
        	}
			I.moveForward();
		}
	}
	
	public void printOriginalRenderFaces(){
		listIterator<double[][]> I=render_faces_original.getIterator();
		while(I.isValid()){
			double[][] face=I.getNode().element();
			System.out.println("*********Original Face*********");
			for(int j=0;j<3;j++){
        		System.out.println((int)(face[j][0]*100)/100.0+","+(int)(face[j][1]*100)/100.0+","+
        		(int)(face[j][2]*100)/100.0+","+face[j][3]+","+face[j][4]+","+face[j][5]);
        	}
			I.moveForward();
		}
	}
	
	public void printProjectFaces(){
		listIterator<int[][]> I=project_faces.getIterator();
		while(I.isValid()){
			int[][] face=I.getNode().element();
			for(int j=0;j<face.length;j++){
        		System.out.println(face[j][0]+","+face[j][1]+","+face[j][2]+","+
        		face[j][3]+","+face[j][4]+","+face[j][5]);
        	}
			I.moveForward();
		}
	}
	
	public double[][] copy(double[][] x){
		double[][] y=new double[x.length][];
		for(int i=0;i<x.length;i++){
			y[i]=new double[x[i].length];
			for(int j=0;j<x[i].length;j++){
				y[i][j]=x[i][j];
			}
		}
		return y;
	}
	
	public double[] copy(double[] x){
		double[] y=new double[x.length];
		for(int i=0;i<x.length;i++){
			y[i]=x[i];			
		}
		return y;
	}
}