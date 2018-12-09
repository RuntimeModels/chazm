package runtimemodels.chazm.model.parsers

import javax.xml.stream.XMLStreamException

@FunctionalInterface
internal interface RunLater {
    @Throws(XMLStreamException::class)
    fun run()
}
