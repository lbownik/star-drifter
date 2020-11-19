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

		FakeUI.RefreshRecord rr = this.view.refreshCalled.get(0);
		rr.assertThatCraftPositionIs(initialCraftPosition);
		rr.assertThatCraftSpeedIs(Speed.zero());
		rr.assertThatScoreIs(0);
		rr.assertThatLevelNumberIs(1);
		rr.assertThatNumberOfPlanetsIs(0);

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

		presenter.activate();
		this.scheduler.run();

		this.view.assertThatRefreshWasCalled(oneTime);
		FakeUI.RefreshRecord rr = this.view.refreshCalled.get(0);
		rr.assertThatCraftPositionIs(initialCraftPosition);
		rr.assertThatCraftSpeedIs(Speed.zero());
		rr.assertThatScoreIs(0);
		rr.assertThatLevelNumberIs(1);
		rr.assertThatNumberOfPlanetsIs(0);

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

		presenter.activate();
		presenter.aimingStarted(initialCraftPosition.getX() + speedFactor * initialSpeed,
				initialCraftPosition.getY());
		this.scheduler.run();

		this.view.assertThatRefreshWithSpeedWasCalled(oneTime);

		FakeUI.RefreshRecord rr = this.view.refreshWithSpeedCalled.get(0);
		rr.assertThatCraftPositionIs(initialCraftPosition);
		rr.assertThatCraftSpeedIs(new Speed(initialSpeed, 0));
		rr.assertThatScoreIs(0);
		rr.assertThatLevelNumberIs(1);
		rr.assertThatNumberOfPlanetsIs(0);

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

		presenter.activate();
		presenter.aimingStarted(initialCraftPosition.getX() + speedFactor * initialSpeed,
				initialCraftPosition.getY());
		presenter.aimingFinished(initialCraftPosition.getX() + speedFactor * initialSpeed,
				initialCraftPosition.getY());

		this.scheduler.run();

		this.view.assertThatRefreshWasCalled(oneTime);

		FakeUI.RefreshRecord rr = this.view.refreshCalled.get(0);
		rr.assertThatCraftPositionIs(
				new Point(initialCraftPosition.getX() + timeInrement * 3.0,
						initialCraftPosition.getY()));
		rr.assertThatCraftSpeedIs(new Speed(initialSpeed, 0));
		rr.assertThatScoreIs(0);
		rr.assertThatLevelNumberIs(1);
		rr.assertThatNumberOfPlanetsIs(0);

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

		presenter.activate();
		presenter.aimingStarted(initialCraftPosition.getX() + speedFactor * initialSpeed,
				initialCraftPosition.getY());
		presenter.aimingFinished(initialCraftPosition.getX() + speedFactor * initialSpeed,
				initialCraftPosition.getY());

		for (int i = 0; i < timeIncrements; ++i) {
			this.scheduler.run();
		}

		this.scheduler.assertThatCancelWasCalled(oneTime);
		
		this.view.assertThatRefreshWasCalled(timeIncrements);

		FakeUI.RefreshRecord rr = this.view.refreshCalled.get(timeIncrements - 1);
		rr.assertThatCraftPositionIs(finalCraftPositionToRight);
		rr.assertThatCraftSpeedIs(new Speed(initialSpeed, 0));
		rr.assertThatScoreIs(0);
		rr.assertThatLevelNumberIs(1);
		rr.assertThatNumberOfPlanetsIs(0);

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

		presenter.activate();
		presenter.aimingStarted(initialCraftPosition.getX(),
				initialCraftPosition.getY() + speedFactor * initialSpeed);
		presenter.aimingFinished(initialCraftPosition.getX(),
				initialCraftPosition.getY() + speedFactor * initialSpeed);

		for (int i = 0; i < timeIncrements; ++i) {
			this.scheduler.run();
		}
		
		this.scheduler.assertThatCancelWasCalled(oneTime);

		this.view.assertThatRefreshWasCalled(timeIncrements);

		FakeUI.RefreshRecord rr = this.view.refreshCalled.get(timeIncrements - 1);
		rr.assertThatCraftPositionIs(finalCraftPositionToBottom);
		rr.assertThatCraftSpeedIs(new Speed(0, initialSpeed));
		rr.assertThatScoreIs(0);
		rr.assertThatLevelNumberIs(1);
		rr.assertThatNumberOfPlanetsIs(0);

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

		presenter.activate();
		presenter.aimingStarted(initialCraftPosition.getX(),
				initialCraftPosition.getY() - speedFactor * initialSpeed);
		presenter.aimingFinished(initialCraftPosition.getX(),
				initialCraftPosition.getY() - speedFactor * initialSpeed);

		for (int i = 0; i < timeIncrements; ++i) {
			this.scheduler.run();
		}
		
		this.scheduler.assertThatCancelWasCalled(oneTime);
		
		this.view.assertThatRefreshWasCalled(timeIncrements);

		FakeUI.RefreshRecord rr = this.view.refreshCalled.get(timeIncrements - 1);
		rr.assertThatCraftPositionIs(finalCraftPositionToTop);
		rr.assertThatCraftSpeedIs(new Speed(0, -initialSpeed));
		rr.assertThatScoreIs(0);
		rr.assertThatLevelNumberIs(1);
		rr.assertThatNumberOfPlanetsIs(0);

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

		presenter.activate();
		presenter.aimingStarted(initialCraftPosition.getX() - speedFactor * initialSpeed,
				initialCraftPosition.getY());
		presenter.aimingFinished(initialCraftPosition.getX() - speedFactor * initialSpeed,
				initialCraftPosition.getY());

		for (int i = 0; i < timeIncrements; ++i) {
			this.scheduler.run();
		}

		this.scheduler.assertThatCancelWasCalled(oneTime);
		
		this.view.assertThatRefreshWasCalled(timeIncrements);

		FakeUI.RefreshRecord rr = this.view.refreshCalled.get(timeIncrements - 1);
		rr.assertThatCraftPositionIs(finalCraftPositionToLeft);
		rr.assertThatCraftSpeedIs(new Speed(-initialSpeed, 0));
		rr.assertThatScoreIs(0);
		rr.assertThatLevelNumberIs(1);
		rr.assertThatNumberOfPlanetsIs(0);

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
	private final Point finalCraftPositionToTop = new Point(20, 20  - finalPosition);
	private final Point finalCraftPositionToBottom = new Point(20, 20  + finalPosition);

	private final static double timeInrement = 0.2;
	private final static double initialSpeed = 3.0;
	private final static double speedFactor = 10;
	private final static int timeIncrements = 34;
	private final static double finalPosition = 20.4;

	private final static int oneTime = 1;
	private final static int zeroTimes = 0;
}
