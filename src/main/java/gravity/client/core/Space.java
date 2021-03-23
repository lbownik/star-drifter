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

package gravity.client.core;

import static java.util.Collections.unmodifiableList;
import static java.util.stream.Collectors.toList;
import static java.util.stream.IntStream.range;

import java.util.ArrayList;
import java.util.List;
import java.util.function.DoubleConsumer;

/*******************************************************************************
 * @author lukasz.bownik@gmail.com
 ******************************************************************************/
public final class Space {

	/****************************************************************************
	 *
	 ***************************************************************************/
	public Space(final int width, final int height, final DoubleConsumer forceSniffer) {

		this.width = width;
		this.height = height;
		this.spaceCraft = new SpaceCraft(new Position(25, this.height / 2), Speed.zero(),
				forceSniffer);
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	public void lounchSpaceCraft() {

		this.moveCraft = this::moveSpaceCraft;
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	public void incrementTime(final double interval) {

		movePlanets(interval);
		this.moveCraft.accept(interval);
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	private void moveSpaceCraft(final double intervel) {

		this.spaceCraft.moveBy(this.spaceCraft.gravitationalForceFrom(this.planets),
				intervel);
		this.spaceCraft.incrementTime(intervel);

		if (hasSpaceCraftCrashed()) {
			this.spaceCraft = new FireBall(this.spaceCraft.getCenter());
			this.moveCraft = this.spaceCraft::incrementTime;
		}
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	private void movePlanets(final double intervel) {

		final List<Force> forces = this.planets.stream()
				.map(planet -> planet.gravitationalForceFrom(this.planets))
				.collect(toList());

		range(0, forces.size()).forEach(i -> {
			this.planets.get(i).moveBy(forces.get(i), intervel);
			this.planets.get(i).incrementTime(intervel);
		});
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	public boolean isSpaceCraftBurning() {

		return this.spaceCraft.getName().equals(FireBall.name);
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	public boolean hasSpaceCraftFinishedBurning() {

		return isSpaceCraftBurning()
				&& this.spaceCraft.getPhaseIndex() >= FireBall.maxIndex;
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	public boolean isSpaceCraftBeyondBounds() {

		return !this.spaceCraft.getCenter().isWithin(this.width, this.height);
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	public boolean hasSpaceCraftPassedRightEdge() {

		return this.spaceCraft.getCenter().getX() >= this.width;
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	public boolean hasSpaceCraftCrashed() {

		return this.planets.stream()
				.anyMatch(planet -> this.spaceCraft.isCollidingWith(planet));
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	public List<? extends Body> getPlanets() {

		return unmodifiableList(this.planets);
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	public Body getSpaceCraft() {

		return this.spaceCraft;
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	void add(final Planet planet) {

		this.planets.add(planet);
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	private final int width;
	private final int height;
	private final List<Planet> planets = new ArrayList<>();
	private Body spaceCraft;
	private DoubleConsumer moveCraft = (d) -> {
	};
}
