#nerd-dinner

###Functional Requirements
1. Nerd can sign up for site. The following information will be provided by the nerd
    1. Name (Required)
    2. Addresses (Optional)
    3. Phone (Optional)
    4. Emails (Required)
    5. Photo (Optional)
    6. Password (Required)
    7. Password must be encrypted at rest and not be transmitted

2. Nerd can reset password
    1. Provide password and reset link will be sent to their email address

3. Site must have support for Internationalization in the following locales
    1. English United States
    2. English United Kingdom
    3. Spanish Spain
    4. Spanish Mexico

3. Nerd can create dinners
    1. Dinner title
    2. Address (Required)
    3. Date/Time
    4. Max attendees
    5. Dinner type Open/Closed

4. Nerds can invite other nerd to dinner
    1. Can filter existing nerd on site
    2. Only the creator of a dinner can invite other nerd to that dinner
    3. Can provide email address, first and last name of other nerd. Account will be created and email invite sent to the new nerd with password reset requirements

5. Nerds can search for dinners
   1. Can filter by zip code, state, city
   2. Only Open dinners will be displayed

6. Nerds can register for a dinner
   1. Dinner slots are allocated on a first come - first served method
   2. If the dinner is full they will be added to the wait-list.

7. Nerds can find dinners they have reserved
   1. Past dinners will be displayed in a separate section
   2. Future dinners will be displayed in soonest to latest order. They can cancel their reservation for the dinner here.

###Nonfunctional/Technical requirements
1. Consists of the following components...
    1. Spring boot web project that would be the main site
        1. Thymeleaf is used for site wide styling and simple pages
        2. Angular applications will be used for complex pages
    2. Spring boot web project that would be the API for the site
    3. Database migration project using flyway for database releases

2. Should support the following environments...
    1. Local debugging using IntelliJ IDEA
        1. Database would be local h2 instance
    1. DEV environment hosted on local network
        1. Web server would be embedded tomcat server
        2. Database server is locally hosted Oracle instance

3. Should have CI tooling setup using Jenkins and Jenkins Pipeline.
    1. CI build that can be queued on demand to deploy to DEV environment
        1. Will deploy database, web application, and API
    2. Release build that will deploy to Production.
    3. Release builds should be versioned with build number and GIT revision number

4. Should have unit tests for business layer of application and database integration tests using local or in memory database

5. Logging should be captured on the file system