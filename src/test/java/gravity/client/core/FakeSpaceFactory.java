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
public final class FakeSpaceFactory implements SpaceFactory {

	/****************************************************************************
	 *
	 ***************************************************************************/
	public FakeSpaceFactory(final int spaceWidth, final int spaceHeight) {

		this.spaceWidth = spaceWidth;
		this.spaceHeight = spaceHeight;

		this.constellations.add(this::getEmptyConstellation);
		this.constellations.add(this::getOnePlanetConstellation);
		this.constellations.add(this::getOneLoosePlanetConstellation);
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
	private Space getEmptyConstellation() {

		return new Space(this.spaceWidth, this.spaceHeight, null);
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	private Space getOnePlanetConstellation() {

		final Space space = new Space(this.spaceWidth, this.spaceHeight, null);

		space.add(new StaticPlanet(rocky, 100, 50,
				new Point(this.spaceWidth - 25, this.spaceHeight / 2), Speed.zero(),
				angleCirculatesAt(1)));

		return space;
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	private Space getOneLoosePlanetConstellation() {

		final Space space = new Space(this.spaceWidth, this.spaceHeight, null);

		space.add(new LoosePlanet(meteorite, 100, 50, new Point(50, this.spaceHeight / 2),
				new Speed(10, 0), this.spaceWidth - 50));

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
