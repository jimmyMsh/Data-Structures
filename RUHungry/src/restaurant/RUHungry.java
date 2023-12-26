package restaurant;

/**
 * RUHungry is a fictitious restaurant. 
 * You will be running RUHungry for a day by seating guests, 
 * taking orders, donation requests and restocking the pantry as necessary.
 * 
 * Compiling and executing:
 * 1. use the run or debug function to run the driver and test your methods 
 * 
 * @author Mary Buist
 * @author Kushi Sharma
*/

public class RUHungry {
    
    /*
     * Instance variables
     */

    // Menu: two parallel arrays. The index in one corresponds to the same index in the other.
    private String[] categoryVar; // array where containing the name of menu categories (e.g. Appetizer, Dessert).
    private MenuNode[] menuVar;   // array of lists of MenuNodes where each index is a category.
    
    // Stock: hashtable using chaining to resolve collisions.
    private StockNode[] stockVar;  // array of linked lists of StockNodes (use hashfunction to organize Nodes: id % stockVarSize)
    private int stockVarSize;

    // Transactions: orders, donations, restock transactions are recorded 
    private TransactionNode transactionVar; // refers to the first front node in linked list

    // Queue keeps track of people who've left the restaurant
    private Queue<People> leftQueueVar;  

    // Tables
    private People[] tables;        // array for people who are currently sitting
    private int[][]  tablesInfo;    // 2-D integer array where the first row contains how many seats there are at each table (each index)
                                    // and the second row contains "0" or "1", where 1 is the table is not available and 0 the opposite

    /*
     * Default constructor
     */
    public RUHungry () {
        categoryVar    = null;
        menuVar        = null;
        stockVar       = null;
        stockVarSize   = 0;
        transactionVar = null;
        leftQueueVar   = null;
        tablesInfo     = null;
        tables         = null;
    }

    /*
     * Get/Set methods
     */
    public MenuNode[] getMenu() { return menuVar; }
    public String[] getCategoryArray() { return categoryVar;}
    public StockNode[] getStockVar() { return stockVar; } 
    public TransactionNode getFrontTransactionNode() { return transactionVar; } 
    public TransactionNode resetFrontNode() {return transactionVar = null;} // method to reset the transactions for a new day
    public Queue<People> getLeftQueueVar() { return leftQueueVar; } 
    public int[][] getTablesInfo() { return tablesInfo; }

    /*
     * Menu methods
     */

    /**
     * 
     * This method populates the two parallel arrays menuVar and categoryVar.
     * 
     * Each index of menuVar corresponds to the same index in categoryVar (a menu category like Appetizers).
     * If index 0 at categoryVar is Appetizers then menuVar at index 0 contains MenuNodes of appetizer dishes.
     * 
     * 1. read the input file:
     *      a) the first number corresponds to the number of categories (aka length of menuVar and categoryVar)
     *      b) the next line states the name of the category (populate CategoryVar as you read each category name)
     *      c) the next number represents how many dishes are in that category - this will be the size of the linked list in menuVar for this category
     *      d) the next line states the name of the dish
     *      e) the first number in the next line represents how many ingredient IDs there are
     *      f) the next few numbers (all in the 100s) are each the ingredient ID
     * 
     * 2. As you read through the input file:
     *      a) populate the categoryVar array
     *      b) populate menuVar depending on which index (aka which category) you are in
     *          i) make a dish object (with filled parameters -- don't worry about "price" and "profit" in the dish object for right now)
     *          ii) create menuNode and insert at the front of menuVar (NOTE! there will be multiple menuNodes in one index)
     * 
     * @param inputFile - use menu.in file which contains all the dishes
     */

    public void menu(String inputFile) {

        StdIn.setFile(inputFile);

        //Catch the number of categories by reading first line in input file //Use as overarching iterator number for file
        int numOfCategories = StdIn.readInt();
        StdIn.readLine(); //Clear new line character after

        //Set length of category and menu to reflect total categories
        this.categoryVar = new String[numOfCategories];
        this.menuVar = new MenuNode[numOfCategories];

        //iterates over the all categories
        for(int i = 0; i < numOfCategories; i++)
        {
            String appetizerName = StdIn.readLine();
            this.categoryVar[i] = appetizerName;

            //Catch the next line to determine the amount of items in each category - use this to go through items in each category  
            int numItemsInCategory = StdIn.readInt();
            StdIn.readLine(); //Clear buffer

            //Start filling menu
            for(int j = 0; j < numItemsInCategory; j++) //Go through items within each category
            {
                String dishName = StdIn.readLine(); //Name of dish from text file
                int[] stockIds = new int[StdIn.readInt()]; //Numer of stockIds

                for(int n = 0; n < stockIds.length; n++) //Fill array of stockIDs
                {
                    stockIds[n] = StdIn.readInt();
                }
                StdIn.readLine(); //clear buffer after reading ints

                //Make dishnode with the caught information 
                Dish dishToAdd = new Dish(appetizerName, dishName, stockIds);
                //Make corresponding MenuNode with dish in it 
                MenuNode newDish = new MenuNode(dishToAdd, null);

                //Check if this is the first MenuNode for this index of Menu- if it is, add it. If it's not, add new object and have it point to last
                if(this.menuVar[i] == null)
                {
                    menuVar[i] = newDish;
                }
                else
                {
                    newDish.setNextMenuNode(menuVar[i]);
                    menuVar[i] = newDish;
                }
            }

        }
    }

    /** 
     * Find and return the MenuNode that contains the dish with dishName in the menuVar.
     * 
     *      ** GIVEN METHOD **
     *      ** DO NOT EDIT **
     * 
     * @param dishName - the name of the dish
     * @return the dish object corresponding to searched dish, null if dishName is not found.
     */

    public MenuNode findDish ( String dishName ) {

        MenuNode menuNode = null;

        // Search all categories since we don't know which category dishName is at
        for ( int category = 0; category < menuVar.length; category++ ) {

            MenuNode ptr = menuVar[category]; // set ptr at the front (first menuNode)
            
            while ( ptr != null ) { // while loop that searches the LL of the category to find the itemOrdered
                if ( ptr.getDish().getDishName().equals(dishName) ) {
                    return ptr;
                } else{
                    ptr = ptr.getNextMenuNode();
                }
            }
        }
        return menuNode;
    }

    /**
     * Find integer that corresponds to the index in menuVar and categoryVar arrays that has that category
     *              
     *      ** GIVEN METHOD **
     *      ** DO NOT EDIT **
     *
     * @param category - the category name
     * @return index of category in categoryVar
     */

    public int findCategoryIndex ( String category ) {
        int index = 0;
        for ( int i = 0; i < categoryVar.length; i++ ){
            if ( category.equalsIgnoreCase(categoryVar[i]) ) {
                index = i;
                break;
            }
        }
        return index;
    }

    /*
     * Stockroom methods
     */

    /**
     * PICK UP LINE OF THE METHOD:
     * *can I insert myself into your life? cuz you always help me sort 
     * out my problems and bring stability to my mine*
     * 
     * ***********
     * This method adds a StockNode into the stockVar hashtable.
     * 
     * 1. get the id of the given newNode and use a hash function to get the index at which the
     *    newNode is being inserted.
     * 
     * HASH FUNCTION: id % stockVarSize
     * 
     * 2. insert at the front of the linked list at the specific index
     * 
     * @param newNode - StockNode that needs to be inserted into StockVar
     */

    public void addStockNode ( StockNode newNode ) {
        //catch Id of StockNode
        int itemID= newNode.getIngredient().getID();

        //Finds where to place ingredient in stockVar using hash function
        int hashPosition= itemID % this.stockVarSize;

        if(this.stockVar[hashPosition] == null) //If position in stock null, add node to front
        {
            this.stockVar[hashPosition] = newNode;
        }
        else //Otherwise, add this newNode to front and point to the initial front node at this position
        {
            newNode.setNextStockNode(this.stockVar[hashPosition]);
            this.stockVar[hashPosition] = newNode;
        }

    }

    /**
     * This method deletes an ingredient (aka the specific stockNode) from stockVar.
     * 
     * 1. find the node that corresponds to the ingredient (based on the ingredientName)
     * 
     * 2. delete the node from stockVar
     *      a) you have to visit each index and look at each node in the corresponding linked list 
     *      b) this is NOT efficient. Hashtables are not good if you can't use the key to find the item.
     * 
     * @param ingredientName - name of the ingredient
     */

    public void deleteStockNode (String ingredientName ) {

        //Begin iterating through array of StockNodes- go through each index and iterate through nodes
        for(int i = 0; i < stockVarSize ; i++)
        {
            //Check if this index has a StockNode- if it doesn't, skip this loop iteration
            if(stockVar[i] == null){ continue; } 
            
            //Check if node to delete is first- if it is, make this array position point to the next item in the list (if no item set to null)
            if(stockVar[i].getIngredient().getName().equals(ingredientName))
            {
            stockVar[i] = stockVar[i].getNextStockNode();
            break;
            }

            //Start at the StockNode at the start at this index position- wint to find node before one to delete 
            StockNode ptr = stockVar[i];

            //Catch name of ingredient to delete 
            String ingredientToDelete = ingredientName;

            //Will check until next ptr is null (i.e item not found)
            while(ptr.getNextStockNode() != null)
            {
                if(ptr.getNextStockNode().getIngredient().getName().equals(ingredientToDelete)) //If name of next pointer is equal...
                {
                    StockNode nodeToDelete = ptr.getNextStockNode();
                    ptr.setNextStockNode(nodeToDelete.getNextStockNode()); //Delete the node by setting the ptr to the node after. If it is last, this node will point to null.
                    return;
                }
                ptr = ptr.getNextStockNode(); //Continue going through nodes
            }
        }
    }

    /**
     * This method finds an ingredient from StockVar (given the ingredientID)
     * 
     * 1. find the node based upon the ingredient ID (you can go to the specific index using the hash function!)
     *      (a) this is an efficient search as it looks only at the linked list which the key hash to
     * 2. find and return the node
     *  
     * @param ingredientID - the ID of the ingredient
     * @return the StockNode corresponding to the ingredientID, null otherwise
     */
   
    public StockNode findStockNode (int ingredientID) {

        //Finds where to place ingredient in stockVar using hash function
        int hashPosition= ingredientID % this.stockVarSize;
        
        //Check if this index has a StockNode- if it doesn't, StockNode no longer here 
        if(stockVar[hashPosition] == null){ return null; } 

        //If StockNode is first position, return it 
        if(stockVar[hashPosition].getIngredient().getID() == ingredientID)
        {
            return stockVar[hashPosition];
        }

        //Iterate through rest of positions in StockNode list 
        StockNode ptr = stockVar[hashPosition];
        while(ptr != null)
        {
            if(ptr.getIngredient().getID() == ingredientID)
            {
                return ptr;
            }
            ptr = ptr.getNextStockNode();
        }

        return null; // if nothing found 
    }

    /**
     * This method is to find an ingredient from StockVar (given the ingredient name).
     * 
     *      ** GIVEN METHOD **
     *      ** DO NOT EDIT **
     * 
     * @param ingredientName - the name of the ingredient
     * @return the specific ingredient StockNode, null otherwise
     */

    public StockNode findStockNode (String ingredientName) {
        
        StockNode stockNode = null;
        
        for ( int index = 0; index < stockVar.length; index ++ ){
            
            StockNode ptr = stockVar[index];
            
            while ( ptr != null ){
                if ( ptr.getIngredient().getName().equalsIgnoreCase(ingredientName) ){
                    return ptr;
                } else {  
                    ptr = ptr.getNextStockNode();
                }
            }
        }
        return stockNode;
    }

    /**
     * This method updates the stock amount of an ingredient.
     * 
     * 1. you will be given the ingredientName **OR** the ingredientID:
     *      a) the ingredientName is NOT null: find the ingredient and add the given stock amount to the
     *         current stock amount
     *      b) the ingredientID is NOT -1: find the ingredient and add the given stock amount to the
     *         current stock amount
     * 
     * (FOR FUTURE USE) SOMETIMES THE STOCK AMOUNT TO ADD MAY BE NEGATIVE (TO REMOVE STOCK)
     * 
     * @param ingredientName - the name of the ingredient
     * @param ingredientID - the id of the ingredient
     * @param stockAmountToAdd - the amount to add to the current stock amount
     */
    
    public void updateStock (String ingredientName, int ingredientID, int stockAmountToAdd) {
        
        //Make StockNode depending on parameter received 
        StockNode itemToUpdate = (ingredientName != null) ? findStockNode(ingredientName) : findStockNode(ingredientID);

        //Change the stock based on the given parameter
        itemToUpdate.getIngredient().updateStockLevel(stockAmountToAdd);

        //If after checking, ingredient is zero, delete the ingredient ??
    }

    /**
     * PICK UP LINE OF THE METHOD:
     * *are you a single ‘for’ loop? cuz i only have i’s for you*
     * 
     * ***********
     * This method goes over menuVar to update the price and profit of each dish,
     * using the stockVar hashtable to lookup for ingredient's costs.
     * 
     * 1. For each dish in menuVar, add up the cost for each ingredient (found in stockVar), 
     *    and multiply the total by 1.2 to get the final price.
     *      a) update the price of each dish
     *  HINT! --> you can use the methods you've previously made!
     * 
     * 2. Calculate the profit of each dish by getting the totalPrice of ingredients and subtracting from 
     *    the price of the dish itself.
     * 
     */

    public void updatePriceAndProfit() {

        //Create dishCost, dishPrice, and profit to hold the values of cost as we iterate through menuVar 
        double dishCost = 0, dishPrice = 0, profit = 0; // dishCost, dishPrice initially zero

        for(int i = 0; i < menuVar.length; i++) //Iterate though entire menuVar 
        {
            
            //Use first MenuNode object to iterate through linked list of MenuNodes
            MenuNode dishPtr = menuVar[i];
            while(dishPtr != null)
            {
                //Captures the stockIds for each dish- need to iterate through these
                //This will be used to find StockNode and then price
                int[] stockIds = dishPtr.getDish().getStockID(); 

                for(int j = 0; j < stockIds.length; j++)
                {
                    //Use findStockNode on each ID in stockID to find associated stockNode. 
                    StockNode stockNode = findStockNode(stockIds[j]);
                    double costForIngredient = stockNode.getIngredient().getCost(); //Get cost of ingredient 

                    //Add cost to dishCost
                    dishCost += costForIngredient;
                }

                dishPrice = 1.2 * dishCost; //Dish price is 1.2x dishCost 
                profit = dishPrice - dishCost; //Dish profit is price - cost 
                
                dishPtr.getDish().setPriceOfDish(dishPrice); //set dish price
                dishPtr.getDish().setProfit(profit); //set dish profit

                // Reset dishCost, dishPrice, and pofit before next node //
                dishCost = 0;
                dishPrice = 0;
                profit = 0;

                //------ Go to next menuNode ------//
                dishPtr = dishPtr.getNextMenuNode(); 
            }
        }
    }

    /**
     * PICK UP LINE OF THE METHOD:
     * *are you a decimal? cuz the thought of you 
     * always floats in my head and the two of use would make double*
     * 
     * ************
     * This method initializes and populates stockVar which is a hashtable where each index contains a 
     * linked list with StockNodes.
     * 
     * 1. set and read the inputFile (stock.in):
     *      a) first integer (on line 1) is the size of StockVar *** update stockVarSize AND create the stockVar array ***
     *      b) first integer of next line represents the ingredientID
     *          i) example: 101 on line 2
     *      c) use StdIn.readChar() to get rid of the space between the id and the name
     *      d) the string that follows is the ingredient name (NOTE! --> there are spaces between certain strings)
     *          i) example: Lettuce
     *      e) the double on the next line corresponds to the ingredient's cost
     *          i) example: 3.12 on line 3
     *      f) the next integer is the stock amount for that ingredient
     *          i) example: 30 on line 3
     * 
     * 2. create a Ingredient object followed by a StockNode then add to stockVar
     *      HINT! --> you may use previous methods written to help you!
     * 
     * @param inputFile - the input file with the ingredients and all their information (stock.in)
     */

    public void createStockHashTable (String inputFile){
        
        StdIn.setFile(inputFile);
        
        //Catch size of stock by reading first line of input file
        int sizeStockTable = StdIn.readInt();
        StdIn.readLine(); //Clear new line character after

        //Set length of stockVar and stockVarSize to reflect this 
        this.stockVar = new StockNode[sizeStockTable];
        this.stockVarSize = sizeStockTable;

        //Vairables to hold the informatin we will be finding in the file
        int ingredientID;
        String ingredientName;
        double ingredientCost;
        int ingredientStock;

        //Iterate over the file as long as there is a line to iterate through
        while(StdIn.hasNextLine())
        {
            /*-- First line for each ingredient --*/
            ingredientID = StdIn.readInt(); //Reads ID
            StdIn.readChar(); //Rid space between ID and name
            ingredientName = StdIn.readLine(); //Reads rest of string corresponding to ingredient name

            /*-- Second line for each ingredient --*/
            //Reads double on next line for ingredient cost
            ingredientCost = StdIn.readDouble();
            ingredientStock = StdIn.readInt();


            /*-- Create ingredient and stockNode -- add node to stockVar --*/
            Ingredient ingredientToAdd = new Ingredient(ingredientID, ingredientName, ingredientStock, ingredientCost); //Make ingredient 
            StockNode stockToAdd = new StockNode(ingredientToAdd, null); //Make node with ingredient 
            addStockNode(stockToAdd); //Add stock to table using method

            StdIn.readLine(); //Cleans rest of line for next iteration            
        }
    }

    /*
     * Transaction methods
     */

    /**
     * This method adds a TransactionNode to the END of the transactions linked list.
     * The front of the list is transactionVar.
     *
     * 1. create a new TransactionNode with the TransactionData paramenter.
     * 2. add the TransactionNode at the end of the linked list transactionVar.
     * 
     * @param data - TransactionData node to be added to transactionVar
     */

    public void addTransactionNode ( TransactionData data ) { // method adds new transactionNode to the end of LL
       
        //Create transaction node with data
        TransactionNode newTransaction = new TransactionNode(data, null); 

        //Intial check to see if there is a start to the transaction linked list
        if(this.transactionVar == null) //No linked list yet, add this TransactionNode to start
        {
            this.transactionVar = newTransaction;
            return;
        }

        //find end of transaction linked list (if there is a linked list with beginning)
        TransactionNode ptr = this.transactionVar;
        while(ptr.getNext() != null) //Stops as soon as next link is null (we are at end of list)
        {
            ptr = ptr.getNext(); 
        }

        ptr.setNext(newTransaction); //Sets last node to new transaction 
    }

    /**
     * PICK UP LINE OF THE METHOD:
     * *are you the break command? cuz everything else stops when I see you*
     * 
     * *************
     * This method checks if there's enough in stock to prepare a dish.
     * 
     * 1. use findDish() method to find the menuNode node for dishName
     * 
     * 2. retrieve the Dish, then traverse ingredient array within the Dish
     * 
     * 3. return boolean based on whether you can sell the dish or not
     * HINT! --> once you determine you can't sell the dish, break and return
     * 
     * @param dishName - String of dish that's being requested
     * @param numberOfDishes - int of how many of that dish is being ordered
     * @return boolean
     */

    public boolean checkDishAvailability ( String dishName, int numberOfDishes ){
        
        //Catch MenuNode who's ingredients we are checking 
        MenuNode dishToCheck = findDish(dishName);

        //Capture stockId from dish- traverse array and determine if we have enough of ingredient 
        int[] stockIds = dishToCheck.getDish().getStockID();

        for(int i = 0; i < stockIds.length; i++)
        {
            //Use findStockNode on each ID in stockID to find associated stockNode. 
            StockNode stockNode = findStockNode(stockIds[i]);
            int stockLevel = stockNode.getIngredient().getStockLevel(); //Get total stock for each ingredient

            //If stock of ingredient < number of dishes, return false
            if (stockLevel < numberOfDishes)
            {
            return false;
            }
        }

        return true; //If there is enough of each ingredient (never less stock than amount needed)
    }

    private void updateStockOfIngredientInDish (String dishName, int numberOfDishes) //There is enough stock of each ingredient, go through ingredients and decrement all stock used
    {
        //Catch MenuNode who's ingredients we are checking 
        MenuNode dishToCheck = findDish(dishName);

        //Capture stockId from dish- traverse array and determine if we have enough of ingredient 
        int[] stockIds = dishToCheck.getDish().getStockID();

        for(int j = 0; j < stockIds.length; j++)
        {
            //Update stock level
            updateStock(null, stockIds[j], (-numberOfDishes));
        }
    }

    /**
     * PICK UP LINE OF THE METHOD:
     * *if you were a while loop and I were a boolean, we could run 
     * together forever because I’ll always stay true to you*
     * 
     * ***************
     * This method simulates a customer ordering a dish. Use the checkDishAvailability() method to check whether the dish can be ordered.
     * 
     * If the dish can be prepared
     *      - create a TransactionData object of type "order" where the item is the dishName, the amount is the quantity being ordered, and profit is the dish profit multiplied by quantity.
     *      - then add the transaction as a successful transaction (call addTransactionNode()) and updates the stock accordingly.
     * 
     * If the dish cannot be prepared
     *      - create a TransactionData object of type "order" where the item is the dishName, the amount is the quantity being ordered, and profit is 0 (zero).
     *      - then add the transaction as an UNsuccessful transaction and,
     *      - simulate the customer trying to order other dishes in the same category linked list:
     *          - if the dish that comes right after the dishName can be prepared, great. If not, try the next one and so on.
     *          - you might have to traverse through the entire category searching for a dish that can be prepared. If you reach the end of the list, start from the beginning until you have visited EVERY dish in the category.
     *          - It is possible that no dish in the entire category can be prepared.
     *          - Note: the next dish the customer chooses is always the one that comes right after the one that could not be prepared. 
     * 
     * 
     * @param dishName - String of dish that's been ordered
     * @param quantity - int of how many of that dish has been ordered
     */

    public void order ( String dishName, int quantity ){

        //Determine whether or not item could be ordered using previous helper method
        boolean canOrder = checkDishAvailability(dishName, quantity);

        //Find MenuNode to determine price of dish
        MenuNode origDishNode = findDish(dishName);

        //Determine dish profit to create TransactionData node with
        double profit = (origDishNode.getDish().getProfit() * quantity);

        //TransactionNode to be created
        TransactionData order;

        if(canOrder)
        {
            order = new TransactionData("order", dishName, quantity, profit, canOrder);
            addTransactionNode(order);

            updateStockOfIngredientInDish(dishName, quantity); //Update all the ingredients for the dish
        }
        else
        {
            order = new TransactionData("order", dishName, quantity, 0, canOrder);
            addTransactionNode(order);

            //Go through rest of catagory until order is made or entire catagory is checked 
            MenuNode dishPtr = origDishNode.getNextMenuNode(); //Start checking dishes after one that has already failed 
            
            while(dishPtr != origDishNode)
           //do
            {
                //dishPtr = dishPtr.getNextMenuNode();
                
                //Start checking from the beginning of the catagory when ptr is null
                if(dishPtr == null)
                {
                    //Catch category name 
                    String category = origDishNode.getDish().getCategory(); 
                    //Use category name to find category index (using helper method)
                    int categoryIndex = findCategoryIndex(category);

                    //Using category index, start checking category from start of the list by setting dishPtr to this item
                    dishPtr = menuVar[categoryIndex];
                    
                    //If setting to start causes us to hit original node -> went through menu 
                    if(dishPtr == origDishNode)
                    {
                    return;
                    }
                }

                //Get the name of the ptr dish
                String dishNameNext = dishPtr.getDish().getDishName();
                //Check if the next ptr could order
                boolean canOrderNext = checkDishAvailability(dishNameNext, quantity);

                double profit2 = dishPtr.getDish().getProfit() * quantity;

                //If we can order: calculate profit again,  make new transactionData, and add it to transaction node
                if(canOrderNext)
                {
                    order = new TransactionData("order", dishNameNext, quantity, profit2, canOrderNext);
                    addTransactionNode(order);

                    updateStockOfIngredientInDish(dishNameNext, quantity); //Update all the ingredients for the dish
                    return;
                }
                //If we can't: add failed node, move dishPtr to next 
                else
                {
                    order = new TransactionData("order", dishNameNext, quantity, 0, canOrderNext);
                    addTransactionNode(order);
                }
                 
                dishPtr = dishPtr.getNextMenuNode();
            }   
            //Iterate through linked list until pointer is back to original dish
            //while(dishPtr.getNextMenuNode() != origDishNode);
        }  
    }

    /**
     * This method returns the total profit for the day
     *
     * The profit is computed by traversing the transaction linked list (transactionVar) 
     * adding up all the profits for the day
     * 
     * @return profit - double value of the total profit for the day
     */

    public double profit () {

        //Catch start of transaction list to traverse
        TransactionNode transactionListPtr = transactionVar;

        //Variable to keep track off profit
        double totalProfit = 0;

        //Traverse through list until pointer is null
        while(transactionListPtr != null)
        {
            totalProfit += transactionListPtr.getData().getProfit(); //Add the transaction profit to the total profit 
            transactionListPtr = transactionListPtr.getNext(); //increment transaction node to next 
        }

        
        return totalProfit; // update the return value
    }

    /**
     * This method simulates donation requests, successful or not.
     * 
     * 1. check whether the profit is > 50 and whether there's enough ingredients in stock.
     * 
     * 2. add transaction to transactionVar
     * 
     * @param ingredientName - String of ingredient that's been requested
     * @param quantity - int of how many of that ingredient has been ordered
     * @return void
     */

    public void donation ( String ingredientName, int quantity ){

        //Catch the total amount of profit 
        double profit = profit();

        //Find ingredient StockNode using helper method (if there is stock, if not this will be set to null)
        StockNode ingredient = findStockNode(ingredientName); 
        int totalIngredients = ingredient.getIngredient().getStockLevel();

        //TransactionData to be added 
        TransactionData donation;

        // (1) Check if the profit for the day is greater than 50 (2) if ingredient in stock (3) If there is enough ingredients to donate
        if(profit > 50 && ingredient != null && totalIngredients >= quantity)
        {
            donation = new TransactionData("donation", ingredientName, quantity, 0, true);
            addTransactionNode(donation);
            updateStock(ingredientName, -1, (-quantity)); //Upadate stock accordingly 
        }
        //If not, create failed donation transaction
        else
        {
            donation = new TransactionData("donation", ingredientName, quantity, 0, false);
            addTransactionNode(donation);
        }
    }

    /**
     * This method simulates restock orders
     * 
     * 1. check whether the profit is sufficient to pay for the total cost of ingredient
     *      a) (how much each ingredient costs) * (quantity)
     *      b) if there is enough profit, adjust stock and profit accordingly
     * 
     * 2. add transaction to transactionVar
     * 
     * @param ingredientName - ingredient that's been requested
     * @param quantity - how many of that ingredient needs to be ordered
     */

    public void restock ( String ingredientName, int quantity ){
        
        //Catch the total amount of profit 
        double profit = profit();

        //Check the total amount needed to buy the ingredient 
        StockNode ingredient = findStockNode(ingredientName); //Find ingredient StockNode using helper method (if there is stock, if not this will be set to null)
        double totalCostForIngredients = (ingredient != null) ? (ingredient.getIngredient().getCost() * quantity) : 0; //Calculate cost if the ingredient stockNode is not null

        //TransactionData to be added 
        TransactionData restock;

        //Add successful transaction if (1) ingredient exists (2) there is enough profit to purchase the ingredients 
        if(ingredient != null && profit > totalCostForIngredients)
        {
            restock = new TransactionData("restock", ingredientName, quantity, (-totalCostForIngredients), true); //If successful, subtract cost of ingredients 
            addTransactionNode(restock);
            updateStock(ingredientName, -1, quantity);
        }
        else
        {
            restock = new TransactionData("restock", ingredientName, quantity, 0, false);
            addTransactionNode(restock);
        }
    }

   /*
    * Seat guests/customers methods
    */

    /**
     * Method to populate tables (which is a 2d integer array) based upon input file
     * 
     * First row of tables[][]: contains the total number of seats available at a table (each table is an index in first row)
     * Second row of tables[][]: initializes all indices to 0
     *      0 --> table is available
     *      1 --> table is occupied
     * 
     *          ** GIVEN METHOD **
     *          ** DO NOT EDIT **
     * 
     * @param inputFile - tables1.in (contains all the tables in the RUHungry restaurant)
     * @return void (aka nothing)
     */

    public void createTables ( String inputFile ) { 

        StdIn.setFile(inputFile);
        int numberOfTables = StdIn.readInt();
        tablesInfo = new int[2][numberOfTables];
        tables = new People[numberOfTables];
        
        for ( int t = 0; t < numberOfTables; t++ ) {
            tablesInfo[0][t] = StdIn.readInt() * StdIn.readInt();
        }
    }

    /**
     * PICK UP LINE OF THE METHOD:
     * *are you a linked list? cuz nothing could stock up to you and 
     * you’re pretty queue(te)*
     * 
     * ***************
     * This method simulates seating guests at tables. You are guaranteed to be able to sit everyone from the waitingQueue eventually.
     *       
     * 1. initialize leftQueueVar a People queue that represents the people that have left the restaurant
     * 
     * 2. while there are people waiting to be sat:
     *      - Starting from index 0 (zero), seat the next party in the first available table that fits their party.
     *      - If there is no available table for the next party, kick a party out from the tables array:
     *          1. starting at index 0 (zero), find the first table big enough to hold the next party in line.
     *          2. remove the current party, add them to the leftQueueVar.
     *          3. seat the next party in line.
     * 
     * tableInfo contains the number of seats per table as well as if the table is occupied or not.
     * tables contains the People object (party) currently at the table.
     * 
     * Note: After everyone has been seated (waitingQueue is empty), remove all the parties from tables and add then to the leftQueueVar.
     * 
     * @param waitingQueue - queue containing people waiting to be seated (each element in queue is a People <-- you are given this class!)
     */

    public void seatAllGuests ( Queue<People> waitingQueue ) {

        // WRITE YOUR CODE HERE
    }
}