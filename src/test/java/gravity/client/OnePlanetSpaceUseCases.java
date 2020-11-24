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

import org.junit.Test;

import gravity.client.app.Presenter;
import gravity.client.core.Point;
import gravity.client.core.SpaceFactory;
import gravity.client.core.Speed;
import gravity.client.fakes.FakeScheduler;
import gravity.client.fakes.FakeSpaceFactory;
import gravity.client.fakes.FakeUI;

/*******************************************************************************
 * @author lukasz.bownik@gmail.com
 ******************************************************************************/
public class OnePlanetSpaceUseCases {

	/****************************************************************************
	 *
	 ***************************************************************************/
	@Test
	public void presenter_displaysYouLoose_whenCraftCrashesIntoPlanet() {

		Presenter presenter = new Presenter(this.view, this.spaceFactory, this.scheduler,
				2);
		this.view.refreshCalled.clear(); // clear recorded view refreshes so far

		presenter.start();
		presenter.aimingStarted(initialCraftPosition.getX() + initialSpeed * speedFactor,
				initialCraftPosition.getY());
		presenter.aimingFinished(initialCraftPosition.getX() + initialSpeed * speedFactor,
				initialCraftPosition.getY());

		for (int i = 0; i < numberOfTimeIncrements; ++i) {
			this.scheduler.run();
		}
		
		this.view.assertThatAimingEnabledWasCalled(oneTime);
		this.view.assertThatAimingDisabledWasCalled(oneTime);
		
		this.scheduler.assertThatCancelWasCalled(oneTime);

		this.scheduler.assertThatSheduleWasCalled(oneTime);
		
		this.view.assertThatRefreshWasCalled(numberOfTimeIncrements);
		FakeUI.RefreshRecord record = this.view.refreshCalled.get(numberOfTimeIncrements - 1);
		record.assertThatCraftPositionIs(finalCraftPositionToRight);
		record.assertThatCraftSpeedIs(new Speed(finalSpeed, 0));
		record.assertThatScoreIs(0);
		record.assertThatLevelNumberIs(2);
		record.assertThatNumberOfPlanetsIs(1);

		this.view.assertThatLaunchWasPlayed(oneTime);
		this.view.assertThatExplosionWasPlayed(oneTime);
		this.view.assertThatSuccessWasShown(zeroTimes);
		this.view.assertThatFailureWasShown(oneTime);
		this.view.assertThatRefreshWithExplosionWasCalled(oneTime);
		this.view.assertThatRefreshWithSpeedWasCalled(zeroTimes);
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	private final SpaceFactory spaceFactory = new FakeSpaceFactory(200, 40);
	private final FakeUI view = new FakeUI();
	private final FakeScheduler scheduler = new FakeScheduler();
	private final Point initialCraftPosition = new Point(20, 20);
	private final Point finalCraftPositionToRight = new Point(20 + finalPosition, 20);
	
	private final static double initialSpeed = 1.0;
	private final static double speedFactor = 10;
	private final static int numberOfTimeIncrements = 70;
	private final static double finalPosition = 86.7679;
	private final static double finalSpeed = 12.7089;
	
	private final static int oneTime = 1;
	private final static int zeroTimes = 0;
}