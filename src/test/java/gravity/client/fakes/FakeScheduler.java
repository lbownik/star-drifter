package gravity.client.fakes;

import java.util.ArrayList;
import java.util.List;

import gravity.client.app.Presenter;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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

		this.scheduleCalled.forEach(p -> p.incrementTime(10));
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
	public void assertThatScheduledTasksAreNotNull() {

		assertTrue(this.scheduleCalled.stream().allMatch(p -> p != null));
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

}
