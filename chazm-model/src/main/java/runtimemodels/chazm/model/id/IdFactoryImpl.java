package runtimemodels.chazm.model.id;

import runtimemodels.chazm.model.aop.profiling.DoNotProfile;
import runtimemodels.chazm.model.aop.validation.DoNotCheck;
import runtimemodels.chazm.api.id.UniqueId;

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
