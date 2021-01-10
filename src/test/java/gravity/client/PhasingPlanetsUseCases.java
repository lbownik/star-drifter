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

package gravity.client;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import gravity.client.app.Presenter;
import gravity.client.core.PhasingPlanetsSpaceFactory;
import gravity.client.core.SpaceFactory;
import gravity.client.fakes.FakeScheduler;
import gravity.client.fakes.FakeUI;

/*******************************************************************************
 * @author lukasz.bownik@gmail.com
 ******************************************************************************/
public class PhasingPlanetsUseCases {

	/****************************************************************************
	 *
	 ***************************************************************************/
	@Test
	public void planets_changePahasesEveryTimeTick_asTimePasses() {

		Presenter presenter = new Presenter(this.view, this.spaceFactory, this.scheduler,
				1);

		presenter.start();

		this.scheduler.run(oneTime);

		FakeUI.RefreshRecord record = this.view.refreshCalled.get(0);
		
		assertEquals(5, record.planets.get(0).getPhase().getIndex());
		assertEquals(1, record.planets.get(1).getPhase().getIndex());
		assertEquals(2, record.planets.get(2).getPhase().getIndex());
		assertEquals(1, record.planets.get(3).getPhase().getIndex());

		this.scheduler.run(oneTime);

		assertEquals(5, record.planets.get(0).getPhase().getIndex());
		assertEquals(2, record.planets.get(1).getPhase().getIndex());
		assertEquals(1, record.planets.get(2).getPhase().getIndex());
		assertEquals(2, record.planets.get(3).getPhase().getIndex());

		this.scheduler.run(oneTime);

		assertEquals(5, record.planets.get(0).getPhase().getIndex());
		assertEquals(3, record.planets.get(1).getPhase().getIndex());
		assertEquals(0, record.planets.get(2).getPhase().getIndex());
		assertEquals(3, record.planets.get(3).getPhase().getIndex());
		
		this.scheduler.run(oneTime);

		assertEquals(5, record.planets.get(0).getPhase().getIndex());
		assertEquals(0, record.planets.get(1).getPhase().getIndex());
		assertEquals(3, record.planets.get(2).getPhase().getIndex());
		assertEquals(3, record.planets.get(3).getPhase().getIndex());
		
		this.scheduler.run(oneTime);

		assertEquals(5, record.planets.get(0).getPhase().getIndex());
		assertEquals(1, record.planets.get(1).getPhase().getIndex());
		assertEquals(2, record.planets.get(2).getPhase().getIndex());
		assertEquals(3, record.planets.get(3).getPhase().getIndex());
	}
	/****************************************************************************
	 *
	 ***************************************************************************/
	@Test
	public void planets_changePahasesEveryTwoTimeTicks_asTimePasses() {

		Presenter presenter = new Presenter(this.view, this.spaceFactory, this.scheduler,
				2);

		presenter.start();

		this.scheduler.run(oneTime);

		FakeUI.RefreshRecord record = this.view.refreshCalled.get(0);
		
		assertEquals(5, record.planets.get(0).getPhase().getIndex());
		assertEquals(0, record.planets.get(1).getPhase().getIndex());
		assertEquals(3, record.planets.get(2).getPhase().getIndex());
		
		this.scheduler.run(oneTime);

		assertEquals(5, record.planets.get(0).getPhase().getIndex());
		assertEquals(1, record.planets.get(1).getPhase().getIndex());
		assertEquals(2, record.planets.get(2).getPhase().getIndex());

		this.scheduler.run(oneTime);

		assertEquals(5, record.planets.get(0).getPhase().getIndex());
		assertEquals(1, record.planets.get(1).getPhase().getIndex());
		assertEquals(2, record.planets.get(2).getPhase().getIndex());
		
		this.scheduler.run(oneTime);

		assertEquals(5, record.planets.get(0).getPhase().getIndex());
		assertEquals(2, record.planets.get(1).getPhase().getIndex());
		assertEquals(1, record.planets.get(2).getPhase().getIndex());

		this.scheduler.run(oneTime);

		assertEquals(5, record.planets.get(0).getPhase().getIndex());
		assertEquals(2, record.planets.get(1).getPhase().getIndex());
		assertEquals(1, record.planets.get(2).getPhase().getIndex());
		
		this.scheduler.run(oneTime);

		assertEquals(5, record.planets.get(0).getPhase().getIndex());
		assertEquals(3, record.planets.get(1).getPhase().getIndex());
		assertEquals(0, record.planets.get(2).getPhase().getIndex());
		
		this.scheduler.run(oneTime);

		assertEquals(5, record.planets.get(0).getPhase().getIndex());
		assertEquals(3, record.planets.get(1).getPhase().getIndex());
		assertEquals(0, record.planets.get(2).getPhase().getIndex());
		
		this.scheduler.run(oneTime);

		assertEquals(5, record.planets.get(0).getPhase().getIndex());
		assertEquals(0, record.planets.get(1).getPhase().getIndex());
		assertEquals(3, record.planets.get(2).getPhase().getIndex());
		
		this.scheduler.run(oneTime);

		assertEquals(5, record.planets.get(0).getPhase().getIndex());
		assertEquals(0, record.planets.get(1).getPhase().getIndex());
		assertEquals(3, record.planets.get(2).getPhase().getIndex());
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	private final SpaceFactory spaceFactory = new PhasingPlanetsSpaceFactory(200, 40);
	private final FakeUI view = new FakeUI();
	private final FakeScheduler scheduler = new FakeScheduler();

	private final static int oneTime = 1;
}
