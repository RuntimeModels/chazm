package model.organization.id;

import aop.profiling.DoNotProfile;

@DoNotProfile
class IdFactoryImpl implements IdFactory {

	@Override
	public <T, U> UniqueId<T> build(final Class<T> clazz, final Class<U> id) {
		return new ClassId<>(clazz, id);
	}

	@Override
	public <T> UniqueId<T> build(final Class<T> clazz, final Long id) {
		return new LongId<>(clazz, id);
	}

	@Override
	public <T> StringId<T> build(final Class<T> clazz, final String id) {
		return new StringId<>(clazz, id);
	}

}
