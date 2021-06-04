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
import static gravity.client.core.Body.angleFollowsSpeed;
import static gravity.client.core.Body.angleIsFixedAt;
import static gravity.client.core.Phase.backwardLooping;
import static gravity.client.core.Phase.forwardBackwardLooping;
import static gravity.client.core.Phase.forwardLooping;
import static gravity.client.core.Phase.constant;
import static gravity.client.core.Planet.Type.blackHole;
import static gravity.client.core.Planet.Type.earthLike;
import static gravity.client.core.Planet.Type.gas;
import static gravity.client.core.Planet.Type.ice;
import static gravity.client.core.Planet.Type.meteorite;
import static gravity.client.core.Planet.Type.rocky;
import static gravity.client.core.Planet.Type.withRings;
import static gravity.client.core.Planet.Type.galaxy;
import static gravity.client.core.Preconditions.throwIf;
import static java.lang.Math.PI;

import java.util.ArrayList;
import java.util.List;
import java.util.function.DoubleConsumer;
import java.util.function.Function;

/*******************************************************************************
 * @author lukasz.bownik@gmail.com
 *******************************************************************************/
public final class DefaultSpaceFactory implements SpaceFactory {

	/****************************************************************************
	 *
	 ***************************************************************************/
	public DefaultSpaceFactory(final int spaceWidth, final int spaceHeight) {

		this.spaceWidth = spaceWidth;
		this.spaceHeight = spaceHeight;

		this.constellations.add(this::getPlanetConstellation0);
		this.constellations.add(this::getPlanetConstellation1);
		this.constellations.add(this::getPlanetConstellation2);
		this.constellations.add(this::getPlanetConstellation3);
		this.constellations.add(this::getPlanetConstellation4);
		this.constellations.add(this::getPlanetConstellation5);
		this.constellations.add(this::getPlanetConstellation6);
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	@Override
	public Space create(final int level, final DoubleConsumer forceSniffer) {

		throwIf(level < 0 | level > getMaxLevel(),
				() -> "Level mustbe between 0 and " + getMaxLevel());

		return this.constellations.get(level).apply(forceSniffer);
	}
	/****************************************************************************
	 *
	 ***************************************************************************/
	@Override
	public Space createBackSpace(int level) {
		
		final Space space = new Space(this.spaceWidth, this.spaceHeight, (f) -> {});
		space.add(new StaticPlanet(galaxy, 1, 1,
				new Position(this.spaceWidth / 2, this.spaceHeight / 2), angleIsFixedAt(0),
				forwardLooping(maxPhaseIndex, 0.8)));
		return space;
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	private Space getPlanetConstellation0(final DoubleConsumer forceSniffer) {

		final Space space = new Space(this.spaceWidth, this.spaceHeight, forceSniffer);

		space.add(new StaticPlanet(rocky, 200, 50,
				new Position(this.spaceWidth / 7, this.spaceHeight / 7), angleIsFixedAt(0),
				forwardLooping(maxPhaseIndex, 0.8)));
		space.add(new StaticPlanet(ice, 100, 50,
				new Position(2 * this.spaceWidth / 7, 2 * this.spaceHeight / 7),
				angleIsFixedAt(0), forwardLooping(maxPhaseIndex, 0.8)));
		space.add(new StaticPlanet(earthLike, 100, 50,
				new Position(3 * this.spaceWidth / 7, this.spaceHeight / 7),
				angleIsFixedAt(0), forwardLooping(maxPhaseIndex, 0.8)));
		space.add(new StaticPlanet(gas, 100, 50,
				new Position(4 * this.spaceWidth / 7, 5 * this.spaceHeight / 7),
				angleIsFixedAt(0), forwardLooping(maxPhaseIndex, 0.8)));
		space.add(new StaticPlanet(withRings, 100, 50,
				new Position(5 * this.spaceWidth / 7, 5 * this.spaceHeight / 7),
				angleIsFixedAt(0), forwardLooping(maxPhaseIndex, 0.8)));
		space.add(new StaticPlanet(blackHole, 100, 50,
				new Position(6 * this.spaceWidth / 7, 5 * this.spaceHeight / 7),
				angleIsFixedAt(0), forwardLooping(maxPhaseIndex, 0.8)));
		space.add(new LoosePlanet(meteorite, 10, 15, new Position(50, this.spaceHeight),
				new Speed(0, -10), forwardBackwardLooping(18, 0.8), this.spaceWidth));

		return space;
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	private Space getPlanetConstellation1(final DoubleConsumer forceSniffer) {

		final Space space = new Space(this.spaceWidth, this.spaceHeight, forceSniffer);

		space.add(new StaticPlanet(rocky, 100, 50,
				new Position(this.spaceWidth / 2, this.spaceHeight / 2), angleIsFixedAt(0),
				forwardLooping(maxPhaseIndex, 0.8)));

		return space;
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	private Space getPlanetConstellation2(final DoubleConsumer forceSniffer) {

		final Space space = new Space(this.spaceWidth, this.spaceHeight, forceSniffer);

		space.add(new StaticPlanet(rocky, 100, 50,
				new Position(this.spaceWidth / 2, this.spaceHeight / 2),
				angleIsFixedAt(-PI / 4), forwardLooping(maxPhaseIndex, 0.8)));
		space.add(new StaticPlanet(ice, 100, 50,
				new Position(5 * this.spaceWidth / 7, 5 * this.spaceHeight / 7),
				angleIsFixedAt(0), forwardLooping(maxPhaseIndex, 0.8)));
		space.add(new StaticPlanet(earthLike, 100, 50,
				new Position(5 * this.spaceWidth / 7, this.spaceHeight / 7),
				angleCirculatesAt(-0.1), forwardLooping(maxPhaseIndex, 0.8)));
		space.add(new LoosePlanet(meteorite, 10, 15,
				new Position(100, this.spaceHeight + 200), new Speed(5, -30),
				forwardBackwardLooping(18, 0.8), this.spaceWidth * 2));

		return space;
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	private Space getPlanetConstellation3(final DoubleConsumer forceSniffer) {

		final Space space = new Space(this.spaceWidth, this.spaceHeight, forceSniffer);

		space.add(new StaticPlanet(gas, 100, 50,
				new Position(this.spaceWidth / 4, this.spaceHeight / 2), angleIsFixedAt(0),
				backwardLooping(maxPhaseIndex, 0.8)));
		space.add(new StaticPlanet(blackHole, 5000, 50,
				new Position(5 * this.spaceWidth / 7, 5 * this.spaceHeight / 7),
				angleIsFixedAt(0), forwardLooping(maxPhaseIndex, 0.8)));

		return space;
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	private Space getPlanetConstellation4(final DoubleConsumer forceSniffer) {

		final Space space = new Space(this.spaceWidth, this.spaceHeight, forceSniffer);

		space.add(new StaticPlanet(gas, 100, 50,
				new Position(this.spaceWidth / 2, this.spaceHeight / 2),
				angleIsFixedAt(PI / 8), forwardLooping(maxPhaseIndex, 0.8)));
		space.add(new StaticPlanet(ice, 100, 50,
				new Position(5 * this.spaceWidth / 7, 5 * this.spaceHeight / 7),
				angleCirculatesAt(0.03), forwardLooping(maxPhaseIndex, 0.8)));
		space.add(new StaticPlanet(earthLike, 100, 50,
				new Position(5 * this.spaceWidth / 7, this.spaceHeight / 7),
				angleIsFixedAt(-PI / 3), forwardLooping(maxPhaseIndex, 0.8)));
		space.add(new StaticPlanet(blackHole, 1000, 50,
				new Position(2 * this.spaceWidth / 7, this.spaceHeight / 7),
				angleIsFixedAt(0), forwardLooping(maxPhaseIndex, 0.8)));
		space.add(new StaticPlanet(withRings, 1000, 50,
				new Position(9 * this.spaceWidth / 10, this.spaceHeight / 2),
				angleIsFixedAt(0), forwardLooping(maxPhaseIndex, 0.8)));

		return space;
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	private Space getPlanetConstellation5(final DoubleConsumer forceSniffer) {

		final Space space = new Space(this.spaceWidth, this.spaceHeight, forceSniffer);

		space.add(new Planet(gas, 500, 50,
				new Position(this.spaceWidth / 2, this.spaceHeight / 7), new Speed(15, 0),
				angleFollowsSpeed(), forwardLooping(maxPhaseIndex, 0.8)));
		space.add(new Planet(rocky, 500, 50,
				new Position(this.spaceWidth / 2, 6 * this.spaceHeight / 7),
				new Speed(-15, 0), angleCirculatesAt(-0.1),
				forwardLooping(maxPhaseIndex, 0.8)));

		return space;
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	private Space getPlanetConstellation6(final DoubleConsumer forceSniffer) {

		final Space space = new Space(this.spaceWidth, this.spaceHeight, forceSniffer);

		space.add(new StaticPlanet(blackHole, 4000, 50,
				new Position(this.spaceWidth / 2, this.spaceHeight / 2), angleIsFixedAt(0),
				forwardLooping(maxPhaseIndex, 0.8)));
		space.add(new Planet(ice, 30, 50,
				new Position(this.spaceWidth / 2, this.spaceHeight / 7), new Speed(-40, 0),
				angleIsFixedAt(0), forwardLooping(maxPhaseIndex, 0.8)));

		return space;
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	public int getMaxLevel() {

		return this.constellations.size() - 1;
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	private final int spaceWidth;
	private final int spaceHeight;
	private final List<Function<DoubleConsumer, Space>> constellations = new ArrayList<>();

	final static int maxPhaseIndex = 49;
}
