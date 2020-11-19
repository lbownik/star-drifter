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

import gravity.client.core.Force;
import gravity.client.core.Planet;
import gravity.client.core.Space;
import gravity.client.core.SpaceCraft;
import gravity.client.core.SpaceFactory;
import gravity.client.core.Speed;

/*******************************************************************************
 * @author lukasz.bownik@gmail.com
 ******************************************************************************/
public final class Engine {

	/****************************************************************************
	 *
	 ***************************************************************************/
	public enum Result {
		notStarted, inPorgress, success, failedCrashed, failedMissed;
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	public Engine(final SpaceFactory spaceFactory, final int initialLevel) {

		this.spaceFactory = spaceFactory;
		loadLevel(initialLevel);
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	public void incrementTime(final double interval) {

		this.space.incrementTime(interval);
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	public Result getGameResult() {

		if(this.space.getSpaceCraft() == null) {
			return Result.notStarted;
		}else if (this.space.hasSpaceCraftCrashed()) {
			return Result.failedCrashed;
		} else if (this.space.isSpaceCraftBeyondBounds()) {
			if (this.space.hasSpaceCraftPassedRightEdge()) {
				return Result.success;
			} else {
				return Result.failedMissed;
			}
		} else {
			return Result.inPorgress;
		}
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	public void setSpaceCraftSpeed(double cursorX, double cursorY) {

		final Speed speed = new Speed(cursorX - getSpaceCraft().getCenter().getX(),
				cursorY - getSpaceCraft().getCenter().getY());
		speed.scaleBy(0.1);
		getSpaceCraft().setSpeed(speed);
	}
	/****************************************************************************
	 *
	 ***************************************************************************/
	public void lounchSpaceCraft() {

		this.space.setSpaceCraft(this.craft);
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	public void loadLevel(final int level) {

		
		this.currentLevel = level;

		this.space = this.spaceFactory.create(level);
		this.craft = new SpaceCraft(this.space.getInitialSpaceCraftPosition(), 
				Speed.zero(), this::sniffForce);
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	private void sniffForce(final SpaceCraft craft, final Force force) {

		this.currentScore += force.getValue() / 10;
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	public void reloadLevel() {

		this.currentScore = 0.0;

		loadLevel(this.currentLevel);
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	public void loadNextLevel() {

		this.totalScore += this.currentScore;
		this.currentScore = 0.0;

		loadLevel(
				this.currentLevel == getLevelsCount() ? 1 : this.currentLevel + 1);
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	public List<Planet> getPlanets() {

		return this.space.getPlanets();
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	public SpaceCraft getSpaceCraft() {

		return this.craft;
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	public int getLevelsCount() {

		return this.spaceFactory.getMaxLevel();
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	public int getCurrentLevelNo() {

		return this.currentLevel;
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	public int getCurrentScore() {

		return (int) this.currentScore;
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	public int getTotalScore() {

		return (int) this.totalScore;
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	private final SpaceFactory spaceFactory;
	private Space space;
	private SpaceCraft craft;
	private int currentLevel = 0;
	private double currentScore = 0;
	private double totalScore = 0;
	
}
