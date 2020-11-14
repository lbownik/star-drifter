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

import gravity.client.core.Planet;
import gravity.client.core.SpaceCraft;
import gravity.client.core.SpaceFactory;

/*******************************************************************************
 *
 ******************************************************************************/
public final class Presenter {

	/****************************************************************************
	 *
	 ***************************************************************************/
	public interface UI {
		
		void setPresenter(Presenter presenter);

		void refresh(SpaceCraft craft, List<Planet> planets, int score, int level,
				int numOfLevels);

		void refreshWithSpeed(SpaceCraft craft, List<Planet> planets, int score,
				int level, int numOfLevels);

		void refreshWithExplosion(SpaceCraft craft, List<Planet> planets, int score,
				int level, int numOfLevels);

		void showFailure();

		void showSuccess(int currrentScore);
		
		void playExlposion();
		
		void playLaunch();

	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	public interface Scheduler {

		void schedule(int interval, Runnable task);

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
	public void activate() {
		
		this.scheduler.schedule(10, this::incrementTime);
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

		this.engine.lounchSpaceCraft();
		this.view.playLaunch();
		this.refreshCommand = this::refreshView;
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	public void playAgain() {

		this.engine.reloadLevel();
		this.refreshCommand = this::refreshView;
		activate();
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	public void playNext() {

		this.engine.loadNextLevel();
		this.refreshCommand = this::refreshView;
		activate();
	}
	/****************************************************************************
	 *
	 ***************************************************************************/
	public void start() {

		this.refreshCommand = this::refreshView;
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	private void incrementTime() {

		this.engine.incrementTime(0.2);
		this.refreshCommand.run();

		switch (this.engine.getGameResult()) {
		case success:
			this.scheduler.cancel();
			this.view.showSuccess(this.engine.getCurrentScore());
			return;
		case failedMissed:
			this.scheduler.cancel();
			this.view.showFailure();
			return;
		case failedCrashed:
			this.scheduler.cancel();
			refreshViewWithExplosion();
			this.view.showFailure();
			return;
		default:
			return;
		}
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	private void refreshView() {

		this.view.refresh(this.engine.getSpaceCraft(), this.engine.getPlanets(),
				this.engine.getTotalScore(), this.engine.getCurrentLevelNo(),
				this.engine.getLevelsCount());
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	private void refreshViewWithSpeed() {

		this.view.refreshWithSpeed(this.engine.getSpaceCraft(), this.engine.getPlanets(),
				this.engine.getTotalScore(), this.engine.getCurrentLevelNo(),
				this.engine.getLevelsCount());
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	private void refreshViewWithExplosion() {

		this.view.playExlposion();
		
		this.view.refreshWithExplosion(this.engine.getSpaceCraft(),
				this.engine.getPlanets(), this.engine.getTotalScore(),
				this.engine.getCurrentLevelNo(), this.engine.getLevelsCount());
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	private final Engine engine;
	private final UI view;
	private final Scheduler scheduler;
	private Runnable refreshCommand = this::refreshView;
}
