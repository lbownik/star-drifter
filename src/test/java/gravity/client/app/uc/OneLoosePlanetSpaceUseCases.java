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

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import gravity.client.app.Presenter;
import gravity.client.core.Body;
import gravity.client.core.OneLoosePlanetSpaceFactory;
import gravity.client.core.Position;
import gravity.client.core.SpaceFactory;
import gravity.client.core.Speed;
import gravity.client.fakes.FakeScheduler;
import gravity.client.fakes.FakeUI;

/*******************************************************************************
 * @author lukasz.bownik@gmail.com
 ******************************************************************************/
public class OneLoosePlanetSpaceUseCases {

	/****************************************************************************
	 *
	 ***************************************************************************/
	@Test
	public void loosePlanet_keepsReturningToTheInitialPosition_afterItGoesTooFar() {

		Presenter presenter = new Presenter(this.view, this.spaceFactory, this.scheduler,
				1);

		presenter.start();

		this.scheduler.run(seventyFiveTimes);

		FakeUI.RefreshRecord r = this.view.refreshCalled.get(0);
		Body planet = r.planets.iterator().next();

		assertEquals(200, planet.getCenter().getX(), 0.001);

		this.scheduler.run(oneTime);

		this.scheduler.assertThatInvariantsHoldTrue();

		assertEquals(initialPosition.getX(), planet.getCenter().getX(), 0.001);
		assertEquals(initialPosition.getY(), planet.getCenter().getY(), 0.001);

		assertEquals(initialSpeed.getX(), planet.getSpeed().getX(), 0.001);
		assertEquals(initialSpeed.getY(), planet.getSpeed().getY(), 0.001);

		assertEquals(0, planet.getAngle(), 0.001);

		this.scheduler.run(seventyFiveTimes);

		assertEquals(200, planet.getCenter().getX(), 0.001);

		this.scheduler.run(oneTime);

		this.scheduler.assertThatInvariantsHoldTrue();

		assertEquals(initialPosition.getX(), planet.getCenter().getX(), 0.001);
		assertEquals(initialPosition.getY(), planet.getCenter().getY(), 0.001);

		assertEquals(initialSpeed.getX(), planet.getSpeed().getX(), 0.001);
		assertEquals(initialSpeed.getY(), planet.getSpeed().getY(), 0.001);
		assertEquals(0, planet.getAngle(), 0.001);
		assertEquals(0, planet.getPhaseIndex());

		this.view.assertThatInvariantsHoldTrue();
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	private final SpaceFactory spaceFactory = new OneLoosePlanetSpaceFactory(200, 40);
	private final FakeUI view = new FakeUI();
	private final FakeScheduler scheduler = new FakeScheduler();
	private final Position initialPosition = new Position(50, 20);
	private final Speed initialSpeed = new Speed(10, 0);

	private final static int seventyFiveTimes = 75;
	private final static int oneTime = 1;
}
