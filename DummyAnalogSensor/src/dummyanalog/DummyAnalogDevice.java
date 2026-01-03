package dummyanalog;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import PamController.PamControlledUnitSettings;
import PamController.PamSettingManager;
import PamController.PamSettings;
import PamController.SettingsNameProvider;
import PamView.dialog.PamDialogPanel;
import analoginput.AnalogDeviceParams;
import analoginput.AnalogDeviceType;
import analoginput.AnalogDevicesManager;
import analoginput.AnalogRangeData;
import analoginput.AnalogRangeData.AnalogType;
import analoginput.calibration.CalibrationData;
import dummyanalog.DummyAnalogSettings.SimType;
import mcc.mccjna.MCCUtils;
import analoginput.AnalogReadException;
import analoginput.AnalogSensorData;
import analoginput.AnalogSensorUser;
import analoginput.SensorChannelInfo;

public class DummyAnalogDevice implements AnalogDeviceType, PamSettings {
	
	public static final String devtype = "Dummy Analog Device";
	
	private List<AnalogRangeData> analogRanges;
	
	private Random random = new Random();
	
	private AnalogRangeData currentRange;
	
	private DummyAnalogSettings dummySettings = new DummyAnalogSettings();

	private AnalogDevicesManager analogDevicesManager;

	private SettingsNameProvider settingsNameProvider;

	private AnalogSensorUser analogSensorUser;
	
	public static final int MAXCHANNELS = 8;
	
	private double[] simSinPhase = new double[MAXCHANNELS];
	
	private DummyAnalogPanel dummyAnalogPanel;

	private int nChannels;

	private AnalogRangeData[] channelRanges;

	private int[] channels;

	private CalibrationData[] calibrationData;

	public DummyAnalogDevice(AnalogDevicesManager analogDevicesManager, SettingsNameProvider settingsNameProvider,
			AnalogSensorUser analogSensorUser) {
		this.analogDevicesManager = analogDevicesManager;
		this.settingsNameProvider = settingsNameProvider;
		this.analogSensorUser = analogSensorUser;
		PamSettingManager.getInstance().registerSettings(this);
	}

	@Override
	public String getDeviceType() {
		return devtype;
	}

	@Override
	public int getNumChannels() {
		return MAXCHANNELS;
	}

	@Override
	public List<AnalogRangeData> getAvailableRanges(int analogChan) {
		if (analogRanges == null) {
			analogRanges = new ArrayList<AnalogRangeData>();
			analogRanges.add(AnalogRangeData.makeBipolarRange(1, AnalogType.VOLTS));
			analogRanges.add(AnalogRangeData.makeBipolarRange(5, AnalogType.VOLTS));
		}
		return analogRanges;
	}

	@Override
	public boolean setChannelRange(AnalogRangeData analogRange) {
		currentRange = analogRange;
		return true;
	}

	@Override
	public AnalogSensorData readData(int item) throws AnalogReadException {
		if (channels.length <= item) {
			return null;
		}
		int chan = channels[item];
		if (chan < 0) {
			throw new AnalogReadException("Channel disabled " + item);
		}
		currentRange = channelRanges[item];
		if (currentRange == null) {
			return null;
		}
		double val = 0;
		if (dummySettings.simType == SimType.RANDOM) {
		  val = (random.nextDouble()-00.5)*2.;
		}
		else {
			val = Math.sin(simSinPhase[item]);
			simSinPhase[item] += Math.PI/20;
		}
		double valCal = val * currentRange.getRange()[1];
		
		return new AnalogSensorData(val, valCal);
	}

	@Override
	public PamDialogPanel getDevicePanel() {
		if (dummyAnalogPanel == null) {
			dummyAnalogPanel = new DummyAnalogPanel(this);
		}
		return dummyAnalogPanel;
	}

	@Override
	public AnalogDeviceParams getDeviceParams() {
		return dummySettings.getAnalogDeviceParams();
	}

	@Override
	public void setDeviceParams(AnalogDeviceParams deviceParams) {
		this.dummySettings.setAnalogDeviceParams(deviceParams);
	}

	@Override
	public void prepareDevice() {
		AnalogDeviceParams channelData = dummySettings.getAnalogDeviceParams();
		SensorChannelInfo[] channelInfo = analogSensorUser.getChannelNames();
		nChannels = channelInfo.length;
		channelRanges = new AnalogRangeData[nChannels];
		channels = new int[nChannels];
		calibrationData = new CalibrationData[nChannels];
		for (int i = 0; i < nChannels; i++) {
			channelRanges[i] = channelData.getItemRange(i);
			Integer chan = channelData.getItemChannel(i);
			if (chan == null) {
				chan = -1;
			}
			channels[i] = chan;
//			if (channels[i] >= 0 && bbRange != null) {
//				bbed549.setInputRange(channels[i], bbRange);
//			}
			calibrationData[i] = channelData.getCalibration(i);
		}
		
	}

	@Override
	public String getUnitName() {
		return settingsNameProvider.getUnitName();
	}

	@Override
	public String getUnitType() {
		return devtype;
	}

	@Override
	public Serializable getSettingsReference() {
		return dummySettings;
	}

	@Override
	public long getSettingsVersion() {
		return DummyAnalogSettings.serialVersionUID;
	}

	@Override
	public boolean restoreSettings(PamControlledUnitSettings pamControlledUnitSettings) {
		this.dummySettings = (DummyAnalogSettings) pamControlledUnitSettings.getSettings();
		return true;
	}

	/**
	 * @return the dummySettings
	 */
	public DummyAnalogSettings getDummySettings() {
		return dummySettings;
	}

	public AnalogSensorUser getSensorUser() {
		return analogSensorUser;
	}

}
