package model.organization.id;

import static model.organization.validation.Checks.checkNotNull;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;

import com.google.inject.assistedinject.Assisted;

class StringId<T> extends AbstractId<T> {

	private static final long serialVersionUID = 522905742372399827L;
	private final String id;
	private transient Integer hashCode = null;

	@Inject
	StringId(@NotNull @Assisted final Class<T> type, @NotNull @Assisted final String id) {
		super(type);
		checkNotNull(id, "id"); //$NON-NLS-1$
		this.id = id;
	}

	@Override
	public boolean equals(final Object object) {
		if (object instanceof StringId) {
			final StringId<?> stringId = (StringId<?>) object;
			return id.equals(stringId.id);
		}
		return false;
	}

	@Override
	public int hashCode() {
		if (hashCode == null) {
			hashCode = id.hashCode();
		}
		return hashCode;
	}

	@Override
	public String toString() {
		return id;
	}

}
