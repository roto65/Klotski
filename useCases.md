---
permalink: /use-cases
---

# Use cases

## Use case #1

| Section              | Section Description                                                                                                                                                                                      |
|:---------------------|:---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Use Case             | Start the software                                                                                                                                                                                       |
| Actors               | Player                                                                                                                                                                                                   |
| Description          | The player start the software to play the game                                                                                                                                                           |
| Flow of events       | The player select the software and execute it, the software then initialize the game board to its default disposition, sets the move counter to zero, the layout identifier to one and loads all buttons |
| Alternative Flow     | None                                                                                                                                                                                                     |
| Special Requirements | None                                                                                                                                                                                                     |
| Pre Conditions       | The program is not running                                                                                                                                                                               |
| Post Conditions      | The program is running                                                                                                                                                                                   |


## Use case #2

| Section              | Section Description                                                                                                                                                                                                                                                                                                                                                                                                |
|:---------------------|:-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Use Case             | Move a block                                                                                                                                                                                                                                                                                                                                                                                                       |
| Actors               | Player                                                                                                                                                                                                                                                                                                                                                                                                             |
| Description          | The player chooses a block and tries to move it within the game board                                                                                                                                                                                                                                                                                                                                              |
| Flow of events       | The player chooses a block, then selects a position where the block should move if there are no obstacles the block moves and updates the move counter adding one move                                                                                                                                                                                                                                             |
| Alternative Flow     | If the end position can't be reached, because there are blocks in between, or if the block would be moved and it would overlap any other block, or it leaves partially or entirely the game board, the block moves in the direction of the end position until it can and then it stops before overlapping another block or exit the game board. If it moves of at least on space, the move counter is incremented. |
| Special Requirements | The game hasn't ended                                                                                                                                                                                                                                                                                                                                                                                              |
| Pre Conditions       | The block must have some blank spaces adjacent to it so that neither the block overlaps any other block nor it leaves the game board                                                                                                                                                                                                                                                                               |
| Post Conditions      | The block is set in the selected position and it is placed correctly                                                                                                                                                                                                                                                                                                                                               |


## Use case #3

| Section              | Section Description                                                                                                                                |
|:---------------------|:---------------------------------------------------------------------------------------------------------------------------------------------------|
| Use Case             | Undo the last move(s)                                                                                                                              |
| Actors               | Player                                                                                                                                             |
| Description          | Undo the last move returning the block in the previous position, this can be done multiple times                                                   |
| Flow of events       | The player selects the undo function and the last block moved goes back to its previous position and updates the move counter subtracting one move |
| Alternative Flow     | If no move was done or the game has ended it does nothing                                                                                          |
| Special Requirements | The game hasn't ended                                                                                                                              |
| Pre Conditions       | At least one move has to been done previously                                                                                                      |
| Post Conditions      | The block that last moved return to its previous position                                                                                          |


## Use case #4

| Section              | Section Description                                                                                                                                                   |
|:---------------------|:----------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Use Case             | Redo the undone move(s)                                                                                                                                               |
| Actors               | Player                                                                                                                                                                |
| Description          | Redo the last undone move returning the block in the position in which it was before the undo, this can be done multiple times                                        |
| Flow of events       | The player selects the redo function and the last block moved by the undo function goes back to the position it was set, and updates the move counter adding one move |
| Alternative Flow     | If no undo was done or the game has ended it does nothing                                                                                                             |
| Special Requirements | The game hasn't ended                                                                                                                                                 |
| Pre Conditions       | At least one undo has to been done previously and the game hasn't ended                                                                                               |
| Post Conditions      | The block that last was undone return to its previous position                                                                                                        |


## Use case #5

| Section              | Section Description                                                                                                                                                                           |
|:---------------------|:----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Use Case             | Best move possible                                                                                                                                                                            |
| Actors               | Player                                                                                                                                                                                        |
| Description          | Move a block so that is the best move to solve the game in the fewest moves possible                                                                                                          |
| Flow of events       | The player selects the hint function and the program finds the next move to make to solve the game in the fewest moves possible, makes the move, and updates the move counter adding one move |
| Alternative Flow     | If the game has ended it does nothing                                                                                                                                                         |
| Special Requirements | The game hasn't ended                                                                                                                                                                         |
| Pre Conditions       | The program is running                                                                                                                                                                        |
| Post Conditions      | A block is set to a new position and it is placed correctly                                                                                                                                   |


## Use case #6

| Section              | Section Description                                                                                                                                                                                                                                                                                                                                                                                                                                           |
|:---------------------|:--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Use Case             | End the game                                                                                                                                                                                                                                                                                                                                                                                                                                                  |
| Actors               | Player                                                                                                                                                                                                                                                                                                                                                                                                                                                        |
| Description          | When the large block ends in a certain spot the game is considered ended                                                                                                                                                                                                                                                                                                                                                                                      |
| Flow of events       | When the large block is placed in the bottom mid of the game board it is displayed that the game has ended and the player cannot move anymore any block nor can use the undo, redo, hint, and save functions. It is opened a new window where it is displayed how many moves the game has ended and the minimum of them required for completing the selected layout; the player can select new game, new layout, load, or quit functions from the new window. |
| Alternative Flow     | None                                                                                                                                                                                                                                                                                                                                                                                                                                                          |
| Special Requirements | A move must has just been done                                                                                                                                                                                                                                                                                                                                                                                                                                |
| Pre Conditions       | The large block isn't placed on the bottom mid part of the game board, so it was moved there                                                                                                                                                                                                                                                                                                                                                                  |
| Post Conditions      | The blocks cannot be move anymore and some functions (undo, redo, hint, and save) are disabled                                                                                                                                                                                                                                                                                                                                                                |


## Use case #7

| Section              | Section Description                                                                                                                                                   |
|:---------------------|:----------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Use Case             | Save the game                                                                                                                                                         |
| Actors               | Player                                                                                                                                                                |
| Description          | The current game board, the moves done and the move counter are saved                                                                                                 |
| Flow of events       | The player selects the save function and it is asked to him to select where he wants to save the game and with which name, then the program saves the game as a file. |
| Alternative Flow     | If a save is already present with the same name the previous one is overwritten.                                                                                      |
| Special Requirements | The game hasn't ended                                                                                                                                                 |
| Pre Conditions       | The program is running                                                                                                                                                |
| Post Conditions      | Everything in the game remains the same as before, and a save file is created or overwritten                                                                          |


## Use case #8

| Section              | Section Description                                                                                                                                                                                                                               |
|:---------------------|:--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Use Case             | Load the game                                                                                                                                                                                                                                     |
| Actors               | Player                                                                                                                                                                                                                                            |
| Description          | The current game board, the moves done and the move counter are loaded from the/a save previously done                                                                                                                                            |
| Flow of events       | The player selects the load function and it is asked to him to select a file that is presumed a previously done save, if it is the game board is updated to make it equal to the one that was saved, and the move counter is set to the one saved |
| Alternative Flow     | If the file selected is not a save file or it is corrupted the system tries to interpret it anyway resulting in strange results                                                                                                                   |
| Special Requirements | None                                                                                                                                                                                                                                              |
| Pre Conditions       | There must have been a save in the past, not necessarily in the current session                                                                                                                                                                   |
| Post Conditions      | The game board is equal to the one saved as the moves done and the move counter                                                                                                                                                                   |


## Use case #9

| Section              | Section Description                                                                                                                                                                                                                                          |
|:---------------------|:-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Use Case             | Change the initial board layout                                                                                                                                                                                                                              |
| Actors               | Player                                                                                                                                                                                                                                                       |
| Description          | The game board is set to a new disposition                                                                                                                                                                                                                   |
| Flow of events       | The player selects the new layout function,it opens a new section where there are all the layouts, then chooses a layout from the ones proposed. The software then loads that layout: setting the blocks in the new positions and resetting the move counter |
| Alternative Flow     | If the player doesn't select any layout nothing happens                                                                                                                                                                                                      |
| Special Requirements | There must be a connection with the Layout Database                                                                                                                                                                                                          |
| Pre Conditions       | The program is running                                                                                                                                                                                                                                       |
| Post Conditions      | All blocks are set so that the disposition is equal to the one chosen by the player and sets the move counter to zero                                                                                                                                        |


## Use case #10

| Section              | Section Description                                                                                                                                              |
|:---------------------|:-----------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Use Case             | Start a new game                                                                                                                                                 |
| Actors               | Player                                                                                                                                                           |
| Description          | The game board is reset to the original disposition                                                                                                              |
| Flow of events       | The player selects the new game function and all blocks return to their original position and reset the move counter making the undo and redo functions unusable |
| Alternative Flow     | None                                                                                                                                                             |
| Special Requirements | None                                                                                                                                                             |
| Pre Conditions       | The program is running                                                                                                                                           |
| Post Conditions      | All blocks are set to their original position and the game counter is set to zero                                                                                |


## Use case #11

| Section              | Section Description                                                                                                |
|:---------------------|:-------------------------------------------------------------------------------------------------------------------|
| Use Case             | Quit                                                                                                               |
| Actors               | Player                                                                                                             |
| Description          | Closes the interface and stops the software                                                                        |
| Flow of events       | The Player select the quit function and the interface closes without saving anything and the program stops running |
| Alternative Flow     | None                                                                                                               |
| Special Requirements | None                                                                                                               |
| Pre Conditions       | The program is running                                                                                             |
| Post Conditions      | The program is not running                                                                                         |
