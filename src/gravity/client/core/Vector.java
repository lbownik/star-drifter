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

import static java.lang.Math.*;

/*******************************************************************************
 *
 ******************************************************************************/
public abstract class Vector {

	/****************************************************************************
	 *
	 ***************************************************************************/
	protected Vector(final double x, double y) {

		this.x = x;
		this.y = y;
	}
	/****************************************************************************
	 *
	 ***************************************************************************/
	public <U extends Vector> U combineWith(final U other) {

		this.x += other.x;
		this.y += other.y;
		return (U) this;
	}
	/****************************************************************************
	 *
	 ***************************************************************************/
	public double getX() {

		return this.x;
	}
	/****************************************************************************
	 *
	 ***************************************************************************/
	public double getY() {

		return this.y;
	}
	/****************************************************************************
	 *
	 ***************************************************************************/
	public double getValue() {

		return hypot(this.x, this.y);
	}
	/****************************************************************************
	 *
	 ***************************************************************************/
	public double getAngle() {

		return atan2(this.y, this.x);
	}
	/****************************************************************************
	 *
	 ***************************************************************************/
	public boolean isZero() {

		return this.x == 0.0 & this.y == 0.0;
	}
	/****************************************************************************
	 *
	 ***************************************************************************/
	protected double x;
	protected double y;
}
