package model.organization.id;

import model.organization.profiling.DoNotProfile;

@DoNotProfile
class IdFactoryImpl implements IdFactory {

	@Override
	public <T, U> UniqueId<T> buildId(final Class<T> clazz, final Class<U> id) {
		return new ClassId<>(clazz, id);
	}

	@Override
	public <T> UniqueId<T> buildId(final Class<T> clazz, final long id) {
		return new LongId<>(clazz, id);
	}

	@Override
	public <T> StringId<T> buildId(final Class<T> clazz, final String id) {
		return new StringId<>(clazz, id);
	}

}
