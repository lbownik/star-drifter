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

import static gravity.client.core.Body.angleCirculatesAt;
import static gravity.client.core.Phase.backwardLooping;
import static gravity.client.core.Phase.constant;
import static gravity.client.core.Phase.forwardBackwardLooping;
import static gravity.client.core.Phase.forwardLooping;
import static gravity.client.core.Phase.forwardStopping;
import static gravity.client.core.Planet.Type.rocky;

import java.util.ArrayList;
import java.util.List;
import java.util.function.DoubleConsumer;
import java.util.function.Supplier;

/*******************************************************************************
 * @author lukasz.bownik@gmail.com
 *******************************************************************************/
public final class PhasingPlanetsSpaceFactory implements SpaceFactory {

	/****************************************************************************
	 *
	 ***************************************************************************/
	public PhasingPlanetsSpaceFactory(final int spaceWidth, final int spaceHeight) {

		this.spaceWidth = spaceWidth;
		this.spaceHeight = spaceHeight;

		this.constellations.add(() -> getConstellation(0.2));
		this.constellations.add(() -> getConstellation(0.4));
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	@Override
	public Space create(final int level, final DoubleConsumer forceSniffer) {

		return this.constellations.get(level - 1).get();

	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	private Space getConstellation(final double phasingSpeed) {

		final Space space = new Space(this.spaceWidth, this.spaceHeight, (f) -> {
		});

		space.add(new StaticPlanet(rocky, 100, 50,
				new Position(this.spaceWidth - 25, this.spaceHeight / 2),
				angleCirculatesAt(1), constant(5)));

		space.add(new StaticPlanet(rocky, 100, 50,
				new Position(this.spaceWidth, this.spaceHeight / 2), angleCirculatesAt(1),
				forwardLooping(3, phasingSpeed)));
		space.add(new StaticPlanet(rocky, 100, 50,
				new Position(this.spaceWidth + 25, this.spaceHeight / 2),
				angleCirculatesAt(1), backwardLooping(3, phasingSpeed)));
		space.add(new StaticPlanet(rocky, 100, 50,
				new Position(this.spaceWidth + 50, this.spaceHeight / 2),
				angleCirculatesAt(1), forwardStopping(3, phasingSpeed)));
		space.add(new StaticPlanet(rocky, 100, 50,
				new Position(this.spaceWidth + 75, this.spaceHeight / 2),
				angleCirculatesAt(1), forwardBackwardLooping(3, phasingSpeed)));

		return space;
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	public int getMaxLevel() {

		return this.constellations.size();
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	private final int spaceWidth;
	private final int spaceHeight;
	private final List<Supplier<Space>> constellations = new ArrayList<>();
}
