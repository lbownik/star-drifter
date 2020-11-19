package gravity.client.core;

import static gravity.client.core.Planet.Type.blackHole;
import static gravity.client.core.Planet.Type.earthLike;
import static gravity.client.core.Planet.Type.gas;
import static gravity.client.core.Planet.Type.ice;
import static gravity.client.core.Planet.Type.rocky;
import static gravity.client.core.Planet.Type.star;
import static gravity.client.core.Preconditions.throwIf;
import static java.util.Arrays.asList;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

/*******************************************************************************
* @author lukasz.bownik@gmail.com
*******************************************************************************/
public final class DefaultSpaceFactory implements SpaceFactory {

	/****************************************************************************
	 *
	 ***************************************************************************/
	public DefaultSpaceFactory(final int spaceWidth, final int spaceHeight) {
		
		this.spaceWidth = spaceWidth;
		this.spaceHeight = spaceHeight;
		
		//this.constellations.add(this::getPlanetConstellation0);
		this.constellations.add(this::getPlanetConstellation1);
		this.constellations.add(this::getPlanetConstellation2);
		this.constellations.add(this::getPlanetConstellation3);
		this.constellations.add(this::getPlanetConstellation4);
		this.constellations.add(this::getPlanetConstellation5);
		this.constellations.add(this::getPlanetConstellation6);
	}
	/****************************************************************************
	 *
	 ***************************************************************************/
	@Override
	public Space create(final int level) {
		
		throwIf(level < 1 | level > getMaxLevel(), 
				() -> "Level mustbe between 1 and " + getMaxLevel());
		
		final List<Planet> planets = this.constellations.get(level -1).get();

		return  new Space(this.spaceWidth, this.spaceHeight, null, planets);
	}
	/****************************************************************************
	 *
	 ***************************************************************************/
	private List<Planet> getPlanetConstellation0() {

		return asList(
				new StaticPlanet(rocky, 200, 50,
						new Point(this.spaceWidth / 7, this.spaceHeight / 7), Speed.zero()),
				new StaticPlanet(ice, 100, 50,
						new Point(2 * this.spaceWidth / 7, 2 * this.spaceHeight / 7),
						Speed.zero()),
				new StaticPlanet(earthLike, 100, 50,
						new Point(3 * this.spaceWidth / 7, this.spaceHeight / 7),
						Speed.zero()),
				new StaticPlanet(gas, 100, 50,
						new Point(4 * this.spaceWidth / 7, 6 * this.spaceHeight / 7),
						Speed.zero()),
				new StaticPlanet(star, 100, 50,
						new Point(5 * this.spaceWidth / 7, 5 * this.spaceHeight / 7),
						Speed.zero()),
				new StaticPlanet(blackHole, 100, 50,
						new Point(6 * this.spaceWidth / 7, 6 * this.spaceHeight / 7),
						Speed.zero()));
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	private List<Planet> getPlanetConstellation1() {

		return asList(new StaticPlanet(rocky, 100, 50,
				new Point(this.spaceWidth / 2, this.spaceHeight / 2), Speed.zero()));
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	private List<Planet> getPlanetConstellation2() {

		return asList(
				new StaticPlanet(rocky, 100, 50,
						new Point(this.spaceWidth / 2, this.spaceHeight / 2), Speed.zero()),
				new StaticPlanet(ice, 100, 50,
						new Point(5 * this.spaceWidth / 7, 5 * this.spaceHeight / 7),
						Speed.zero()),
				new StaticPlanet(earthLike, 100, 50,
						new Point(5 * this.spaceWidth / 7, this.spaceHeight / 7),
						Speed.zero()));
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	private List<Planet> getPlanetConstellation3() {

		return asList(
				new StaticPlanet(gas, 100, 50,
						new Point(this.spaceWidth / 4, this.spaceHeight / 2), Speed.zero()),
				new StaticPlanet(blackHole, 5000, 50,
						new Point(5 * this.spaceWidth / 7, 5 * this.spaceHeight / 7),
						Speed.zero()));
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	private List<Planet> getPlanetConstellation4() {

		return asList(
				new StaticPlanet(gas, 100, 50,
						new Point(this.spaceWidth / 2, this.spaceHeight / 2), Speed.zero()),
				new StaticPlanet(ice, 100, 50,
						new Point(5 * this.spaceWidth / 7, 5 * this.spaceHeight / 7),
						Speed.zero()),
				new StaticPlanet(earthLike, 100, 50,
						new Point(5 * this.spaceWidth / 7, this.spaceHeight / 7),
						Speed.zero()),
				new StaticPlanet(blackHole, 1000, 50,
						new Point(2 * this.spaceWidth / 7, this.spaceHeight / 7),
						Speed.zero()),
				new StaticPlanet(star, 1000, 50,
						new Point(9 * this.spaceWidth / 10, this.spaceHeight / 2),
						Speed.zero()));
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	private List<Planet> getPlanetConstellation5() {

		return asList(
				new Planet(gas, 500, 50,
						new Point(this.spaceWidth / 2, this.spaceHeight / 7), new Speed(15, 0)),
				new Planet(rocky, 500, 50,
						new Point(this.spaceWidth / 2, 6 * this.spaceHeight / 7),
						new Speed(-15, 0)));
	}
	/****************************************************************************
	 *
	 ***************************************************************************/
	private List<Planet> getPlanetConstellation6() {

		return asList(
				new StaticPlanet(blackHole, 5000, 50,
						new Point(this.spaceWidth / 2, this.spaceHeight / 2), Speed.zero()),
				new Planet(ice, 100, 50,
						new Point(this.spaceWidth / 2, this.spaceHeight / 7),
						new Speed(-60, 0)));
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
