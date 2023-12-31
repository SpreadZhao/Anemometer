package network.cookie

import org.xml.sax.Attributes
import org.xml.sax.helpers.DefaultHandler

class CookieParseHandler : DefaultHandler() {

    val trieRoot: TrieNode? = null

    override fun startDocument() {
        super.startDocument()
    }

    override fun endDocument() {
        super.endDocument()
    }

    override fun startElement(uri: String?, localName: String?, qName: String?, attributes: Attributes?) {
        super.startElement(uri, localName, qName, attributes)
    }

    override fun endElement(uri: String?, localName: String?, qName: String?) {
        super.endElement(uri, localName, qName)
    }

    override fun characters(ch: CharArray?, start: Int, length: Int) {
        super.characters(ch, start, length)
    }
}