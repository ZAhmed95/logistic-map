import java.lang.Math;
/*
 * Description: this class contains a method PsuedoRandomNumbers() which populates an array of doubles
 * following the equation x(n+1) = A[x(n)][1 - x(n)], 
 * where the coefficient A and the initial value x(0) are specified by the user
 * for appropriate results, A must be in range [1,4] and the initial value in range [0,1]
 * appropriate values of A and x(0) result in, after a few iterations of the equation,
 * numbers that appear seemingly random   
 */
public class RandomNumbers {

	/*
	 * this function populates the array "nums" with results of the equation x(n+1) = A[x(n)][1 - x(n)]:
	 * nums has 500 rows and 3 columns
	 * column 0 holds iteration number
	 * column 1 holds results of equation using initial value 1
	 * column 2 holds results of equation using initial value 2
	 * where A is the input parameter "a", and x(0) is input parameter "initial"
	 */
	static Double[][] psuedoRandomNumbers(double a, double initial1, double initial2)
	{
		Double[][] nums = new Double[500][4];
		//populate column 0, which holds iteration number
		for (int i = 0; i < nums.length; i++)
		{
			nums[i][0] = (double)i;
		}
		
		//set initial values
		nums[0][1] = initial1;
		nums[0][2] = initial2;
		//set initial difference
		nums[0][3] = initial1 - initial2;
		
		//fill rest of array according to the equation
		for (int i = 0; i < nums.length - 1; i++)
		{
			// x(n+1) = A[x(n)][1 - x(n)]
			nums[i + 1][1] = a * nums[i][1] * (1.0 - nums[i][1]); //calculate value for column 1
			nums[i + 1][2] = a * nums[i][2] * (1.0 - nums[i][2]); //calculate value for column 2
			nums[i + 1][3] = nums[i + 1][1] - nums[i + 1][2]; //calculate difference
		}
		
		//round results to 6 decimal places
		for (int i = 0; i < nums.length; i++)
		{
			for (int j = 1; j < nums[0].length; j++)
			{
				nums[i][j] = (double)Math.round(nums[i][j] * 1000000) / 1000000.0;
			}
		}
		
		return nums;
	}
	
	//takes an input array of Doubles "nums", and returns a 2D Double array,
	//where the first column holds nums[i], and the second column holds nums[i + 1]
	static Double[][] phaseDiagram(Double[] nums)
	{
		Double[][] phase = new Double[nums.length - 1][2];
		
		for (int i = 0; i < nums.length - 1; i++)
		{
			phase[i][0] = nums[i];
			phase[i][1] = nums[i + 1];
		}
		return phase;
	}
	
	//returns true if parameter s is a double, false otherwise
	static boolean checkDouble(String s)
	{
		try
		{
			//attempt to parse string into double
			Double.parseDouble(s);
		}
		catch (Exception e)
		{
			//if double parse fails, return false
			return false;
		}
		return true;
	}
}
