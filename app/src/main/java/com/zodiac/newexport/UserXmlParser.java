package com.zodiac.newexport;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;
import java.util.ArrayList;
import java.io.StringReader;

public class UserXmlParser {

    private ArrayList<User> users;

    public UserXmlParser(){
        users = new ArrayList<>();
    }

    public ArrayList<User> getUsers(){
        return  users;
    }

    public boolean parse(String xmlData){
        boolean status = true;
        User currentUser = null;
        boolean inEntry = false;
        String textValue = "";

        try{
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();

            xpp.setInput(new StringReader(xmlData));
            int eventType = xpp.getEventType();
            while(eventType != XmlPullParser.END_DOCUMENT){

                String tagName = xpp.getName();
                switch (eventType){
                    case XmlPullParser.START_TAG:
                        if("user".equalsIgnoreCase(tagName)){
                            inEntry = true;
                            currentUser = new User();
                        }
                        break;
                    case XmlPullParser.TEXT:
                        textValue = xpp.getText();
                        break;
                    case XmlPullParser.END_TAG:
                        if(inEntry){
                            if("user".equalsIgnoreCase(tagName)){
                                users.add(currentUser);
                                inEntry = false;
                            } else if("to".equalsIgnoreCase(tagName)){
                                currentUser.setTo(textValue);
                            } else if("from".equalsIgnoreCase(tagName)){
                                currentUser.setFrom(textValue);
                            }else if("heading".equalsIgnoreCase(tagName)){
                                currentUser.setHeading(textValue);
                            }else if("body".equalsIgnoreCase(tagName)){
                                currentUser.setBody(textValue);
                            }
                        }
                        break;
                    default:
                }
                eventType = xpp.next();
            }
        }
        catch (Exception e){
            status = false;
            e.printStackTrace();
        }
        return  status;
    }
}
