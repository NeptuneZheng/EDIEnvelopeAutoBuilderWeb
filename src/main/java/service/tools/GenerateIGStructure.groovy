package service.tools

import com.alibaba.fastjson.JSON
import org.apache.commons.io.IOUtils
import org.apache.poi.ss.formula.functions.T
import org.dom4j.Document
import org.dom4j.DocumentHelper
import org.dom4j.Element
import service.tools.mode.Segment

class GenerateIGStructure {
    public static void main(String[] args){
        InputStream is = GenerateIGStructure.class.getResourceAsStream("/IG/CUS_D96B_IFTMBC_CS.xml")
        if(is){
            String ig_xml = IOUtils.toString(is)
            Document document = DocumentHelper.parseText(ig_xml)
            Element root = document.getRootElement()

            Segment rootSegment = new Segment()
            generateSegment(root, rootSegment)

            String json = JSON.toJSONString(rootSegment)
            FileWriter writer = new FileWriter("src/main/resources/IG/CUS_D96B_IFTMBC_CS.json")
            writer.write(json)

            writer.flush()
            writer.close()
        }

    }

    static void generateSegment(Element element, Segment fatherSegment) {
        fatherSegment.setName(element.attribute("Name").value)
        List<Segment> list = new ArrayList<>()
        if(element.elements("Component").size() > 0){
            fatherSegment.setLoop(true)
            element.elements("Component").each { curr_Element ->
                Segment segment = new Segment()
                generateSegment((Element)curr_Element,segment)
                list.add(segment)
            }
            fatherSegment.setLoopSegment(list as List<T>)
        }else{
            fatherSegment.setLoop(false)
            fatherSegment.setLoopSegment(list as List<T>)
        }
    }
}
