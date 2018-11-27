package runtimemodels.chazm.api

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import runtimemodels.chazm.api.entity.*
import runtimemodels.chazm.api.function.Goodness
import runtimemodels.chazm.api.id.UniqueId
import runtimemodels.chazm.api.relation.Assignment
import java.util.function.Predicate

internal class OrganizationTest {

    @ParameterizedTest
    @CsvSource("true", "true", "false", "true", "false")
    fun isValid(result: Boolean) {
        val organization = object : Organization {
            override fun addUses(roleId: UniqueId<Role>, pmfId: UniqueId<Pmf>) {

            }

            override fun getUses(id: UniqueId<Role>): Set<Pmf>? {
                return null
            }

            override fun getUsedBy(id: UniqueId<Pmf>): Set<Role>? {
                return null
            }

            override fun removeUses(roleId: UniqueId<Role>, pmfId: UniqueId<Pmf>) {

            }

            override fun removeAllUses() {

            }

            override fun addRequires(roleId: UniqueId<Role>, capabilityId: UniqueId<Capability>) {

            }

            override fun getRequires(id: UniqueId<Role>): Set<Capability>? {
                return null
            }

            override fun getRequiredBy(id: UniqueId<Capability>): Set<Role>? {
                return null
            }

            override fun removeRequires(roleId: UniqueId<Role>, capabilityId: UniqueId<Capability>) {

            }

            override fun removeAllRequires() {

            }

            override fun addPossesses(agentId: UniqueId<Agent>, capabilityId: UniqueId<Capability>, score: Double) {

            }

            override fun getPossesses(id: UniqueId<Agent>): Set<Capability>? {
                return null
            }

            override fun getPossessedBy(id: UniqueId<Capability>): Set<Agent>? {
                return null
            }

            override fun getPossessesScore(agentId: UniqueId<Agent>, capabilityId: UniqueId<Capability>): Double {
                return 0.0
            }

            override fun setPossessesScore(agentId: UniqueId<Agent>, capabilityId: UniqueId<Capability>, score: Double) {

            }

            override fun removePossesses(agentId: UniqueId<Agent>, capabilityId: UniqueId<Capability>) {

            }

            override fun removeAllPossesses() {

            }

            override fun addNeeds(roleId: UniqueId<Role>, attributeId: UniqueId<Attribute>) {

            }

            override fun getNeeds(id: UniqueId<Role>): Set<Attribute>? {
                return null
            }

            override fun getNeededBy(id: UniqueId<Attribute>): Set<Role>? {
                return null
            }

            override fun removeNeeds(roleId: UniqueId<Role>, attributeId: UniqueId<Attribute>) {

            }

            override fun removeAllNeeds() {

            }

            override fun addModerates(pmfId: UniqueId<Pmf>, attributeId: UniqueId<Attribute>) {

            }

            override fun getModerates(id: UniqueId<Pmf>): Attribute? {
                return null
            }

            override fun getModeratedBy(id: UniqueId<Attribute>): Set<Pmf>? {
                return null
            }

            override fun removeModerates(pmfId: UniqueId<Pmf>, attributeId: UniqueId<Attribute>) {

            }

            override fun removeAllModerates() {

            }

            override fun addHas(agentId: UniqueId<Agent>, attributeId: UniqueId<Attribute>, value: Double) {

            }

            override fun getHas(id: UniqueId<Agent>): Set<Attribute>? {
                return null
            }

            override fun getHadBy(id: UniqueId<Attribute>): Set<Agent>? {
                return null
            }

            override fun getHasValue(agentId: UniqueId<Agent>, attributeId: UniqueId<Attribute>): Double? {
                return null
            }

            override fun setHasValue(agentId: UniqueId<Agent>, attributeId: UniqueId<Attribute>, value: Double) {

            }

            override fun removeHas(agentId: UniqueId<Agent>, attributeId: UniqueId<Attribute>) {

            }

            override fun removeAllHas() {

            }

            override fun addContains(roleId: UniqueId<Role>, characteristicId: UniqueId<Characteristic>, value: Double) {

            }

            override fun getContains(id: UniqueId<Role>): Set<Characteristic>? {
                return null
            }

            override fun getContainedBy(id: UniqueId<Characteristic>): Set<Role>? {
                return null
            }

            override fun getContainsValue(roleId: UniqueId<Role>, characteristicId: UniqueId<Characteristic>): Double? {
                return null
            }

            override fun setContainsValue(roleId: UniqueId<Role>, characteristicId: UniqueId<Characteristic>, value: Double) {

            }

            override fun removeContains(roleId: UniqueId<Role>, characteristicId: UniqueId<Characteristic>) {

            }

            override fun removeAllContains() {

            }

            override fun addAssignment(assignment: Assignment) {

            }

            override fun addAssignments(assignments: Collection<Assignment>) {

            }

            override fun getAssignment(id: UniqueId<Assignment>): Assignment? {
                return null
            }

            override fun getAssignments(): Set<Assignment>? {
                return null
            }

            override fun getAssignmentsByAgent(id: UniqueId<Agent>): Set<Assignment>? {
                return null
            }

            override fun removeAssignment(id: UniqueId<Assignment>) {

            }

            override fun removeAssignments(ids: Collection<UniqueId<Assignment>>) {

            }

            override fun removeAllAssignments() {

            }

            override fun addAchieves(roleId: UniqueId<Role>, goalId: UniqueId<SpecificationGoal>) {

            }

            override fun getAchieves(id: UniqueId<Role>): Set<SpecificationGoal>? {
                return null
            }

            override fun getAchievedBy(id: UniqueId<SpecificationGoal>): Set<Role>? {
                return null
            }

            override fun removeAchieves(roleId: UniqueId<Role>, goalId: UniqueId<SpecificationGoal>) {

            }

            override fun removeAllAchieves() {

            }

            override fun addSpecificationGoal(goal: SpecificationGoal) {

            }

            override fun addSpecificationGoals(goals: Collection<SpecificationGoal>) {

            }

            override fun getSpecificationGoal(id: UniqueId<SpecificationGoal>): SpecificationGoal? {
                return null
            }

            override fun getSpecificationGoals(): Set<SpecificationGoal>? {
                return null
            }

            override fun removeSpecificationGoal(id: UniqueId<SpecificationGoal>) {

            }

            override fun removeSpecificationGoals(ids: Collection<UniqueId<SpecificationGoal>>) {

            }

            override fun removeAllSpecificationGoals() {

            }

            override fun addRole(role: Role) {

            }

            override fun addRoles(roles: Collection<Role>) {

            }

            override fun getRole(id: UniqueId<Role>): Role? {
                return null
            }

            override fun getRoles(): Set<Role>? {
                return null
            }

            override fun removeRole(id: UniqueId<Role>) {

            }

            override fun removeRoles(ids: Collection<UniqueId<Role>>) {

            }

            override fun removeAllRoles() {

            }

            override fun addPolicy(policy: Policy) {

            }

            override fun addPolicies(policies: Collection<Policy>) {

            }

            override fun getPolicy(id: UniqueId<Policy>): Policy? {
                return null
            }

            override fun getPolicies(): Set<Policy>? {
                return null
            }

            override fun removePolicy(policyId: UniqueId<Policy>) {

            }

            override fun removePolicies(ids: Collection<UniqueId<Policy>>) {

            }

            override fun removeAllPolicies() {

            }

            override fun addPmf(pmf: Pmf) {

            }

            override fun addPmfs(pmfs: Collection<Pmf>) {

            }

            override fun getPmf(id: UniqueId<Pmf>): Pmf? {
                return null
            }

            override fun getPmfs(): Set<Pmf>? {
                return null
            }

            override fun removePmf(id: UniqueId<Pmf>) {

            }

            override fun removePmfs(ids: Collection<UniqueId<Pmf>>) {

            }

            override fun removeAllPmfs() {

            }

            override fun addInstanceGoal(goal: InstanceGoal) {

            }

            override fun addInstanceGoals(goals: Collection<InstanceGoal>) {

            }

            override fun getInstanceGoal(id: UniqueId<InstanceGoal>): InstanceGoal? {
                return null
            }

            override fun getInstanceGoals(): Set<InstanceGoal>? {
                return null
            }

            override fun removeInstanceGoal(id: UniqueId<InstanceGoal>) {

            }

            override fun removeInstanceGoals(ids: Collection<UniqueId<InstanceGoal>>) {

            }

            override fun removeAllInstanceGoals() {

            }

            override fun addCharacteristic(characteristic: Characteristic) {

            }

            override fun addCharacteristics(characteristics: Collection<Characteristic>) {

            }

            override fun getCharacteristic(id: UniqueId<Characteristic>): Characteristic? {
                return null
            }

            override fun getCharacteristics(): Set<Characteristic>? {
                return null
            }

            override fun removeCharacteristic(id: UniqueId<Characteristic>) {

            }

            override fun removeCharacteristics(ids: Collection<UniqueId<Characteristic>>) {

            }

            override fun removeAllCharacteristic() {

            }

            override fun addCapability(capability: Capability) {

            }

            override fun addCapabilities(capabilities: Collection<Capability>) {

            }

            override fun getCapability(id: UniqueId<Capability>): Capability? {
                return null
            }

            override fun getCapabilities(): Set<Capability>? {
                return null
            }

            override fun removeCapability(id: UniqueId<Capability>) {

            }

            override fun removeCapabilities(ids: Collection<UniqueId<Capability>>) {

            }

            override fun removeAllCapabilities() {

            }

            override fun addAttribute(attribute: Attribute) {

            }

            override fun addAttributes(attributes: Collection<Attribute>) {

            }

            override fun getAttribute(id: UniqueId<Attribute>): Attribute? {
                return null
            }

            override fun getAttributes(): Set<Attribute>? {
                return null
            }

            override fun removeAttribute(id: UniqueId<Attribute>) {

            }

            override fun removeAttributes(ids: Collection<UniqueId<Attribute>>) {

            }

            override fun removeAllAttributes() {

            }

            override fun addAgent(agent: Agent) {

            }

            override fun addAgents(agents: Collection<Agent>) {

            }

            override fun getAgent(id: UniqueId<Agent>): Agent? {
                return null
            }

            override fun getAgents(): Set<Agent>? {
                return null
            }

            override fun removeAgent(id: UniqueId<Agent>) {

            }

            override fun removeAgents(ids: Collection<UniqueId<Agent>>) {

            }

            override fun removeAllAgents() {

            }

            override fun effectiveness(assignments: Collection<Assignment>): Double {
                return 0.0
            }

            override fun getGoodness(id: UniqueId<Role>): Goodness? {
                return null
            }

            override fun setGoodness(id: UniqueId<Role>, goodness: Goodness) {

            }
        }
        val predicate = Predicate<Organization> { result }

        assertThat(organization.isValid(predicate)).isEqualTo(result)
    }

}
