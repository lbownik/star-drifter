package gravity.client.core;

import static gravity.client.core.Planet.Type.meteorite;

/*******************************************************************************
 * @author lukasz.bownik@gmail.com
 *******************************************************************************/
public final class OneLoosePlanetSpaceFactory implements SpaceFactory {

	/****************************************************************************
	 *
	 ***************************************************************************/
	public OneLoosePlanetSpaceFactory(final int spaceWidth, final int spaceHeight) {

		this.spaceWidth = spaceWidth;
		this.spaceHeight = spaceHeight;

	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	@Override
	public Space create(final int level) {

		final Space space = new Space(this.spaceWidth, this.spaceHeight, null);

		space.add(
				new LoosePlanet(meteorite, 100, 50, new Position(50, this.spaceHeight / 2),
						new Speed(10, 0), new Phase.Static(), this.spaceWidth - 50));

		return space;
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	public int getMaxLevel() {

		return 1;
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	private final int spaceWidth;
	private final int spaceHeight;
}
