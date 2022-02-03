package SaToCoT.CoT_engine;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.StringWriter;

public class CoT_output {
    public String CoT_output(String origin, String uid, String type,
                             String time, String start, String stale, String lat,
                             String lon, String alias, String speed, String course) {

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        StringWriter writer = new StringWriter();

        //Change UID prefix
        switch (origin) {
            case ("harris"):
                uid = "harris.sa." + uid.toLowerCase();
                break;
            case ("moto"):
                uid = "moto.sa." + uid.toLowerCase();
                break;
            case ("adsb"):
                uid = "adsb.sa." + uid.toLowerCase();
                break;
        }

        try {
            builder = factory.newDocumentBuilder();
            Document doc = builder.newDocument();

            //Create root tag "event"
            Element version = doc.createElement("event");
            version.setAttribute("version", "2.0");
            version.setAttribute("uid", uid);
            version.setAttribute("type", type);
            version.setAttribute("time", time);
            version.setAttribute("start", start);
            version.setAttribute("stale", stale);
            version.setAttribute("how", "m-g");
            doc.appendChild(version);

            //adding child tag "point" to "event"
            Element point = doc.createElement("point");
            point.setAttribute("lat", lat);
            point.setAttribute("lon", lon);
            point.setAttribute("hae", "25.0");
            point.setAttribute("ce", "5.0");
            point.setAttribute("le", "0.0");
            version.appendChild(point);

            //adding child tag "detail" to "event"
            Element detail = doc.createElement("detail");
            version.appendChild(detail);

            //adding child tag "contact" to "detail"
            Element contact = doc.createElement("contact");
            contact.setAttribute("callsign", alias);
            detail.appendChild(contact);

            //adding child tag "precisionlocation" to "detail"
            Element presloc = doc.createElement("precisionlocation");
            presloc.setAttribute("geopointsrc", "Radio");
            presloc.setAttribute("altsrc", "Harris GPS");
            detail.appendChild(presloc);

            //adding child tag "track" to "detail"
            Element track = doc.createElement("track");
            track.setAttribute("speed", speed);
            track.setAttribute("course", course);
            detail.appendChild(track);

            //create TransformerFactory for output
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            transformer.transform(new DOMSource(doc), new StreamResult(writer));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return writer.getBuffer().toString();
    }
}
