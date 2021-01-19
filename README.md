# Star Drifter
Star Drifter - a multi level, gravity based space game (play **[here](https://star-drifter.appspot.com/)**).

## Use cases
The following diagram presentes the use cases implemented by the game.

![Use Cases](/docs/use_cases.png)

**Figure 1. Game states.**

The __Scheduler__ is a virtual actor implemented by the platform providing time related event mechanism.

## Components
The following digram shows building blocks of the game.

![Game components](/docs/components.png)

**Figure 2. Game components.**

Green components are platform independent.
Red components are platfrom (browser) specific.

## Game states
The game operates acording to the following state machine implemented jointly by __Presenter__ and __User Interface__.

![Game states](/docs/states.png)

**Figure 3. Game states.**

Purple denotes passive initial state when __Player__ is expected to start the game.
Blue denotes active states when persiodic screen refreshes governed by __Scheduler__ take place.
Green denotes passive states where no screen refreshes take place, and __Player__ decition is awaited.

The main event loop of the game controlled by __Scheduler__ is depicted below.

![Game loop](/docs/game_loop.png)

**Figure 4. Main game loop.**


## Implementation
The game has been written in Java with the use of [Google Web Toolkit](http://www.gwtproject.org/) as browser adaptation mechanism.
The game is ment to be portable across many Java implementations (client side web with GWT, desktop with [Swing](https://en.wikipedia.org/wiki/Swing_(Java))
 or [SWT](https://en.wikipedia.org/wiki/Standard_Widget_Toolkit), Andorid).

![Compile time dependencies](/docs/comile_time_dependencies.png)

**Figure 5. Compile time dependencies.**

The organization of code dependencies follows [hexagonal architecture](https://en.wikipedia.org/wiki/Hexagonal_architecture_(software)) 
with platform independent core logic and platform adapters.
The clean seperation is acheved with a combined use of the following design patterns:
* [Model-View-Presenter](https://en.wikipedia.org/wiki/Model%E2%80%93view%E2%80%93presenter),
* [Separated Interface](https://java-design-patterns.com/patterns/separated-interface/),
* [Virtual Factory](https://en.wikipedia.org/wiki/Factory_method_pattern),
* [Inversion Of Control](https://en.wikipedia.org/wiki/Inversion_of_control).

## Testing strategy
Automatic game testing is done mainy through unit testes with the help of test doubles used for controling inputs and sensing outputs.
The only unit exlicitely under test is __Presenter__. Verification of other portable components is done indireclty through verification of observable behavior of __Presenter__.
In this case a unit is understood as entire platform-independent part of the game.

![Tests setup](/docs/tests_setup.png)

**Figure 6. Tests setup.**

__User Interface__, __Scheduler__ and __Space Factory__ are intentionally not tested automaticaly.


## Deployment
The game is deployed on [Google AppEngine](https://cloud.google.com/appengine/) platform.