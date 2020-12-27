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

package gravity.client.ui;

import static com.google.gwt.user.client.Event.ONMOUSEDOWN;
import static com.google.gwt.user.client.Event.ONMOUSEMOVE;
import static com.google.gwt.user.client.Event.ONMOUSEUP;
import static com.google.gwt.user.client.Event.setEventListener;
import static com.google.gwt.user.client.Event.sinkEvents;
import static java.lang.String.valueOf;

import java.util.List;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.dom.client.CanvasElement;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.user.client.Event;

import gravity.client.core.Body;
import gravity.client.core.Planet;
import gravity.client.core.SpaceCraft;

/*******************************************************************************
 * @author lukasz.bownik@gmail.com
 ******************************************************************************/
public final class SpaceCanvas {

	/****************************************************************************
	 *
	 ***************************************************************************/
	public interface MouseEventListener {
		void onMouseEvent(int type, int button, int x, int y);
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	public SpaceCanvas(final MouseEventListener mouseListener) {

		this.canvas = (CanvasElement) this.document.getElementById("canvas");
		this.context = this.canvas.getContext2d();
		this.mouseListener = mouseListener;

		sinkEvents(this.canvas, ONMOUSEDOWN | ONMOUSEUP | ONMOUSEMOVE);
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	public void enableClickEvents() {

		setEventListener(this.canvas, this::onCanvasEvent);
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	public void disableClickEvents() {

		setEventListener(this.canvas, null);
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	public int getVisibleWidth() {

		return this.canvas.getClientWidth();
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	public int getVisibleHeight() {

		return this.canvas.getClientHeight();
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	private void onCanvasEvent(final Event e) {

		this.mouseListener.onMouseEvent(e.getTypeInt(), e.getButton(),
				e.getClientX() - this.canvas.getAbsoluteLeft(),
				e.getClientY() - this.canvas.getAbsoluteTop());
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	public void refresh(final SpaceCraft craft, final List<Planet> planets,
			final int score, final int level, final int numOfLevels) {

		clear();
		draw(planets, craft, score, level, numOfLevels);
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	public void refreshWithSpeed(final SpaceCraft craft, final List<Planet> planets,
			final int score, final int level, final int numOfLevels) {

		clear();
		drawWithSpeed(planets, craft, score, level, numOfLevels);
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	public void refreshWithExplosion(final SpaceCraft craft, final List<Planet> planets,
			final int score, final int level, final int numOfLevels) {

		clear();
		drawWithExplosion(planets, craft, score, level, numOfLevels);
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	private void draw(final List<Planet> planets, final SpaceCraft craft,
			final int score, final int level, final int numOfLevels) {

		draw(planets);
		draw(score, level, numOfLevels);
		draw(craft);
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	private void drawWithSpeed(final List<Planet> planets, final SpaceCraft craft,
			final int score, final int level, final int numOfLevels) {

		draw(planets);
		drawWithSpeed(craft);
		draw(score, level, numOfLevels);
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	private void drawWithExplosion(final List<Planet> planets, final SpaceCraft craft,
			final int score, final int level, final int numOfLevels) {

		draw(planets);
		drawWithExplosion(craft);
		draw(score, level, numOfLevels);
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	private void drawWithSpeed(final SpaceCraft craft) {

		draw(craft);

		final double x = craft.getCenter().getX();
		final double y = craft.getCenter().getY();

		this.context.beginPath();
		this.context.setStrokeStyle("red");
		this.context.setLineWidth(3);
		this.context.moveTo(x, y);
		this.context.lineTo(x + craft.getSpeed().getX(), y + craft.getSpeed().getY());
		this.context.stroke();
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	private void drawWithExplosion(final SpaceCraft craft) {

		final double x = craft.getCenter().getX() - getImage("explosion").getWidth() / 2;
		final double y = craft.getCenter().getY() - getImage("explosion").getHeight() / 2;

		this.context.drawImage(getImage("explosion"), x, y);
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	private void draw(final List<Planet> planets) {

		planets.forEach(planet -> draw(planet));
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	private void draw(final Planet planet) {

		draw(planet, getImage(planet.getType().name()));
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	private void draw(final SpaceCraft craft) {
		
		draw(craft, getImage("spaceship"));
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	private void draw(final Body body, final ImageElement image) {

		this.context.save();

		this.context.translate(body.getCenter().getX(), body.getCenter().getY());
		this.context.rotate(body.getAngle());
		this.context.drawImage(image, -body.getRadius(), -body.getRadius());
		
		this.context.restore();
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	private void draw(final int score, final int level, final int numOfLevels) {

		this.context.fillText("Level " + valueOf(level) + " of " + valueOf(numOfLevels),
				30, 30);
		this.context.fillText("Total Score: ".concat(valueOf(score)), 600, 30);
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	private void clear() {

		this.context.setFillStyle("yellow");
		this.context.setFont("20px Comic Sans MS");
		this.context.clearRect(0, 0, this.canvas.getWidth(), this.canvas.getHeight());
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	private ImageElement getImage(final String name) {

		return (ImageElement) this.document.getElementById(name);
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	private final CanvasElement canvas;
	private final Context2d context;
	private final MouseEventListener mouseListener;
	private final Document document = Document.get();

}
