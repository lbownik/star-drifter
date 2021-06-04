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

package gravity.client.app;

import java.util.List;

import gravity.client.core.Body;
import gravity.client.core.SpaceFactory;

/*******************************************************************************
 * @author lukasz.bownik@gmail.com
 ******************************************************************************/
public final class Presenter {

	/****************************************************************************
	 *
	 ***************************************************************************/
	public interface UI {

		void setPresenter(Presenter presenter);

		void refresh(Body craft, List<? extends Body> planets,
				List<? extends Body> backPlanets, int score, int level, int numOfLevels);

		void refreshWithSpeed(Body craft, List<? extends Body> planets,
				List<? extends Body> backPlanets, int score, int level, int numOfLevels);

		void showFailure();

		void showSuccess(int currrentScore);

		void playExlposion();

		void playLaunch();

		void enableAming();

		void disableAiming();
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	public interface Scheduler {

		void schedule(Presenter presenter);

		void cancel();
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	public Presenter(final Presenter.UI view, final SpaceFactory spaceFacotry,
			final Scheduler scheduler, int initialLevel) {

		this.view = view;
		this.view.setPresenter(this);
		this.scheduler = scheduler;
		this.engine = new Engine(spaceFacotry, initialLevel);
		refreshView();
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	public void aimingStarted(final double x, final double y) {

		this.engine.setSpaceCraftSpeed(x, y);
		this.refreshCommand = this::refreshViewWithSpeed;
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	public void aimingFinished(final double x, final double y) {

		this.view.disableAiming();
		this.engine.lounchSpaceCraft();
		this.view.playLaunch();
		this.refreshCommand = this::refreshView;
		this.playExplosionCommand = this.view::playExlposion;
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	public void playAgain() {

		this.engine.reloadLevel();
		this.refreshCommand = this::refreshView;
		start();
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	public void playNext() {

		this.engine.loadNextLevel();
		this.refreshCommand = this::refreshView;
		start();
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	public void start() {

		this.scheduler.schedule(this);
		this.refreshCommand = this::refreshView;
		this.view.enableAming();
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	public void incrementTime(final int interval_ms) {

		this.engine.incrementTime(interval_ms, this);
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	void inProgress() {

		this.refreshCommand.run();
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	void crashed() {

		this.playExplosionCommand.run();
		this.playExplosionCommand = () -> {
		};
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	void success() {

		this.scheduler.cancel();
		refreshView();
		this.view.showSuccess(this.engine.getCurrentScore());
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	void failure() {

		this.scheduler.cancel();
		refreshView();
		this.view.showFailure();
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	private void refreshView() {

		this.view.refresh(this.engine.getSpaceCraft(), this.engine.getPlanets(),
				this.engine.getBackPlanets(), this.engine.getTotalScore(),
				this.engine.getCurrentLevelNo(), this.engine.getLevelsCount());
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	private void refreshViewWithSpeed() {

		this.view.refreshWithSpeed(this.engine.getSpaceCraft(), this.engine.getPlanets(),
				this.engine.getBackPlanets(), this.engine.getTotalScore(),
				this.engine.getCurrentLevelNo(), this.engine.getLevelsCount());
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	private final Engine engine;
	private final UI view;
	private final Scheduler scheduler;
	private Runnable refreshCommand = this::refreshView;
	private Runnable playExplosionCommand = () -> {
	};
}
