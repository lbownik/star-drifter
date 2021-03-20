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

import org.junit.Test;
import static org.junit.Assert.*;
import static gravity.client.core.Planet.Type.rocky;

/*******************************************************************************
 * @author lukasz.bownik@gmail.com
 ******************************************************************************/
public class GravityUseCases {

	/****************************************************************************
	 *
	 ***************************************************************************/
	public GravityUseCases() {
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	@Test
	public void forceIsZero_forTwoZeroMassObjects() throws Exception {

		Planet p = new Planet(rocky, 0, 1, new Position(0, 0), Speed.zero());
		SpaceCraft c = new SpaceCraft(new Position(1, 0), Speed.zero(), (f) -> {
		});

		Force f = c.gravitationalForceFrom(p);

		assertEquals(0.0, f.x, 0.00000001);
		assertEquals(0.0, f.y, 0.00000001);
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	@Test
	public void forceIsZero_forZeroMassObject_whanOtherObjectHassMass()
			throws Exception {

		Planet p = new Planet(rocky, 1, 1, new Position(0, 0), Speed.zero());
		Planet c = new Planet(rocky, 0, 1, new Position(1, 0), Speed.zero());

		Force f = c.gravitationalForceFrom(p);

		assertEquals(0.0, f.x, 0.00000001);
		assertEquals(0.0, f.y, 0.00000001);
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	@Test
	public void forceCannotBeComputed_forTwoMassiveObjects_whanThenOccupyTheSamePoint()
			throws Exception {

		Planet p = new Planet(rocky, 1, 1, new Position(0, 0), Speed.zero());
		SpaceCraft c = new SpaceCraft(new Position(0, 0), Speed.zero(), (f) -> {
		});

		try {
			c.gravitationalForceFrom(p);
		} catch (final IllegalStateException e) {
			assertEquals("Two objects cannot ocupy the same point.", e.getMessage());
		}
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	private void verifyThatForceIsOne(Position planetCenter, Position cOverCenter,
			Position cOnTheRightCenter, Position cUnderCenter, Position cOntheLeftCenter)
			throws Exception {

		Planet p = new Planet(rocky, 1, 1, planetCenter, Speed.zero());
		SpaceCraft oOver = new SpaceCraft(cOverCenter, Speed.zero(), (f) -> {
		});
		SpaceCraft cOnTheRight = new SpaceCraft(cOnTheRightCenter, Speed.zero(), (f) -> {
		});
		SpaceCraft cUnder = new SpaceCraft(cUnderCenter, Speed.zero(), (f) -> {
		});
		SpaceCraft cOnTheLeft = new SpaceCraft(cOntheLeftCenter, Speed.zero(), (f) -> {
		});

		Force fOver = oOver.gravitationalForceFrom(p);

		assertEquals(0.0, fOver.x, 0.00000001);
		assertEquals(-1.0, fOver.y, 0.00000001);

		Force fOnTheRight = cOnTheRight.gravitationalForceFrom(p);

		assertEquals(-1.0, fOnTheRight.x, 0.00000001);
		assertEquals(0.0, fOnTheRight.y, 0.00000001);

		Force fUnder = cUnder.gravitationalForceFrom(p);

		assertEquals(0.0, fUnder.x, 0.00000001);
		assertEquals(1.0, fUnder.y, 0.00000001);

		Force fOnTheLeft = cOnTheLeft.gravitationalForceFrom(p);

		assertEquals(1.0, fOnTheLeft.x, 0.00000001);
		assertEquals(0.0, fOnTheLeft.y, 0.00000001);
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	@Test
	public void forceEqualsOne_forTwoMassiveObjects_whanTheDistanceIsOne()
			throws Exception {

		verifyThatForceIsOne(new Position(0, 0), new Position(0, 1), new Position(1, 0),
				new Position(0, -1), new Position(-1, 0));

		verifyThatForceIsOne(new Position(1, 1), new Position(1, 2), new Position(2, 1),
				new Position(1, 0), new Position(0, 1));

		verifyThatForceIsOne(new Position(2, 2), new Position(2, 3), new Position(3, 2),
				new Position(2, 1), new Position(1, 2));

		verifyThatForceIsOne(new Position(1, -1), new Position(1, 0), new Position(2, -1),
				new Position(1, -2), new Position(0, -1));

		verifyThatForceIsOne(new Position(2, -2), new Position(2, -1),
				new Position(3, -2), new Position(2, -3), new Position(1, -2));

		verifyThatForceIsOne(new Position(-1, -1), new Position(-1, 0),
				new Position(0, -1), new Position(-1, -2), new Position(-2, -1));

		verifyThatForceIsOne(new Position(-2, -2), new Position(-2, -1),
				new Position(-1, -2), new Position(-2, -3), new Position(-3, -2));

		verifyThatForceIsOne(new Position(-1, 1), new Position(-1, 2), new Position(0, 1),
				new Position(-1, 0), new Position(-2, 1));

		verifyThatForceIsOne(new Position(-2, 2), new Position(-2, 3),
				new Position(-1, 2), new Position(-2, 1), new Position(-3, 2));
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	private void verifyThatForceIsOneHalf(Position planetCenter,
			Position cTopRightCenter, Position cBottomRightCenter,
			Position cBottomLeftCenter, Position cTopLeftCenter) throws Exception {

		double oneHalf = 0.5;
		double accuracy = 0.0000001;

		Planet p = new Planet(rocky, 1, 1, planetCenter, Speed.zero());
		SpaceCraft cTopRight = new SpaceCraft(cTopRightCenter, Speed.zero(), (f) -> {
		});
		SpaceCraft cBottomRight = new SpaceCraft(cBottomRightCenter, Speed.zero(),
				(f) -> {
				});
		SpaceCraft cBottomLeft = new SpaceCraft(cBottomLeftCenter, Speed.zero(), (f) -> {
		});
		SpaceCraft cTopLeft = new SpaceCraft(cTopLeftCenter, Speed.zero(), (f) -> {
		});

		Force fTopRight = cTopRight.gravitationalForceFrom(p);

		assertEquals(-oneHalf, fTopRight.x, accuracy);
		assertEquals(-oneHalf, fTopRight.y, accuracy);

		Force fBottomRight = cBottomRight.gravitationalForceFrom(p);

		assertEquals(-oneHalf, fBottomRight.x, accuracy);
		assertEquals(oneHalf, fBottomRight.y, accuracy);

		Force fBottomLeft = cBottomLeft.gravitationalForceFrom(p);

		assertEquals(oneHalf, fBottomLeft.x, accuracy);
		assertEquals(oneHalf, fBottomLeft.y, accuracy);

		Force fTopLeft = cTopLeft.gravitationalForceFrom(p);

		assertEquals(oneHalf, fTopLeft.x, accuracy);
		assertEquals(-oneHalf, fTopLeft.y, accuracy);
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	@Test
	public void forceEqualsOneHalf_forTwoMassiveObjects_whanTheDistanceIsSquareRootOfTwo()
			throws Exception {

		verifyThatForceIsOneHalf(new Position(0, 0), new Position(1, 1),
				new Position(1, -1), new Position(-1, -1), new Position(-1, 1));

		verifyThatForceIsOneHalf(new Position(1, 1), new Position(2, 2),
				new Position(2, 0), new Position(0, 0), new Position(0, 2));

		verifyThatForceIsOneHalf(new Position(2, 2), new Position(3, 3),
				new Position(3, 1), new Position(1, 1), new Position(1, 3));

		verifyThatForceIsOneHalf(new Position(1, -1), new Position(2, 0),
				new Position(2, -2), new Position(0, -2), new Position(0, 0));

		verifyThatForceIsOneHalf(new Position(2, -2), new Position(3, -1),
				new Position(3, -3), new Position(1, -3), new Position(1, -1));

		verifyThatForceIsOneHalf(new Position(-1, -1), new Position(0, 0),
				new Position(0, -2), new Position(-2, -2), new Position(-2, 0));

		verifyThatForceIsOneHalf(new Position(-2, -2), new Position(-1, -1),
				new Position(-1, -3), new Position(-3, -3), new Position(-3, -1));

		verifyThatForceIsOneHalf(new Position(-1, 1), new Position(0, 2),
				new Position(0, 0), new Position(-2, 0), new Position(-2, 2));

		verifyThatForceIsOneHalf(new Position(-2, 2), new Position(-1, 3),
				new Position(-1, 1), new Position(-3, 1), new Position(-3, 3));
	}
	/****************************************************************************
	 *
	 ***************************************************************************/
}
