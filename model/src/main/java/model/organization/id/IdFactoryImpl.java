package model.organization.id;

import javax.validation.constraints.NotNull;

import aop.profiling.DoNotProfile;

@DoNotProfile
class IdFactoryImpl implements IdFactory {

	@Override
	public <T, U> UniqueId<T> build(@NotNull final Class<T> clazz, @NotNull final Class<U> id) {
		return new ClassId<>(clazz, id);
	}

	@Override
	public <T> UniqueId<T> build(@NotNull final Class<T> clazz, @NotNull final Long id) {
		return new LongId<>(clazz, id);
	}

	@Override
	public <T> StringId<T> build(@NotNull final Class<T> clazz, @NotNull final String id) {
		return new StringId<>(clazz, id);
	}

}
