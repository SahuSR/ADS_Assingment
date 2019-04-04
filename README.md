# Project Title

## B-Tree Implementation

### Introduction
This project demonstrates the B-Tree operations like Insertion, Deletion and Searching in GCC compiler. B-Tree is a self-balancing search tree. When the number of keys is high, the data is read from disk in the form of blocks. Disk access time is very high compared to main memory access time. The main idea of using B-Trees is to reduce the number of disk accesses. 

### B-Tree Properties

```
1) All leaves are at same level.
2) A B-Tree is defined by the term minimum degree ‘t’. The value of t depends upon disk block size.
3) Every node except root must contain at least t-1 keys. Root may contain minimum 1 key.
4) All nodes (including root) may contain at most 2t – 1 keys.
5) Number of children of a node is equal to the number of keys in it plus 1.
6) All keys of a node are sorted in increasing order. The child between two keys k1 and k2 contains all keys in the range from k1 and k2.
7) B-Tree grows and shrinks from the root which is unlike Binary Search Tree. Binary Search Trees grow downward and also shrink from downward.
8) Like other balanced Binary Search Trees, time complexity to search, insert and delete is O(Logn).
```
### B-Tree Searching

Search is similar to the search in Binary Search Tree. Let the key to be searched be k. We start from the root and recursively traverse down. For every visited non-leaf node, if the node has the key, we simply return the node. Otherwise, we recur down to the appropriate child (The child which is just before the first greater key) of the node. If we reach a leaf node and don’t find k in the leaf node, we return NULL.

### B-Tree Insertion
Insertion algorithm is as follows:
```
1) Initialize x as root.
2) While x is not leaf, do following
  a) Find the child of x that is going to to be traversed next. Let the child be y.
  b) If y is not full, change x to point to y.
  c) If y is full, split it and change x to point to one of the two parts of y. If k is smaller than mid key in y, then set x as first part
  of y. Else second part of y. When we split y, we move a key from y to its parent x.
3) The loop in step 2 stops when x is leaf. x must have space for 1 extra key as we have been splitting all nodes in advance. So simply 
  insert k to x. 
```
### B-Tree Deletion
We will check for the following cases and implement as follows:
```
1. If the key k is in node x and x is a leaf, delete the key k from x.

2. If the key k is in node x and x is an internal node, do the following.
    a) If the child y that precedes k in node x has at least t keys, then find the predecessor k0 of k in the sub-tree rooted at y. 
       Recursively delete k0, and replace k by k0 in x. (We can find k0 and delete it in a single downward pass.)
    b) If y has fewer than t keys, then, symmetrically, examine the child z that follows k in node x. If z has at least t keys, then find 
       the successor k0 of k in the subtree rooted at z. Recursively delete k0, and replace k by k0 in x. (We can find k0 and delete it in a 
       single downward pass.)
    c) Otherwise, if both y and z have only t-1 keys, merge k and all of z into y, so that x loses both k and the pointer to z, and y now 
       contains 2t-1 keys. Then free z and recursively delete k from y.

3. If the key k is not present in internal node x, determine the root x.c(i) of the appropriate subtree that must contain k, if k is in the 
   tree at all. If x.c(i) has only t-1 keys, execute step 3a or 3b as necessary to guarantee that we descend to a node containing at least t 
   keys. Then finish by recursing on the appropriate child of x.
    a) If x.c(i) has only t-1 keys but has an immediate sibling with at least t keys, give x.c(i) an extra key by moving a key from x down 
       into x.c(i), moving a key from x.c(i) ’s immediate left or right sibling up into x, and moving the appropriate child pointer from the 
       sibling into x.c(i).
    b) If x.c(i) and both of x.c(i)’s immediate siblings have t-1 keys, merge x.c(i) with one sibling, which involves moving a key from x 
       down into the new merged node to become the median key for that node.
```

## Author
* **Shreyansh Sahu**

## References
I have refered https://www.geeksforgeeks.org/b-tree-set-1-introduction-2/ for the concept of B-Tree.
