package org.camokatuk.madcomposer.ui;

import java.util.List;

import javax.sound.midi.MidiDevice;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JToolBar;

import org.camokatuk.madcomposer.engine.control.EngineControlRoom;
import org.camokatuk.madcomposer.ui.playback.BpmChangeListener;
import org.camokatuk.madcomposer.ui.playback.TestListener;

public class MainFrame extends JFrame
{
	private final EngineControlRoom controlRoom;

	public MainFrame(EngineControlRoom controlRoom)
	{
		this.controlRoom = controlRoom;
		this.initialize();
	}

	private void initialize()
	{
		this.addPlaybackControls(controlRoom);
		this.pack();
	}

	private void addPlaybackControls(EngineControlRoom controlRoom)
	{
		final JToolBar toolBar = new JToolBar();

		toolBar.add(this.constructDeviceSelector());

		JTextField textField = new JTextField(Integer.toString(controlRoom.getStateMonitor().getBpm()), 3);
		textField.getDocument().addDocumentListener(new BpmChangeListener(controlRoom));
		toolBar.add(textField);

		JButton button = new JButton("Test");
		button.addActionListener(new TestListener(controlRoom));
		toolBar.add(button);

		this.getContentPane().add(toolBar);
	}

	private JComboBox constructDeviceSelector()
	{
		List<MidiDevice> devices = controlRoom.getMidiSpammerController().listMidiDevices();
		devices.add(0, null);
		JComboBox<MidiDevice> selector = new JComboBox<>(devices.toArray(new MidiDevice[devices.size()]));
		selector.setSelectedItem(controlRoom.getMidiSpammerController().getCurrentDevice());

		DefaultListCellRenderer defaultListCellRenderer = new DefaultListCellRenderer();
		selector.setRenderer((list, midiDevice, index, isSelected, cellHasFocus) -> defaultListCellRenderer
			.getListCellRendererComponent(list, midiDevice == null ? "-- Select device --" : midiDevice.getDeviceInfo().getName(), index, isSelected,
				cellHasFocus));
		selector.addActionListener(e -> {
			boolean switchSuccessful = controlRoom.getMidiSpammerController().trySwitchingDeviceTo((MidiDevice) selector.getSelectedItem());
			if (!switchSuccessful)
			{
				selector.setSelectedItem(controlRoom.getMidiSpammerController().getCurrentDevice());
			}
		});
		return selector;
	}
}
