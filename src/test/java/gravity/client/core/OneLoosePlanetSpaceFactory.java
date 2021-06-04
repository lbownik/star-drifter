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

import java.util.function.DoubleConsumer;

/*******************************************************************************
 * @author lukasz.bownik@gmail.com
 *******************************************************************************/
public final class OneLoosePlanetSpaceFactory implements SpaceFactory {

	/****************************************************************************
	 *
	 ***************************************************************************/
	public OneLoosePlanetSpaceFactory(final int spaceWidth, final int spaceHeight) {

		this.spaceWidth = spaceWidth;
		this.spaceHeight = spaceHeight;

	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	@Override
	public Space create(final int level, final DoubleConsumer forceSniffer) {

		final Space space = new Space(this.spaceWidth, this.spaceHeight, forceSniffer);

		space.add(
				new LoosePlanet(meteorite, 100, 50, new Position(50, this.spaceHeight / 2),
						new Speed(10, 0), Phase.constant(0), this.spaceWidth - 50));

		return space;
	}
	/****************************************************************************
	 *
	 ***************************************************************************/
	@Override
	public Space createBackSpace(int level) {
		
		return new Space(this.spaceWidth, this.spaceHeight, (f) -> {});
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	public int getMaxLevel() {

		return 1;
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	private final int spaceWidth;
	private final int spaceHeight;
}
