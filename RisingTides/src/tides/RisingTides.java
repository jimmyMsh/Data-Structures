package tides;

import java.util.*;

/**
 * This class contains methods that provide information about select terrains 
 * using 2D arrays. Uses floodfill to flood given maps and uses that 
 * information to understand the potential impacts. 
 * Instance Variables:
 *  - a double array for all the heights for each cell
 *  - a GridLocation array for the sources of water on empty terrain 
 * 
 * @author Original Creator Keith Scharz (NIFTY STANFORD) 
 * @author Vian Miranda (Rutgers University)
 */
public class RisingTides {

    // Instance variables
    private double[][] terrain;     // an array for all the heights for each cell
    private GridLocation[] sources; // an array for the sources of water on empty terrain 

    /**
     * DO NOT EDIT!
     * Constructor for RisingTides.
     * @param terrain passes in the selected terrain 
     */
    public RisingTides(Terrain terrain) {
        this.terrain = terrain.heights;
        this.sources = terrain.sources;
    }

    /**
     * Find the lowest and highest point of the terrain and output it.
     * 
     * @return double[][], with index 0 and index 1 being the lowest and 
     * highest points of the terrain, respectively
     */
    public double[] elevationExtrema() {

    double[] elevExtrema = new double[2];

    double max = Double.MIN_VALUE;
    double least = Double.MAX_VALUE;

        for(int r = 0; r < terrain.length; r++){
            for(int c = 0; c < terrain[r].length; c++){
            
                if(terrain[r][c] < least)
                {
                    least = terrain[r][c];
                }
                if(terrain[r][c] > max)
                {
                    max = terrain[r][c];
                }
            }
        }
    elevExtrema[0]= least;
    elevExtrema[1]= max;

        return elevExtrema; 
    }

    /**
     * Implement the floodfill algorithm using the provided terrain and sources.
     * 
     * All water originates from the source GridLocation. If the height of the 
     * water is greater than that of the neighboring terrain, flood the cells. 
     * Repeat iteratively till the neighboring terrain is higher than the water 
     * height.
     * 
     * 
     * @param height of the water
     * @return boolean[][], where flooded cells are true, otherwise false
     */
    public boolean[][] floodedRegionsIn(double height) {
        
        /* WRITE YOUR CODE BELOW */
        boolean[][] resultingArray = new boolean[terrain.length][terrain[0].length]; 
        ArrayList<GridLocation> gridLocations = new ArrayList<>();

        // Add each source of water from GridLocation to the ArrayList of gridLocations
        for(GridLocation sourceAt : sources) 
        {
        gridLocations.add(sourceAt);
        resultingArray[sourceAt.row][sourceAt.col] = true;
        }

        while(!gridLocations.isEmpty()) 
        {
        GridLocation floodCheck = gridLocations.remove(0); // remove the first element

        // Variables for north, south, west, and east
        int northCheck = floodCheck.row - 1;
        int southCheck = floodCheck.row + 1;
        int westCheck = floodCheck.col - 1;
        int eastCheck = floodCheck.col + 1;

        // North check
        if(northCheck >= 0 && terrain[northCheck][floodCheck.col] <= height && !resultingArray[northCheck][floodCheck.col]) {
            resultingArray[northCheck][floodCheck.col] = true;
            gridLocations.add(new GridLocation(northCheck, floodCheck.col));
        }

        // South check
        if(southCheck < terrain.length && terrain[southCheck][floodCheck.col] <= height && !resultingArray[southCheck][floodCheck.col]) {
            resultingArray[southCheck][floodCheck.col] = true;
            gridLocations.add(new GridLocation(southCheck, floodCheck.col));
        }

        // West check
        if(westCheck >= 0 && terrain[floodCheck.row][westCheck] <= height && !resultingArray[floodCheck.row][westCheck]) {
            resultingArray[floodCheck.row][westCheck] = true;
            gridLocations.add(new GridLocation(floodCheck.row, westCheck));
        }

        // East check
        if(eastCheck < terrain[0].length && terrain[floodCheck.row][eastCheck] <= height && !resultingArray[floodCheck.row][eastCheck]) {
            resultingArray[floodCheck.row][eastCheck] = true;
            gridLocations.add(new GridLocation(floodCheck.row, eastCheck));
        }
        }

        return resultingArray;
    }
    
    /**
     * Checks if a given cell is flooded at a certain water height.
     * 
     * @param height of the water
     * @param cell location 
     * @return boolean, true if cell is flooded, otherwise false
     */
    public boolean isFlooded(double height, GridLocation cell) {
        
        //Catch the row and col of this grid location cell
        int rowNum= cell.row;
        int colNum= cell.col;
        
        //flood the map. Check to see if the above cords are flooded in this array
        boolean[][] floodArray = floodedRegionsIn(height);

        if(floodArray[rowNum][colNum])
            return true;
            
        return false; 
    }

    /**
     * Given the water height and a GridLocation find the difference between 
     * the chosen cells height and the water height.
     * 
     * If the return value is negative, the Driver will display "meters below"
     * If the return value is positive, the Driver will display "meters above"
     * The value displayed will be positive.
     * 
     * @param height of the water
     * @param cell location
     * @return double, representing how high/deep a cell is above/below water
     */
    public double heightAboveWater(double height, GridLocation cell) {
        
        //Catch the row and col of this grid location cell
        int rowNum= cell.row;
        int colNum= cell.col;
        
        //catches the terrain height at this location
        double heightAtLocation = terrain[rowNum][colNum];
        
        //calculates the difference between the height at the location and water height 
        double differenceWaterLand = heightAtLocation - height;

        return differenceWaterLand; 
    }

    /**
     * Total land available (not underwater) given a certain water height.
     * 
     * @param height of the water
     * @return int, representing every cell above water
     */
    public int totalVisibleLand(double height) {
    

    //Creates array with flooded objects to determine land above water
    boolean[][] waterArea = floodedRegionsIn(height);

    //Counts amount of cells above water 
    int totalCellsAboveWater = 0;

    
    for(int r = 0; r < waterArea.length; r++)
    {
        for(int c = 0; c < waterArea[r].length; c++)
        {
            if(!waterArea[r][c])
            {
            totalCellsAboveWater ++;
            }
        }
    }
        return totalCellsAboveWater; 
    } 


    /**
     * Given 2 heights, find the difference in land available at each height. 
     * 
     * If the return value is negative, the Driver will display "Will gain"
     * If the return value is positive, the Driver will display "Will lose"
     * The value displayed will be positive.
     * 
     * @param height of the water
     * @param newHeight the future height of the water
     * @return int, representing the amount of land lost or gained
     */
    public int landLost(double height, double newHeight) {
        
    //Amount of land above at first height
    int totalHeightOne = totalVisibleLand(height);
    System.out.println(totalHeightOne);

    //Amount of land above second height
    int totalHeightTwo = totalVisibleLand(newHeight);
    System.out.println(newHeight);

    
    return (totalHeightOne - totalHeightTwo); 
    }

    /**
     * Count the total number of islands on the flooded terrain.
     * 
     * Parts of the terrain are considered "islands" if they are completely 
     * surround by water in all 8-directions. Should there be a direction (ie. 
     * left corner) where a certain piece of land is connected to another 
     * landmass, this should be considered as one island. A better example 
     * would be if there were two landmasses connected by one cell. Although 
     * seemingly two islands, after further inspection it should be realized 
     * this is one single island. Only if this connection were to be removed 
     * (height of water increased) should these two landmasses be considered 
     * two separate islands.
     * 
     * @param height of the water
     * @return int, representing the total number of islands
     */
    public int numOfIslands(double height) {
        
    //Creates array with flooded objects to determine land above water
    boolean[][] waterArea = floodedRegionsIn(height);

    //Creates Weighted Union Values -- Keeps track of islands 
    WeightedQuickUnionUF unionGrid = new WeightedQuickUnionUF(terrain.length, terrain[0].length);

    for(int r = 0; r < waterArea.length; r++)
    {
        for(int c = 0; c < waterArea[r].length; c++)
        {
            //Grid location object we are up to
            GridLocation currentLoc  = new GridLocation(r, c);

            //If land -> proceed to check each neighboring position and union the positions if there is land
            if(!waterArea[r][c])
            {
            // Top-left
                if (r-1 >= 0 && c-1 >= 0 && !waterArea[r-1][c-1]) 
                {
                unionGrid.union(currentLoc, new GridLocation(r-1, c-1));
                }

                // Top
                if (r-1 >= 0 && !waterArea[r-1][c]) {
                unionGrid.union(currentLoc, new GridLocation(r-1, c));
                }

                // Top-right
                if (r-1 >= 0 && c+1 < waterArea[0].length && !waterArea[r-1][c+1]) {
                unionGrid.union(currentLoc, new GridLocation(r-1, c+1));
                }

                // Right
                if (c+1 < waterArea[0].length && !waterArea[r][c+1]) {
                unionGrid.union(currentLoc, new GridLocation(r, c+1));
                }

                // Bottom-right
                if (r+1 < waterArea.length && c+1 < waterArea[0].length && !waterArea[r+1][c+1]) {
                unionGrid.union(currentLoc, new GridLocation(r+1, c+1));
                }

                // Bottom
                if (r+1 < waterArea.length && !waterArea[r+1][c]) {
                unionGrid.union(currentLoc, new GridLocation(r+1, c));
                }

                // Bottom-left
                if (r+1 < waterArea.length && c-1 >= 0 && !waterArea[r+1][c-1]) {
                unionGrid.union(currentLoc, new GridLocation(r+1, c-1));
                }

                // Left
                if (c-1 >= 0 && !waterArea[r][c-1]) {
                unionGrid.union(currentLoc, new GridLocation(r, c-1));
                }
            }
        }
    }

    //Count amount of unique roots 

    int uniqueIslands = 0;

    //Check each cell to see if it's its own root 
    for(int r = 0; r < waterArea.length; r++)
    {
        for(int c = 0; c < waterArea[0].length; c++)
        {
            if(!waterArea[r][c])
            {
                GridLocation cell = new GridLocation(r, c);
                GridLocation root = unionGrid.find(cell);

                if(cell.equals(root))
                uniqueIslands++;
            }
        }
    }

    
        return uniqueIslands; 
    }

}