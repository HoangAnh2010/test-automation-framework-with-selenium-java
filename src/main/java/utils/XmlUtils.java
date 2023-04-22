package utils;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import model.Data;


import java.io.File;

public class XmlUtils {
    public static Data xmlToData(String fileName){
        try {

            File file = new File(fileName);
            JAXBContext jaxbContext = JAXBContext.newInstance(Data.class);

            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            Data data= (Data) jaxbUnmarshaller.unmarshal(file);

            System.out.println(data.toString());
            return data;

        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        Data d = xmlToData("G:\\Download\\GP\\dataEngine\\data.xml");
    }
}
