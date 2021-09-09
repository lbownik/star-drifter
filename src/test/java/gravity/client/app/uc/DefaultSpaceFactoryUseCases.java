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

package gravity.client.app.uc;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import gravity.client.core.DefaultSpaceFactory;
import gravity.client.core.SpaceFactory;

/*******************************************************************************
 * @author lukasz.bownik@gmail.com
 ******************************************************************************/
public class DefaultSpaceFactoryUseCases {

	/****************************************************************************
	 *
	 ***************************************************************************/
	@Test
	public void factory_neverThrowsExceptions_WhileProducingSpaces() {

		for (int level = 0; level <= this.spaceFactory.getMaxLevel(); level++) {
			this.spaceFactory.create(level, (f) -> {});
		}
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	@Test
	public void factory_throwsException_ifGivenLeessZeroLevelNumber() {

		try {
			this.spaceFactory.create(-1);
			fail();
		} catch (final IllegalArgumentException e) {
			return;
		}
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	@Test
	public void factory_throwsException_ifGivenLevelNumberOverMaxLevel() {

		try {
			this.spaceFactory.create(this.spaceFactory.getMaxLevel() + 1);
			fail();
		} catch (final IllegalArgumentException e) {
			return;
		}
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	private final SpaceFactory spaceFactory = new DefaultSpaceFactory(200, 40);
}
