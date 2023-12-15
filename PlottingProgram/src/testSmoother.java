
public class testSmoother {
	public static void main(String[] args) 
	{
        smoother smoother = new smoother("salted_sqrt_points.csv", "smoothed_data.csv");
        smoother.smoothData(5); 
    }

}
