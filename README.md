# womh

> What is this?

An implementation of the game *Werewolves of Millers Hollow* as a Spigot-Plugin

> **Note:**
> 
> This Plugin is still in a very early phase of developement, so it is not guaranteed it will work at it's current state.

#### Implemented Features

* Create a game and invite players

#### Yet to be implemented

* Core
  * Create a new Game & invite People
  * Add required Roles
    * Ordinary Townsfolk (Well, just to have everything here)
    * Werewolfes
    * Seer
    * Witch
    * Hunter
    * Cupid
  * Add optional/ additional Roles
    * Thief
    * Little Girl (how would this work?)
  * Add Status of Sherrif
* Game Mechanics
  * Add Day/ Night-Cycle (easiest would be blindness i guess?)
  * Nightphase
    * Seer gains Knowledge whether a Player is a werewolf or not
    * Werewolfes choose someone to die
    * Witch is told who will die and can use one or both of her potions
    * Cupid can select the two Lovers
  * Dayphase
    * Working Voting on who should die (Major decides if it is a draw)
    * "Working Death" <- Some cool last words, dying in an explosion while levitating up?
    * Passing the "Role" of the Major to someone else
    * After-Death Actions:
      * One Lover dies with the other
      * Hunter can choose who to take with him to the dead.
  *  The Plugin is able to check if the game is already over (e.g. if after voting 2 werewolfes and two townspeople are left, the game is over most of the time)
  
If you have any Suggestions on how to improve this or ideas on functionality to be added feel free to open up an issue or [send me a mail](mailto:cinex.mail@web.de).
