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

import static gravity.client.core.Phase.forwardStopping;

import java.util.function.Consumer;

/*******************************************************************************
 * @author lukasz.bownik@gmail.com
 ******************************************************************************/
class SpaceCraft extends Body {

	/****************************************************************************
	 *
	 ***************************************************************************/
	public SpaceCraft(final Position center, final Speed speed,
			final Consumer<Force> forceSniffer) {

		super(1, 25, center, speed, angleFollowsSpeed(), forwardStopping(49, 0.8));

		this.forceSniffer = forceSniffer;
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	@Override
	void moveBy(final Force force, final double timeInterval) {

		super.moveBy(force, timeInterval);
		this.forceSniffer.accept(force);
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	@Override
	public String getName() {

		return "spacecraft";
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	private final Consumer<Force> forceSniffer;
}
