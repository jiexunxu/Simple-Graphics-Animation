public class Camera{
	public double[] cam_pos=new double[3];
	public double[] cam_direction=new double[3];
	public int width;
	public int height;
	private double focus;
	
	public Camera(double x,double y,double z,int w,int h,double[] vector, double f){
		setCamLocation(x,y,z);
		setBounds(w,h);
		setCamDirection(vector);
		focus=-f;
	}
	
	public Camera(double x,double y,double z,int w,int h, double f){
		setCamLocation(x,y,z);
		setBounds(w,h);
		focus=-f;
	}
	
	public Camera(int w,int h,double[] vector){
		setBounds(w,h);
		setCamDirection(vector);
	}
	
	public Camera(int w,int h,double f){
		setBounds(w,h);
		focus=-f;
	}
	
	public Camera(int w,int h){
		setBounds(w,h);
		focus=-10.0;
	}
	
	public double getFocus(){
		return focus;
	}
	
	public void setBounds(int w,int h){
		width=w;
		height=h;
	}
	
	public void setCamLocation(double x, double y, double z){
		cam_pos[0]=x;
		cam_pos[1]=y;
		cam_pos[2]=z;
	}
	
	public void setCamDirection(double[] vector){
		cam_direction[0]=vector[0];
		cam_direction[1]=vector[1];
		cam_direction[2]=vector[2];
	}
	
	public void projectCoordinate(double[] input,int[] output){
		output[0]=(int)(input[0]*focus/input[2]+width/2);
		output[1]=(int)(input[1]*focus/input[2]+height/2);		
	}	
}