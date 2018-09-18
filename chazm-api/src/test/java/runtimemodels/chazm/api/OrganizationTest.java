package runtimemodels.chazm.api;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import runtimemodels.chazm.api.entity.*;
import runtimemodels.chazm.api.function.Goodness;
import runtimemodels.chazm.api.id.UniqueId;
import runtimemodels.chazm.api.relation.Assignment;

import java.util.Collection;
import java.util.Set;
import java.util.function.Predicate;

import static org.assertj.core.api.Assertions.assertThat;

class OrganizationTest {

    @ParameterizedTest
    @CsvSource({"true", "true", "false", "true", "false"})
    void isValid(boolean result) {
        var organization = new Organization() {
            @Override
            public void addUses(UniqueId<Role> roleId, UniqueId<Pmf> pmfId) {

            }

            @Override
            public Set<Pmf> getUses(UniqueId<Role> id) {
                return null;
            }

            @Override
            public Set<Role> getUsedBy(UniqueId<Pmf> id) {
                return null;
            }

            @Override
            public void removeUses(UniqueId<Role> roleId, UniqueId<Pmf> pmfId) {

            }

            @Override
            public void removeAllUses() {

            }

            @Override
            public void addRequires(UniqueId<Role> roleId, UniqueId<Capability> capabilityId) {

            }

            @Override
            public Set<Capability> getRequires(UniqueId<Role> id) {
                return null;
            }

            @Override
            public Set<Role> getRequiredBy(UniqueId<Capability> id) {
                return null;
            }

            @Override
            public void removeRequires(UniqueId<Role> roleId, UniqueId<Capability> capabilityId) {

            }

            @Override
            public void removeAllRequires() {

            }

            @Override
            public void addPossesses(UniqueId<Agent> agentId, UniqueId<Capability> capabilityId, double score) {

            }

            @Override
            public Set<Capability> getPossesses(UniqueId<Agent> id) {
                return null;
            }

            @Override
            public Set<Agent> getPossessedBy(UniqueId<Capability> id) {
                return null;
            }

            @Override
            public double getPossessesScore(UniqueId<Agent> agentId, UniqueId<Capability> capabilityId) {
                return 0;
            }

            @Override
            public void setPossessesScore(UniqueId<Agent> agentId, UniqueId<Capability> capabilityId, double score) {

            }

            @Override
            public void removePossesses(UniqueId<Agent> agentId, UniqueId<Capability> capabilityId) {

            }

            @Override
            public void removeAllPossesses() {

            }

            @Override
            public void addNeeds(UniqueId<Role> roleId, UniqueId<Attribute> attributeId) {

            }

            @Override
            public Set<Attribute> getNeeds(UniqueId<Role> id) {
                return null;
            }

            @Override
            public Set<Role> getNeededBy(UniqueId<Attribute> id) {
                return null;
            }

            @Override
            public void removeNeeds(UniqueId<Role> roleId, UniqueId<Attribute> attributeId) {

            }

            @Override
            public void removeAllNeeds() {

            }

            @Override
            public void addModerates(UniqueId<Pmf> pmfId, UniqueId<Attribute> attributeId) {

            }

            @Override
            public Attribute getModerates(UniqueId<Pmf> id) {
                return null;
            }

            @Override
            public Set<Pmf> getModeratedBy(UniqueId<Attribute> id) {
                return null;
            }

            @Override
            public void removeModerates(UniqueId<Pmf> pmfId, UniqueId<Attribute> attributeId) {

            }

            @Override
            public void removeAllModerates() {

            }

            @Override
            public void addHas(UniqueId<Agent> agentId, UniqueId<Attribute> attributeId, double value) {

            }

            @Override
            public Set<Attribute> getHas(UniqueId<Agent> id) {
                return null;
            }

            @Override
            public Set<Agent> getHadBy(UniqueId<Attribute> id) {
                return null;
            }

            @Override
            public Double getHasValue(UniqueId<Agent> agentId, UniqueId<Attribute> attributeId) {
                return null;
            }

            @Override
            public void setHasValue(UniqueId<Agent> agentId, UniqueId<Attribute> attributeId, double value) {

            }

            @Override
            public void removeHas(UniqueId<Agent> agentId, UniqueId<Attribute> attributeId) {

            }

            @Override
            public void removeAllHas() {

            }

            @Override
            public void addContains(UniqueId<Role> roleId, UniqueId<Characteristic> characteristicId, double value) {

            }

            @Override
            public Set<Characteristic> getContains(UniqueId<Role> id) {
                return null;
            }

            @Override
            public Set<Role> getContainedBy(UniqueId<Characteristic> id) {
                return null;
            }

            @Override
            public Double getContainsValue(UniqueId<Role> roleId, UniqueId<Characteristic> characteristicId) {
                return null;
            }

            @Override
            public void setContainsValue(UniqueId<Role> roleId, UniqueId<Characteristic> characteristicId, double value) {

            }

            @Override
            public void removeContains(UniqueId<Role> roleId, UniqueId<Characteristic> characteristicId) {

            }

            @Override
            public void removeAllContains() {

            }

            @Override
            public void addAssignment(Assignment assignment) {

            }

            @Override
            public void addAssignments(Collection<Assignment> assignments) {

            }

            @Override
            public Assignment getAssignment(UniqueId<Assignment> id) {
                return null;
            }

            @Override
            public Set<Assignment> getAssignments() {
                return null;
            }

            @Override
            public Set<Assignment> getAssignmentsByAgent(UniqueId<Agent> id) {
                return null;
            }

            @Override
            public void removeAssignment(UniqueId<Assignment> id) {

            }

            @Override
            public void removeAssignments(Collection<UniqueId<Assignment>> ids) {

            }

            @Override
            public void removeAllAssignments() {

            }

            @Override
            public void addAchieves(UniqueId<Role> roleId, UniqueId<SpecificationGoal> goalId) {

            }

            @Override
            public Set<SpecificationGoal> getAchieves(UniqueId<Role> id) {
                return null;
            }

            @Override
            public Set<Role> getAchievedBy(UniqueId<SpecificationGoal> id) {
                return null;
            }

            @Override
            public void removeAchieves(UniqueId<Role> roleId, UniqueId<SpecificationGoal> goalId) {

            }

            @Override
            public void removeAllAchieves() {

            }

            @Override
            public void addSpecificationGoal(SpecificationGoal goal) {

            }

            @Override
            public void addSpecificationGoals(Collection<SpecificationGoal> goals) {

            }

            @Override
            public SpecificationGoal getSpecificationGoal(UniqueId<SpecificationGoal> id) {
                return null;
            }

            @Override
            public Set<SpecificationGoal> getSpecificationGoals() {
                return null;
            }

            @Override
            public void removeSpecificationGoal(UniqueId<SpecificationGoal> id) {

            }

            @Override
            public void removeSpecificationGoals(Collection<UniqueId<SpecificationGoal>> ids) {

            }

            @Override
            public void removeAllSpecificationGoals() {

            }

            @Override
            public void addRole(Role role) {

            }

            @Override
            public void addRoles(Collection<Role> roles) {

            }

            @Override
            public Role getRole(UniqueId<Role> id) {
                return null;
            }

            @Override
            public Set<Role> getRoles() {
                return null;
            }

            @Override
            public void removeRole(UniqueId<Role> id) {

            }

            @Override
            public void removeRoles(Collection<UniqueId<Role>> ids) {

            }

            @Override
            public void removeAllRoles() {

            }

            @Override
            public void addPolicy(Policy policy) {

            }

            @Override
            public void addPolicies(Collection<Policy> policies) {

            }

            @Override
            public Policy getPolicy(UniqueId<Policy> id) {
                return null;
            }

            @Override
            public Set<Policy> getPolicies() {
                return null;
            }

            @Override
            public void removePolicy(UniqueId<Policy> policyId) {

            }

            @Override
            public void removePolicies(Collection<UniqueId<Policy>> ids) {

            }

            @Override
            public void removeAllPolicies() {

            }

            @Override
            public void addPmf(Pmf pmf) {

            }

            @Override
            public void addPmfs(Collection<Pmf> pmfs) {

            }

            @Override
            public Pmf getPmf(UniqueId<Pmf> id) {
                return null;
            }

            @Override
            public Set<Pmf> getPmfs() {
                return null;
            }

            @Override
            public void removePmf(UniqueId<Pmf> id) {

            }

            @Override
            public void removePmfs(Collection<UniqueId<Pmf>> ids) {

            }

            @Override
            public void removeAllPmfs() {

            }

            @Override
            public void addInstanceGoal(InstanceGoal goal) {

            }

            @Override
            public void addInstanceGoals(Collection<InstanceGoal> goals) {

            }

            @Override
            public InstanceGoal getInstanceGoal(UniqueId<InstanceGoal> id) {
                return null;
            }

            @Override
            public Set<InstanceGoal> getInstanceGoals() {
                return null;
            }

            @Override
            public void removeInstanceGoal(UniqueId<InstanceGoal> id) {

            }

            @Override
            public void removeInstanceGoals(Collection<UniqueId<InstanceGoal>> ids) {

            }

            @Override
            public void removeAllInstanceGoals() {

            }

            @Override
            public void addCharacteristic(Characteristic characteristic) {

            }

            @Override
            public void addCharacteristics(Collection<Characteristic> characteristics) {

            }

            @Override
            public Characteristic getCharacteristic(UniqueId<Characteristic> id) {
                return null;
            }

            @Override
            public Set<Characteristic> getCharacteristics() {
                return null;
            }

            @Override
            public void removeCharacteristic(UniqueId<Characteristic> id) {

            }

            @Override
            public void removeCharacteristics(Collection<UniqueId<Characteristic>> ids) {

            }

            @Override
            public void removeAllCharacteristic() {

            }

            @Override
            public void addCapability(Capability capability) {

            }

            @Override
            public void addCapabilities(Collection<Capability> capabilities) {

            }

            @Override
            public Capability getCapability(UniqueId<Capability> id) {
                return null;
            }

            @Override
            public Set<Capability> getCapabilities() {
                return null;
            }

            @Override
            public void removeCapability(UniqueId<Capability> id) {

            }

            @Override
            public void removeCapabilities(Collection<UniqueId<Capability>> ids) {

            }

            @Override
            public void removeAllCapabilities() {

            }

            @Override
            public void addAttribute(Attribute attribute) {

            }

            @Override
            public void addAttributes(Collection<Attribute> attributes) {

            }

            @Override
            public Attribute getAttribute(UniqueId<Attribute> id) {
                return null;
            }

            @Override
            public Set<Attribute> getAttributes() {
                return null;
            }

            @Override
            public void removeAttribute(UniqueId<Attribute> id) {

            }

            @Override
            public void removeAttributes(Collection<UniqueId<Attribute>> ids) {

            }

            @Override
            public void removeAllAttributes() {

            }

            @Override
            public void addAgent(Agent agent) {

            }

            @Override
            public void addAgents(Collection<Agent> agents) {

            }

            @Override
            public Agent getAgent(UniqueId<Agent> id) {
                return null;
            }

            @Override
            public Set<Agent> getAgents() {
                return null;
            }

            @Override
            public void removeAgent(UniqueId<Agent> id) {

            }

            @Override
            public void removeAgents(Collection<UniqueId<Agent>> ids) {

            }

            @Override
            public void removeAllAgents() {

            }

            @Override
            public double effectiveness(Collection<Assignment> assignments) {
                return 0;
            }

            @Override
            public Goodness getGoodness(UniqueId<Role> id) {
                return null;
            }

            @Override
            public void setGoodness(UniqueId<Role> id, Goodness goodness) {

            }
        };
        var predicate = new Predicate<Organization>() {
            @Override
            public boolean test(Organization organization) {
                return result;
            }
        };

        assertThat(organization.isValid(predicate)).isEqualTo(result);
    }

}
