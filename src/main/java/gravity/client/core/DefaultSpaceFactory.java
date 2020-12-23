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

import static gravity.client.core.Planet.Type.blackHole;
import static gravity.client.core.Planet.Type.earthLike;
import static gravity.client.core.Planet.Type.gas;
import static gravity.client.core.Planet.Type.ice;
import static gravity.client.core.Planet.Type.meteorite;
import static gravity.client.core.Planet.Type.rocky;
import static gravity.client.core.Planet.Type.star;
import static gravity.client.core.Preconditions.throwIf;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

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

		// this.constellations.add(this::getPlanetConstellation0);
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
	public Space create(final int level) {

		throwIf(level < 1 | level > getMaxLevel(),
				() -> "Level mustbe between 1 and " + getMaxLevel());

		return this.constellations.get(level - 1).get();
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	private Space getPlanetConstellation0() {

		final Space space = new Space(this.spaceWidth, this.spaceHeight, null);

		space.add(new StaticPlanet(rocky, 200, 50,
				new Point(this.spaceWidth / 7, this.spaceHeight / 7), Speed.zero()));
		space.add(new StaticPlanet(ice, 100, 50,
				new Point(2 * this.spaceWidth / 7, 2 * this.spaceHeight / 7),
				Speed.zero()));
		space.add(new StaticPlanet(earthLike, 100, 50,
				new Point(3 * this.spaceWidth / 7, this.spaceHeight / 7), Speed.zero()));
		space.add(new StaticPlanet(gas, 100, 50,
				new Point(4 * this.spaceWidth / 7, 6 * this.spaceHeight / 7),
				Speed.zero()));
		space.add(new StaticPlanet(star, 100, 50,
				new Point(5 * this.spaceWidth / 7, 5 * this.spaceHeight / 7),
				Speed.zero()));
		space.add(new StaticPlanet(blackHole, 100, 50,
				new Point(6 * this.spaceWidth / 7, 6 * this.spaceHeight / 7),
				Speed.zero()));
		space.add(new LoosePlanet(meteorite, 10, 15,
				new Point(50, this.spaceHeight), new Speed(0, -10), this.spaceWidth));

		return space;
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	private Space getPlanetConstellation1() {

		final Space space = new Space(this.spaceWidth, this.spaceHeight, null);

		space.add(new StaticPlanet(rocky, 100, 50,
				new Point(this.spaceWidth / 2, this.spaceHeight / 2), Speed.zero()));

		space.add(new LoosePlanet(meteorite, 10, 15,
				new Point(this.spaceWidth - 100, -100), new Speed(-5, 30),
				this.spaceWidth));

		return space;
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	private Space getPlanetConstellation2() {

		final Space space = new Space(this.spaceWidth, this.spaceHeight, null);

		space.add(new StaticPlanet(rocky, 100, 50,
				new Point(this.spaceWidth / 2, this.spaceHeight / 2), Speed.zero()));
		space.add(new StaticPlanet(ice, 100, 50,
				new Point(5 * this.spaceWidth / 7, 5 * this.spaceHeight / 7),
				Speed.zero()));
		space.add(new StaticPlanet(earthLike, 100, 50,
				new Point(5 * this.spaceWidth / 7, this.spaceHeight / 7), Speed.zero()));

		space.add(new LoosePlanet(meteorite, 10, 15,
				new Point(100, this.spaceHeight + 200), new Speed(5, -30),
				this.spaceWidth + 100));

		return space;
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	private Space getPlanetConstellation3() {

		final Space space = new Space(this.spaceWidth, this.spaceHeight, null);

		space.add(new StaticPlanet(gas, 100, 50,
				new Point(this.spaceWidth / 4, this.spaceHeight / 2), Speed.zero()));
		space.add(new StaticPlanet(blackHole, 5000, 50,
				new Point(5 * this.spaceWidth / 7, 5 * this.spaceHeight / 7),
				Speed.zero()));

		return space;
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	private Space getPlanetConstellation4() {

		final Space space = new Space(this.spaceWidth, this.spaceHeight, null);

		space.add(new StaticPlanet(gas, 100, 50,
				new Point(this.spaceWidth / 2, this.spaceHeight / 2), Speed.zero()));
		space.add(new StaticPlanet(ice, 100, 50,
				new Point(5 * this.spaceWidth / 7, 5 * this.spaceHeight / 7),
				Speed.zero()));
		space.add(new StaticPlanet(earthLike, 100, 50,
				new Point(5 * this.spaceWidth / 7, this.spaceHeight / 7), Speed.zero()));
		space.add(new StaticPlanet(blackHole, 1000, 50,
				new Point(2 * this.spaceWidth / 7, this.spaceHeight / 7), Speed.zero()));
		space.add(new StaticPlanet(star, 1000, 50,
				new Point(9 * this.spaceWidth / 10, this.spaceHeight / 2), Speed.zero()));

		return space;
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	private Space getPlanetConstellation5() {

		final Space space = new Space(this.spaceWidth, this.spaceHeight, null);

		space.add(new Planet(gas, 500, 50,
				new Point(this.spaceWidth / 2, this.spaceHeight / 7), new Speed(15, 0)));
		space.add(new Planet(rocky, 500, 50,
				new Point(this.spaceWidth / 2, 6 * this.spaceHeight / 7),
				new Speed(-15, 0)));

		return space;
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	private Space getPlanetConstellation6() {

		final Space space = new Space(this.spaceWidth, this.spaceHeight, null);

		space.add(new StaticPlanet(blackHole, 5000, 50,
				new Point(this.spaceWidth / 2, this.spaceHeight / 2), Speed.zero()));
		space.add(new Planet(ice, 100, 50,
				new Point(this.spaceWidth / 2, this.spaceHeight / 7), new Speed(-60, 0)));

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
