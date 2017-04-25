package forwardserver;
import java.io.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
public class ReadClientDataXml{
 public static String readClientCount(int i,int name) {
  Element element = null;
  // 可以使用绝对路劲
  File f = new File("forwardserver/clientDataAPM.xml");
  // documentBuilder为抽象不能直接实例化(将XML文件转换为DOM文件)
  DocumentBuilder db = null;
  DocumentBuilderFactory dbf = null;
  try {
   // 返回documentBuilderFactory对象
   dbf = DocumentBuilderFactory.newInstance();
   // 返回db对象用documentBuilderFatory对象获得返回documentBuildr对象
   db = dbf.newDocumentBuilder();
   // 得到一个DOM并返回给document对象
   Document dt = db.parse(f);
   // 得到一个elment根元素
   element = dt.getDocumentElement();
   // 获得根节点
   // 获得根元素下的子节点
   NodeList childNodes = element.getChildNodes();
   
   // 获得每个对应位置i的结点
   Node node1 = childNodes.item(i);
   if("Account".equals(node1.getNodeName()))
   {
        // 获得<Accounts>下的节点
     NodeList nodeDetail = node1.getChildNodes();
     // 遍历<Accounts>下的节点
     for (int j = 0; j < nodeDetail.getLength(); j++) 
	 {
      // 获得<Accounts>元素每一个节点
      	Node detail = nodeDetail.item(j);
	    if ("name1".equals(detail.getNodeName()) && name == 1)
	   	{
              return detail.getTextContent();
	    }
		 if ("name2".equals(detail.getNodeName()) && name == 2)
	   	{
              return detail.getTextContent();
	    }
      }
   }
  }
  catch (Exception e) 
  {
   e.printStackTrace();
  }

  return null;
 	}
}
