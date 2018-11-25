package runtimemodels.chazm.model.id

import runtimemodels.chazm.api.id.UniqueId
import runtimemodels.chazm.model.aop.profiling.DoNotProfile
import runtimemodels.chazm.model.aop.validation.DoNotCheck

@DoNotProfile
@DoNotCheck
internal class IdFactoryImpl : IdFactory {

    override fun <T, U> build(clazz: Class<T>, id: Class<U>): UniqueId<T> {
        return ClassId(clazz, id)
    }

    override fun <T> build(clazz: Class<T>, id: Long?): UniqueId<T> {
        return LongId(clazz, id)
    }

    override fun <T> build(clazz: Class<T>, id: String): UniqueId<T> {
        return StringId(clazz, id)
    }

}

inline fun <reified T, U> IdFactory.build(id: Class<U>): UniqueId<T> {
    return build(T::class.java, id)
}

inline fun <reified T> IdFactory.build(id: Long): UniqueId<T> {
    return build(T::class.java, id)
}

inline fun <reified T> IdFactory.build(id: String): UniqueId<T> {
    return build(T::class.java, id)
}
