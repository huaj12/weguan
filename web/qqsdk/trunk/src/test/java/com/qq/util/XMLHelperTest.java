package com.qq.util;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

import java.io.IOException;
import java.util.HashMap;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.Test;
import org.xml.sax.SAXException;

public class XMLHelperTest {

    public static final int FOUR = 4;

    @Test
    public void testGetInstance() {
    }

    @SuppressWarnings("static-access")
    @Test
    public void testGetInfo() throws SAXException, IOException, ParserConfigurationException {
        String info_xml = "<?xml version='1.0' encoding='UTF-8'?> <data>" + "<ret>0</ret><msg></msg><nickname>Peter</nickname>"
                + "<figureurl>http://qzapp.qlogo.cn/qzapp/00000000000000" + "0000000000007B4196/50</figureurl></data>";
        XMLHelper xmlhelper = XMLHelper.getInstance();
        HashMap<String, String> userinfo = xmlhelper.getInstance().getInfo(info_xml);
        System.out.println(userinfo.size());
        assertThat(userinfo.size(), is(XMLHelperTest.FOUR));
        assertThat(userinfo.get("nickname"), is("Peter"));
        assertThat(userinfo.get("figureurl"), is("http://qzapp.qlogo.cn/qzapp/000000000000000000000000007B4196/50"));
    }

}
