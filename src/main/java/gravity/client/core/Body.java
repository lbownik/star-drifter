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
import static gravity.client.core.Preconditions.throwIfNull;
import static java.lang.Math.hypot;

import java.util.List;
import java.util.function.DoubleUnaryOperator;

/*******************************************************************************
 * @author lukasz.bownik@gmail.com
 ******************************************************************************/
public abstract class Body {

	/****************************************************************************
	 *
	 ***************************************************************************/
	public Body(final double mass, final Position center, final Speed speed,
			final IncrementableOperator angleStrategy) {

		throwIf(mass < 0, "Negative mass");
		throwIfNull(center, "Null center");
		throwIfNull(speed, "Null speed");
		throwIfNull(angleStrategy, "Null angleStrategy");

		this.mass = mass;
		this.center = center;
		this.speed = speed;
		this.angleStrategy = angleStrategy;
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	public Position getCenter() {

		return this.center;
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	public Force gravitationalForceFrom(final Body body) {

		if (body == this) {
			return Force.zero();
		} else {
			final double dx = body.center.dx(this.center);
			final double dy = body.center.dy(this.center);
			final double distance = hypot(dx, dy);
			// appearently true gravity does not make game very exiting
			final double squaredDistance = distance;// * distance;

			if (squaredDistance == 0.0) {
				throw new IllegalStateException("Two objects cannot ocupy the same point.");
			}

			final double F = G * this.mass * body.mass / squaredDistance;
			return new Force(F * dx / distance, F * dy / distance);
		}
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	public Force gravitationalForceFrom(final List<? extends Body> bodies) {

		return bodies.stream().map(this::gravitationalForceFrom).reduce(Force.zero(),
				Force::combineWith);
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	public void moveBy(final Force force, final double timeInterval) {

		this.speed.changeBy(force.accelerate(this.mass), timeInterval);
		this.center.moveAt(this.speed, timeInterval);
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	protected void moveTo(final Position position) {

		this.center = position;
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	public void incrementTime(final double interval) {

		this.angleStrategy.incrementTime(interval);
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	public double distanceTo(final Body other) {

		return this.center.distanceTo(other.center);
	}
	/****************************************************************************
	 *
	 ***************************************************************************/
	public boolean isCollidingWith(final Body other) {

		final double minDistance = this.getRadius() + other.getRadius();
		return distanceTo(other) <= minDistance;
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	public Vector getSpeed() {

		return this.speed;
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	public double getAngle() {

		return this.angleStrategy.applyAsDouble(this.speed.getAngle());
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	public void setSpeed(final Speed speed) {

		this.speed = speed;
	}
	/****************************************************************************
	 *
	 ***************************************************************************/
	public abstract double getRadius();

	/****************************************************************************
	 *
	 ***************************************************************************/
	public static IncrementableOperator angleFollowsSpeed() {

		return a -> a;
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	public static IncrementableOperator angleIsFixedAt(final double angle) {

		return a -> angle;
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	public static IncrementableOperator angleCirculatesAt(final double delta) {

		return new CircularOperator(delta);
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	private final double mass;
	private Position center;
	private Speed speed;
	private final IncrementableOperator angleStrategy;

	private static final double G = 1;

	/****************************************************************************
	 *
	 ***************************************************************************/
	interface IncrementableOperator extends DoubleUnaryOperator {

		/*************************************************************************
		 *
		 ************************************************************************/
		@SuppressWarnings("unused")
		public default void incrementTime(final double interval) {

			return;
		}
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	private static final class CircularOperator
			implements IncrementableOperator {

		/*************************************************************************
		 *
		 ************************************************************************/
		public CircularOperator(final double speed) {

			this.speed = speed;
		}

		/*************************************************************************
		 *
		 ************************************************************************/
		@Override
		public double applyAsDouble(final double x) {

			return this.angle;
		}
		/*************************************************************************
		 *
		 ************************************************************************/
		@Override
		public void incrementTime(final double interval) {

			this.angle += this.speed * interval;
		}

		/*************************************************************************
		 *
		 ************************************************************************/
		private double angle = 0;
		private final double speed;
	}
}
