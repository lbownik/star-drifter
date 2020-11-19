package gravity.client.fakes;

import java.util.ArrayList;
import java.util.List;

import gravity.client.app.Presenter;
import static org.junit.Assert.assertEquals;

/*******************************************************************************
 * @author lukasz.bownik@gmail.com
 ******************************************************************************/
public class FakeScheduler implements Presenter.Scheduler {

	/****************************************************************************
	 *
	 ***************************************************************************/
	public static class Record {

		/*************************************************************************
		 *
		 ************************************************************************/
		public Record(final int interval, final Runnable task) {

			this.interval = interval;
			this.task = task;
		}

		/*************************************************************************
		 *
		 ************************************************************************/
		public final int interval;
		public final Runnable task;
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	@Override
	public void schedule(final int interval, final Runnable task) {

		this.scheduleCalled.add(new Record(interval, task));
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

		this.scheduleCalled.forEach(r -> r.task.run());
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
	public void clearAll() {
		
		this.scheduleCalled.clear();
		this.cancelCalled.clear();
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	public List<Boolean> cancelCalled = new ArrayList<>();
	public List<Record> scheduleCalled = new ArrayList<>();

}
