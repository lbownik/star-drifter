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

/*******************************************************************************
 * @author lukasz.bownik@gmail.com
 ******************************************************************************/
public final class Speed extends Vector {

	/****************************************************************************
	 *
	 ***************************************************************************/
	public Speed(final double x, final double y) {

		super(x, y);
	}
	/****************************************************************************
	 *
	 ***************************************************************************/
	public Speed clone() {

		return new Speed(this.x, this.y);
	}
	/****************************************************************************
	 *
	 ***************************************************************************/
	void changeBy(final Acceleration acc, final double timeInterval) {

		this.x += acc.x * timeInterval;
		this.y += acc.y * timeInterval;
	}
	/****************************************************************************
	 *
	 ***************************************************************************/
	public void scaleBy(final double factor) {
		
		this.x *= factor;
		this.y *= factor;
	}
	/****************************************************************************
	 *
	 ***************************************************************************/
	public static Speed zero() {

		return new Speed(0, 0);
	}
	/****************************************************************************
	 *
	 ***************************************************************************/
}
