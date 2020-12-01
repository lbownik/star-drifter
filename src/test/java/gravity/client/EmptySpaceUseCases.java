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
import static java.lang.Math.PI;

/*******************************************************************************
 * @author lukasz.bownik@gmail.com
 ******************************************************************************/
public class EmptySpaceUseCases {

	/****************************************************************************
	 *
	 ***************************************************************************/
	@Test
	public void presenter_createsSpaceCraftAndRefreshesView_whenInitialized() {

		Presenter presenter = new Presenter(this.view, this.spaceFactory, this.scheduler,
				1);

		this.view.assertThatPresenterIs(presenter);
		this.view.assertThatRefreshWasCalled(oneTime);
		this.view.assertThatSpacecraftWasNeverNull();
		this.view.assertThatPlanetsWereNeverNull();

		FakeUI.RefreshRecord record = this.view.refreshCalled.get(0);
		record.assertThatCraftPositionIs(initialCraftPosition);
		record.assertThatCraftSpeedIs(Speed.zero());
		record.assertThatCraftAngleIs(0);
		record.assertThatScoreIs(0);
		record.assertThatLevelNumberIs(1);
		record.assertThatNumberOfPlanetsIs(0);

		this.view.assertThatLaunchWasPlayed(zeroTimes);
		this.view.assertThatExplosionWasPlayed(zeroTimes);
		this.view.assertThatSuccessWasShown(zeroTimes);
		this.view.assertThatFailureWasShown(zeroTimes);
		this.view.assertThatRefreshWithExplosionWasCalled(zeroTimes);
		this.view.assertThatRefreshWithSpeedWasCalled(zeroTimes);
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	@Test
	public void presenter_schedulesViewRefreshes_whenActivated() {

		Presenter presenter = new Presenter(this.view, this.spaceFactory, this.scheduler,
				1);
		this.view.refreshCalled.clear(); // clear recorded view refreshes so far

		presenter.start();
		
		this.scheduler.assertThatScheduledTasksAreNotNull();
		
		this.scheduler.run();

		this.view.assertThatAimingEnabledWasCalled(oneTime);
		this.view.assertThatAimingDisabledWasCalled(zeroTimes);
		
		this.view.assertThatRefreshWasCalled(oneTime);
		this.view.assertThatSpacecraftWasNeverNull();
		this.view.assertThatPlanetsWereNeverNull();
		
		FakeUI.RefreshRecord record = this.view.refreshCalled.get(0);
		record.assertThatCraftPositionIs(initialCraftPosition);
		record.assertThatCraftSpeedIs(Speed.zero());
		record.assertThatCraftAngleIs(0);
		record.assertThatScoreIs(0);
		record.assertThatLevelNumberIs(1);
		record.assertThatNumberOfPlanetsIs(0);

		this.view.assertThatLaunchWasPlayed(zeroTimes);
		this.view.assertThatExplosionWasPlayed(zeroTimes);
		this.view.assertThatSuccessWasShown(zeroTimes);
		this.view.assertThatFailureWasShown(zeroTimes);
		this.view.assertThatRefreshWithExplosionWasCalled(zeroTimes);
		this.view.assertThatRefreshWithSpeedWasCalled(zeroTimes);
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	@Test
	public void presenter_schedulesViewRefreshesWithSpeed_whenAimingStarted() {

		Presenter presenter = new Presenter(this.view, this.spaceFactory, this.scheduler,
				1);
		this.view.refreshCalled.clear(); // clear recorded view refreshes so far

		presenter.start();		
		presenter.aimingStarted(initialCraftPosition.getX() + speedFactor * initialSpeed,
				initialCraftPosition.getY());
		
		this.scheduler.assertThatScheduledTasksAreNotNull();
		
		this.scheduler.run();

		this.view.assertThatAimingEnabledWasCalled(oneTime);
		this.view.assertThatAimingDisabledWasCalled(zeroTimes);
		
		this.view.assertThatRefreshWithSpeedWasCalled(oneTime);
		this.view.assertThatSpacecraftWasNeverNull();
		this.view.assertThatPlanetsWereNeverNull();

		FakeUI.RefreshRecord record = this.view.refreshWithSpeedCalled.get(0);
		record.assertThatCraftPositionIs(initialCraftPosition);
		record.assertThatCraftSpeedIs(new Speed(initialSpeed, 0));
		record.assertThatCraftAngleIs(0);
		record.assertThatScoreIs(0);
		record.assertThatLevelNumberIs(1);
		record.assertThatNumberOfPlanetsIs(0);

		this.view.assertThatLaunchWasPlayed(zeroTimes);
		this.view.assertThatExplosionWasPlayed(zeroTimes);
		this.view.assertThatSuccessWasShown(zeroTimes);
		this.view.assertThatFailureWasShown(zeroTimes);
		this.view.assertThatRefreshWithExplosionWasCalled(zeroTimes);
		this.view.assertThatRefreshWasCalled(zeroTimes);
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	@Test
	public void presenter_schedulesViewRefreshPlaysLaunchSoundAndMovesCraft_whenAimingFinished() {

		Presenter presenter = new Presenter(this.view, this.spaceFactory, this.scheduler,
				1);
		this.view.refreshCalled.clear(); // clear recorded view refreshes so far

		presenter.start();
		presenter.aimingStarted(initialCraftPosition.getX() + speedFactor * initialSpeed,
				initialCraftPosition.getY());
		presenter.aimingFinished(initialCraftPosition.getX() + speedFactor * initialSpeed,
				initialCraftPosition.getY());

		this.scheduler.assertThatScheduledTasksAreNotNull();
		
		this.scheduler.run();

		this.view.assertThatAimingEnabledWasCalled(oneTime);
		this.view.assertThatAimingDisabledWasCalled(oneTime);
		
		this.view.assertThatRefreshWasCalled(oneTime);
		this.view.assertThatSpacecraftWasNeverNull();
		this.view.assertThatPlanetsWereNeverNull();

		FakeUI.RefreshRecord record = this.view.refreshCalled.get(0);
		record.assertThatCraftPositionIs(
				new Point(initialCraftPosition.getX() + timeInrement * 3.0,
						initialCraftPosition.getY()));
		record.assertThatCraftSpeedIs(new Speed(initialSpeed, 0));
		record.assertThatCraftAngleIs(0);
		record.assertThatScoreIs(0);
		record.assertThatLevelNumberIs(1);
		record.assertThatNumberOfPlanetsIs(0);

		this.view.assertThatLaunchWasPlayed(oneTime);
		this.view.assertThatExplosionWasPlayed(zeroTimes);
		this.view.assertThatSuccessWasShown(zeroTimes);
		this.view.assertThatFailureWasShown(zeroTimes);
		this.view.assertThatRefreshWithExplosionWasCalled(zeroTimes);
		this.view.assertThatRefreshWithSpeedWasCalled(zeroTimes);
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	@Test
	public void presenter_displaysYouWin_whenCraftReachesRightEdgeOfSpace() {

		Presenter presenter = new Presenter(this.view, this.spaceFactory, this.scheduler,
				1);
		this.view.refreshCalled.clear(); // clear recorded view refreshes so far

		presenter.start();
		presenter.aimingStarted(initialCraftPosition.getX() + speedFactor * initialSpeed,
				initialCraftPosition.getY());
		presenter.aimingFinished(initialCraftPosition.getX() + speedFactor * initialSpeed,
				initialCraftPosition.getY());

		this.scheduler.assertThatScheduledTasksAreNotNull();
		
		this.scheduler.run(numberOfTimeIncrements);

		this.view.assertThatAimingEnabledWasCalled(oneTime);
		this.view.assertThatAimingDisabledWasCalled(oneTime);
		
		this.scheduler.assertThatCancelWasCalled(oneTime);

		this.view.assertThatRefreshWasCalled(numberOfTimeIncrements);
		this.view.assertThatSpacecraftWasNeverNull();
		this.view.assertThatPlanetsWereNeverNull();

		FakeUI.RefreshRecord record = this.view.refreshCalled
				.get(numberOfTimeIncrements - 1);
		record.assertThatCraftPositionIs(finalCraftPositionToRight);
		record.assertThatCraftSpeedIs(new Speed(initialSpeed, 0));
		record.assertThatCraftAngleIs(0);
		record.assertThatScoreIs(0);
		record.assertThatLevelNumberIs(1);
		record.assertThatNumberOfPlanetsIs(0);

		this.view.assertThatLaunchWasPlayed(oneTime);
		this.view.assertThatExplosionWasPlayed(zeroTimes);
		this.view.assertThatSuccessWasShown(oneTime);
		this.view.assertThatFailureWasShown(zeroTimes);
		this.view.assertThatRefreshWithExplosionWasCalled(zeroTimes);
		this.view.assertThatRefreshWithSpeedWasCalled(zeroTimes);

		this.view.refreshCalled.clear(); // clear recorded view refreshes so far
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	@Test
	public void presenter_displaysYouLoose_whenCraftReachesBottomEdgeOfSpace() {

		Presenter presenter = new Presenter(this.view, this.spaceFactory, this.scheduler,
				1);
		this.view.refreshCalled.clear(); // clear recorded view refreshes so far

		presenter.start();
		presenter.aimingStarted(initialCraftPosition.getX(),
				initialCraftPosition.getY() + speedFactor * initialSpeed);
		presenter.aimingFinished(initialCraftPosition.getX(),
				initialCraftPosition.getY() + speedFactor * initialSpeed);

		this.scheduler.assertThatScheduledTasksAreNotNull();
		
		this.scheduler.run(numberOfTimeIncrements);

		this.view.assertThatAimingEnabledWasCalled(oneTime);
		this.view.assertThatAimingDisabledWasCalled(oneTime);
		
		this.scheduler.assertThatCancelWasCalled(oneTime);

		this.view.assertThatRefreshWasCalled(numberOfTimeIncrements);
		this.view.assertThatSpacecraftWasNeverNull();
		this.view.assertThatPlanetsWereNeverNull();

		FakeUI.RefreshRecord record = this.view.refreshCalled
				.get(numberOfTimeIncrements - 1);
		record.assertThatCraftPositionIs(finalCraftPositionToBottom);
		record.assertThatCraftSpeedIs(new Speed(0, initialSpeed));
		record.assertThatCraftAngleIs(PI / 2);
		record.assertThatScoreIs(0);
		record.assertThatLevelNumberIs(1);
		record.assertThatNumberOfPlanetsIs(0);

		this.view.assertThatLaunchWasPlayed(oneTime);
		this.view.assertThatExplosionWasPlayed(zeroTimes);
		this.view.assertThatSuccessWasShown(zeroTimes);
		this.view.assertThatFailureWasShown(oneTime);
		this.view.assertThatRefreshWithExplosionWasCalled(zeroTimes);
		this.view.assertThatRefreshWithSpeedWasCalled(zeroTimes);
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	@Test
	public void presenter_displaysYouLoose_whenCraftReachesTopEdgeOfSpace() {

		Presenter presenter = new Presenter(this.view, this.spaceFactory, this.scheduler,
				1);
		this.view.refreshCalled.clear(); // clear recorded view refreshes so far

		presenter.start();
		presenter.aimingStarted(initialCraftPosition.getX(),
				initialCraftPosition.getY() - speedFactor * initialSpeed);
		presenter.aimingFinished(initialCraftPosition.getX(),
				initialCraftPosition.getY() - speedFactor * initialSpeed);

		this.scheduler.assertThatScheduledTasksAreNotNull();
		
		this.scheduler.run(numberOfTimeIncrements);
		
		this.view.assertThatAimingEnabledWasCalled(oneTime);
		this.view.assertThatAimingDisabledWasCalled(oneTime);
		
		this.scheduler.assertThatCancelWasCalled(oneTime);

		this.view.assertThatRefreshWasCalled(numberOfTimeIncrements);
		this.view.assertThatSpacecraftWasNeverNull();
		this.view.assertThatPlanetsWereNeverNull();

		FakeUI.RefreshRecord record = this.view.refreshCalled
				.get(numberOfTimeIncrements - 1);
		record.assertThatCraftPositionIs(finalCraftPositionToTop);
		record.assertThatCraftSpeedIs(new Speed(0, -initialSpeed));
		record.assertThatCraftAngleIs(-PI / 2);
		record.assertThatScoreIs(0);
		record.assertThatLevelNumberIs(1);
		record.assertThatNumberOfPlanetsIs(0);

		this.view.assertThatLaunchWasPlayed(oneTime);
		this.view.assertThatExplosionWasPlayed(zeroTimes);
		this.view.assertThatSuccessWasShown(zeroTimes);
		this.view.assertThatFailureWasShown(oneTime);
		this.view.assertThatRefreshWithExplosionWasCalled(zeroTimes);
		this.view.assertThatRefreshWithSpeedWasCalled(zeroTimes);
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	@Test
	public void presenter_displaysYouLoose_whenCraftReachesLeftEdgeOfSpace() {

		Presenter presenter = new Presenter(this.view, this.spaceFactory, this.scheduler,
				1);
		this.view.refreshCalled.clear(); // clear recorded view refreshes so far

		presenter.start();
		presenter.aimingStarted(initialCraftPosition.getX() - speedFactor * initialSpeed,
				initialCraftPosition.getY());
		presenter.aimingFinished(initialCraftPosition.getX() - speedFactor * initialSpeed,
				initialCraftPosition.getY());

		this.scheduler.assertThatScheduledTasksAreNotNull();
		
		this.scheduler.run(numberOfTimeIncrements);

		this.view.assertThatAimingEnabledWasCalled(oneTime);
		this.view.assertThatAimingDisabledWasCalled(oneTime);
		
		this.scheduler.assertThatCancelWasCalled(oneTime);

		this.view.assertThatRefreshWasCalled(numberOfTimeIncrements);
		this.view.assertThatSpacecraftWasNeverNull();
		this.view.assertThatPlanetsWereNeverNull();

		FakeUI.RefreshRecord record = this.view.refreshCalled
				.get(numberOfTimeIncrements - 1);
		record.assertThatCraftPositionIs(finalCraftPositionToLeft);
		record.assertThatCraftSpeedIs(new Speed(-initialSpeed, 0));
		record.assertThatCraftAngleIs(PI);
		record.assertThatScoreIs(0);
		record.assertThatLevelNumberIs(1);
		record.assertThatNumberOfPlanetsIs(0);

		this.view.assertThatLaunchWasPlayed(oneTime);
		this.view.assertThatExplosionWasPlayed(zeroTimes);
		this.view.assertThatSuccessWasShown(zeroTimes);
		this.view.assertThatFailureWasShown(oneTime);
		this.view.assertThatRefreshWithExplosionWasCalled(zeroTimes);
		this.view.assertThatRefreshWithSpeedWasCalled(zeroTimes);
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	private final SpaceFactory spaceFactory = new FakeSpaceFactory(40, 40);
	private final FakeUI view = new FakeUI();
	private final FakeScheduler scheduler = new FakeScheduler();
	private final Point initialCraftPosition = new Point(20, 20);
	private final Point finalCraftPositionToLeft = new Point(20 - finalPosition, 20);
	private final Point finalCraftPositionToRight = new Point(20 + finalPosition, 20);
	private final Point finalCraftPositionToTop = new Point(20, 20 - finalPosition);
	private final Point finalCraftPositionToBottom = new Point(20, 20 + finalPosition);

	private final static double timeInrement = 0.2;
	private final static double initialSpeed = 3.0;
	private final static double speedFactor = 10;
	private final static int numberOfTimeIncrements = 34;
	private final static double finalPosition = 20.4;

	private final static int oneTime = 1;
	private final static int zeroTimes = 0;
}
