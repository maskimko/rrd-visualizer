package RRDtools;

public class RRDRRTestDrive {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String[] fields = {"sensStateTemper" , "sensStateHymid" };
		String output = "1373005800: 2,5000000000e+001 3,2000000000e+001";
		try {
			RRDRR resrec = new RRDRR(output, fields);
			System.out.println(resrec.toString());
		}  catch (Exception ex) {
			System.out.print("Error: ");
			ex.printStackTrace();
		}
	}

}
