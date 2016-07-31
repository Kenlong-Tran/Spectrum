# Spectrum
The add a single string functions creates a node of that value while the add two strings function, creates the two nodes if they are not present and makes an edge linking them together. 
The associated function  checks if there is a path from the first string to the second string. 
The connections function finds the least number of steps it takes from all the paths possible to the  string.

For example the input:
add a
add b c
add b a
add a d
connections a
associated c d
add e
associated a e

Will produce the tree:
d-a-b-c       e

connections a returns the value below.
1: 2
2: 1
Which means a has two nodes that are 1 step away and 1 node that is 2 steps away.

associated c d returns the value below.
yes: 3
This means that going from node c to node d is 3 steps away.
While associated a e produces the following:
no
Because there is no path from a to e.
