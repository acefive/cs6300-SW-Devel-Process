# Test Changes
## AssignmentTest.java

 1.  Where appropriate, I took out fake data creation in the setup() and replaced it with the actual processing of GradesDB so I could test with real data
 2.  Took out all references to fake data and replaced with references to real data
 3.  Renamed method from testGetProjDescription() to testGetAssignmentDescription() - the method name now correctly reflects its purpose

## AssignmentsTest.java

 1.  In testRemoveAssignment() & testAssignmentListRefresh() I removed extraneous variables and refined the test logic.
 2.  They do the same thing but without the extra vars and processing

## ProjectsTest.java

 1.  In testprojListRefresh() I removed extraneous variables and refined the test logic.


## TeamTests.java

 1. Changed function getTeamByName() to getTeam(int, int). Updated tests to reflect this.
 2. For list of strings assertion fixed bug regarding initial `|`
 3. Added null check assertions

## TeamsTests.java

 1. Changed function getTeamByName() to getTeam(int, int). Updated tests to reflect this.
 2. Added null check assertions
 3. Added two new tests

## GradesDBTest2.java

1. I moved setting up the demo student into setUp() rather than in individual tests.
2. Added tests to cover extra credit requirements.
