package dummyanalog;

import PamController.SettingsNameProvider;
import PamModel.PamDependency;
import PamModel.PamPluginInterface;
import analoginput.AnalogDevicePlugin;
import analoginput.AnalogDeviceType;
import analoginput.AnalogDevicesManager;
import analoginput.AnalogSensorUser;

public class DummyAnalogPlugin implements AnalogDevicePlugin {

	private String jarFile;

	@Override
	public String getDefaultName() {
		return DummyAnalogDevice.devtype;
	}

	@Override
	public String getHelpSetName() {
		return "help/DummyAnalogHelp.hs";
	}

	@Override
	public void setJarFile(String jarFile) {
		this.jarFile = jarFile;
	}

	@Override
	public String getJarFile() {
		return jarFile;
	}

	@Override
	public String getDeveloperName() {
		return "Doug Gillespie";
	}

	@Override
	public String getContactEmail() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getVersion() {
		return "0.0";
	}

	@Override
	public String getPamVerDevelopedOn() {
		return "2.02.18";
	}

	@Override
	public String getPamVerTestedOn() {
		return "2.02.18";
	}

	@Override
	public String getAboutText() {
		return "Dummy analog device plugin";
	}

	@Override
	public AnalogDeviceType createAnalogDevice(AnalogDevicesManager analogDevicesManager,
			SettingsNameProvider settingsNameProvider, AnalogSensorUser analogSensorUser) {
		return new DummyAnalogDevice(analogDevicesManager, settingsNameProvider, analogSensorUser);
	}



}
