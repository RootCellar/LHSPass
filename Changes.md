
Changes - OLD
---------

Info is from engineering notebook, and is summarized.

Notebook itself may be more up-to-date.

Development started: 8/25/18

File Created: 9/6/18

8/25/18
-

- Enum of Permissions
- Created PermissionList class to handle permissions

8/26/18
-

- Idea: Only allow admins to give permissions that they have (eg. don't let an admin without USER_MODIFY give it to someone else)

8/27/18
-

- Created class UserFileHandler
- Uses Scanner to read permissions and tag info
- Use FileWriter to write permissions and tag info back to files
- Has a static method that reads and returns all user data
- Has a static final boolean DEBUG variable that says whether or not to print debug info
- Uses name format FIRSTNAME_LASTNAME

9/1/18 - 9/3/18
-

- Class GUIHandler. Has an enum of States.
- Constructor: State(Menu m)
- Keeps track of GUI states and has the menu
- When changing states, class closes other windows, then opens the window for the new state.
- Storing menus in the State Enum allows for easily looping through the states, obtaining their menus, and managing them.
- Enum allows for easy ifs such as:

If( State.equals( State.DEBUG ) ) {

  //Do the stuff
  
}
