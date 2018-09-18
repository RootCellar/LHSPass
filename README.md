# LHSPass
LHS Pass Program
by Darian Marvel
----------------

Current state of program: Functional, ready for extensive testing.

Development started 8/25/18

This program is a rewrite of the original program.

Ideas copied from engineering notebook
--------------------------------------

- Add main loop, make it more responsive to schedule
- Many debug features
- Logs of different kinds
- Admin controls by password and/or tag
- User permissions (If a specific user uses restroom VERY frequently, allow admin to limit or revoke permission)
- Permissions to allow some users to have admin access
- Possible make system account based, allow users to set some simple settings like favorite color
- Make program responsive and user friendly
- Allow admin to change different states (end possible infinite loops, disable passes, etc.)

More ideas
----------

User setting ideas:
- Favorite color
- Title/nickname to be referred to as (format "[<title>] <name/nickname>", like "[Admin] Mr. Benshoof")
- How the program will greet you ("Salutations, <name>" or "Greetings, Sir <name>")

If a user wants a certain title/nickname, they may have to type on the keyboard and have what they type checked to make sure it isn't too long, and that it doesn't have special characters

User Permission ideas:
- Locker Permission
- Restroom permission
- Permission to go drink water? Weird to think about, but still may be necessary (Some students may abuse this privilege by walking around the school or something)
- 'Other' permission

Admin Permission ideas:
- Limit / revoke permissions of users
- Disable passes
- Change current schedule
- Ability to create user accounts
- Permission to even log in as an admin
- Ability to view logs (Debug, previous passes, other infos/numbers, logs for each user)
- Allow admin to lock program. Prevent any user access until admin swipes tag/enters a password
- Override permission (override permission limits)
- Allow admin to swipe a tag and see if it belongs to a user, otherwise just display tag serial number
- Allow admin to view user accounts and their info (settings, recent log)

Other permission ideas:
- Permission to view debug information
- Create an admin-only debug GUI that contains numbers that may be useful or is just cool to look at (With a back button of course)

GUI ideas:
- On log in, buttons to enter as user or admin. If user doesn't have permission to log in as admin, then automatically log in as user

Permissions
-----------
A permission needs to have a name, and a number. Not all permissions will use that number. The number will be for limiting how many times someone can go somewhere
in one class period. Don't allow people to use the restroom 10 times in one period, unless the admin gives the user permission (you never know, there may be special cases).

Admins should have the ability to override the permission limit, if needed. If a user is denied permission, the admin could swipe his tag/enter a password to give them permission.
