# Version: 6.0.0
* An overhaul of the performance moderator functions (PMFs) functionality

# Version: 5.0.0
* Initial version that allows the use of performance moderator functions (PMFs)

# Version: 4.1.0
* Deprecated some methods to be consistent when using `UniqueIdentifier` instead of the actual class
* Added an event-based notification interface that can be used for push updates of changes to the organization model

# Version: 4.0.0
* Encapsulated the notion of identifiers
* Added the ability to load and initialize an organization model from a given XML file
* Updated collection to be thread-safe
* Relations management have been improved
* The `toString()` method for the following classes have been updated for cleaner output:
    * `Assignment`
    * `InstanceGoalImpl`
* The following methods have been renamed:
    * `getAssignmentsByAgent()` to `getAssignments()`
    * `getGoalType()` to `getSpecificationGoal()`
* Improved `StringIdentifier` class for thread-safety
* The `equals()` and `hashCode()` methods have been added to the following classes:
    * `AgentImpl`
    * `CapabilityImpl`
    * `InstanceGoalImpl`
    * `RoleImpl`
    * `SpecificationGoalImpl`
* Visualization code have been updated to be more robust
* Layout of visualization panels have been improved
* `SimpleGoal` is no longer used in visualization code
* Beautified the output of `DetailedInformationPanel`
* A new interface, `RoleCapabilityFunction`, has been extracted for the `rcf()` method
* An interface has been extracted from `Organization`, which is now the interface; the implementation class is called `OrganizationImpl`
* All classes that use Java Generics in the declaration have been removed 
* `asserts` statements have been replaced with code to throw `IllegalArgumentException`
* Methods that were previously marked as deprecated have been removed

# Version: 3.4.1
* Generic method signatures has changed in a number of classes; e.g. `void method(C<?> p)` to  `<T> void method(C<T> p)`
* Extracted interfaces for the following classes:
    * `SimpleAgent` (interface) to `SimpleAgentImpl`
    * `SimpleCapability` to `SimpleCapabilityImpl`
    * `SimpleGoal` to `SimpleGoalImpl`
    * `SimpleRole` to `SimpleRoleImpl`
    * `Agent` to `AgentImpl`;
    * `Capability` to `CapabilityImpl`
    * `Goal` to `GoalImpl`
    * `InstanceGoal` to `InstanceGoalImpl`
    * `Policy` to `PolicyImpl`
    * `Role` to `RoleImpl`
* Removed the interface definition for obtaining a `InstanceGoal`
* `GoaImpl` no longer contains a set of `InstanceGoal`
* Added properties files so that the project can be an Eclipse Plug-In
* Renamed class `Goal` to `SpecificationGoal`, `GoalImpl` to `SpecificationGoalImpl`
* Renamed method `getParent()` from `InstanceGoal` class to `getGoalType()`
* Renamed class `GoalFactory` to `InstanceGoalFactory`, `GoalFactoryImpl` to `InstanceGoalFactoryImpl`
* Removed the `AgentType` generic from the `Agent` interface as well as other appropriate classes
* Changed the generic definition of the `achieves()` method
* The `getName()` method from the `SimpleAgent`, `SimpleCapability`, `SimpleGoal`, `SimpleRole`, `Organization`, `Assignment` have been renamed to `getIdentifier()`
* `getInstanceName()` from `InstanceGoal` renamed to `getCompleteIdentifier()`
* Added convenience method `getAssignmentsByAgent()`
* Exposed the `getAchievesSet()` method in the `Role` interface
* Added a visualization package for the entities in the organization model
* Added a convenience method `getSpecificationIdentifer()` to InstanceGoal
* Factory method `setFactory()` has been removed from interface definition and made a static method in the implementation class
* Added convenience method `removeActiveGoals()` to remove a set of goals

# Version: 3.4.0
* All the relation classes (`AchievesRelation`, `CapableRelation`, and `PossessesRelation`) are now hidden except for the minimum/maximum score

# Version: 3.3.0
* Added simplified versions the following organizational entities:
    * `Agent`: `SimpleAgent`
    * `Capability`: `SimpleCapability`
    * `Goal`: `SimpleGoal`
    * `Role`: `SimpleRole`
* The `DefaultGoalFactory` class has been renamed to `GoalFactoryImpl`

# Version: 3.2.1
* Fixed a bug in the `rcf()` method of the `Role` class, where integer division is used instead of double division
* The `oaf()` method has been extracted into an interface, `OrganizationAssignmentFunction`
* Modified the `Goal` and `InstanceGoal` classes so that they are easier to extend

# Version: 3.2.0
* Removed generic <T> from the `Goal` class
* Added the `InstanceGoal` class to represent a concrete instance of a `Goal`
* Renamed the `PotentialRelation` class back to `Assignment`
* Added a factory interface and an implementation for creating instances of the `Goal` class

# Version: 3.1.0
* Split the policy functionality into a different project
* The following classes have been removed:
    * `AssignmentPolicy`
    * `BehaviouralPolicy`
    * `StructuralPolicy`
    * `EveryCapabilityRequiredByRolesExistsWithinTheCapabilitySet`
    * `EveryGoalIsAchievedByAtLeastOneRole`
    * `EveryRoleRequiresAtLeastOneCapability`
* New methods have been added to the following classes:
    * `Agent` class
        * `getCapableOf()`
        * `getCapableOfSet()`
        * `addCapableOf()`
        * `addCapableOfSet()`
        * `removeCapableOf()`
        * `clearCapableOfSet()`
    * `Role` class
        * `getCapableBy()`
        * `getCapableBySet()`
        * `addCapableBy()`
        * `removeCapableBy()`
    * `PossessesRelation` class
        * `updateScore()`
* The `isValid()` method has been updated reflect removal of structural policies

# Version: 3.0.1
* Added missing code to `isValid()` method

# Version: 3.0.0
* Split the `GoalTree` functionality into a different project
* Split the utilities code into a different project
* The `Goal` class have been added with the following functionality:
    * `getAchievedBy()`
    * `getAchievedBySet()`
    * `addAchievedBy()`
    * `removeAchievedBy()`
* The following classes have been removed:
    * `ScoreNode`
    * `ThereIsExactlyOneTopLevelGoal`
    * `ThereAreNoLoopsInTheGoalTree`
    * `EveryRelatedRelationIsSymmetric`
    * `EachSubgoalHasExactlyOneParent`
    * `GoalLeaf`
    * `Event`
* The following classes have been renamed:
    * `Achieves` to `AchievesRelation`
    * `Capable` to `CapableRelation`
    * `Possesses` to `PossessesRelation`
    * `Potential` to `PotentialRelation`
    * `Assignment` to `Potential`
    * `EveryLeafGoalIsAchievedByAtLeastOneRole` to `EveryGoalIsAchievedByAtLeastOneRole`
* The following methods have been removed:
    * `Role` class:
        * `getRelated()`
        * `getRelatedSet()`
        * `addRelated()`
        * `addRelatedSet()`
        * `removeRelated()`
        * `clearRelatedSet()`
    * `Organization` class:
        * `getAchieves(String)`
        * `getAchievesSet()`
        * `addAchieves(Achieves)`
        * `addAchievesSet(Collection<Achieves>)`
        * `removeAchieves(Achieves)`
        * `clearAchievesSet()`
        * `getCapable(String)`
        * `getCapableSet()`
        * `addCapable(Capable)`
        * `addCapableSet(Collection<Capable>)`
        * `removeCapable(Capable)`
        * `clearCapableSet()`
        * `getPossesses(String)`
        * `getPossessesSet()`
        * `addPossesses(Possesses)`
        * `addPossessesSet(Collection<Possesses>)`
        * `removePossesses(Possesses)`
        * `clearPossessesSet()`
        * `getPotential(String)`
        * `getPotentialSet()`
        * `addPotential(Potential)`
        * `addPotentialSet(Collection<Potential>)`
        * `removePotential(Potential)`
        * `clearPotentialSet()`
        * `isViable()`
        * `oaf(Goal<?>)`
 * The following methods in the `Organization` class have been renamed:
    * `getGoalSet()` to `getGoals()`
    * `addGoalSet()` to `addGoals()`
    * `clearGoalSet()` to `clearGoals()`
    * `getRoleSet()` to `getRoles()`
    * `addRoleSet()` to `addRoles()`
    * `clearRoleSet()` to `clearRoles()`
    * `getAgentSet()` to `getAgents()`
    * `addAgentSet()` to `addAgents()`
    * `clearAgentSet()` to `clearAgents()`
    * `getCapabilitySet()` to `getCapabilities()`
    * `addCapabilitySet()` to `addCapabilities()`
    * `clearCapabilitySet()` to `clearCapabilities()`
    * `getPolicySet()` to `getPolicies()`
    * `addPolicySet()` to `addPolicies()`
    * `clearPolicySet()` to `clearPolicies()`
    * `getAssignmentSet()` to `getAssignments()`
    * `addAssignmentSet()` to `addAssignments()`
    * `clearAssignmentSet()` to `clearAssignments()`

# Version: 2.0.0
* Major overhaul

# Version: 1.0.0
* Initial Release