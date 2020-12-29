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
package gravity.client.core;

import static gravity.client.core.Preconditions.throwIf;

/*******************************************************************************
 * @author lukasz.bownik@gmail.com
 ******************************************************************************/
public interface Phase {

	/****************************************************************************
	 *
	 ***************************************************************************/
	void incrementTime(final double interval);

	/****************************************************************************
	 *
	 ***************************************************************************/
	int getValue();

	/****************************************************************************
	 *
	 ***************************************************************************/
	class Static implements Phase {

		/*************************************************************************
		 *
		 ************************************************************************/
		@Override
		public void incrementTime(final double interval) {

			return;
		}

		/*************************************************************************
		 *
		 ************************************************************************/
		@Override
		public int getValue() {

			return 0;
		}
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	class Looping implements Phase {

		/*************************************************************************
		 *
		 ************************************************************************/
		public Looping(final int maxValue,
				final double changeTimeInterval, final int valueIncrement) {

			throwIf(valueIncrement == 0, "valueIncrement is 0");

			this.maxValue = maxValue;
			this.changeTimeInterval = changeTimeInterval;

			if (valueIncrement > 0) {
				this.value = 0;
				this.changePhase = () -> this.increasePhase(valueIncrement);
			} else {
				this.value = maxValue;
				this.changePhase = () -> this.decreasePhase(-valueIncrement);
			}
		}

		/*************************************************************************
		 *
		 ************************************************************************/
		@Override
		public void incrementTime(final double interval) {

			this.threshold += interval;
			if (this.threshold >= this.changeTimeInterval) {
				this.threshold = 0;
				this.changePhase.run();
			}
		}

		/*************************************************************************
		 *
		 ************************************************************************/
		private void increasePhase(final int delta) {

			this.value = (this.value < this.maxValue) ? this.value + delta : 0;
		}

		/*************************************************************************
		 *
		 ************************************************************************/
		private void decreasePhase(final int delta) {

			this.value = (this.value > 0) ? this.value - delta : this.maxValue;
		}

		/*************************************************************************
		 *
		 ************************************************************************/
		@Override
		public int getValue() {

			return this.value;
		}

		/*************************************************************************
		 *
		 ************************************************************************/
		private int value;
		private final int maxValue;
		private final double changeTimeInterval;
		private double threshold = 0;
		private final Runnable changePhase;
	}
}
