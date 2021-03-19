package gravity.client.fakes;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import gravity.client.app.Presenter;

/*******************************************************************************
 * @author lukasz.bownik@gmail.com
 ******************************************************************************/
public class FakeScheduler implements Presenter.Scheduler {

	/****************************************************************************
	 *
	 ***************************************************************************/
	@Override
	public void schedule(final Presenter presenter) {

		this.scheduleCalled.add(presenter);
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	@Override
	public void cancel() {

		this.cancelCalled.add(true);
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	public void run() {

		this.scheduleCalled.forEach(p -> p.incrementTime(timeInterval));
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	public void run(final int numberOfTimeIncrements) {

		for (int i = 0; i < numberOfTimeIncrements; ++i) {
			this.run();
		}
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	public void assertThatSheduleWasCalled(final int numberOfTimes) {

		assertEquals(numberOfTimes, this.scheduleCalled.size());
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	public void assertThatCancelWasCalled(final int times) {

		assertEquals(times, this.cancelCalled.size());
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	public void assertThatInvariantsHoldTrue() {

		assertThatScheduledTasksAreNeverNull();
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	private void assertThatScheduledTasksAreNeverNull() {

		assertTrue(this.scheduleCalled.stream().allMatch(Objects::nonNull));
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	public void clearAll() {

		this.scheduleCalled.clear();
		this.cancelCalled.clear();
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	public List<Boolean> cancelCalled = new ArrayList<>();
	public List<Presenter> scheduleCalled = new ArrayList<>();
	public static final int timeInterval = 10;

}
