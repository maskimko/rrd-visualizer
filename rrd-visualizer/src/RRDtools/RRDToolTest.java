package RRDtools;




public class RRDToolTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		try {
			//File rrdfile = new File("D:\\transfer\\pvs2_sensstatetemper_12198.rrd");
			String basedirectory = System.getProperty("user.dir");
			//String basedirectory = "/home/maskimko/Downloads";
			//String rrdfilename = "file.rrd";
			//String rrdfilename = "pvs2_sensstatetemper_12198.rrd";
			//String rrdfilename = "pvs2_sensstatetemper_12212.rrd";
			String rrdfilename = "d:\\transfer\\pvs2_sensstatetemper_12212.rrd";
			System.out.println("Setting temporary directory to " + basedirectory);
			//RRDp rrd = new RRDp(System.getProperty("java.io.tmpdir"), "55");
			RRDp rrd = new RRDp(basedirectory);
			//String[] command = {"fetch", "D:\\transfer\\file.rrd", "AVERAGE", "-r", "1800", "-s", "-1d"};
			String[] command = {"fetch", rrdfilename, "AVERAGE", "-s", "1373001900", "-e", "1373007900"};
			CommandResult result = rrd.command(command);
			
			if (!result.ok){
				System.out.println("Something went wrong " + result.error);
			} else {
				System.out.println("Result output:");
				System.out.println(result.getOutput());
				System.out.println("Object output:");
				for (RRDRR rr : result.getOutputObject()){
					System.out.println(rr);
				}
			}
			System.out.println("End of program!");
		} catch (Exception ex) {
			System.out.println("Error: " + ex.getMessage());
		}

	}

}
