package com.kp.framework.utils.kptool;

import de.odysseus.staxon.json.JsonXMLConfig;
import de.odysseus.staxon.json.JsonXMLConfigBuilder;
import de.odysseus.staxon.json.JsonXMLInputFactory;
import de.odysseus.staxon.json.JsonXMLOutputFactory;
import de.odysseus.staxon.xml.util.PrettyXMLEventWriter;
import lombok.experimental.UtilityClass;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;


/**
 * @Author lipeng
 * @Description XML操作
 * @Date 2021/10/14 14:13
 * @return
 **/
@UtilityClass
public final class KPXmlUtil {


    /**
     * @Author lipeng
     * @Description xml转json
     * @Date 2021/10/14 14:13
     * @param xml
     * @return java.lang.String
     **/
    public static String xml2json(String xml){
        xml = xml.replaceAll("\"\"","\"null\"").replaceAll("xml:space=\"preserve\"","");
        StringReader input = new StringReader(xml);
        StringWriter output = new StringWriter();
        JsonXMLConfig config = new JsonXMLConfigBuilder()
                .repairingNamespaces(false)
                .multiplePI(false)
                .namespaceDeclarations(false)
                .autoArray(true)
                .autoPrimitive(true)
                .prettyPrint(true)
                .build();
        try {
            XMLEventReader reader = XMLInputFactory.newInstance().createXMLEventReader(input);
            XMLEventWriter writer = new JsonXMLOutputFactory(config).createXMLEventWriter(output);
            writer.add(reader);
            reader.close();
            writer.close();
        } catch( Exception e){
            e.printStackTrace();
        } finally {
            try {
                output.close();
                input.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return output.toString().replaceAll("@","#");
    }



    /**
     * @Author lipeng
     * @Description json转xml
     * @Date 2021/10/14 14:14
     * @param json
     * @return java.lang.String
     **/
    public static String json2xml(String json){
        StringReader input = new StringReader(json);
        StringWriter output = new StringWriter();
        JsonXMLConfig config = new JsonXMLConfigBuilder().multiplePI(false).repairingNamespaces(false).build();
        try {
            XMLEventReader reader = new JsonXMLInputFactory(config).createXMLEventReader(input);
            XMLEventWriter writer = XMLOutputFactory.newInstance().createXMLEventWriter(output);
            writer = new PrettyXMLEventWriter(writer);
            writer.add(reader);
            reader.close();
            writer.close();
        } catch( Exception e){
            e.printStackTrace();
        } finally {
            try {
                output.close();
                input.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(output.toString().length()>=38){//remove &lt;?xml version=&#34;1.0&#34; encoding=&#34;UTF-8&#34;?&gt;
            return output.toString().substring(39);
        }
        return output.toString();
    }

}
