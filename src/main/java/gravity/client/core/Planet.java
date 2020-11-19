
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
		rocky, earthLike, gas, ice, star, blackHole;
	}
	/****************************************************************************
	 *
	 ***************************************************************************/
	public Planet(final Type type, final double mass, final double radius, 
			  final Point center, final Speed speed) {

		super(mass, center, speed);
		throwIf(radius < 0, "Negative radius");

		this.type = type;
		this.radius = radius;
	}
	/****************************************************************************
	 *
	 ***************************************************************************/
	public double getRaduis() {

		return this.radius;
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
	private final double radius;
	private final Type type;
}
