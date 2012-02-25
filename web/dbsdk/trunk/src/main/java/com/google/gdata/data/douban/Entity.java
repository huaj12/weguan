package com.google.gdata.data.douban;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;

import com.google.gdata.data.BaseEntry;
import com.google.gdata.data.ExtensionDescription;
import com.google.gdata.data.ExtensionPoint;
import com.google.gdata.data.ExtensionProfile;
import com.google.gdata.data.Link;
import com.google.gdata.util.ParseException;
import com.google.gdata.util.XmlParser;
import com.google.gdata.util.XmlParser.ElementHandler;
import com.google.gdata.util.common.xml.XmlWriter;

@ExtensionDescription.Default(nsAlias = "db", nsUri = "http://www.douban.com/xmlns/", localName = "entity")
public class Entity extends BaseEntry<Entity> {

        public Entity() {
                super();
        }

        public Entity(BaseEntry<Entity> sourceEntry) {
                super(sourceEntry);
        }

        @Override
        public void declareExtensions(ExtensionProfile extProfile) {
                super.declareExtensions(extProfile);
                extProfile.declare(Entity.class, Link.class);
                extProfile.declareArbitraryXmlExtension(Entity.class);
        }

        protected String name;

        public String getName() {
                return name;
        }
        
        public void setName(String v) {
                name = v;
        }

        public ElementHandler getHandler(ExtensionProfile extProfile,
                        String namespace, String localName, Attributes attrs) {
                try {
                        return new Handler(extProfile);
                } catch (ParseException e) {
                        e.printStackTrace();
                } catch (IOException e) {
                        e.printStackTrace();
                }
                return null;
        }

        private class Handler extends ExtensionPoint.ExtensionHandler {

                public Handler(ExtensionProfile extProfile) throws ParseException,
                                IOException {
                        super(extProfile, Entity.class);
                }

                public void processAttribute(String namespace, String localName,
                                String value) throws ParseException {

                        if (namespace.equals("")) {
                                if (localName.equals("name")) {

                                        try {
                                                name = value;
                                        } catch (NumberFormatException e) {
                                                throw new ParseException("Invalid db:Tag/@name.", e);
                                        }

                                }
                        }
                }
                
                public ElementHandler getChildHandler(String namespace,
                                String localName, Attributes attrs) throws ParseException,
                                IOException {
                        if (localName.equals("uri")) {
                                return new IdHandler();
                        } else if (localName.equals("link")) {
                                Link link = new Link();
                                state.links.add(link);
                                return link.new AtomHandler(extProfile);
                        } else {
                                return super.getChildHandler(namespace, localName, attrs);
                        }
                }

                class IdHandler extends XmlParser.ElementHandler {

                        public void processEndElement() throws ParseException {

                                if (state.id != null) {
                                        throw new ParseException("Duplicate entry ID.");
                                }

                                if (value == null) {
                                        throw new ParseException("ID must have a value.");
                                }

                                state.id = value;
                        }
                }
        }

        public void generate(XmlWriter w, ExtensionProfile extProfile)
                        throws IOException {
                List<XmlWriter.Attribute> attributes = new ArrayList<XmlWriter.Attribute>(1);
                XmlWriter.Attribute attr = new XmlWriter.Attribute("name", name);
                attributes.add(attr);
                generateStartElement(w, Namespaces.doubanNs, "entity", attributes, null);

                if (state.id != null) {
                        w.simpleElement(null, "uri", null, state.id);
                }

                // Invoke ExtensionPoint.
                generateExtensions(w, extProfile);

                w.endElement(Namespaces.doubanNs, "entity");
        }

}