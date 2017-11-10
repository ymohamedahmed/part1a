public class Matrix {
	public static void main(String[] args) {
		float[][] test = new Matrix().create(20, 30);
	}

	// In Java values in a new array are automatically set to zero
	public float[][] create(int n, int m) {
		return new float[n][m];
	}
	
	// method to transpose an array
	public float[][] transpose(float[][] x) {
		int n = x.length;
		int m = x[0].length;
		float[][] result = new float[m][n];
		for(int i = 0; i < result.length; i++) {
			for(int j = 0; j < result[i].length; j++) {
				result[i][j] = x[j][i];
			}
		}
		return result;
	}
}
