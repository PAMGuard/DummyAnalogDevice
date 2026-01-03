package dummyanalog;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.TitledBorder;

import PamView.dialog.PamDialogPanel;
import PamView.dialog.PamGridBagContraints;
import analoginput.AnalogDeviceParams;
import analoginput.AnalogSensorUser;
import analoginput.swing.AnalogChannelPanel;
import dummyanalog.DummyAnalogSettings.SimType;

public class DummyAnalogPanel implements PamDialogPanel {

	private DummyAnalogDevice dummyAnalogDevice;
	
	private JPanel mainPanel;
	
	private JRadioButton random, sin;

	private AnalogChannelPanel channelPanel;

	public DummyAnalogPanel(DummyAnalogDevice dummyAnalogDevice) {
		this.dummyAnalogDevice = dummyAnalogDevice;
		random = new JRadioButton("Random numbers");
		sin = new JRadioButton("Sin Wave");
		ButtonGroup bg = new ButtonGroup();
		bg.add(random);
		bg.add(sin);
		mainPanel = new JPanel(new BorderLayout());
		
		JPanel topPanel = new JPanel(new GridBagLayout());
		topPanel.setBorder(new TitledBorder("Dummy sensor settings"));
		GridBagConstraints c = new PamGridBagContraints();
		topPanel.add(random, c);
		c.gridy++;
		topPanel.add(sin, c);
		mainPanel.add(BorderLayout.NORTH, topPanel);
		
		AnalogSensorUser sensorUser = dummyAnalogDevice.getSensorUser();
		if (sensorUser != null) {
			channelPanel = new AnalogChannelPanel(sensorUser.getChannelNames(), DummyAnalogDevice.MAXCHANNELS, dummyAnalogDevice.getAvailableRanges(0), true);
			mainPanel.add(BorderLayout.SOUTH, channelPanel.getDialogComponent());
		}
	}

	@Override
	public JComponent getDialogComponent() {
		return mainPanel;
	}

	@Override
	public void setParams() {
		DummyAnalogSettings p = dummyAnalogDevice.getDummySettings();
		random.setSelected(p.simType == SimType.RANDOM);
		sin.setSelected(p.simType == SimType.SINWAVE);
		if (channelPanel != null) {
			channelPanel.setDeviceParams(dummyAnalogDevice.getDeviceParams());
		}
	}

	@Override
	public boolean getParams() {
		DummyAnalogSettings p = dummyAnalogDevice.getDummySettings();
		if (random.isSelected()) {
			p.simType = SimType.RANDOM;
		}
		else {
			p.simType = SimType.SINWAVE;
		}
		if (channelPanel != null) {
			AnalogDeviceParams newP = channelPanel.getDeviceParams(dummyAnalogDevice.getDeviceParams());
			dummyAnalogDevice.setDeviceParams(newP);
		}
		return true;
	}

}
