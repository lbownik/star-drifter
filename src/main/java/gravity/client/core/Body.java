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

import static java.lang.Math.hypot;
import static gravity.client.core.Preconditions.*;
import java.util.List;

/*******************************************************************************
 *
 ******************************************************************************/
public abstract class Body {

	/****************************************************************************
	 *
	 ***************************************************************************/
	public Body(final double mass, final Point center, final Speed speed) {

		throwIf(mass < 0, "Negative mass"::toString);
		throwIfNull(center, "Null center"::toString);
		throwIfNull(speed, "Null speed"::toString);

		this.mass = mass;
		this.center = center;
		this.speed = speed;
	}
	/****************************************************************************
	 *
	 ***************************************************************************/
	public Point getCenter() {

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

		return bodies.stream()
				  .map(this::gravitationalForceFrom)
				  .reduce(Force.zero(), Force::combineWith);
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
	public double distanceTo(final Body other) {

		return this.center.distanceTo(other.center);
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
	public void setSpeed(final Speed speed) {

		this.speed = speed;
	}
	
	/****************************************************************************
	 *
	 ***************************************************************************/
	private final double mass;
	private final Point center;
	private Speed speed;
	private static final double G = 1;
}
