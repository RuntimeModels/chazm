package runtimemodels.chazm.model.relation;

import com.google.inject.assistedinject.Assisted;
import runtimemodels.chazm.api.entity.Agent;
import runtimemodels.chazm.api.entity.Attribute;
import runtimemodels.chazm.api.relation.Has;
import runtimemodels.chazm.message.E;
import runtimemodels.chazm.message.M;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import java.util.Objects;

class HasRelation implements Has {

    /**
     * <code>QUALITY_MIN_AMOUNT</code> is the minimum possible value for a quality-type score, which is {@value} .
     */
    static final double QUALITY_MIN_AMOUNT = 0.0;
    /**
     * <code>QUALITY_MAX_AMOUNT</code> is the maximum possible value for a quality-type score, which is {@value} .
     */
    static final double QUALITY_MAX_AMOUNT = 1.0;
    /**
     * <code>QUANTITY_MIN_AMOUNT</code> is the minimum possible value for a quantity-type score, which is {@value} .
     */
    private static final double QUANTITY_MIN_AMOUNT = 0.0;

    private final Agent agent;
    private final Attribute attribute;
    private double value;
    private transient Integer hashCode = null;

    @Inject
    HasRelation(@NotNull @Assisted final Agent agent, @NotNull @Assisted final Attribute attribute, @NotNull @Assisted final double value) {
        this.agent = agent;
        this.attribute = attribute;
        setValue(value);
    }

    @Override
    public final void setValue(final double value) {
        final Attribute.Type type = getAttribute().getType();
        switch (type) {
            case POSITIVE_QUALITY:
            case NEGATIVE_QUALITY:
                if (value < QUALITY_MIN_AMOUNT || value > QUALITY_MAX_AMOUNT) {
                    throw new IllegalArgumentException(E.VALUE_BETWEEN.get(type, value, QUALITY_MIN_AMOUNT, QUALITY_MAX_AMOUNT));
                }
                break;
            case POSITIVE_QUANTITY:
            case NEGATIVE_QUANTITY:
                if (value < QUANTITY_MIN_AMOUNT) {
                    throw new IllegalArgumentException(E.VALUE_AT_LEAST.get(type, value, QUANTITY_MIN_AMOUNT));
                }
                break;
            case NEGATIVE_UNBOUNDED:
            case POSITIVE_UNBOUNDED:
                break;
        }
        this.value = value;
    }

    @Override
    public boolean equals(final Object object) {
        if (object instanceof Has) {
            final Has hasRelation = (Has) object;
            return getAgent().equals(hasRelation.getAgent()) && getAttribute().equals(hasRelation.getAttribute());
        }
        return false;
    }

    @Override
    public int hashCode() {
        if (hashCode == null) {
            hashCode = Objects.hash(getAgent(), getAttribute());
        }
        return hashCode;
    }

    @Override
    public String toString() {
        return M.RELATION_WITH_VALUE.get(getAgent().getId(), getAttribute().getId(), getValue());
    }

    public Agent getAgent() {
        return this.agent;
    }

    public Attribute getAttribute() {
        return this.attribute;
    }

    public double getValue() {
        return this.value;
    }
}
