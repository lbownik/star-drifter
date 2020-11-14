//Star Drifter - a gravity base space game.
//Copyright (C) 2020  name of lukasz.bownik@gmail.com
//
//This program is free software; you can redistribute it and/or
//modify it under the terms of the GNU General Public License
//as published by the Free Software Foundation; either version 2
//of the License, or (at your option) any later version.
//
//This program is distributed in the hope that it will be useful,
//but WITHOUT ANY WARRANTY; without even the implied warranty of
//MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//GNU General Public License for more details.
//
//You should have received a copy of the GNU General Public License
//along with this program; if not, write to the Free Software
//Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.

package gravity.client.ui;

import static com.google.gwt.dom.client.Document.get;
import static com.google.gwt.dom.client.NativeEvent.BUTTON_LEFT;
import static com.google.gwt.dom.client.Style.Display.BLOCK;
import static com.google.gwt.dom.client.Style.Display.NONE;
import static com.google.gwt.user.client.Event.ONCLICK;
import static com.google.gwt.user.client.Event.ONMOUSEDOWN;
import static com.google.gwt.user.client.Event.ONMOUSEMOVE;
import static com.google.gwt.user.client.Event.ONMOUSEUP;
import static com.google.gwt.user.client.Event.setEventListener;
import static com.google.gwt.user.client.Event.sinkEvents;
import static java.lang.String.valueOf;

import java.util.List;

import com.google.gwt.dom.client.AudioElement;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.EventListener;

import gravity.client.app.Presenter;
import gravity.client.core.Planet;
import gravity.client.core.SpaceCraft;

/*******************************************************************************
 *
 ******************************************************************************/
public final class WebUI implements Presenter.UI {

	/****************************************************************************
	 *
	 ***************************************************************************/
	public WebUI() {

		this.spacePane = new SpaceCanvas(
				(type, button, x, y) -> onMouseEvent(type, button, x, y));

		this.winDialog = this.document.getElementById("winDialog");
		this.looseDialog = this.document.getElementById("looseDialog");
		this.blast = (AudioElement) this.document.getElementById("blast");
		this.launch = (AudioElement) this.document.getElementById("launch");
		this.logo = this.document.getElementById("logo");

		setClickListener("reloadLevel_win", this::reloadLevelClicked);
		setClickListener("reloadLevel_loose", this::reloadLevelClicked);
		setClickListener("nextLevel", this::nextLevelClicked);
		setClickListener("startButton", this::startClicked);
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	private void setClickListener(final String id, final EventListener listener) {

		final Element button = this.document.getElementById(id);
		sinkEvents(button, ONCLICK);
		setEventListener(button, listener);
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	private void onMouseEvent(int type, int button, int x, int y) {

		switch (type) {
		case ONMOUSEDOWN:
			this.leftButtonPressed = (button & BUTTON_LEFT) != 0;
			// fallthrough
		case ONMOUSEMOVE:
			if (this.leftButtonPressed) {
				this.presenter.aimingStarted(x, y);
			}
			return;
		case ONMOUSEUP:
			this.leftButtonPressed = false;
			this.presenter.aimingFinished(x, y);
			return;
		}
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	private void reloadLevelClicked(final Event e) {

		this.looseDialog.getStyle().setDisplay(NONE);
		this.winDialog.getStyle().setDisplay(NONE);
		this.presenter.playAgain();
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	private void nextLevelClicked(final Event e) {

		this.looseDialog.getStyle().setDisplay(NONE);
		this.winDialog.getStyle().setDisplay(NONE);
		this.presenter.playNext();
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	private void startClicked(final Event e) {

		this.logo.getStyle().setDisplay(NONE);
		this.presenter.start();
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	@Override
	public void setPresenter(final Presenter presenter) {

		this.presenter = presenter;
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	public int getVisibleWidth() {

		return this.spacePane.getVisibleWidth();
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	public int getVisibleHeight() {

		return this.spacePane.getVisibleHeight();
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	@Override
	public void refresh(final SpaceCraft craft, final List<Planet> planets,
			final int score, final int level, final int numOfLevels) {

		this.spacePane.refresh(craft, planets, score, level, numOfLevels);
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	@Override
	public void refreshWithSpeed(final SpaceCraft craft, final List<Planet> planets,
			final int score, final int level, final int numOfLevels) {

		this.spacePane.refreshWithSpeed(craft, planets, score, level, numOfLevels);
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	@Override
	public void refreshWithExplosion(final SpaceCraft craft, final List<Planet> planets,
			final int score, int level, int numOfLevels) {

		this.spacePane.refreshWithExplosion(craft, planets, score, level, numOfLevels);
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	@Override
	public void showFailure() {

		this.looseDialog.getStyle().setDisplay(BLOCK);
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	@Override
	public void showSuccess(final int score) {

		get().getElementById("score").setInnerText(valueOf(score));
		this.winDialog.getStyle().setDisplay(BLOCK);
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	@Override
	public void playExlposion() {

		// mute lounch first
		this.launch.pause();
		this.launch.setCurrentTime(0);

		this.blast.play();
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	@Override
	public void playLaunch() {

		this.launch.play();
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	private Presenter presenter;
	private final Document document = Document.get();
	private final SpaceCanvas spacePane;
	private boolean leftButtonPressed = false;
	private final Element winDialog;
	private final Element looseDialog;
	private final AudioElement launch;
	private final AudioElement blast;
	private final Element logo;
}
