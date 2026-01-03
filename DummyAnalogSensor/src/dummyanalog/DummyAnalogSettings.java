package dummyanalog;

import java.io.Serializable;

import analoginput.AnalogDeviceParams;

public class DummyAnalogSettings implements Serializable, Cloneable {

	public static final long serialVersionUID = 1L;

	public enum SimType {RANDOM, SINWAVE};

	private AnalogDeviceParams analogDeviceParams;
	
	public SimType simType = SimType.RANDOM;

	@Override
	protected DummyAnalogSettings clone() {
		try {
			return (DummyAnalogSettings) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * @return the analogDeviceParams
	 */
	public AnalogDeviceParams getAnalogDeviceParams() {
		if (analogDeviceParams == null) {
			analogDeviceParams = new AnalogDeviceParams();
		}
		return analogDeviceParams;
	}

	/**
	 * @param analogDeviceParams the analogDeviceParams to set
	 */
	public void setAnalogDeviceParams(AnalogDeviceParams analogDeviceParams) {
		this.analogDeviceParams = analogDeviceParams;
	}
	

}
