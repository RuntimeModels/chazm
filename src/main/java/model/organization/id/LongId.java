package model.organization.id;

import static model.organization.validation.Checks.checkNotNull;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;

import com.google.inject.assistedinject.Assisted;

class LongId<T> extends AbstractId<T> {

	private static final long serialVersionUID = 8542765061773217208L;
	private final long id;
	private transient Integer hashCode = null;
	private transient String toString = null;

	@Inject
	LongId(@NotNull @Assisted final Class<T> type, @NotNull @Assisted final long id) {
		super(type);
		checkNotNull(id, "id");
		this.id = id;
	}

	@Override
	public boolean equals(final Object object) {
		if (object instanceof LongId) {
			final LongId<?> otherId = (LongId<?>) object;
			return id == otherId.id;
		}
		return false;
	}

	@Override
	public int hashCode() {
		if (hashCode == null) {
			hashCode = (int) id;
		}
		return hashCode;
	}

	@Override
	public String toString() {
		if (toString == null) {
			toString = String.valueOf(id);
		}
		return toString;
	}

}