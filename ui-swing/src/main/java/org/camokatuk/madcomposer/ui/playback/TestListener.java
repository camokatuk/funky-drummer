package org.camokatuk.madcomposer.ui.playback;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.camokatuk.madcomposer.engine.control.EngineControlRoom;
import org.camokatuk.madcomposer.engine.control.PlaybackController;

public class TestListener implements ActionListener
{
	private final PlaybackController playbackController;

	public TestListener(EngineControlRoom controlRoom)
	{
		this.playbackController = controlRoom.getPlaybackController();
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		this.playbackController.test();
	}
}
