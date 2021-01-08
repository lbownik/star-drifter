
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
public class Planet extends Body {

	/****************************************************************************
	 *
	 ***************************************************************************/
	public enum Type {
		rocky, earthLike, gas, ice, star, blackHole, meteorite;
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	Planet(final Type type, final double mass, final double radius,
			final Position center, final Speed speed,
			final IncrementableOperator angleStrategy, final Phase phase) {

		super(mass, center, speed, angleStrategy);
		throwIf(radius < 0, "Negative radius.");
		throwIfNull(phase, "Null phase.");

		this.type = type;
		this.radius = radius;
		this.phase = phase;
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
	public double getRadius() {

		return this.radius;
	}
	/****************************************************************************
	 *
	 ***************************************************************************/
	@Override
	public void incrementTime(final double interval) {

		super.incrementTime(interval);
		this.phase.incrementTime(interval);
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	public Type getType() {

		return this.type;
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	public Phase getPhase() {

		return this.phase;
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	private final double radius;
	private final Type type;
	private final Phase phase;
}
