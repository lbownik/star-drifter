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

import static gravity.client.core.Planet.Type.meteorite;
import static gravity.client.core.Planet.Type.rocky;
import static gravity.client.core.Body.angleCirculatesAt;
import static java.lang.Math.PI;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/*******************************************************************************
 * @author lukasz.bownik@gmail.com
 *******************************************************************************/
public final class FakeSpaceFactory implements SpaceFactory {

	/****************************************************************************
	 *
	 ***************************************************************************/
	public FakeSpaceFactory(final int spaceWidth, final int spaceHeight) {

		this.spaceWidth = spaceWidth;
		this.spaceHeight = spaceHeight;

		this.constellations.add(this::getEmptyConstellation);
		this.constellations.add(this::getOnePlanetConstellation);
		this.constellations.add(this::getOneLoosePlanetConstellation);
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	@Override
	public Space create(final int level, final Consumer<Force> forceSniffer) {

		return this.constellations.get(level - 1).apply(forceSniffer);

	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	private Space getEmptyConstellation(final Consumer<Force> forceSniffer) {

		return new Space(this.spaceWidth, this.spaceHeight, forceSniffer);
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	private Space getOnePlanetConstellation(final Consumer<Force> forceSniffer) {

		final Space space = new Space(this.spaceWidth, this.spaceHeight, forceSniffer);

		space.add(new StaticPlanet(rocky, 100, 50,
				new Position(this.spaceWidth - 25, this.spaceHeight / 2),
				angleCirculatesAt(1), Phase.constant(0)));

		return space;
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	private Space getOneLoosePlanetConstellation(final Consumer<Force> forceSniffer) {

		final Space space = new Space(this.spaceWidth, this.spaceHeight, forceSniffer);

		space.add(
				new LoosePlanet(meteorite, 100, 50, new Position(50, this.spaceHeight / 2),
						new Speed(10, 0), Phase.constant(0), this.spaceWidth - 50));

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
	private final List<Function<Consumer<Force>, Space>> constellations = new ArrayList<>();
}
