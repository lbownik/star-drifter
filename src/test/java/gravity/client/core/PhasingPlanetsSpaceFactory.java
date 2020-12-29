package gravity.client.core;

import static gravity.client.core.Planet.Type.meteorite;
import static gravity.client.core.Planet.Type.rocky;
import static gravity.client.core.Body.angleCirculatesAt;
import static java.lang.Math.PI;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

/*******************************************************************************
 * @author lukasz.bownik@gmail.com
 *******************************************************************************/
public final class PhasingPlanetsSpaceFactory implements SpaceFactory {

	/****************************************************************************
	 *
	 ***************************************************************************/
	public PhasingPlanetsSpaceFactory(final int spaceWidth, final int spaceHeight) {

		this.spaceWidth = spaceWidth;
		this.spaceHeight = spaceHeight;

		this.constellations.add(() -> getConstellation(0.2));
		this.constellations.add(() -> getConstellation(0.4));
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	@Override
	public Space create(final int level) {

		return this.constellations.get(level - 1).get();

	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	private Space getConstellation(final double phasingSpeed) {

		final Space space = new Space(this.spaceWidth, this.spaceHeight, null);

		space.add(new StaticPlanet(rocky, 100, 50,
				new Position(this.spaceWidth - 25, this.spaceHeight / 2), Speed.zero(),
				angleCirculatesAt(1), new Phase.Static()));

		space.add(new StaticPlanet(rocky, 100, 50,
				new Position(this.spaceWidth, this.spaceHeight / 2), Speed.zero(),
				angleCirculatesAt(1), new Phase.Looping(3, phasingSpeed, 1)));
		space.add(new StaticPlanet(rocky, 100, 50,
				new Position(this.spaceWidth + 25, this.spaceHeight / 2), Speed.zero(),
				angleCirculatesAt(1), new Phase.Looping(3, phasingSpeed, -1)));

		return space;
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	public int getMaxLevel() {

		return this.constellations.size();
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	private final int spaceWidth;
	private final int spaceHeight;
	private final List<Supplier<Space>> constellations = new ArrayList<>();
}
