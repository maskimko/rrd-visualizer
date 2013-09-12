package Racks;

public class RadiationParameters implements RadiationParametersInterface {

	private float[] hr, sr, br, fractions;
	private int[] ar;
	
	
	RadiationParameters(float[] hueRange, float[] saturationRange, float[] brightnessRange, int[] alphaRange, float[] fractions){
		hr = hueRange;
		sr = saturationRange;
		br = brightnessRange;
		ar = alphaRange;
		this.fractions = fractions;
	}
	
	@Override
	public float[] getHueRange() {
		return hr;
	}

	@Override
	public float[] getSaturationRange() {
		return sr;
	}

	@Override
	public float[] getBrightnessRange() {
		return br;
	}

	@Override
	public int[] getAlphaRange() {
		return ar;
	}

	@Override
	public float[] getFractions() {
		return this.fractions;
	}

	
	private boolean checkIsOkBounds(float[] array, float max, float min){
		boolean isOk = true;
		for (int i = 0; i < array.length; i++) {
			if (array[i] > max || array[i] < min){
				isOk = false;
			}
		}
		return isOk;
	}
	
	private boolean checkIsOkBounds(int[] array, int max, int min){
		boolean isOk = true;
		for (int i = 0; i < array.length; i++) {
			if (array[i] > max || array[i] < min){
				isOk = false;
			}
		}
		return isOk;
	}
	

	

	
	private boolean checkIsOkOrder(float[] array){
		boolean isOk = true;
			if (array.length > 2) {
				if (array[0] < array[1]) {
					for (int i = 0; i < array.length - 1; i++){
						if (array[i] > array[i+1]) {
							isOk = false;
						}
					}
				} else  {
					for (int i = 0; i < array.length - 1; i++){
						if (array[i] < array[i+1]) {
							isOk = false;
						}
					}
				}
			} 
		return isOk;
	}
	
	private boolean checkIsOkOrder(int[] array){
		boolean isOk = true;
			if (array.length > 2) {
				if (array[0] < array[1]) {
					for (int i = 0; i < array.length - 1; i++){
						if (array[i] > array[i+1]) {
							isOk = false;
						}
					}
				} else  {
					for (int i = 0; i < array.length - 1; i++){
						if (array[i] < array[i+1]) {
							isOk = false;
						}
					}
				}
			} 
		return isOk;
	}
	
	@Override
	public void setHueRange(float[] hueRange) throws IllegalArgumentException {
		if(checkIsOkBounds(hueRange, 1, 2)){
			if (checkIsOkOrder(hueRange)) {
				hr = hueRange;
			} else {
				throw new IllegalArgumentException("Error: invalid array elements order");
			}
		} else {
			throw new IllegalArgumentException("Error: Range must be an array with length 1 or 2 elements.");
		}

	}

	@Override
	public void setSaturationRange(float[] saturationRange)
			throws IllegalArgumentException {
		if(checkIsOkBounds(saturationRange, 1, 2)){
			if (checkIsOkOrder(saturationRange)) {
				sr = saturationRange;
			} else {
				throw new IllegalArgumentException("Error: invalid array elements order");
			}
		} else {
			throw new IllegalArgumentException("Error: Range must be an array with length 1 or 2 elements.");
		}
	}

	@Override
	public void setBrightnessRange(float[] brightnessRange)
			throws IllegalArgumentException {
		if(checkIsOkBounds(brightnessRange, 1, 2)){
			if (checkIsOkOrder(brightnessRange)) {
				br = brightnessRange;
			} else {
				throw new IllegalArgumentException("Error: invalid array elements order");
			}
		} else {
			throw new IllegalArgumentException("Error: Range must be an array with length 1 or 2 elements.");
		}
	}

	@Override
	public void setAlphaRange(int[] alphaRange) throws IllegalArgumentException {
		if(checkIsOkBounds(alphaRange, 1, 2)){
			if (checkIsOkOrder(alphaRange)) {
				ar = alphaRange;
			} else {
				throw new IllegalArgumentException("Error: invalid array elements order");
			}
		} else {
			throw new IllegalArgumentException("Error: Range must be an array with length 1 or 2 elements.");
		}

	}

	@Override
	public void setFractions(float[] fractions) throws IllegalArgumentException {
		if(checkIsOkBounds(fractions, 1, 100)){
			if (checkIsOkOrder(fractions)) {
				this.fractions = fractions;
			} else {
				throw new IllegalArgumentException("Error: invalid array elements order");
			}
		} else {
			throw new IllegalArgumentException("Error: Range must be an array with length 1 or 2 elements.");
		}

	}

}
