
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

import static gravity.client.core.Preconditions.*;

/*******************************************************************************
 * @author lukasz.bownik@gmail.com
 ******************************************************************************/
class Planet extends Body {

	/****************************************************************************
	 *
	 ***************************************************************************/
	public enum Type {
		rocky, earthLike, gas, ice, withRings, blackHole, meteorite;
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	Planet(final Type type, final double mass, final double radius,
			final Position center, final Speed speed,
			final IncrementableOperator angleStrategy, final Phase phase) {

		super(mass, radius, center, speed, angleStrategy, phase);
		throwIf(radius < 0, "Negative radius.");

		this.type = type;
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	Planet(final Type type, final double mass, final double radius,
			final Position center, final Speed speed) {

		this(type, mass, radius, center, speed, angleIsFixedAt(0), Phase.constant(0));
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	@Override
	void incrementTime(final double interval) {

		super.incrementTime(interval);
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	@Override
	public String getName() {

		return this.type.name();
	}

	/****************************************************************************
	 *
	 ***************************************************************************/

	private final Type type;
}
