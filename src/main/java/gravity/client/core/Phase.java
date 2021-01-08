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

import java.util.function.IntUnaryOperator;

/*******************************************************************************
 * @author lukasz.bownik@gmail.com
 ******************************************************************************/
public interface Phase {

	/****************************************************************************
	 *
	 ***************************************************************************/
	default void incrementTime(final double interval) {

		return;
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	int getIndex();

	/****************************************************************************
	 *
	 ***************************************************************************/
	static Phase constant(final int index) {

		return () -> index;
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	static Phase forwardLooping(final int maxIndex, final double changeTimeInterval) {

		return new Looping(0, changeTimeInterval,
				(index) -> (index < maxIndex) ? index + 1 : 0);
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	static Phase backwardLooping(final int maxIndex, final double changeTimeInterval) {

		return new Looping(maxIndex, changeTimeInterval,
				(index) -> (index > 0) ? index - 1 : maxIndex);
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	class Looping implements Phase {

		/*************************************************************************
		 *
		 ************************************************************************/
		public Looping(final int index, final double changeTimeInterval,
				final IntUnaryOperator operator) {

			throwIf(index < 0, "Negative index.");
			throwIf(changeTimeInterval <= 0.0, "Non-positive changeTimeInterval.");

			this.index = index;
			this.changeTimeInterval = changeTimeInterval;
			this.operator = operator;
		}

		/*************************************************************************
		 *
		 ************************************************************************/
		@Override
		public void incrementTime(final double interval) {

			this.threshold += interval;
			if (this.threshold >= this.changeTimeInterval) {
				this.threshold = 0;
				this.index = this.operator.applyAsInt(this.index);
			}
		}

		/*************************************************************************
		 *
		 ************************************************************************/
		@Override
		public int getIndex() {

			return this.index;
		}

		/*************************************************************************
		 *
		 ************************************************************************/
		private int index;
		private final double changeTimeInterval;
		private double threshold = 0;
		private final IntUnaryOperator operator;
	}
}
