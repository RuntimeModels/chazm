package model.organization.id;

class IdFactoryImpl implements IdFactory {

	public <T, U> UniqueId<T> buildId(Class<T> clazz, Class<U> id) {
		return new ClassId<>(clazz, id);
	}

	public <T> UniqueId<T> buildId(Class<T> clazz, long id) {
		return new LongId<>(clazz, id);
	}

	@Override
	public <T> StringId<T> buildId(Class<T> clazz, String id) {
		return new StringId<>(clazz, id);
	}

}
