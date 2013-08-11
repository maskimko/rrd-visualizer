package RRDtools;


//import net.stamfest.rrd.CommandResult;
//import net.stamfest.rrd.RRDp;

public class RRDToolTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		try {
			//File rrdfile = new File("D:\\transfer\\pvs2_sensstatetemper_12198.rrd");
			//String basedirectory = "d:\\transfer\\";
			String basedirectory = "/home/maskimko/Downloads/";
			//String rrdfilename = "file.rrd";
			String rrdfilename = "pvs2_sensstatetemper_12198.rrd";
			System.out.println("Setting temporary directory to " + basedirectory);
			//RRDp rrd = new RRDp(System.getProperty("java.io.tmpdir"), "55");
			RRDp rrd = new RRDp(basedirectory, "55555");
			//String[] command = {"fetch", "D:\\transfer\\file.rrd", "AVERAGE", "-r", "1800", "-s", "-1d"};
			//String[] command = {"fetch", rrdfilename, "AVERAGE", "-s", "1373001900", "-e", "1373007900"};
			String[] command = {"fetch", rrdfilename, "AVERAGE", "-r", "300", "-s", "-19d", "-e", "-18d"};
			CommandResult result = rrd.command(command);
			
			if (!result.ok){
				System.out.println("Something went wrong " + result.error);
			} else {
				System.out.println("Result output:");
				System.out.println(result.output);
			}
			System.out.println("End of program!");
		} catch (Exception ex) {
			System.out.println("Error: " + ex.getMessage());
		}

	}

}
