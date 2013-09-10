package RRDtools;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OSDiffMatcher {
	private static Matcher linematch;
	static private Pattern okpatLinux = Pattern.compile("^OK u:([0-9,]+) s:([0-9,]+) r:([0-9,]+)");
	//static private Pattern okpatLinux = Pattern.compile("^.*OK.*$");
	// static private Pattern infoPatLinux =
	// Pattern.compile("^\\s*(.*?)\\s*=\\s*(.*?)\\s*$");
	//static private Pattern headerpat = Pattern.compile("^\\s*(.*?)\\s*(.*?)\\s*$");
	private static Pattern headerpat = Pattern.compile("^\\s*(([A-Za-z0-9_]+[\\s\\t]*)+)$");
	static private Pattern okpatWindows = Pattern.compile("^OK\r*$");

	//TODO
	//Define other supported OS 
	
	public static boolean isOKMatch(String line, CommandResult result)
			throws Exception {
		boolean whatistheresult = false;
		String os = System.getProperty("os.name").toLowerCase();
		if (os.indexOf("win") >= 0) {
			linematch = okpatWindows.matcher(line);
			if (linematch.find()) {
				result.user = 0f;
				result.system = 0f;
				result.total = 0f;
				result.ok = true;
				whatistheresult = true;
			}
		} else if (os.indexOf("linux") >= 0) {
			linematch = okpatLinux.matcher(line);
			if (linematch.find()) {
				result.user = Float.parseFloat(linematch.group(1).replace(',', '.'));
				result.system = Float.parseFloat(linematch.group(2).replace(',', '.'));
				result.total = Float.parseFloat(linematch.group(3).replace(',', '.'));
				result.ok = true;
				whatistheresult = true;
			}
		} else
			throw new Exception("You use unsupported OS version!");
		return whatistheresult;
	}
	
	public static boolean isHeaderMatch(String line, CommandResult result)  throws Exception{
		boolean whatistheresult = false;
		if (!isOKMatch(line, result)) {
		linematch = headerpat.matcher(line);
		if (linematch.find()){
			String[] fields = linematch.group().split("\\s+");
			for (int i = 0; i < fields.length; i++){
				if (fields[i].length() != 0) result.addFieldName(fields[i]);
			}
			whatistheresult = true;
		}
		}
		
		return whatistheresult;
		
	}

}
