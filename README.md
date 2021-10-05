# GUITAR CUSTOM SHOP #
An application that helps to organize a workflow of a guitar custom shop. It provides tools for employees to manage the 
site (e.g. overview and change all data models, track client orders and notify users about progress on those orders)
and for clients to use it (e.g. assembling their guitars from given parts, receiving emails on orders progress, managing
their profiles and more).

Application Role | Description
--- | ---
Guest | Unauthenticated user
Not confirmed user | Authenticated user without confirmed email that has reduced access rights
Client | Authenticated user
Maker | Authenticated user
Admin | Authenticated user with it's own exclusive rights and an access to pages dedicated to any other role

### Guest ###
* Has access to home page
* Can register on site and receiving confirmation link for registered profile via email
* Login on site and using forgot password function which helps to restore password to an account via registered email
* Change application language

### Not confirmed user ###
* Has access to home and profile pages
* Can use existing confirmation link to finish registration and acquire full assigned role access
* View and change profile info:
    * Change email
    * Change login
    * Change password
* Resend and receive confirmation link to current email specified in user profile
* Change application language

### Client ###
* Has access to home, profile, guitars construction and my guitars pages
* Can view and change profile info:
    * Change email
    * Change login
    * Change password
* Construct and order a guitar:
    * Assign existing components
    * Search through some of them for ease of use
    * Creating an order on constructed guitar
* View ordered guitars:
    * Search through your orders by assigned guitar names
    * Tracking status of your order (ordered, in progress, finished)
* Receiving a notification via email about changes of order status
* Change application language

### Maker ###
* Has access to home, profile, and guitar orders pages
* Can view and change profile info:
    * Change email
    * Change login
    * Change password
* View and interact with guitar orders table:
    * Viewing existing active orders (with ordered and in progress statuses)
    * Filtering orders by criteria: name, body name, neck name, pickups name, neck joint, color, user login, order status
    * Changing "ordered" order status to "in progress" and sending an auto email notification to the client
    * Changing "in progress" orders status to "finished" after assigning a photo to the specified order  and sending
      an auto email notification to the client
* Change application language

### Admin ###
* Has access to all **Client** and **Maker** pages as well as an admin panel
* View and interact with admin panel:
    * Users
        * View users
        * Add user
        * Update user
        * Delete user and all associated guitar orders
        * Filter by criteria: id, email, login, role, status
    * Guitars
        * View guitars
        * Add guitar
        * Update guitar
        * Delete guitar
        * Filter by criteria: id, name, body name, neck name, pickups name, neck joint, color, user login, order status
    * Guitar bodies
        * View bodies
        * Add body
        * Update body
        * Delete body
        * Filter by criteria: id, name, wood
    * Guitar necks
        * View necks
        * Add neck
        * Update neck
        * Delete neck
        * Filter by criteria: id, name, neck wood, fretboard wood
    * Guitar pickups
        * View pickups
        * Add pickup
        * Update pickup
        * Delete pickup
        * Filter by criteria: id, name
    * Wood species
        * View woods
        * Add wood
        * Update wood
        * Delete wood
        * Filter by criteria: id, name
* Change application language

### Database EER Diagram ###
![Database scheme](https://raw.githubusercontent.com/ChoCoBo1712/guitar-custom-shop/master/schema/schema.png)