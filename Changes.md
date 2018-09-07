  Changes
---------

Info is from engineering notebook

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
