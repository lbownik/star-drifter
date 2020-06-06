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

import static gravity.client.core.Planet.Type.blackHole;
import static gravity.client.core.Planet.Type.earthLike;
import static gravity.client.core.Planet.Type.gas;
import static gravity.client.core.Planet.Type.ice;
import static gravity.client.core.Planet.Type.rocky;
import static gravity.client.core.Planet.Type.star;
import static java.util.Arrays.asList;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import gravity.client.core.Force;
import gravity.client.core.Planet;
import gravity.client.core.Point;
import gravity.client.core.Space;
import gravity.client.core.SpaceCraft;
import gravity.client.core.Speed;
import gravity.client.core.StaticPlanet;

/*******************************************************************************
 *
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
	public Engine(final int spaceWidth, final int spaceHeight, final int initialLevel) {

		this.spaceWidth = spaceWidth;
		this.spaceHeight = spaceHeight;

		//this.constellations.add(this::getPlanetConstellation0);
		this.constellations.add(this::getPlanetConstellation1);
		this.constellations.add(this::getPlanetConstellation2);
		this.constellations.add(this::getPlanetConstellation3);
		this.constellations.add(this::getPlanetConstellation4);
		this.constellations.add(this::getPlanetConstellation5);
		this.constellations.add(this::getPlanetConstellation6);
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

		this.craft = new SpaceCraft(initialSpaceCraftPosition(), Speed.zero(),
				this::sniffForce);
		final List<Planet> planets = this.constellations.get(level).get();

		this.space = new Space(this.spaceWidth, this.spaceHeight, null, planets);
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	private Point initialSpaceCraftPosition() {

		return new Point(20, this.spaceHeight / 2);
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
				this.currentLevel == (getLevelsCount() - 1) ? 0 : this.currentLevel + 1);
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	private List<Planet> getPlanetConstellation0() {

		return asList(
				new StaticPlanet(rocky, 200, 50,
						new Point(this.spaceWidth / 7, this.spaceHeight / 7), Speed.zero()),
				new StaticPlanet(ice, 100, 50,
						new Point(2 * this.spaceWidth / 7, 2 * this.spaceHeight / 7),
						Speed.zero()),
				new StaticPlanet(earthLike, 100, 50,
						new Point(3 * this.spaceWidth / 7, this.spaceHeight / 7),
						Speed.zero()),
				new StaticPlanet(gas, 100, 50,
						new Point(4 * this.spaceWidth / 7, 6 * this.spaceHeight / 7),
						Speed.zero()),
				new StaticPlanet(star, 100, 50,
						new Point(5 * this.spaceWidth / 7, 5 * this.spaceHeight / 7),
						Speed.zero()),
				new StaticPlanet(blackHole, 100, 50,
						new Point(6 * this.spaceWidth / 7, 6 * this.spaceHeight / 7),
						Speed.zero()));
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	private List<Planet> getPlanetConstellation1() {

		return asList(new StaticPlanet(rocky, 100, 50,
				new Point(this.spaceWidth / 2, this.spaceHeight / 2), Speed.zero()));
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	private List<Planet> getPlanetConstellation2() {

		return asList(
				new StaticPlanet(rocky, 100, 50,
						new Point(this.spaceWidth / 2, this.spaceHeight / 2), Speed.zero()),
				new StaticPlanet(ice, 100, 50,
						new Point(5 * this.spaceWidth / 7, 5 * this.spaceHeight / 7),
						Speed.zero()),
				new StaticPlanet(earthLike, 100, 50,
						new Point(5 * this.spaceWidth / 7, this.spaceHeight / 7),
						Speed.zero()));
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	private List<Planet> getPlanetConstellation3() {

		return asList(
				new StaticPlanet(gas, 100, 50,
						new Point(this.spaceWidth / 4, this.spaceHeight / 2), Speed.zero()),
				new StaticPlanet(blackHole, 5000, 50,
						new Point(5 * this.spaceWidth / 7, 5 * this.spaceHeight / 7),
						Speed.zero()));
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	private List<Planet> getPlanetConstellation4() {

		return asList(
				new StaticPlanet(gas, 100, 50,
						new Point(this.spaceWidth / 2, this.spaceHeight / 2), Speed.zero()),
				new StaticPlanet(ice, 100, 50,
						new Point(5 * this.spaceWidth / 7, 5 * this.spaceHeight / 7),
						Speed.zero()),
				new StaticPlanet(earthLike, 100, 50,
						new Point(5 * this.spaceWidth / 7, this.spaceHeight / 7),
						Speed.zero()),
				new StaticPlanet(blackHole, 1000, 50,
						new Point(2 * this.spaceWidth / 7, this.spaceHeight / 7),
						Speed.zero()),
				new StaticPlanet(star, 1000, 50,
						new Point(9 * this.spaceWidth / 10, this.spaceHeight / 2),
						Speed.zero()));
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	private List<Planet> getPlanetConstellation5() {

		return asList(
				new Planet(gas, 500, 50,
						new Point(this.spaceWidth / 2, this.spaceHeight / 7), new Speed(15, 0)),
				new Planet(rocky, 500, 50,
						new Point(this.spaceWidth / 2, 6 * this.spaceHeight / 7),
						new Speed(-15, 0)));
	}
	/****************************************************************************
	 *
	 ***************************************************************************/
	private List<Planet> getPlanetConstellation6() {

		return asList(
				new StaticPlanet(blackHole, 5000, 50,
						new Point(this.spaceWidth / 2, this.spaceHeight / 2), Speed.zero()),
				new Planet(ice, 100, 50,
						new Point(this.spaceWidth / 2, this.spaceHeight / 7),
						new Speed(-60, 0)));
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

		return this.constellations.size();
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	public int getCurrentLevelNo() {

		return this.currentLevel + 1;
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
	private final int spaceWidth;
	private final int spaceHeight;
	private Space space;
	private SpaceCraft craft;
	private int currentLevel = 0;
	private double currentScore = 0;
	private double totalScore = 0;
	private final List<Supplier<List<Planet>>> constellations = new ArrayList<>();
}
