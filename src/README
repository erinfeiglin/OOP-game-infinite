The game programmed was a homework assignment from my OOP class.
I used the danogames library to program this game.
The following is the readme we were told to write to explain the game's functions and the program.


I chose to do trees in the following way: the flora class is responsible for deciding where trees are going to be placed. it has the createInRange method that, together with the
proceduralGenerationManager class, spawns trees across the current visible range consistently.
the flora class calls the TreeGenerator class, which it holds an instance of, which in turn creates the different parts of the tree (leaves, trunk, fruit), combines them into a tree object and returns it. 
the Tree class holds its components as fields for ease of use.
the fruit are consumed via a callback that the avatar activates when collision with a fruit
is detected.
the leaves and fruit are generated on a grid that can hold a leaf, fruit, or be empty.

The cloud class is generated as a grid of blocks, they spawn off camera, and follow a dummy GameObject to simulate the transition.
the cloud's shape is generated randomly, and upon completion of the animation
a new random cloud spawns in.
There is also a raindrop generator that, using a callback, generates raindrops that fall from
the cloud each time the avatar jumps. the raindrops fade and are then removed from the game.

I also added a procedural generation manager class to handle consistent generation of
terrain and trees across the infinite world. generation is dependent on the seed and X values.
when a chunk of terrain/trees goes offscreen, the generation manager despawns them,
and spawns new terrain and trees as they come into the screen.

finally, I added some cosmetic changes, changed the avatar and added background music
to match the winter theme.
since some constants are re-used across classes, I decided to make a Constants class for consistency and ease of use.
