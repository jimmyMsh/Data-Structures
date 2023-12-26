## Overview

The Rising Tides project is a Java-based simulation that demonstrates flood impacts using data structures and algorithms. This project implements a variety of methods that simulate the rising sea levels on different terrains and analyze the resulting changes. The project is visualized through a provided graphical user interface (GUI).

## Features

### Elevation Extrema (`elevationExtrema`)
This method determines the lowest and highest points on the terrain by iterating through a 2D array of doubles representing land heights. The method returns the minimum and maximum elevation points as a double array.

### Flooded Regions (`floodedRegionsIn`)
Adopting a breadth-first search approach, this method simulates the flooding process over the terrain for a specified water height. Flooded areas are identified in a boolean 2D array where true values represent flooded cells.

### Flood Check (`isFlooded`)
Using the `floodedRegionsIn` method, this evaluates whether a specific `GridLocation` is flooded for a given water height and returns a boolean value accordingly.

### Height Above Water (`heightAboveWater`)
This calculates the visible height (or depth) of a `GridLocation` above (or below) the water height, providing the physical difference in elevation against the water level.

### Total Visible Land (`totalVisibleLand`)
This method computes the number of visible (non-flooded) cells at a certain water height by leveraging the `floodedRegionsIn` method to iterate through all cells and count those that are not underwater.

### Land Lost (`landLost`)
Determines how much land will potentially be lost if the water level rises from its current height to a predicted future height. It returns an integer representing the number of cells lost (or gained if negative).

### Number of Islands (`numOfIslands`)
This method calculates the number of disconnected land masses or "islands" using a union-find data structure. It takes into account all eight directions around a cell to determine connectivity.

## Skills and Concepts Demonstrated

- **Data Structures**: 2D arrays, dynamic arrays (ArrayLists), and Union-Find data structure.
- **Algorithms**: Flood fill algorithm, breadth-first search, and connected components identification (Union-Find).
- **Problem Solving**: Approaching complex problems with methodical solutions.
- **Java Programming**: Creating well-structured, modular Java code that interacts across multiple methods and classes.

## Usage

To interact with the project:

1. Use the provided `Driver` class to load terrain files and manipulate the GUI.
2. Adjust water height, future water height, and the targeted grid location for analysis.
3. Click “Load” to visualize the terrain and “Go!” to see the simulation results based on input configurations.
4. Hover over the visualization for specific coordinates and resize the window if outlined in purple to ensure accurate coordinates.

## Known Issues

- If the "Go!" button disappears due to a reduced window size, extend the window for it to reappear.
- Make sure to return a value in all implemented methods to prevent errors when loading the driver.

## Additional Files

- `GridLocation`: Represents a cell location.
- `RisingTidesVisualizer`: Converts terrain heights to colors for the GUI display.
- `Terrain` & `TerrainLoader`: Used to load and represent the terrain.
- `WeightedQuickUnionUF`: Implements the Weighted Quick-Union algorithm.
- `Terrain Files`: Located in the "terrains/" folder for testing various terrains.
