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
	public Body(final double mass, final double radius, final Position center,
			final Speed speed, final IncrementableOperator angleStrategy,
			final Phase phase) {

		throwIf(mass < 0, "Negative mass");
		throwIf(radius < 0, "Negative radius");
		throwIfNull(center, "Null center");
		throwIfNull(speed, "Null speed");
		throwIfNull(angleStrategy, "Null angleStrategy");
		throwIfNull(phase, "Null phase");

		this.mass = mass;
		this.radius = radius;
		this.center = center;
		this.speed = speed;
		this.phase = phase;
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
	Force gravitationalForceFrom(final Body body) {

		// appearently true gravity does not make the game very exiting
		final double dx = body.center.dx(this.center);
		final double dy = body.center.dy(this.center);
		final double distance = hypot(dx, dy);

		if (distance < 0.1) {
			return Force.zero();
		} else {
			final double F = this.mass * body.mass / distance;
			return new Force(F * dx / distance, F * dy / distance);
		}
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	Force gravitationalForceFrom(final List<? extends Body> bodies) {

		return bodies.stream().map(this::gravitationalForceFrom).reduce(Force.zero(),
				Force::combineWith);
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	void moveBy(final Force force, final double timeInterval) {

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
	void incrementTime(final double interval) {

		this.angleStrategy.incrementTime(interval);
		this.phase.incrementTime(interval);
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	double distanceTo(final Body other) {

		return this.center.distanceTo(other.center);
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	boolean isCollidingWith(final Body other) {

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
	public double getRadius() {

		return this.radius;
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	public abstract String getName();

	/****************************************************************************
	 *
	 ***************************************************************************/
	public int getPhaseIndex() {

		return this.phase.getIndex();
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	static IncrementableOperator angleFollowsSpeed() {

		return a -> a;
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	static IncrementableOperator angleIsFixedAt(final double angle) {

		return a -> angle;
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	static IncrementableOperator angleCirculatesAt(final double delta) {

		return new CircularOperator(delta);
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	private final double mass;
	private Position center;
	private final double radius;
	private Speed speed;
	private final Phase phase;
	private final IncrementableOperator angleStrategy;

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
	private static final class CircularOperator implements IncrementableOperator {

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
