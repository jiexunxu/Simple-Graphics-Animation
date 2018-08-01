import java.awt.*;
import java.awt.event.*;
import java.applet.*;

public class MainScene extends MISApplet {
	//Data fields
	public double[][] zBuffer;
	public int[][][] frameBuffer;
	public int[][][] frameBufferStar;
	public double hour;
	public stackMatrix stack;
	public Matrix view_matrix;
	public double windStrength;
	
	public Light light;
	public Camera camera;
	
	public Cube ground;
		public Cube houseBody;
			public TriCylinder roof;
			public Cube chimeny;
			public Cube front_window;
			public Cube front_window_shelf;
			public Cube side_window;
			public Cube side_window_shelf;
			public Cube door;
			public Cube inside_door;
	public Sakura_Tree tree;
	public Chair chair1;
	public Chair chair2;
	public Chair chair3;
	public Chair chair4;
	public Cylinder table;
	public Cylinder table_support;
					
	//The following are the core methods to draw the entire scenerio		
	public void initFrame(double time){
		zBuffer=new double[W][H];		
		hour=(hour+0.25)%24;
		if((hour<=7)||(hour>=17)){
			if(((int)(hour)%2==0)&&(hour-(int)(hour)<0.05)){
				createBackground();
			}
		}else{
			createBackground();
		}
		if((hour<=7)||(hour>=17)){
			copyStarFrame();	
		}			
		createMainStructure(time);
	}
	
	public void createBackground(){			
		frameBuffer=new int[W][H][3];	
		frameBufferStar=new int[W][H][3];	
		for(int i=0;i<frameBuffer.length;i++){
			for(int j=0;j<frameBuffer[i].length;j++){
				if(((hour>=5)&&(hour<=7))||((hour>=17)&&(hour<=19))){
					frameBuffer[i][j][0]=0;
					frameBuffer[i][j][1]=0;
					frameBuffer[i][j][2]=(int)(255-Math.abs(hour-12)*255/7);
					frameBufferStar[i][j][0]=0;
					frameBufferStar[i][j][1]=0;
					frameBufferStar[i][j][2]=frameBuffer[i][j][2];
				}else if((hour>7)&&(hour<17)){
					frameBuffer[i][j][0]=(int)(Math.abs(hour-12)*10);
					frameBuffer[i][j][1]=frameBuffer[i][j][0];
					frameBuffer[i][j][2]=(int)(255-Math.abs(hour-12)*255/7);
					frameBufferStar[i][j][0]=frameBuffer[i][j][0];
					frameBufferStar[i][j][1]=frameBuffer[i][j][1];
					frameBufferStar[i][j][2]=frameBuffer[i][j][2];
				}
			}
		}
		if((hour<=7)||(hour>=17)){
			createStars();
		}
	}
							
	public void initialize(){
		hour=0;
		stack=new stackMatrix();
		view_matrix=new Matrix();
		windStrength=0;	
		createBackground();
		
		double[] amb_color=new double[3];//any term in amb_color should not be greater than 0.1
		double[] dif_color=new double[3];
		double[] spe_color=new double[3];
		
		amb_color=new double[]{0.01,0.08,0.01};
		dif_color=new double[]{0.08,0.79,0.08};
		spe_color=new double[]{0.03,0.3,0.03};
		ground=new Cube(new Matrix(),amb_color,dif_color,spe_color);
		
			amb_color=new double[]{0.099,0.096,0.064};
			dif_color=new double[]{0.945,0.929,0.628};
			spe_color=new double[]{0.98,0.96,0.66};
			houseBody=new Cube(new Matrix(),amb_color,dif_color,spe_color);
			
				amb_color=new double[]{0.1,0.01,0};
				dif_color=new double[]{0.99,0.09,0.01};
				spe_color=new double[]{1,0.1,0.02};
				roof=new TriCylinder(new Matrix(),amb_color,dif_color,spe_color,-1.5,-0.5,1.5,-0.5);
				
				amb_color=new double[]{0.08,0.08,0.08};
				dif_color=new double[]{0.7,0.7,0.7};
				spe_color=new double[]{0.8,0.8,0.8};
				chimeny=new Cube(new Matrix(),amb_color,dif_color,spe_color);
				
				dif_color=new double[]{0.4,0.4,0.4};										
				if((hour>=7)&&(hour<=17)){
					amb_color=new double[]{0.09,0.95,0.98};
				}else if((hour>17)){
					amb_color=new double[]{1,0.98,0.09};
				}else{
					amb_color=new double[3];
				}
				front_window=new Cube(new Matrix(),amb_color,dif_color,new double[3]);
				
				inside_door=new Cube(new Matrix(),amb_color,dif_color,new double[3]);

				side_window=new Cube(new Matrix(),amb_color,dif_color,spe_color);

				amb_color=new double[]{0.06,0.032,0.093};
				dif_color=new double[]{0.6,0.32,0.93};
				spe_color=new double[]{0.4,0.24,0.75};
				front_window_shelf=new Cube(new Matrix(),amb_color,dif_color,spe_color);
				
				side_window_shelf=new Cube(new Matrix(),amb_color,dif_color,spe_color);
				
				door=new Cube(new Matrix(),amb_color,dif_color,spe_color);
				
			tree=new Sakura_Tree(true,true);
			chair1=new Chair();	
			chair2=new Chair();	
			chair3=new Chair();	
			chair4=new Chair();
			
			amb_color=new double[]{0,0.025,0.05};
			dif_color=new double[]{0,0.25,0.5};	
			spe_color=new double[]{0,0,5,1};	
			table=new Cylinder(20,new Matrix(),amb_color,dif_color,spe_color);
			
			amb_color=new double[]{0,0.05,0.1};
			dif_color=new double[]{0,0.2,0.4};	
			spe_color=new double[3];	
			table_support=new Cylinder(16,new Matrix(),amb_color,dif_color,spe_color);		
	}
	
	public void createMainStructure(double time){
		double light_intensity;	
		if((hour<=6)||(hour>=18)){
			light_intensity=0.25-Math.abs(hour-12)*0.01;
		}else{
			light_intensity=1.0-Math.abs(hour-12)*Math.abs(hour-12)/48;	
		}
		light=new Light(Light.PARALLEL_LIGHT,calculateLightDirection(),light_intensity);
		camera=new Camera(W,H,800.0*H/600.0);
		Matrix m1=new Matrix();
		Matrix m2=new Matrix();//auxiliary matrix
		
		double[] amb_color;
		if((hour>=6)&&(hour<=18)){
			amb_color=new double[]{0.09,0.95,0.98};
		}else if((hour>17)){
			amb_color=new double[]{1,0.98,0.09};
		}else{
			amb_color=new double[3];
		}
		//The base(grassland)						
		m2.transformationMatrix(0,800*H/600.0,-3000);				
		m1.multiply(m2);	
		m1.multiply(view_matrix);		
		stack.push(m1);
		m2.transformationMatrix(0,15,0);
		m1.multiply(m2);
		m2.scaleMatrix(900,15,900);
		m1.multiply(m2);				
		ground.rendering(m1,light,camera);
		setFrame(ground);
		m1.identityMatrix();
		
			//The house			
			m1.multiply(stack.peek());
			m2.transformationMatrix(-300,-210,-500);
			m1.multiply(m2);
			stack.push(m1);
			m2.scaleMatrix(500,200,300);
			m1.multiply(m2);			
			houseBody.rendering(m1,light,camera);
			setFrame(houseBody);
			m1.identityMatrix();
			
				//The roof				
				m1.multiply(stack.peek());
				m2.transformationMatrix(0,-306.67,0);
				m1.multiply(m2);
				m2.xRotationMatrix(Math.PI/2);
				m1.multiply(m2);
				m2.zRotationMatrix(Math.PI/2);
				m1.multiply(m2);				
				m2.scaleMatrix(226.67,520,226.67);
				m1.multiply(m2);				
				roof.rendering(m1,light,camera);
				setFrame(roof);
				m1.identityMatrix();
				
				//The chimeny				
				m1.multiply(stack.peek());
				m2.transformationMatrix(-370,-550,0);
				m1.multiply(m2);
				m2.scaleMatrix(60,200,100);
				m1.multiply(m2);				
				chimeny.rendering(m1,light,camera);
				setFrame(chimeny);
				m1.identityMatrix();
			
				//The front window				
				m1.multiply(stack.peek());
				m2.transformationMatrix(200,-20,300);
				m1.multiply(m2);
				m2.scaleMatrix(90,92,1);
				m1.multiply(m2);
				front_window.setColor(amb_color,new double[3],new double[3]);
				front_window.rendering(m1,light,camera);
				setFrame(front_window);
				m1.identityMatrix();
				
				//The enviroment inside door
				m1.multiply(stack.peek());
				m2.transformationMatrix(-200,40,300);
				m1.multiply(m2);
				m2.scaleMatrix(90,160,1);
				m1.multiply(m2);
				inside_door.setColor(amb_color,new double[3],new double[3]);
				inside_door.rendering(m1,light,camera);
				setFrame(inside_door);
				m1.identityMatrix();
				
				//The side window
				m1.multiply(stack.peek());
				m2.transformationMatrix(-500,-20,0);
				m1.multiply(m2);
				m2.scaleMatrix(1,92,90);
				m1.multiply(m2);
				side_window.setColor(amb_color,new double[3],new double[3]);
				side_window.rendering(m1,light,camera);
				setFrame(side_window);
				m1.identityMatrix();
			
				//The front window shelf				
				m1.multiply(stack.peek());
				m2.transformationMatrix(200,86,320);
				m1.multiply(m2);
				m2.scaleMatrix(120,7,20);
				m1.multiply(m2);
				front_window_shelf.rendering(m1,light,camera);
				setFrame(front_window_shelf);
				m1.identityMatrix();
				
				//The side window shelf
				m1.multiply(stack.peek());
				m2.transformationMatrix(-520,86,0);
				m1.multiply(m2);
				m2.scaleMatrix(20,7,120);
				m1.multiply(m2);
				side_window_shelf.rendering(m1,light,camera);
				setFrame(side_window_shelf);
				m1.identityMatrix();
				
				//The door				
				m1.multiply(stack.peek());
				m2.transformationMatrix(-200,40,300);
				m1.multiply(m2);
				rotateDoor(m1,Math.PI/4);
				m2.scaleMatrix(90,160,6);
				m1.multiply(m2);
				door.rendering(m1,light,camera);
				setFrame(door);
				m1.identityMatrix();
				
			stack.pop();
			double wind_strength=getWindStrength(time);		
			m1.multiply(stack.peek());
			m2.transformationMatrix(650,-350,-550);
			m1.multiply(m2);
			m2.xRotationMatrix(Math.PI);
			m1.multiply(m2);
			m2.scaleMatrix(1.7,1.7,1.7);
			m1.multiply(m2);
			tree.rendering(m1,light,camera,wind_strength,getWindMatrix(wind_strength,time));
			drawEntireSakuraTree(tree);	
			m1.identityMatrix();
			
			m1.multiply(stack.peek());				
			m2.transformationMatrix(160,-91.9,160);
			m1.multiply(m2);
			m2.yRotationMatrix(-Math.PI/4);
			m1.multiply(m2);
			m2.scaleMatrix(3,3,3);
			m1.multiply(m2);			
			chair1.rendering(m1,light,camera);
			drawChair(chair1);
			m1.identityMatrix();
			
			m1.multiply(stack.peek());
			m2.transformationMatrix(740,-91.9,160);
			m1.multiply(m2);
			m2.yRotationMatrix(-Math.PI*3/4);
			m1.multiply(m2);
			m2.scaleMatrix(3,3,3);
			m1.multiply(m2);			
			chair2.rendering(m1,light,camera);
			drawChair(chair2);
			m1.identityMatrix();
			
			m1.multiply(stack.peek());
			m2.transformationMatrix(740,-91.9,740);
			m1.multiply(m2);
			m2.yRotationMatrix(-Math.PI*5/4);
			m1.multiply(m2);
			m2.scaleMatrix(3,3,3);
			m1.multiply(m2);			
			chair3.rendering(m1,light,camera);
			drawChair(chair3);
			m1.identityMatrix();
			
			m1.multiply(stack.peek());
			m2.transformationMatrix(160,-91.9,740);
			m1.multiply(m2);
			m2.yRotationMatrix(-Math.PI*7/4);
			m1.multiply(m2);
			m2.scaleMatrix(3,3,3);
			m1.multiply(m2);			
			chair4.rendering(m1,light,camera);
			drawChair(chair4);
			m1.identityMatrix();
			
			m1.multiply(stack.peek());
			m2.transformationMatrix(450,-140,450);
			m1.multiply(m2);
			m2.scaleMatrix(200,10,200);
			m1.multiply(m2);
			table.rendering(m1,light,camera);
			setFrame(table);
			m1.identityMatrix();
			
			m1.multiply(stack.peek());
			m2.transformationMatrix(450,-65,450);
			m1.multiply(m2);
			m2.scaleMatrix(100,65,100);
			m1.multiply(m2);
			table_support.rendering(m1,light,camera);
			setFrame(table_support);
			m1.identityMatrix();
			
		stack.pop();
	}
	
	//The following are animation methods
	public void rotateDoor(Matrix m, double angle){
		Matrix n=new Matrix();
		n.transformationMatrix(-90,0,0);
		m.multiply(n);
		n.yRotationMatrix(-angle);
		m.multiply(n);
		n.transformationMatrix(90,0,0);
		m.multiply(n);
	}
	
	public Matrix getWindMatrix(double wind, double time){
		Matrix m=new Matrix();
		Matrix n=new Matrix();
		
		n.xRotationMatrix(Math.sin(time/5)*wind*Math.PI/100);
		m.multiply(n);
		n.zRotationMatrix(Math.sin(time/5)*wind*Math.PI/100);
		m.multiply(n);
		return m;
	}
	
	public double getWindStrength(double time){
		return windStrength+Math.sin(time);
	}
	
	public void keyActions(int key){
		Matrix n=new Matrix();
		switch(key){
			case (int)('i'):
				n.xRotationMatrix(0.09);
				view_matrix.multiply(n);
				break;
			case (int)('k'):
				n.xRotationMatrix(-0.09);
				view_matrix.multiply(n);
				break;
			case (int)('j'):
				n.yRotationMatrix(0.09);
				view_matrix.multiply(n);
				break;
			case (int)('l'):
				n.yRotationMatrix(-0.09);
				view_matrix.multiply(n);
				break;
			case (int)('w'):
				n.transformationMatrix(0,0,-20);
				view_matrix.multiply(n);
				break;
			case (int)('s'):
				n.transformationMatrix(0,0,20);
				view_matrix.multiply(n);
				break;
			case (int)('a'):
				n.transformationMatrix(-20,0,0);
				view_matrix.multiply(n);
				break;
			case (int)('d'):
				n.transformationMatrix(20,0,0);
				view_matrix.multiply(n);
				break;
			case (int)(' '):
				view_matrix.identityMatrix();
				break;
			case (int)('='):
				if(windStrength<=9.75){
					windStrength=windStrength+0.25;
				}				
				break;
			case (int)('-'):
				if(windStrength>=0.25){
					windStrength=windStrength-0.25;
				}			
				break;
			default:break;
		}
	}
	
	//The following are helper methods 		
	public boolean keyDown(Event e, int key){
		keyActions(key);
		return true;
	}
	
	public void drawEntireSakuraTree(Sakura_Tree tree){
		doublyLinkedList<Shapes> list=tree.getAllElements();
		listIterator<Shapes> I=list.getIterator();
		while(I.isValid()){
			setFrame(I.getNode().element());
			I.moveForward();
		}
	}
	
	public void drawChair(Chair chair){
		doublyLinkedList<Shapes> list=chair.getAllElements();
		listIterator<Shapes> I=list.getIterator();
		while(I.isValid()){
			setFrame(I.getNode().element());
			I.moveForward();
		}
	}
	
	public void setFrame(Shapes cu){
		listIterator<int[][]> I=cu.project_faces.getIterator();
		while(I.isValid()){
			int[][] face=I.getNode().element();
			for(int i=face[2][1];i<=face[0][1];i++){
				double t=((double)(i-face[2][1]))/(face[0][1]-face[2][1]);
				int x_left=(int)(t*face[0][0]+(1-t)*face[2][0]);
				int x_right=(int)(t*face[1][0]+(1-t)*face[3][0]);
				double z_left=t*face[0][2]+(1-t)*face[2][2];
				double z_right=t*face[1][2]+(1-t)*face[3][2];
				for(int j=x_left;j<=x_right;j++){
					if((j>=0)&&(j<W)&&(i>=0)&&(i<H)){
						double t2=((double)j-x_left)/(x_right-x_left);
						double z=z_left*(1-t2)+z_right*t2;						
						if(1.0/z<=1.0/zBuffer[j][i]){
							zBuffer[j][i]=z;
							frameBuffer[j][i][0]=face[0][3];
							frameBuffer[j][i][1]=face[0][4];
							frameBuffer[j][i][2]=face[0][5];							
						}
					}					
				}
			}
			I.moveForward();		
		}
	}
	
	public double[] calculateLightDirection(){
		double theta;
		double[] dir=new double[3];
		if((hour>=6)&&(hour<=18)){
			theta=(hour-6)/4.587+0.262;
			dir[0]=Math.cos(theta);
			dir[1]=-Math.sin(theta);
			dir[2]=0;
		//}else if(hour>=18){
			//theta=1.57+(24-hour)/4.587;
		}
		//	theta=(6-hour)/4.587+0.262;							
		return dir;
	}	
	
	public void setPixel(int x,int y,int[] rgb){
		rgb[0]=frameBuffer[x][y][0]; 
		rgb[1]=frameBuffer[x][y][1]; 
		rgb[2]=frameBuffer[x][y][2]; 
	}
	
	public int random(int min,int max){
		return (int)(Math.random()*(max-min+1))+min;
	}
	
	public void createStars(){		
		for(int i=0;i<(int)((5.0*Math.abs(hour-12)-random(10,20))*H/600);i++){
			createStar(random(15,W-16),random(15,H-16),random(1,7));
		}
	}
	
	public void createStar(int x,int y,int w){
		for(int i=x-w/2;i<=x+w/2;i++){
			frameBuffer[i][y][0]=255;
			frameBuffer[i][y][1]=255;
			frameBuffer[i][y][2]=255;
			frameBufferStar[i][y][0]=255;
			frameBufferStar[i][y][1]=255;
			frameBufferStar[i][y][2]=255;
		}
		
		for(int i=y-w/2;i<=y+w/2;i++){
			frameBuffer[x][i][0]=255;
			frameBuffer[x][i][1]=255;
			frameBuffer[x][i][2]=255;
			frameBufferStar[x][i][0]=255;
			frameBufferStar[x][i][1]=255;
			frameBufferStar[x][i][2]=255;
		}
	}
	
	public void copyStarFrame(){
		for(int i=0;i<frameBufferStar.length;i++){
			for(int j=0;j<frameBufferStar[i].length;j++){
				frameBuffer[i][j][0]=frameBufferStar[i][j][0];
				frameBuffer[i][j][1]=frameBufferStar[i][j][1];
				frameBuffer[i][j][2]=frameBufferStar[i][j][2];
			}
		}
	}
}