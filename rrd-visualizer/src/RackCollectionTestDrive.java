import java.util.Calendar;

import Racks.Rack;
import Racks.RackCollection;
import Racks.RackProperty;
import Racks.Radiation;
import Racks.RadiationTemp;



public class RackCollectionTestDrive {
	
	private static int racknumber = 0;
	
	private Rack createRackUglyCoordinates(int x1, int y1, int x2, int y2, RackProperty rackProp){
		int x = Math.min(x1, x2);
		int y = Math.min(y1, y2);
		int width = Math.abs(x1 -x2);
		int height = Math.abs(y1 -y2);
		Rack createdrack = new Rack("Rack " + racknumber, x, y, width, height, rackProp);
		racknumber++;
		return createdrack;
	}
	
	private Rack createRackUglyCoordinates(int x1, int y1, int x2, int y2){
		int x = Math.min(x1, x2);
		int y = Math.min(y1, y2);
		int width = Math.abs(x1 -x2);
		int height = Math.abs(y1 -y2);
		Rack createdrack = new Rack("Rack " + racknumber, x, y, width, height, null);
		racknumber++;
		return createdrack;
	}
	
	private static RackProperty createRackProperty(){
		
		RackProperty rp = new RackProperty("temperature", RadiationTemp.RadiationParametersTemp, Calendar.getInstance(), Calendar.getInstance()) {
			
			float value = (float) Math.random() * 100;
			
			@Override
			public float getValue(Calendar cal) throws IllegalArgumentException,
					NullPointerException {
				
				return value;
			}
			
			@Override
			public Radiation getRadiation(Rack rack, Calendar cal)
					throws IllegalArgumentException, NullPointerException {
			
				return new Radiation(rack, this.description, this.stop, this.radParam);
			}
			
			@Override
			public String getDescription() {
				
				return this.description;
			}
		};
		return rp;
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
	
			rc.add(rctd.createRackUglyCoordinates(i, 401, i + 24, 434, createRackProperty()));
			rc.add(rctd.createRackUglyCoordinates(i, 328, i + 24, 361, createRackProperty()));
			
		}
		
		return rc;
	}
}
