package gravity.client;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import gravity.client.core.GravityUseCases;

/*******************************************************************************
 * @author lukasz.bownik@gmail.com
 ******************************************************************************/
@RunWith(Suite.class)
@SuiteClasses({ EmptySpaceUseCases.class,
					 OnePlanetSpaceUseCases.class,
					 GravityUseCases.class })
public class AllUseCases {

}
