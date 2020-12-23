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

import java.util.function.BiConsumer;

/*******************************************************************************
 * @author lukasz.bownik@gmail.com
 ******************************************************************************/
public class SpaceCraft extends Body {

	/****************************************************************************
	 *
	 ***************************************************************************/
	public SpaceCraft(final Point center, final Speed speed,
			  final BiConsumer<SpaceCraft, Force> forceSniffer) {

		super(1, center, speed, angleFollowsSpeed);

		this.radius = 20;
		this.forceSniffer = forceSniffer;
	}
	/****************************************************************************
	 *
	 ***************************************************************************/
	@Override
	public void moveBy(final Force force, final double timeInterval) {

		super.moveBy(force, timeInterval);
		this.forceSniffer.accept(this, force);
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
	private final double radius;
	private final BiConsumer<SpaceCraft, Force> forceSniffer;
}
