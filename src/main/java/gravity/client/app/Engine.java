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

package gravity.client.app;

import java.util.List;

import gravity.client.core.Body;
import gravity.client.core.Space;
import gravity.client.core.SpaceFactory;
import gravity.client.core.Speed;

/*******************************************************************************
 * @author lukasz.bownik@gmail.com
 ******************************************************************************/
final class Engine {

	/****************************************************************************
	 *
	 ***************************************************************************/
	Engine(final SpaceFactory spaceFactory, final int initialLevel) {

		this.spaceFactory = spaceFactory;
		loadLevel(initialLevel);
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	void incrementTime(final int interval_ms, final Presenter presenter) {

		final double refreshRateIndependentInterval = interval_ms * gamePace;
		this.space.incrementTime(refreshRateIndependentInterval);

		if (this.space.isSpaceCraftBeyondBounds()) {
			if (this.space.hasSpaceCraftPassedRightEdge()) {
				presenter.success();
			} else {
				presenter.failure();
			}
		} else {
			if (this.space.hasSpaceCraftCrashed()) {
				presenter.crashed();
			}
			if (this.space.hasSpaceCraftFinishedBurning()) {
				presenter.failure();
			} else {
				presenter.inProgress();
			}
		}
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	void setSpaceCraftSpeed(double cursorX, double cursorY) {

		final Speed speed = new Speed(cursorX - getSpaceCraft().getCenter().getX(),
				cursorY - getSpaceCraft().getCenter().getY());
		speed.scaleBy(0.1);
		this.space.setSpaceCraftSpeed(speed);
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	void lounchSpaceCraft() {

		this.space.lounchSpaceCraft();
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	void loadLevel(final int level) {

		this.currentLevel = level;

		this.space = this.spaceFactory.create(level, this::sniffForce);
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	private void sniffForce(final double force) {

		this.currentScore += force / 10;
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	void reloadLevel() {

		this.currentScore = 0.0;

		loadLevel(this.currentLevel);
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	void loadNextLevel() {

		this.totalScore += this.currentScore;
		this.currentScore = 0.0;

		loadLevel(this.currentLevel == getLevelsCount() ? 1 : this.currentLevel + 1);
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	List<? extends Body> getPlanets() {

		return this.space.getPlanets();
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	Body getSpaceCraft() {

		return this.space.getSpaceCraft();
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	int getLevelsCount() {

		return this.spaceFactory.getMaxLevel();
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	int getCurrentLevelNo() {

		return this.currentLevel;
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	int getCurrentScore() {

		return (int) this.currentScore;
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	int getTotalScore() {

		return (int) this.totalScore;
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	private final SpaceFactory spaceFactory;
	private Space space;
	private int currentLevel = 0;
	private double currentScore = 0;
	private double totalScore = 0;

	private final double gamePace = 0.02;
}
