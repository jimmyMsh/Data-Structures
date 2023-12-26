The "RU Hunger Games" project involves implementing a simulation of the Hunger Games using binary search trees (BST) in Java. This project gives us a unique setting—modeled after the fiction of the Hunger Games—where our primary goal is to manipulate populations within a set of districts using BST manipulations to simulate the game events.

Skills demonstrated through this project:
- Designing and manipulating binary search trees
- Applying object-oriented programming principles in Java
- Handling file I/O in Java to read simulation data
- Implementing recursive algorithms
- Using ArrayLists for dynamic data storage
- Implementing custom sorting and search logic

## Project Structure

The project consists of several Java classes representing the different components of the Hunger Games:

- `HungerGames`: The main class containing logic for moving players, eliminating participants, and progressing through the game rounds using BST structures.
- `District`: Represents a district and stores populations based on birth months (odd and even).
- `Person`: Models a participant in the Hunger Games with properties like name, age, district ID, and effectiveness.
- `DuelPair`: Represents a pairing of two participants for a duel.
- `TreeNode`: A BST node that holds a District and pointers to child nodes.

Additional utility classes like `StdIn`, `StdOut`, and `StdRandom` are used for command-line I/O and random number generation, respectively.

## Method Descriptions

### `setupPanem`
Establishes the fictional universe of Panem by reading district-specific and player-specific data from input files. Players are inserted into their corresponding districts in an ArrayList.

### `setupDistricts`
Processes input data to create `District` objects and organizes them in an ArrayList based on their appearance order in the input file.

### `setupPeople`
Continues reading data from the setup file and allocates `Person` objects to corresponding districts based on their age and birth month. Children aged 12-17 are marked as volunteers for the games.

### `addDistrictToGame`
Integrates the districts from the ArrayList into the game by constructing the BST. Districts are inserted based on their IDs and removed from the ArrayList after being added to the BST.

### `findDistrict`
Searches the BST for a specific district by ID and returns it. This method employs a recursive search strategy similar to standard BST search operations.

### `selectDuelers`
Chooses two players from distinct districts for a duel. The selection prioritizes children (participants with `tessera=true`) and ensures one participant is from the odd month population and the other from the even month population.

### `eliminateDistrict`
Removes a district from the BST upon complete elimination of its participants. The removal follows standard BST deletion procedures, handling cases with zero, one, or two children nodes.

### `eliminateDueler`
Enables a duel between a pair of players and takes the appropriate actions post-duel, including potentially eliminating a district if its population reaches zero.

## Usage and Testing

The project includes interactive testing through the `Driver` class, where methods can be tested piece-wise or systematically as per the user's input commands. 
