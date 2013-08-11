


public class RackCollectionTestDrive {
	
	private int racknumber = 0;
	
	private Rack createRackUglyCoordinates(int x1, int y1, int x2, int y2, float temperature){
		int x = Math.min(x1, x2);
		int y = Math.min(y1, y2);
		int width = Math.abs(x1 -x2);
		int height = Math.abs(y1 -y2);
		Rack createdrack = new Rack("Rack" + racknumber, x, y, width, height, temperature);
		racknumber++;
		return createdrack;
	}
	
	private Rack createRackUglyCoordinates(int x1, int y1, int x2, int y2){
		int x = Math.min(x1, x2);
		int y = Math.min(y1, y2);
		int width = Math.abs(x1 -x2);
		int height = Math.abs(y1 -y2);
		Rack createdrack = new Rack("Rack" + racknumber, x, y, width, height, 30);
		racknumber++;
		return createdrack;
	}
	
	public static RackCollection createStaticCollection(){
		RackCollectionTestDrive rctd = new RackCollectionTestDrive();
		RackCollection rc = new RackCollection();
		/*rc.add(rctd.createRackUglyCoordinates(196, 328, 223, 361));
		rc.add(rctd.createRackUglyCoordinates(230-7, 328, 256-7, 361));
		rc.add(rctd.createRackUglyCoordinates(256-7, 328, 275-7, 361));
		rc.add(rctd.createRackUglyCoordinates(275-7, 328, 294, 361));
		rc.add(rctd.createRackUglyCoordinates(294-7, 328, 313, 361));
		rc.add(rctd.createRackUglyCoordinates(313-7, 328, 331, 361));
		rc.add(rctd.createRackUglyCoordinates(331-7, 328, 371, 361));
		rc.add(rctd.createRackUglyCoordinates(371-7, 328, 390, 361));
		rc.add(rctd.createRackUglyCoordinates(390-7, 328, 409, 361));
		rc.add(rctd.createRackUglyCoordinates(409-7, 328, 428, 361));
		rc.add(rctd.createRackUglyCoordinates(428-7, 328, 448, 361));
		rc.add(rctd.createRackUglyCoordinates(448-7, 328, 448, 361));
		rc.add(rctd.createRackUglyCoordinates(470-7, 328, 473, 361));
		*/
		for (int i = 190; i < 496; i = i + 25) {
			float temperature = (float)Math.random()*100;
			rc.add(rctd.createRackUglyCoordinates(i, 401, i + 23, 434, temperature));
			rc.add(rctd.createRackUglyCoordinates(i, 328, i + 23, 361, temperature));
			//System.out.println("Temp: " + temperature);
		}
		rc.add(rctd.createRackUglyCoordinates(305, 228,  350, 261, 100));
		return rc;
	}
}
