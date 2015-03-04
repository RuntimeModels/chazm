# Version: 4.1.0
* Deprecated some methods to be consistent with using UniqueIdentifier instead of the actual class.
* Added an event-based notification interface that can be used for push updates of changes to the organization model.

# Version: 4.0.0
* Encapsulated the notion of identifiers.
* Ability to load initialize the organization model with a given xml file.
* Updated collection to be thread-safe.
* Modified and added a number of methods for dealing with relations.
* Updated the `Assignment` class for a cleaner `toString()` output.
* Updated `InstanceGoalImpl` class for a cleaner `toString()` output.
* Removed `SimpleGoal` uses from visualization code.
* Beautified the output of `DetailedInformationPanel`.
* Improved `StringIdentifier` class for thread-safety.
* Improved the layout of the visualization panels.
* Renamed the `getAssignmentsByAgent(...)` to `getAssignments()`.
* Added the `equals()` and `hashCode()` methods to the classes: `AgentImpl`, `CapabilityImpl`, `InstanceGoalImpl`, `RoleImpl`, and `SpecificationGoalImpl`. These `equals()` and `hashCode()` method simply uses the result from their super class.
* Updated visualization code to be more robust.
* Extracted an interface for the `rcf()` function called `RoleCapabilityFunction`.
* Added a series of extra methods to the `Organization` class.
* Deprecated methods have been removed.
* Renamed `getGoalType()` method to `getSpecificationGoal()` method.
* Asserts in `AgentImpl` converted to `IllegalArgumentException`s.
* Extracted interface from `Organization` class.
<<<<<<< HEAD
    * `Organization` class is renamed to `OrganizationImpl`.
    * Interface is called `Organization`.
=======
  * `Organization` class is renamed to `OrganizationImpl`.
  * Interface is called `Organization`.
>>>>>>> 32c630f28cd3000252e23e35cc3a013f931f03f9
* Removed all Java generics.
* Replaced most asserts with `IllegalArgumentException`.

# Version: 3.4.1
* Generic method signatures has changed in a number of files. e.g. `void method(C<?> p)` ->  `<T> void method(C<T> p)`
* Updated existing code that utilize the changed methods
* Constructors also follows the new way 
* Extracted interfaces for the following classes: `SimpleAgent` (interface) -> `SimpleAgentImpl`; `SimpleCapability` -> `SimpleCapabilityImpl`; `SimpleGoal` -> `SimpleGoalImpl`; `SimpleRole` -> `SimpleRoleImpl`; `Agent` -> `AgentImpl`;
  Capability -> CapabilityImpl; interface = Capability
  Goal -> GoalImpl; interface = Goal
  InstanceGoal -> InstanceGoalImpl; interface = InstanceGoal
  Policy -> PolicyImpl; interface = Policy
  Role -> RoleImpl; interface = Role
* Removed the interface definition for obtaining the InstanceGoal
  GoaImpl no longer contains a set of InstanceGoal
* Added properties files so that the project can be an Eclipse Plug-In
* Renamed class Goal to SpecificationGoal, GoalImpl to SpecificationGoalImpl
* Renamed method getParent() from InstanceGoal class getGoalType()
* Renamed class GoalFactory to InstanceGoalFactory, GoalFactoryImpl to
  InstanceGoalFactoryImpl
* Removed the AgentType generic from the Agent interface as well as other
  appropriate classes
* Changed the generic definition of the achieves() methods
* All getName() method from the SimpleAgent, SimpleCapability, SimpleGoal,
  and SimpleRole has been renamed to getIdentifier()
* getInstanceName() from InstanceGoal renamed to getCompleteIdentifier()
* getName() from Organization, Assignment renamed to getIdentifier() for
  consistency
* Added convenience method getAssignmentsByAgent()
* Exposed the getAchievesSet() method in the Role interface
* Added a visualization package for the entities in the organization model
* Added a convenience method getSpecificationIdentifer() to InstanceGoal
* Factory method setFactory() has been removed from interface definition
  and made a static method in the implementation class
* Added convenience method removeActiveGoals() to remove a set of goals

# Version: 3.4.0
* All the relation classes (AchievesRelation.java, CapableRelation.java,
  PossessesRelation.java) are now hidden except for the min/max score;
  all related classes have been updated to reflect the change

# Version: 3.3.0
* Introduced a simplified version of the basic entities for use
* DefaultGoalFactory.java renamed to GoalFactoryImpl.java

# Version: 3.2.1
* Fixed bug in Role.java rcf function that uses integer division instead of
  double
* Added final keyword to parameters, local variables, fields when possible
* Corrected some generics
* Corrected some double division, they were casted to int with int results
* Extracted OrganizationAssignmentFunction from Organization as an interface
* Modifed InstanceGoal and Goal class to allow for easier use by subclasses

# Version: 3.2.0
* Goal.java
  - Removed generic <T> from Goal.java
  - Field parameter moved to InstanceGoal.java
+ InstanceGoal.java
  + InstanceGoal functions as a concrete instance of the Goal class and is used
    for the active goal set
- PotentialRelation.java
  - Class is renamed to Assignment.java
+ Factory.java
  + New interface for instantiating InstanceGoal.java
+ DefaultFactory.java
  + A default factory that implements Factory.java

# Version: 3.1.0
* Agent.java
  + Field capableOf added
    + Method getCapableOf(String) added
    + Method getCapableOfSet() added
    + Method addCapableOf(CapableRelation) added
    + Method addCapableOfSet(Collection<CapableRelation>) added
    + Method removeCapableOf(CapableRelation) added
    + Method clearCapableOfSet() added
* Role.java
  + Field capableBy added
    + Method getCapableBy(String) added
    + Method getCapableBySet() added
    + Method addCapableBy(CapableRelation) added
    + Method removeCapableBy(CapableRelation) added
* PossessesRelation.java
  + Method updateScore(double) added
* Organization.java
  * Method isValid() updated to reflect removal of structural policies
- Package edu.ksu.cis.macr.organization.model.policies removed
  - AssignmentPolicy.java removed
  - BehaviouralPolicy.java removed
  - StructuralPolicy.java removed
- Package edu.ksu.cis.macr.organization.model.policies.structural removed
  - EveryCapabilityRequiredByRolesExistsWithinTheCapabilitySet.java removed
  - EveryGoalIsAchievedByAtLeastOneRole.java removed
  - EveryRoleRequiresAtLeastOneCapability.java removed

# Version: 3.0.1
* Organization.java
  * Added missing code from isValid() method

# Version: 3.0.0
* Initial Release
** Overall Changes **
  - Detached the Goal Tree structure from OrganizationModel
  - Package edu.ksu.cis.macr.organization.model.utils moved to a different
    project, OrganizationModelUtilities
- Event.java removed
- GoalLeaf.java removed
  * Field 'achievedBy' moved to Goal.java
  * Method getAchievedBy(String) moved to Goal.java
  * Method getAchievedBySet() moved to Goal.java
  * Method addAchievedBy(Role) moved to Goal.java
  * Method removeAchievedBy(Role) moved to Goal.java
- Assignment.java replaced by Potential.java
  * Potential.java has the same functionality
- EachSubgoalHasExactlyOneParent.java removed
- EveryRelatedRelationIsSymmetric.java removed
- ThereAreNoLoopsInTheGoalTree.java removed
- ThereIsExactlyOneTopLevelGoal.java removed
- ScoreNode.java removed
* Goal.java
  - Removed most functionality except for basic functionality
  + Field 'identifier' added
  + New constructor Goal(String, String) added
* EveryLeafGoalIsAchievedByAtLeastOneRole.java renamed to
  EveryGoalIsAchievedByAtLeastOneRole.java
* Role.java
  - Method getRelated(String) removed
  - Method getRelatedSet() removed
  - Method addRelated(Role) removed
  - Method addRelatedSet(Collection<Role>) removed
  - Method removeRelated(Role) removed
  - Method clearRelatedSet() removed
* Organization.java
  * Field assignmentSet renamed to assignments
  - Field achievesMap removed
  - Field capables removed
  - Field possessesMap removed
  - Field potentials removed
  * Method getGoalSet() renamed to getGoals()
  * Method addGoalSet(Collection<Goal>) renamed to addGoals(Collection<Goal>)
  * Method clearGoalSet() renamed to clearGoals()
  * Method getRoleSet() renamed to getRoles()
  * Method addRoleSet(Collection<Role>) renamed to addRoles(Collection<Role>)
  * Method clearRoleSet() renamed to clearRoles()
  * Method getAgentSet() renamed to getAgents()
  * Method addAgentSet(Collection<Agent>) renamed to
    addAgents(Collection<Agent>)
  * Method clearAgentSet() renamed to clearAgents()
  * Method getCapabilitySet() renamed to getCapabilities()
  * Method addCapabilitySet(Collection<Capability>) renamed to
    addCapabilities(Collection<Capability>)
  * Method clearCapabilitySet() renamed to clearCapabilities()
  * Method getPolicySet() renamed to getPolicies()
  * Method addPolicySet(Collection<Policy>) renamed to
    addPolicies(Collection<Policy>)
  * Method clearPolicySet() renamed to clearPolicies()
  * Method getAssignmentSet() renamed to getAssignments()
  * Method addAssignmentSet(Collection<Potential>) renamed to
    addAssignments(Collection<Potential>)
  * Method clearAssignmentSet() renamed to clearAssignments()
  - Method getAchieves(String) removed
  - Method getAchievesSet() removed
  - Method addAchieves(Achieves) removed
  - Method addAchievesSet(Collection<Achieves>) removed
  - Method removeAchieves(Achieves) removed
  - Method clearAchievesSet() removed
  - Method getCapable(String) removed
  - Method getCapableSet() removed
  - Method addCapable(Capable) removed
  - Method addCapableSet(Collection<Capable>) removed
  - Method removeCapable(Capable) removed
  - Method clearCapableSet() removed
  - Method getPossesses(String) removed
  - Method getPossessesSet() removed
  - Method addPossesses(Possesses) removed
  - Method addPossessesSet(Collection<Possesses>) removed
  - Method removePossesses(Possesses) removed
  - Method clearPossessesSet() removed
  - Method getPotential(String) removed
  - Method getPotentialSet() removed
  - Method addPotential(Potential) removed
  - Method addPotentialSet(Collection<Potential>) removed
  - Method removePotential(Potential) removed
  - Method clearPotentialSet() removed
  - Method isViable() removed
  - Method oaf(Goal<?>) removed
* Achieves.java renamed to AchievesRelation.java
* Capable.java renamed to CapableRelation.java
* Possesses.java renamed to PossessesRelation.java
* Potential.java renamed to PotentialRelation.java

# Version: 2.0.0
* Initial Release

# Version: 1.0.0
* Initial Release