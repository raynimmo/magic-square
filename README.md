magic-square
============

Title: CO32018 - Internet Programming - A Magic Square with a Hole

The aim of the coursework is to create an implementation of a puzzle on the WWW which can be manipulated and solved by the user.

The aim of the implementation is a graphical representation of the puzzle which provides the facilities that a player needs to be able to solve the problem: manipulating the symbols, appropriate description and explanation, and help where suitable. The program should make use of the visual programming facilities of the chosen representation to give the user an implementation of the puzzle which is friendly and easy to use.

Whilst using all 28 dominoes, form a rectangular array with a central hole, as shown. The sum of each of the eight rows, eight columns, and each of the diagonals (shown by dotted lines) must be 21. For rows, add whole dominoes; for columns, add half dominoes (squares) and for diagonals, add the 6 half-dominoes covered by the dotted line. “The fourth row from the top has been filled in. Its sum is 5+6+5+5=21. The four corner pieces have also been filled in, including the double blank in the bottom right corner.” Your implementation should be able to keep track of the sums of all the rows, diagonals and columns, and be able to allow the user to move dominoes from a stockpile into the allowed positions in the layout.

Copyright: Ray Nimmo(c) 2006
Version 1.8.9
Author Ray Nimmo - 05005802



todo:  screen flickers when moving dominoes - implement paint update function 
todo:  implement a snap to position function for dominoes on board
