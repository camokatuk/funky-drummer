package org.camokatuk.madcomposer.engine.control;

import java.util.List;
import java.util.stream.Collectors;

import javax.sound.midi.MidiDevice;

import org.camokatuk.madcomposer.midi.MidiPlayer;

public class MidiPlayerController
{
	private final MidiPlayer midiPlayer;

	public MidiPlayerController(MidiPlayer midiPlayer)
	{
		this.midiPlayer = midiPlayer;
	}

	public List<MidiDevice> listMidiDevices()
	{
		return midiPlayer.getDeviceManager().getOutputDevices().values().stream()
			.sorted((d1, d2) -> d1.getDeviceInfo().getName().compareTo(d2.getDeviceInfo().getName())).collect(Collectors.toList());
	}

	public MidiDevice getCurrentDevice()
	{
		return midiPlayer.getMidiGenerator().getCurrentDevice();
	}

	public boolean trySwitchingDeviceTo(MidiDevice selectedItem)
	{
		return midiPlayer.trySwitchingDeviceTo(selectedItem);
	}
}