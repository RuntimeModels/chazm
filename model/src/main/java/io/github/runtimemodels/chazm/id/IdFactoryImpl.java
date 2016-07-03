package io.github.runtimemodels.chazm.id;

import io.github.runtimemodels.aop.profiling.DoNotProfile;
import io.github.runtimemodels.aop.validation.DoNotCheck;

@DoNotProfile
@DoNotCheck
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
