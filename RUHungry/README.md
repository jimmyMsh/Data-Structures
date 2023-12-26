## Summary
**RU Hungry** is a comprehensive Java project that simulates the operations of a fictitious restaurant. The project utilizes various data structures, such as hash tables with separate chaining, arrays, and linked lists, to manage and execute tasks like handling a menu, managing stockroom and inventory, processing transactions, and managing the seating for guests. It demonstrates proficiency in object-oriented programming, data structure manipulation, and complex algorithmic logic.

## Data Structures and Concepts Utilized
### Arrays and 2D Arrays
Used to hold information on the menu categories and each table in the restaurant.

### Hash Tables with Separate Chaining
Employs a custom hash function for indexing and resolving collisions, maintaining stock availability, and transaction history.

### Linked Lists
Each category in the menu is represented by a linked list containing details about dishes available. The stock and transactions are also managed through linked lists to handle varying volumes of data dynamically.

### Object-Oriented Programming
The program is structured around objects representing restaurant entities, such as dishes, ingredients, stock items, and transactions, to facilitate easier management of related data and actions.

### Queue Data Structure
Used to simulate a waiting line for restaurant seating, enabling the seating and removal of guest groups based on table availability.

## Features and Method Descriptions
### Menu Management (`menu`)
Creation and management of the restaurant's menu involving reading from a file, populating categories, and arranging dishes using linked lists within an array.

### Stock Management (`addStockNode`, `deleteStockNode`, `findStockNode`, `updateStock`, `createStockHashTable`, `updatePriceAndProfit`)
Enables the addition, deletion, updating, and searching of stock items in the restaurant’s inventory using hashtable methods. Responsible for adjusting prices and calculating profits based on cost and stock levels.

### Transaction Processing (`addTransactionNode`, `order`, `profit`, `donation`, `restock`)
Handles different transactions, calculating profits and managing donations and restocking actions based on current profit levels and stock availability.


## Skills Demonstrated
- Use of fundamental and advanced Java data structures.
- Integration of various data structures to build a complex and robust application.
- Debugging, testing, and validation of data using custom written code.
- Reading from and writing to external files.
- Execution of object-oriented programming principles such as encapsulation and abstraction.

> Note: The class `RUHungry` is the central file where the described methods are implemented. Additional support files representing the various objects in use (e.g., `Dish`, `Ingredient`, `TransactionData`) accompany this class file to complete the project functionality.
