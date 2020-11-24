package gravity.client.fakes;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import gravity.client.app.Presenter;
import gravity.client.core.Planet;
import gravity.client.core.Point;
import gravity.client.core.SpaceCraft;
import gravity.client.core.Speed;

/*******************************************************************************
 * @author lukasz.bownik@gmail.com
 ******************************************************************************/
public class FakeUI implements Presenter.UI {

	/****************************************************************************
	 *
	 ***************************************************************************/
	public static class RefreshRecord {

		/*************************************************************************
		 *
		 ************************************************************************/
		public RefreshRecord(final SpaceCraft craft, final List<Planet> planets,
				final int score, final int level, final int numOfLevels) {

			this.craft = craft;
			this.planets = planets;
			this.score = score;
			this.level = level;
			this.numOfLevels = numOfLevels;
		}

		/*************************************************************************
		 *
		 ************************************************************************/
		public void assertThatLevelNumberIs(final int number) {

			assertEquals(number, this.level);
		}

		/*************************************************************************
		 *
		 ************************************************************************/
		public void assertThatScoreIs(final int score) {

			assertEquals(score, this.score);
		}

		/*************************************************************************
		 *
		 ************************************************************************/
		public void assertThatNumberOfPlanetsIs(final int numberOfPlanets) {

			assertEquals(numberOfPlanets, this.planets.size());
		}

		/*************************************************************************
		 *
		 ************************************************************************/
		public void assertThatCraftPositionIs(final Point position) {

			assertEquals(position.getX(), this.craft.getCenter().getX(), 0.0001);
			assertEquals(position.getY(), this.craft.getCenter().getY(), 0.0001);
		}

		/*************************************************************************
		 *
		 ************************************************************************/
		public void assertThatCraftSpeedIs(final Speed speed) {

			assertEquals(speed.getX(), this.craft.getSpeed().getX(), 0.0001);
			assertEquals(speed.getY(), this.craft.getSpeed().getY(), 0.0001);
		}
		/*************************************************************************
		 *
		 ************************************************************************/
		public void assertThatCraftAngleIs(final double angle) {

			assertEquals(angle, this.craft.getSpeed().getAngle(), 0.0001);
		}

		/*************************************************************************
		 *
		 ************************************************************************/
		public final SpaceCraft craft;
		public final List<Planet> planets;
		public final int score;
		public final int level;
		public final int numOfLevels;
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	@Override
	public void setPresenter(final Presenter presenter) {

		this.presenter = presenter;
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	@Override
	public void refresh(final SpaceCraft craft, final List<Planet> planets,
			final int score, final int level, final int numOfLevels) {

		this.refreshCalled
				.add(new RefreshRecord(craft, planets, score, level, numOfLevels));
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	@Override
	public void refreshWithSpeed(final SpaceCraft craft, final List<Planet> planets,
			final int score, final int level, final int numOfLevels) {

		this.refreshWithSpeedCalled
				.add(new RefreshRecord(craft, planets, score, level, numOfLevels));
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	@Override
	public void refreshWithExplosion(final SpaceCraft craft, final List<Planet> planets,
			final int score, final int level, final int numOfLevels) {

		this.refreshWithExplosionCalled
				.add(new RefreshRecord(craft, planets, score, level, numOfLevels));
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	@Override
	public void showFailure() {

		this.failureShown.add(true);
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	@Override
	public void showSuccess(final int currrentScore) {

		this.successShown.add(currrentScore);
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	@Override
	public void playExlposion() {

		this.explosionPlayed.add(true);
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	@Override
	public void playLaunch() {

		this.launchPlayed.add(true);
	}
	/****************************************************************************
	 *
	 ***************************************************************************/
	@Override
	public void enableAming() {
		
		this.aimingEnabled.add(true);
	}
	/****************************************************************************
	 *
	 ***************************************************************************/
	@Override
	public void disableAiming() {
	
		this.aimingDisabled.add(true);
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	public void assertThatLaunchWasPlayed(final int numberOfTimes) {

		assertEquals(numberOfTimes, this.launchPlayed.size());
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	public void assertThatExplosionWasPlayed(final int numberOfTimes) {

		assertEquals(numberOfTimes, this.explosionPlayed.size());
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	public void assertThatFailureWasShown(final int numberOfTimes) {

		assertEquals(numberOfTimes, this.failureShown.size());
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	public void assertThatSuccessWasShown(final int numberOfTimes) {

		assertEquals(numberOfTimes, this.successShown.size());
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	public void assertThatRefreshWasCalled(final int numberOfTimes) {

		assertEquals(numberOfTimes, this.refreshCalled.size());
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	public void assertThatRefreshWithSpeedWasCalled(final int numberOfTimes) {

		assertEquals(numberOfTimes, this.refreshWithSpeedCalled.size());
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	public void assertThatRefreshWithExplosionWasCalled(final int numberOfTimes) {

		assertEquals(numberOfTimes, this.refreshWithExplosionCalled.size());
	}
	
	/****************************************************************************
	 *
	 ***************************************************************************/
	public void assertThatAimingEnabledWasCalled(final int numberOfTimes) {

		assertEquals(numberOfTimes, this.aimingEnabled.size());
	}
	/****************************************************************************
	 *
	 ***************************************************************************/
	public void assertThatAimingDisabledWasCalled(final int numberOfTimes) {

		assertEquals(numberOfTimes, this.aimingDisabled.size());
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	public void assertThatPresenterIs(final Presenter presenter) {

		assertEquals(presenter, this.presenter);
	}
	/****************************************************************************
	 *
	 ***************************************************************************/
	public void clearAll() {
		
		this.presenter = null;
		this.explosionPlayed.clear();
		this.failureShown.clear();
		this.launchPlayed.clear();
		this.refreshCalled.clear();
		this.refreshWithExplosionCalled.clear();
		this.refreshWithSpeedCalled.clear();
		this.successShown.clear();
		this.aimingDisabled.clear();
		this.aimingEnabled.clear();
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	public Presenter presenter;
	public List<Boolean> explosionPlayed = new ArrayList<>();
	public List<Boolean> launchPlayed = new ArrayList<>();
	public List<Boolean> aimingEnabled = new ArrayList<>();
	public List<Boolean> aimingDisabled = new ArrayList<>();
	public List<Boolean> failureShown = new ArrayList<>();
	public List<Integer> successShown = new ArrayList<>();
	public List<RefreshRecord> refreshCalled = new ArrayList<>();
	public List<RefreshRecord> refreshWithSpeedCalled = new ArrayList<>();
	public List<RefreshRecord> refreshWithExplosionCalled = new ArrayList<>();

}
