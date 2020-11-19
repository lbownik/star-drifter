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

		presenter.activate();
		presenter.aimingStarted(initialCraftPosition.getX() + initialSpeed * speedFactor,
				initialCraftPosition.getY());
		presenter.aimingFinished(initialCraftPosition.getX() + initialSpeed * speedFactor,
				initialCraftPosition.getY());

		for (int i = 0; i < timeIncrements; ++i) {
			this.scheduler.run();
		}
		this.scheduler.assertThatCancelWasCalled(oneTime);

		this.scheduler.assertThatSheduleWasCalled(oneTime);
		
		this.view.assertThatRefreshWasCalled(timeIncrements);
		FakeUI.RefreshRecord rr = this.view.refreshCalled.get(timeIncrements - 1);
		rr.assertThatCraftPositionIs(finalCraftPositionToRight);
		rr.assertThatCraftSpeedIs(new Speed(finalSpeed, 0));
		rr.assertThatScoreIs(0);
		rr.assertThatLevelNumberIs(2);
		rr.assertThatNumberOfPlanetsIs(1);

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
	
	private final static double timeInrement = 0.2;
	private final static double initialSpeed = 1.0;
	private final static double speedFactor = 10;
	private final static int timeIncrements = 70;
	private final static double finalPosition = 86.7679;
	private final static double finalSpeed = 12.7089;
	
	private final static int oneTime = 1;
	private final static int zeroTimes = 0;
}
