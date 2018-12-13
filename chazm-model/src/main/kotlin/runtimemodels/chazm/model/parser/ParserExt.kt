package runtimemodels.chazm.model.parser

import runtimemodels.chazm.api.id.UniqueId
import runtimemodels.chazm.model.message.E
import javax.xml.namespace.QName
import javax.xml.stream.XMLEventReader
import javax.xml.stream.XMLStreamException
import javax.xml.stream.events.StartElement
import javax.xml.stream.events.XMLEvent

private const val ID_ATTRIBUTE = "id" //$NON-NLS-1$
fun <T : UniqueId<U>, U> build(
    id: T,
    map: MutableMap<String, T>,
    element: StartElement,
    f: (T) -> U,
    c: (U) -> Unit
) {
    map[element attribute ID_ATTRIBUTE] = id
    try {
        c(f(id))
    } catch (e: IllegalArgumentException) {
        throw XMLStreamException(e)
    }
}

infix fun StartElement.attribute(localPart: String): String {
    val attribute = getAttributeByName(QName(localPart))
        ?: throw XMLStreamException(E.MISSING_ATTRIBUTE_IN_TAG[name.localPart, localPart])
    return attribute.value
}

fun <T : UniqueId<U>, U, V : UniqueId<W>, W> addRelation(
    id1: T,
    ids: List<String>,
    map: Map<String, V>,
    c: (T, V) -> Unit,
    clazz: Class<W>
) {
    for (id in ids) {
        map[id]?.let {
            c(id1, it)
        } ?: throw XMLStreamException(E.INCOMPLETE_XML_FILE[clazz.simpleName, id])
    }
}

inline fun <T : UniqueId<U>, U, V : UniqueId<W>, reified W : Any> addRelation(
    id1: T,
    ids: List<String>,
    map: Map<String, V>,
    c: (T, V) -> Unit
) {
    for (id in ids) {
        map[id]?.let {
            c(id1, it)
        } ?: throw XMLStreamException(E.INCOMPLETE_XML_FILE[W::class.java.simpleName, id])
    }
}


private const val CHILD_ELEMENT = "child" //$NON-NLS-1$
fun collectIds(reader: XMLEventReader, tagName: QName): List<String> {
    val ids = mutableListOf<String>()
    reader.forEach {
        when {
            it is XMLEvent && it.isStartElement -> {
                val element = it.asStartElement()
                val name = element.name
                if (CHILD_ELEMENT == name.localPart) {
                    ids.add(reader.nextEvent().asCharacters().data)
                }
            }
            it is XMLEvent && it.isEndElement -> {
                val element = it.asEndElement()
                if (element.name == tagName) {
                    return ids
                }
            }
        }
    }
    throw XMLStreamException(E.MISSING_END_TAG[tagName]) // should not happen as XMLEventReader will do it for us
}
