public class Light{
	public static final int PARALLEL_LIGHT=1;
	private int type;
	private double[] dir;
	public double intensity;//a value between 0 and 1
	
	public Light(int t, double[] direction, double intense){
		intensity=intense;
		type=t;
		dir=new double[3];
		if((direction[0]==0)&&(direction[1]==0)&&(direction[2]==0)){
			dir=new double[3];
		}else{
			double L=Math.sqrt(Math.pow(direction[0],2)+Math.pow(direction[1],2)
			+Math.pow(direction[2],2));
			//double L=Math.sqrt(dir[0]*dir[0]+dir[1]*dir[1]+dir[2]*dir[2]);		
			dir[0]=direction[0]/L;
			dir[1]=direction[1]/L;
			dir[2]=direction[2]/L;
		}
	
	}
	
	public double[] getDirection(double x, double y, double z){
		switch(type){
			case PARALLEL_LIGHT:
				return dir;
			default: return null;
		}
	}
}