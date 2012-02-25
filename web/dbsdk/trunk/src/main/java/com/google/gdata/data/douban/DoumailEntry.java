package com.google.gdata.data.douban;

import java.util.List;

import com.google.gdata.data.BaseEntry;
import com.google.gdata.data.Entry;
import com.google.gdata.data.ExtensionDescription;
import com.google.gdata.data.ExtensionProfile;

@ExtensionDescription.Default(nsAlias = "", nsUri = "http://www.w3.org/2005/Atom", localName = "entry")
public class DoumailEntry extends BaseEntry<DoumailEntry> {

        public DoumailEntry() {
                super();
        }

        public DoumailEntry(BaseEntry<DoumailEntry> sourceEntry) {
                super(sourceEntry);
        }

        @Override
        public void declareExtensions(ExtensionProfile extProfile) {
                super.declareExtensions(extProfile);
                extProfile.declareAdditionalNamespace(Namespaces.doubanNs);
                extProfile.declare(DoumailEntry.class, Attribute.class);
                extProfile.declare(DoumailEntry.class, Entity.class);
                extProfile.declareArbitraryXmlExtension(DoumailEntry.class);
        }
        
        public Entity getEntityEntry() {
                return getExtension(Entity.class);
        }
        
        public void setEntity(Entity entity) {
                if (entity == null) {
                        removeExtension(Entity.class);
                } else {
                        setExtension(entity);
                }
        }
        
        protected List<Attribute> attributes;

        public List<Attribute> getAttributes() {
                return getRepeatingExtension(Attribute.class);
        }
        public void setAttributes(List<Attribute> atts) {
                if (atts == null) {
                        removeExtension(Attribute.class);
                } else {
                        for (Attribute att : atts)
                                addRepeatingExtension(att);
                }
        }
}