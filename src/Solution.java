import javax.xml.namespace.QName;
import javax.xml.stream.*;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.*;
import java.util.*;


public class Solution {
    public static void main(String[] args) throws Exception {
        Map<String,List<Role>> members = new TreeMap<>();

        XMLInputFactory factory = XMLInputFactory.newInstance();
        XMLEventReader eventReader =
                factory.createXMLEventReader(new FileReader("test2.txt"));
        readProjectXML(members, eventReader);

        String xmlString = generateXMLString(members);
        System.out.println(xmlString);


    }

    private static String generateXMLString(Map<String, List<Role>> members) throws XMLStreamException, IOException {
        StringWriter stringWriter = new StringWriter();

        XMLOutputFactory xmlOutputFactory = XMLOutputFactory.newInstance();
        XMLStreamWriter xmlStreamWriter =
                xmlOutputFactory.createXMLStreamWriter(stringWriter);


        xmlStreamWriter.writeStartElement("members");

        for(Map.Entry<String,List<Role>> entry : members.entrySet()){
            int depth = 1;

            String memberName = entry.getKey();

            indentation(xmlStreamWriter, depth);
            xmlStreamWriter.writeStartElement("member");
            xmlStreamWriter.writeAttribute("name",memberName);


            List<Role> roles = entry.getValue();
            Collections.sort(roles);

            depth++;
            for(Role role : roles) {
                String roleName = role.getName();
                String roleProjectName = role.getProjectName();

                indentation(xmlStreamWriter, depth);
                xmlStreamWriter.writeEmptyElement("role");
                xmlStreamWriter.writeAttribute("name",roleName);
                xmlStreamWriter.writeAttribute("project",roleProjectName);

            }
            depth--;

            indentation(xmlStreamWriter, depth);
            xmlStreamWriter.writeEndElement();
        }
        xmlStreamWriter.writeCharacters("\n");
        xmlStreamWriter.writeEndElement();

        xmlStreamWriter.flush();
        xmlStreamWriter.close();

        String xmlString = stringWriter.getBuffer().toString();
        stringWriter.close();
        return xmlString;
    }

    private static void indentation(XMLStreamWriter xmlStreamWriter, int depth) throws XMLStreamException {
        xmlStreamWriter.writeCharacters("\n");
        for(int i=0; i<depth; i++) {
            xmlStreamWriter.writeCharacters("    ");
        }
    }

    private static void readProjectXML(Map<String,List<Role>> members, XMLEventReader eventReader) throws XMLStreamException {
        String currentProjectName = "";
        while (eventReader.hasNext()) {
            XMLEvent event = eventReader.nextEvent();
            switch (event.getEventType()) {
                case XMLStreamConstants.START_ELEMENT: {
                    StartElement startElement = event.asStartElement();
                    String qName = startElement.getName().getLocalPart();
                    if (qName.equalsIgnoreCase("project")) {
                        Attribute name = startElement.getAttributeByName(new QName("name"));
                        currentProjectName = name.getValue();
                    } else if (qName.equalsIgnoreCase("member")) {
                        String name = startElement.getAttributeByName(new QName("name")).getValue();
                        String role = startElement.getAttributeByName(new QName("role")).getValue();
                        Role tempRole= new Role(role, currentProjectName);
                        if(members.containsKey(name)) {
                            List<Role> containedRoles = members.get(name);
                            containedRoles.add(tempRole);
                        }
                        else {
                            List<Role> roleList = new ArrayList<>();
                            roleList.add(tempRole);
                            members.put(name,roleList);
                        }
                    }

                    break;
                }
            }
        }
    }
}