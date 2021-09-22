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

import static java.lang.Math.hypot;

/*******************************************************************************
 * @author lukasz.bownik@gmail.com
 ******************************************************************************/
public final class Position {

	/****************************************************************************
	 *
	 ***************************************************************************/
	public Position(final double x, final double y) {

		this.x = x;
		this.y = y;
	}
	/****************************************************************************
	 *
	 ***************************************************************************/
	public Position clone() {
		
		return new Position(this.x, this.y);
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
	double dx(final Position p) {

		return this.x - p.x;
	}
	/****************************************************************************
	 *
	 ***************************************************************************/
	double dy(final Position p) {

		return this.y - p.y;
	}
	/****************************************************************************
	 *
	 ***************************************************************************/
	void moveAt(final Speed speed, final double timeInterval) {

		this.x += speed.x * timeInterval;
		this.y += speed.y * timeInterval;
	}
	/****************************************************************************
	 *
	 ***************************************************************************/
	boolean isWithin(final double width, final double height) {

		return this.x >= 0 & this.x < width & this.y >= 0 & this.y < height;
	}
	/****************************************************************************
	 *
	 ***************************************************************************/
	double distanceTo(final Position other) {

		return hypot(this.dx(other), this.dy(other));
	}
	/****************************************************************************
	 *
	 ***************************************************************************/
	private double x;
	private double y;
}
