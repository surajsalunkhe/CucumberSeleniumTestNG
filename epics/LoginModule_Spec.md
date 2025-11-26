---
URL: URL:https://automationexercise.com/login
---
## EPIC: User Authentication on AutomationExercise

**As a visitor to AutomationExercise, I want to create an account and securely log in and log out, so that I can manage my shopping experience and access personalized features.**

### Goals

- Enable new users to sign up with a unique email and password
- Allow registered users to log in with valid credentials
- Provide secure session management with logout capability
- Display informative error messages for invalid login or sign-up attempts
- Prevent duplicate registration for existing accounts

### Features Included

- New user sign-up with name, email address, and password
- Email uniqueness check with error display for existing users upon registration
- Password validation mechanism (minimum length, etc.)
- Successful login redirects to user dashboard/profile
- Login error messages for incorrect credentials
- Logout functionality that ends the session and redirects to login page
- Account deletion option after login
- Feedback messages for all user actions (success/error)  
- Responsive design for desktop and mobile

### Acceptance Criteria

- Users see both login and new user signup forms on the same page.
- A new user can sign up only with a unique email, and is prompted if the email is already registered.
- Registered users can log in with their credentials and view a personalized message confirming login.
- Incorrect email/password attempts display a clear error message, and the user stays on the login page.
- Upon successful login, users can log out, which redirects them back to the login page and deletes the session.
- Users can request deletion of their account while logged in, and receive a confirmation message upon deletion.
- All forms are validated for required fields and proper input formats.
- All user actions (signup, login, logout, account deletion) return clear success or error messages.