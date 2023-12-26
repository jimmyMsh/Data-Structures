package games;

import java.util.ArrayList;


/**
 * This class contains methods to represent the Hunger Games using BSTs.
 * Moves people from input files to districts, eliminates people from the game,
 * and determines a possible winner.
 * 
 * @author Pranay Roni
 * @author Maksims Kurjanovics Kravcenko
 * @author Kal Pandit
 */
public class HungerGames {

    private ArrayList<District> districts;  // all districts in Panem.
    private TreeNode            game;       // root of the BST. The BST contains districts that are still in the game.

    /**
     * ***** DO NOT REMOVE OR UPDATE this method *********
     * Default constructor, initializes a list of districts.
     */
    public HungerGames() {
        districts = new ArrayList<>();
        game = null;
        StdRandom.setSeed(2023);
    }

    /**
     * ***** DO NOT REMOVE OR UPDATE this method *********
     * Sets up Panem, the universe in which the Hunger Games takes place.
     * Reads districts and people from the input file.
     * 
     * @param filename will be provided by client to read from using StdIn
     */
    public void setupPanem(String filename) { 
        StdIn.setFile(filename);  // open the file - happens only once here
        setupDistricts(filename); 
        setupPeople(filename);
    }

    /**
     * Reads the following from input file:
     * - Number of districts
     * - District ID's (insert in order of insertion)
     * Insert districts into the districts ArrayList in order of appearance.
     * 
     * @param filename will be provided by client to read from using StdIn
     */
    public void setupDistricts (String filename) {

        // Read number of districts from first line on input file
        int numDistricts = StdIn.readInt();

        //Iterate thorugh the next lines - dependent on the previous number that specifies the amount of districts 
        int districtID;  //will catch districtID in loop
        District district; //will be reference to district object 
        
        for(int i = 0; i < numDistricts ; i++)
        {
            districtID = StdIn.readInt();
            district = new District(districtID);

            //add the district to district array list 
            districts.add(district);
        }

    }

    /**
     * Reads the following from input file (continues to read from the SAME input file as setupDistricts()):
     * Number of people
     * Space-separated: first name, last name, birth month (1-12), age, district id, effectiveness
     * Districts will be initialized to the instance variable districts
     * 
     * Persons will be added to corresponding district in districts defined by districtID
     * 
     * @param filename will be provided by client to read from using StdIn
     */
    public void setupPeople (String filename) {

        // Read number of poeple in in line of input (after districts)
        int numPlayers = StdIn.readInt();
        //System.out.println(numPlayers);

        //iterate through next lines- as many times as previous number
        String line; //will hold player information at each line 

        // Clear the buffer by reading the leftover newline character
        StdIn.readLine();

        String[] playerDetails; //will hold string of different pieces of info for player

        for(int i = 0; i < numPlayers ; i++)
        {
            line = StdIn.readLine();
            playerDetails = line.split(" "); //Splits at every space, as this is the file format 

            //Create player object 
            Person player = new Person(Integer.parseInt(playerDetails[2]), playerDetails[0], playerDetails[1], Integer.parseInt(playerDetails[3]), Integer.parseInt(playerDetails[4]), Integer.parseInt(playerDetails[5]));
            
            //Check Tessera prop
            if(player.getAge() >= 12 && player.getAge() < 18)
            {
                player.setTessera(true);
            }

           addPersonToDistrict(player);
            
        }
    }

    /**
     * Adds a district to the game BST.
     * If the district is already added, do nothing
     * 
     * @param root        the TreeNode root which we access all the added districts
     * @param newDistrict the district we wish to add
     */
    public void addDistrictToGame(TreeNode root, District newDistrict) {

        //Make district into TreeNode that can be applied to the tree
        TreeNode districtToAdd = new TreeNode(newDistrict, null, null);


        //If current TreeNode is null, make root equal to this node 
        if(root == null)
        {
            game = districtToAdd; //Change main BST root to reflect
            districts.remove(newDistrict);
            return;
        }
        
        //Save the pointer of the node before the place we will be inserting newDistrict
        TreeNode ptrBefore = null;
        TreeNode temp = root;

        //Transverse the entire structure until finding the poistion to insert newDistrict 
        while(temp != null)
        {
            if(temp.getDistrict().getDistrictID() > newDistrict.getDistrictID())
            {
                ptrBefore = temp;
                temp = temp.getLeft();
            }
            else if (temp.getDistrict().getDistrictID() <  newDistrict.getDistrictID())
            {
                ptrBefore = temp;
                temp = temp.getRight();
            }
            else if (temp.getDistrict().getDistrictID() == newDistrict.getDistrictID())
            {
                return;
            }
        }

        //Set the newDistrict 
        if(newDistrict.getDistrictID() > ptrBefore.getDistrict().getDistrictID())
        {
            ptrBefore.setRight(districtToAdd);
            districts.remove(newDistrict); //Might have to transverse list to remove 
        }
        else if(newDistrict.getDistrictID() < ptrBefore.getDistrict().getDistrictID())
        {
            ptrBefore.setLeft(districtToAdd);
            districts.remove(newDistrict); //Might have to transverse list to remove 
        }
    }

    /**
     * Searches for a district inside of the BST given the district id.
     * 
     * @param id the district to search
     * @return the district if found, null if not found
     */
    public District findDistrict(int id) 
    {
    return (findDistrictHelper(game, id));    
    }

    private District findDistrictHelper(TreeNode root, int id)
    {
        //Check if the root is null (base case - nothing found)
        if (root == null)
        {
            return null;
        }
        //If id greater, go right 
        else if(id > root.getDistrict().getDistrictID())
        {
            return(findDistrictHelper(root.getRight(), id));
        }
        //If id less, go left
        else if(id < root.getDistrict().getDistrictID())
        {
            return(findDistrictHelper(root.getLeft(), id));
        }
        //Last else is found, so return the district we are at
        else
        {
            return root.getDistrict();
        }
    }

    /**
     * Selects two duelers from the tree, following these rules:
     * - One odd person and one even person should be in the pair.
     * - Dueler with Tessera (age 12-18, use tessera instance variable) must be
     * retrieved first.
     * - Find the first odd person and even person (separately) with Tessera if they
     * exist.
     * - If you can't find a person, use StdRandom.uniform(x) where x is the respective 
     * population size to obtain a dueler.
     * - Add odd person dueler to person1 of new DuelerPair and even person dueler to
     * person2.
     * - People from the same district cannot fight against each other.
     * 
     * @return the pair of dueler retrieved from this method.
     */
    public DuelPair selectDuelers() {
        
        //Create DuelPair Object that will store people- start with no one
        DuelPair duel = new DuelPair(null, null);

        //Try to set first player in duel (tessera true: odd)
        duel.setPerson1(selectDuelersHelper(true, "odd", this.game, null));

        //Next, try to set second person 
        duel.setPerson2(selectDuelersHelper(true, "even", this.game, duel.getPerson1()));

        //If first person is null after previous call, means there was no tessera and odd person, so just get odd person (make sure not to add person from same district as player2)
        if (duel.getPerson1() == null) 
        {
            duel.setPerson1(selectDuelersHelper(false, "odd", this.game, duel.getPerson2()));
        }

        //If second person null after previous call, means there was no tessera and even person, just get even person
        if (duel.getPerson2() == null)
        {
            duel.setPerson2(selectDuelersHelper(false, "even", this.game, duel.getPerson1()));
        }

        return duel; 
    }

    private Person selectDuelersHelper(boolean tessera, String parity, TreeNode root, Person otherPlayer) {
    // If the root is null, return null as no player can be found
    if (root == null) return null;

    // Select the appropriate population based on the parity
    ArrayList<Person> districtCheck = parity.equalsIgnoreCase("odd") ?
    root.getDistrict().getOddPopulation() : root.getDistrict().getEvenPopulation();

    // If there are players in the population and they are from a different district than the other player
    if (districtCheck.size() > 0 && (otherPlayer == null || root.getDistrict().getDistrictID() != otherPlayer.getDistrictID())) {
        // Search for a player with the specified tessera status
        for (Person player : districtCheck) {
            if (player.getTessera() == tessera && (otherPlayer == null || player.getDistrictID() != otherPlayer.getDistrictID())) {
                districtCheck.remove(player);  // Remove the found player from the population
                return player;
            }
        }
        // If no tessera player was found and we are not specifically looking for tessera players, 
        // select a random player from the population
        if (!tessera) {
            int randomIndex = StdRandom.uniform(districtCheck.size());
            return districtCheck.remove(randomIndex);  // Remove and return the random player
        }
    }

    // If no player was found in the current district, search the left and right subtrees
    Person found = selectDuelersHelper(tessera, parity, root.getLeft(), otherPlayer);
    if (found == null) {
        found = selectDuelersHelper(tessera, parity, root.getRight(), otherPlayer);
    }
    return found;
}



    /**
     * Deletes a district from the BST when they are eliminated from the game.
     * Districts are identified by id's.
     * If district does not exist, do nothing.
     * 
     * This is similar to the BST delete we have seen in class.
     * 
     * @param id the ID of the district to eliminate
     */
    public void eliminateDistrict(int id) {

        this.game = eliminateDistrictHelper(id, game);
        return;
    }


    private TreeNode eliminateDistrictHelper(int id, TreeNode root)
    {
        if(root == null)
        {
            return root;
        }
        if(id > root.getDistrict().getDistrictID()) //If ID greater than current node, go right
        {
            root.setRight(eliminateDistrictHelper(id, root.getRight()));
        }
        else if(id < root.getDistrict().getDistrictID()) //If ID less than current node, go left 
        {
            root.setLeft(eliminateDistrictHelper(id, root.getLeft()));
        }
        else //Only other option is id = root
        {
            if(root.getLeft() == null) // (1) child right only 
            {
                return (root.getRight()); //Sets root of caller to root after node to the right 
            }
            else if(root.getRight() == null) // (2) child left when no child right  
            {
                return (root.getLeft());
            }
            else // (3) 2 children, passes both previous method calls 
            {
                //Find successor district in seperate method call, set the current root equal to this district 
                root.setDistrict(inOrderSuccessorDistrict(root));

                //Now delete the District that we just made root equal to
                root.setRight(eliminateDistrictHelper(root.getDistrict().getDistrictID(), root.getRight()));

            }
        }
        return root;
    }

    //Helper method to find in-order successor- keep going left after first right of node
    private District inOrderSuccessorDistrict(TreeNode root)
    {
        TreeNode successor = root.getRight();
        if(successor.getLeft() == null)
        {
            return (successor.getDistrict());
        }

        return inOrderSuccessorDistrict(successor.getLeft());
    }

    /**
     * Eliminates a dueler from a pair of duelers.
     * - Both duelers in the DuelPair argument given will duel
     * - Winner gets returned to their District
     * - Eliminate a District if it only contains a odd person population or even
     * person population
     * 
     * @param pair of persons to fight each other.
     */
    public void eliminateDueler(DuelPair pair) 
    {
        Person playerOne = pair.getPerson1();
        Person playerTwo = pair.getPerson2();

        if(playerTwo == null || playerOne == null) //If either player is null
        {
            if (playerOne != null)
            {
                addPersonToDistrict(playerOne);
            }
            else
            {
                addPersonToDistrict(playerTwo);
            }
            return;
        }

        Person winner = playerOne.duel(playerTwo); //Make players duel

        District winnerDist = findDistrict(winner.getDistrictID()); //Find district of winner
        //Determine whether to add winner to even or odd population 
        if(winner.getBirthMonth() % 2 > 0)
        {
            winnerDist.addOddPerson(winner);
        }
        else
        {
            winnerDist.addEvenPerson(winner);
        }
        
        
        //Check if either player's odd/even population is zero
        District playerOneDist = findDistrict(playerOne.getDistrictID()); //Check player one
        if (playerOneDist.getEvenPopulation().size() < 1 || playerOneDist.getOddPopulation().size() < 1)
        {
            eliminateDistrict(playerOne.getDistrictID());
        }

        District playerTwoDist = findDistrict(playerTwo.getDistrictID()); //check player two
        if (playerTwoDist.getEvenPopulation().size() < 1 || playerTwoDist.getOddPopulation().size() < 1)
        {
            eliminateDistrict(playerTwo.getDistrictID());
        }
    }


    //Helper method to add person when needed 
    private void addPersonToDistrict(Person person)
    {
        for(District district: this.districts) //Go through districts to find which one person belongs in 
        {
           if(district.getDistrictID() == person.getDistrictID())
            {
                if(person.getBirthMonth() % 2 > 0)
                {
                    district.addOddPerson(person); //if person has odd birthmonth
                }
                else
                {
                    district.addEvenPerson(person);
                }
            }
        }
    }



    /**
     * ***** DO NOT REMOVE OR UPDATE this method *********
     * 
     * Obtains the list of districts for the Driver.
     * 
     * @return the ArrayList of districts for selection
     */
    public ArrayList<District> getDistricts() {
        return this.districts;
    }

    /**
     * ***** DO NOT REMOVE OR UPDATE this method *********
     * 
     * Returns the root of the BST
     */
    public TreeNode getRoot() {
        return game;
    }
}
