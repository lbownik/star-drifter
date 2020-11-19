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

		for (int i = 0; i < numberOfTimeIncrements; ++i) {
			this.scheduler.run();
		}

		
		this.scheduler.assertThatCancelWasCalled(oneTime);
		this.view.assertThatSuccessWasShown(oneTime);
		
		this.view.clearAll();
		this.scheduler.clearAll();
		
		presenter.playAgain();
		
		this.scheduler.assertThatSheduleWasCalled(oneTime);
		
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

		for (int i = 0; i < numberOfTimeIncrements; ++i) {
			this.scheduler.run();
		}

		
		this.scheduler.assertThatCancelWasCalled(oneTime);
		this.view.assertThatSuccessWasShown(oneTime);
		
		this.view.clearAll();
		this.scheduler.clearAll();
		
		presenter.playNext();
		
		this.scheduler.assertThatSheduleWasCalled(oneTime);
		
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
		
		this.view.assertThatRefreshWasCalled(oneTime);
		FakeUI.RefreshRecord record = this.view.refreshCalled.get(0);
		record.assertThatLevelNumberIs(2);
		
		this.view.clearAll();
		this.scheduler.clearAll();
		
		presenter.playNext();
		this.scheduler.run();
		this.scheduler.cancel();
		
		this.view.assertThatRefreshWasCalled(oneTime);
		record = this.view.refreshCalled.get(0);
		record.assertThatLevelNumberIs(1);
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	private final SpaceFactory spaceFactory = new FakeSpaceFactory(200, 40);
	private final FakeUI view = new FakeUI();
	private final FakeScheduler scheduler = new FakeScheduler();
	private final Point initialCraftPosition = new Point(20, 20);
	
	private final static double timeInrement = 0.2;
	private final static double initialSpeed = 10;
	private final static double speedFactor = 10;
	private final static int numberOfTimeIncrements = 90;
	private final static double finalPosition = 20.4;
	
	private final static int oneTime = 1;
	private final static int zeroTimes = 0;
}
