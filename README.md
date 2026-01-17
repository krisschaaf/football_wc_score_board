# Minimalistic Football Worldcup Score Board

### Author: Kristoffer Schaaf

Developed as part of the Sportradar technical interview.

## Deps
- Java 21
- Maven
- Junit 6.0.3

### Comitting code
- Commit messages follow the **'conventional commit'** principle for readability purposes
  (https://www.conventionalcommits.org/en/v1.0.0/)
- Commits are intentionally small at the beginning with most changes driven by one test at a time to illustrate **TDD**.

### Why no UI is implemented
This solution is implemented as a plain Java library without a user interface. 
The exercise explicitly asks for a **simple library** with an in-memory store and states that it is 
not looking for a REST API, web service or microservice.

### Requirements Engineering
To gain an initial idea of the system, a small rudimentary component diagram was created as starting point.
The final design of the system was driven by TDD.
In retrospective, this diagram helped plan the initial data structures, but had many flaws that had to be updated
during the further implementation.

### Efficiency of this system using HashMap
- **startGame(2):** insert 1 into HashMap -> O(1)
- **finishGame(2):** remove 1 from HashMap (with correct params) -> O(1) 
- **updateScore(4):** get 1 from HashMap (with correct params) -> O(1)
- **getSummary(0):** sort HashMap via TimSort -> O(n log(n))

### Documentation
[Click here](docs/class_component_diagram.png) to find the **class component diagram**.\
[Click here](docs/coveragehtmlReport/index.html) to find the **test coverage report**.

