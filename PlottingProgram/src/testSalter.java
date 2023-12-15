public class testSalter 
{
	public static void main(String[] args) 
	{
        salter salter = new salter("sqrt_points.csv", "salted_sqrt_points.csv");
        salter.saltData(2.0); // Noise level can be adjusted
    }

}
