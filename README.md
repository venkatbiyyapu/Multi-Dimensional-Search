### Multi-Dimensional Search (MDS)
---
### Overview:

This project implements a multi-dimensional search system that efficiently manages and queries products with three attributes: `id`, `description`, and `price`. The MDS system supports operations such as inserting and deleting products, updating prices, and finding products based on their descriptions. It is optimized for handling large datasets, such as those used by e-commerce platforms like Amazon, where products can be searched based on different attributes using efficient data structures.

---

### Supported Operations:

1. **Insert(id, price, description)**:
   - Adds a new product with the specified `id`, `price`, and a list of long integers as its `description`.
   - If a product with the same `id` exists, its price and description are updated, unless the description is empty, in which case only the price is updated.
   - **Returns**: `1` if a new product is added, `0` if an existing product is updated.

2. **Find(id)**:
   - Searches for a product by its `id` and returns its price.
   - **Returns**: The product's price or `0` if the product does not exist.

3. **Delete(id)**:
   - Removes the product with the given `id`.
   - **Returns**: The sum of the long integers in the description of the deleted product, or `0` if the product does not exist.

4. **FindMinPrice(n)**:
   - Finds the product with the lowest price whose description contains the long integer `n`.
   - **Returns**: The lowest price, or `0` if no product matches.

5. **FindMaxPrice(n)**:
   - Finds the product with the highest price whose description contains the long integer `n`.
   - **Returns**: The highest price, or `0` if no product matches.

6. **FindPriceRange(n, low, high)**:
   - Searches for products whose descriptions contain the long integer `n` and whose prices fall within the range `[low, high]`.
   - **Returns**: The number of matching products.

7. **PriceHike(l, h, r%)**:
   - Increases the price of all products with `id` in the range `[l, h]` by `r%`. Any fractional pennies are discarded.
   - **Returns**: The sum of the price increases for all affected products.

8. **RemoveNames(id, list)**:
   - Removes the elements in `list` from the description of the product with the specified `id`.
   - **Returns**: The sum of the numbers removed from the description, or `0` if the product or description does not exist.

---

### How to Run the Program:

1. **Compilation**: 
   - Compile the Java files (`MDS.java` and `MDSDriver.java`) using the following command:
     ```bash
     javac MDS.java MDSDriver.java
     ```

2. **Execution**:
   - To run the program with input from a file or from standard input:
     - Example with input file:
       ```bash
       java MDSDriver <inputfile>
       ```
     - Example with standard input:
       ```bash
       java MDSDriver
       ```
   - For verbose output (prints additional details about the operations):
     ```bash
     java MDSDriver <inputfile> true
     ```

---

### Input Format:

- Each operation is provided on a new line. The operation name is followed by the respective parameters (separated by spaces).
- For example:
  ```
  Insert 22 19.97 475 1238 9742 0
  Find 22
  Delete 22
  FindMinPrice 475
  ```

---

### Output:

- The program outputs a single number, which is the sum of all the results from the processed operations. 
- For example, an output could be:
  ```
  1448
  Time: 15 msec.
  Memory: 2 MB / 64 MB.
  ```

---

### Performance Considerations:

The MDS system is built for efficient searching, updating, and querying across multiple dimensions of data. It uses data structures like balanced trees and indexing to handle large datasets, ensuring that operations such as insertion, deletion, and search are optimized for speed. It can efficiently handle millions of input operations, keeping memory and time usage within practical limits.

