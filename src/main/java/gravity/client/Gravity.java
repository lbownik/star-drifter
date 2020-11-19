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

package gravity.client;

import static com.google.gwt.user.client.Window.Location.getHash;
import static java.lang.Integer.parseInt;

import com.google.gwt.core.client.EntryPoint;

import gravity.client.app.Presenter;
import gravity.client.core.DefaultSpaceFactory;
import gravity.client.core.SpaceFactory;
import gravity.client.ui.WebUI;

/*******************************************************************************
* @author lukasz.bownik@gmail.com
******************************************************************************/
public class Gravity implements EntryPoint {

	/****************************************************************************
	 *
	 ***************************************************************************/
	public void onModuleLoad() {

		final WebUI view = new WebUI();
		final SpaceFactory spaceFactory = new DefaultSpaceFactory(
				view.getVisibleWidth(), view.getVisibleHeight());
		new Presenter(view, spaceFactory, new WebScheduler(), getInitialLevel());
	}

	/****************************************************************************
	 *
	 ***************************************************************************/
	private static int getInitialLevel() {

		final String hashString = getHash();
		if (hashString != null && hashString.startsWith("#level")) {
			return parseInt(hashString.substring("#level".length()));
		} else {
			return 1;
		}
	}
	/****************************************************************************
	 *
	 ***************************************************************************/
}
