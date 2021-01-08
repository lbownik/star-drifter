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
import gravity.client.core.FakeSpaceFactory;
import gravity.client.core.Position;
import gravity.client.core.SpaceFactory;
import gravity.client.core.Speed;
import gravity.client.fakes.FakeScheduler;
import gravity.client.fakes.FakeUI;

/*******************************************************************************
 * @author lukasz.bownik@gmail.com
 ******************************************************************************/
public class LevelTransitionUseCases {

	/****************************************************************************
	 *
	 ***************************************************************************/
	@Test
	public void presenter_displaysYouWin_whenCraftPassesRightEdge_andThenReloadsTheSameLevel() {

		Presenter presenter = new Presenter(this.view, this.spaceFactory, this.scheduler,
				1);

		presenter.start();
		presenter.aimingStarted(initialCraftPosition.getX() + speedFactor * initialSpeed,
				initialCraftPosition.getY());
		presenter.aimingFinished(initialCraftPosition.getX() + speedFactor * initialSpeed,
				initialCraftPosition.getY());

		this.scheduler.assertThatInvariantsHoldTrue();

		this.scheduler.run(ninetyTimes);

		this.view.assertThatAimingEnabledWasCalled(oneTime);
		this.view.assertThatAimingDisabledWasCalled(oneTime);

		this.scheduler.assertThatCancelWasCalled(oneTime);
		this.view.assertThatSuccessWasShown(oneTime);

		this.view.clearAll();
		this.scheduler.clearAll();

		presenter.playAgain();

		this.view.assertThatAimingEnabledWasCalled(oneTime);
		this.view.assertThatAimingDisabledWasCalled(zeroTimes);

		this.scheduler.assertThatSheduleWasCalled(oneTime);
		this.scheduler.assertThatInvariantsHoldTrue();

		this.scheduler.run();

		this.view.assertThatRefreshWasCalled(oneTime);

		FakeUI.RefreshRecord record = this.view.refreshCalled.get(0);
		record.assertThatCraftPositionIs(initialCraftPosition);
		record.assertThatCraftSpeedIs(Speed.zero());
		record.assertThatScoreIs(0);
		record.assertThatLevelNumberIs(1);
		record.assertThatNumberOfPlanetsIs(0);

		this.view.assertThatLaunchWasPlayed(zeroTimes);
		this.view.assertThatExplosionWasPlayed(zeroTimes);
		this.view.assertThatSuccessWasShown(zeroTimes);
		this.view.assertThatFailureWasShown(zeroTimes);
		this.view.assertThatRefreshWithExplosionWasCalled(zeroTimes);
		this.view.assertThatRefreshWithSpeedWasCalled(zeroTimes);
		this.view.assertThatInvariantsHoldTrue();
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	@Test
	public void presenter_displaysYouWin_whenCraftPassesRightEdge_andThenReloadsNextLevel() {

		Presenter presenter = new Presenter(this.view, this.spaceFactory, this.scheduler,
				1);

		presenter.start();
		presenter.aimingStarted(initialCraftPosition.getX() + speedFactor * initialSpeed,
				initialCraftPosition.getY());
		presenter.aimingFinished(initialCraftPosition.getX() + speedFactor * initialSpeed,
				initialCraftPosition.getY());

		this.scheduler.assertThatInvariantsHoldTrue();

		this.scheduler.run(ninetyTimes);

		this.scheduler.assertThatCancelWasCalled(oneTime);
		this.view.assertThatSuccessWasShown(oneTime);

		this.view.clearAll();
		this.scheduler.clearAll();

		presenter.playNext();

		this.scheduler.assertThatSheduleWasCalled(oneTime);
		this.scheduler.assertThatInvariantsHoldTrue();

		this.scheduler.run();

		this.view.assertThatRefreshWasCalled(oneTime);

		FakeUI.RefreshRecord record = this.view.refreshCalled.get(0);
		record.assertThatCraftPositionIs(initialCraftPosition);
		record.assertThatCraftSpeedIs(Speed.zero());
		record.assertThatScoreIs(0);
		record.assertThatLevelNumberIs(2);
		record.assertThatNumberOfPlanetsIs(1);

		this.view.assertThatLaunchWasPlayed(zeroTimes);
		this.view.assertThatExplosionWasPlayed(zeroTimes);
		this.view.assertThatSuccessWasShown(zeroTimes);
		this.view.assertThatFailureWasShown(zeroTimes);
		this.view.assertThatRefreshWithExplosionWasCalled(zeroTimes);
		this.view.assertThatRefreshWithSpeedWasCalled(zeroTimes);
		this.view.assertThatInvariantsHoldTrue();
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	@Test
	public void presenter_transitionsToFirstLevel_afterCompletetingLasOne() {

		Presenter presenter = new Presenter(this.view, this.spaceFactory, this.scheduler,
				1);

		this.view.clearAll();
		this.scheduler.clearAll();

		presenter.playNext();
		this.scheduler.run();
		this.scheduler.cancel();
		
		this.scheduler.assertThatInvariantsHoldTrue();

		this.view.assertThatRefreshWasCalled(oneTime);
		this.view.assertThatInvariantsHoldTrue();
		FakeUI.RefreshRecord record = this.view.refreshCalled.get(0);
		record.assertThatLevelNumberIs(2);

		this.view.clearAll();
		this.scheduler.clearAll();
		

		presenter.playNext();
		this.scheduler.run();
		this.scheduler.cancel();
		
		this.scheduler.assertThatInvariantsHoldTrue();

		this.view.assertThatRefreshWasCalled(oneTime);
		this.view.assertThatInvariantsHoldTrue();
		record = this.view.refreshCalled.get(0);
		record.assertThatLevelNumberIs(3);

		this.view.clearAll();
		this.scheduler.clearAll();

		presenter.playNext();
		this.scheduler.run();
		this.scheduler.cancel();
		
		this.scheduler.assertThatInvariantsHoldTrue();

		this.view.assertThatRefreshWasCalled(oneTime);
		this.view.assertThatInvariantsHoldTrue();
		record = this.view.refreshCalled.get(0);
		record.assertThatLevelNumberIs(1);
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	private final SpaceFactory spaceFactory = new FakeSpaceFactory(200, 40);
	private final FakeUI view = new FakeUI();
	private final FakeScheduler scheduler = new FakeScheduler();
	private final Position initialCraftPosition = new Position(20, 20);

	private final static double initialSpeed = 10;
	private final static double speedFactor = 10;
	private final static int ninetyTimes = 90;

	private final static int oneTime = 1;
	private final static int zeroTimes = 0;
}
