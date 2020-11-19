package gravity.client.fakes;

import static gravity.client.core.Planet.Type.rocky;
import static java.util.Arrays.asList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

import gravity.client.core.Planet;
import gravity.client.core.Point;
import gravity.client.core.Space;
import gravity.client.core.SpaceFactory;
import gravity.client.core.Speed;
import gravity.client.core.StaticPlanet;

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
	}
	/****************************************************************************
	 *
	 ***************************************************************************/
	@Override
	public Space create(final int level) {
		
		final List<Planet> planets = this.constellations.get(level -1).get();

		return  new Space(this.spaceWidth, this.spaceHeight, null, planets);
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	private List<Planet> getEmptyConstellation() {

		return Collections.emptyList();
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	private List<Planet> getOnePlanetConstellation() {

		return asList(
				new StaticPlanet(rocky, 100, 50,
						new Point(this.spaceWidth -25, this.spaceHeight / 2), Speed.zero()));
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
	private final List<Supplier<List<Planet>>> constellations = new ArrayList<>();
}
