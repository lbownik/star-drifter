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
import java.util.List;
import static java.util.stream.Collectors.toList;
import static java.util.stream.IntStream.range;

/*******************************************************************************
 *
 ******************************************************************************/
public final class Space {

	/****************************************************************************
	 *
	 ***************************************************************************/
	public Space(final int width, final int height, final SpaceCraft spaceCraft,
			final List<Planet> planets) {

		this.width = width;
		this.height = height;
		this.spaceCraft = spaceCraft;
		this.planets = unmodifiableList(planets);
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	public void incrementTime(final double intervel) {

		if (this.spaceCraft != null) {
			this.spaceCraft.moveBy(this.spaceCraft.gravitationalForceFrom(this.planets),
					intervel);
		}
		final List<Force> forces = this.planets.stream()
				.map(planet -> planet.gravitationalForceFrom(this.planets))
				.collect(toList());

		range(0, forces.size())
				.forEach(i -> this.planets.get(i).moveBy(forces.get(i), intervel));
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

		return this.planets.stream().anyMatch(this::hasSpaceCraftCrashed);
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	private boolean hasSpaceCraftCrashed(final Planet planet) {

		final double minDistance = this.spaceCraft.getRaduis() + planet.getRaduis();
		return this.spaceCraft.distanceTo(planet) <= minDistance;
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	public List<Planet> getPlanets() {

		return this.planets;
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	public SpaceCraft getSpaceCraft() {

		return this.spaceCraft;
	}
	/****************************************************************************
	 *
	 ***************************************************************************/
	public void setSpaceCraft(final SpaceCraft craft) {

		this.spaceCraft = craft;
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	private final int width;
	private final int height;
	private final List<Planet> planets;
	private SpaceCraft spaceCraft;
}