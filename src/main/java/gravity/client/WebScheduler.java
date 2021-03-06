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

import com.google.gwt.user.client.Timer;

import gravity.client.app.Presenter;

/*******************************************************************************
 * @author lukasz.bownik@gmail.com
 ******************************************************************************/
public final class WebScheduler extends Timer implements Presenter.Scheduler {

	/****************************************************************************
	 *
	 ***************************************************************************/
	public WebScheduler(final int interval) {

		this.interval = interval;
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	@Override
	public void schedule(final Presenter presenter) {

		this.presenter = presenter;
		scheduleRepeating(this.interval);
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	@Override
	public void cancel() {

		this.presenter = null;
		super.cancel();
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	@Override
	public void run() {

		if (this.presenter != null) {
			this.presenter.incrementTime(this.interval);
		}
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	private final int interval;
	private Presenter presenter;
}
