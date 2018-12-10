package runtimemodels.chazm.model.koin

import org.koin.dsl.context.ModuleDefinition
import org.koin.dsl.definition.BeanDefinition
import org.koin.experimental.builder.create

inline fun <reified T : Any> ModuleDefinition.single(): BeanDefinition<T> = single { create<T>() }
