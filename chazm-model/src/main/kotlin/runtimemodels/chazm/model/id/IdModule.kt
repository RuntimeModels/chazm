package runtimemodels.chazm.model.id

import com.google.inject.AbstractModule

/**
 * The [IdModule] class provides a Guice binding module for id.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
class IdModule : AbstractModule() {

    override fun configure() {
        bind(IdFactory::class.java).to(IdFactoryImpl::class.java)
    }

}
