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
  // ����ʹ�þ���·��
  File f = new File("forwardserver/clientDataAPM.xml");
  // documentBuilderΪ������ֱ��ʵ����(��XML�ļ�ת��ΪDOM�ļ�)
  DocumentBuilder db = null;
  DocumentBuilderFactory dbf = null;
  try {
   // ����documentBuilderFactory����
   dbf = DocumentBuilderFactory.newInstance();
   // ����db������documentBuilderFatory�����÷���documentBuildr����
   db = dbf.newDocumentBuilder();
   // �õ�һ��DOM�����ظ�document����
   Document dt = db.parse(f);
   // �õ�һ��elment��Ԫ��
   element = dt.getDocumentElement();
   // ��ø��ڵ�
   // ��ø�Ԫ���µ��ӽڵ�
   NodeList childNodes = element.getChildNodes();
   
   // ���ÿ����Ӧλ��i�Ľ��
   Node node1 = childNodes.item(i);
   if("Account".equals(node1.getNodeName()))
   {
        // ���<Accounts>�µĽڵ�
     NodeList nodeDetail = node1.getChildNodes();
     // ����<Accounts>�µĽڵ�
     for (int j = 0; j < nodeDetail.getLength(); j++) 
	 {
      // ���<Accounts>Ԫ��ÿһ���ڵ�
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
