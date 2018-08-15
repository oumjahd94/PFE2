/*    */ package ma.mt.fo.util;
/*    */ 
/*    */ import java.io.FileInputStream;
/*    */ import java.io.PrintStream;
/*    */ import java.util.Hashtable;
/*    */ import javax.xml.parsers.DocumentBuilder;
/*    */ import javax.xml.parsers.DocumentBuilderFactory;
/*    */ import javax.xml.parsers.ParserConfigurationException;
/*    */ import org.w3c.dom.Document;
/*    */ import org.w3c.dom.Node;
/*    */ import org.w3c.dom.NodeList;
/*    */ 
/*    */ public class xmlParser
/*    */ {
/*    */   private String Node_Name;
/*    */   private String Node_Value;
/*    */   private Hashtable HT;
/*    */ 
/*    */   public xmlParser()
/*    */   {
/* 19 */     this.HT = new Hashtable();
/*    */   }
/*    */ 
/*    */   public Hashtable GetParameters(String FF, String LA)
/*    */     throws ParserConfigurationException, Exception
/*    */   {
/*    */     try
/*    */     {
/* 27 */       DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
/* 28 */       DocumentBuilder builder = factory.newDocumentBuilder();
/* 29 */       Document doc = builder.parse(new FileInputStream(FF));
/* 30 */       Node n = doc.getDocumentElement();
/* 31 */       NodeList nl = n.getChildNodes();
/* 32 */       n = null;
/* 33 */       for (int i = 0; i <= nl.getLength(); i++)
/*    */       {
/* 35 */         if (!nl.item(i).getNodeName().equals(LA))
/*    */           continue;
/* 37 */         n = nl.item(i);
/* 38 */         break;
/*    */       }
/*    */ 
/* 41 */       if (n == null)
/* 42 */         return null;
/* 43 */       PrintNodes(n);
/*    */     }
/*    */     catch (ParserConfigurationException pce)
/*    */     {
/* 47 */       System.out.println("Exception = " + pce);
/* 48 */       return null;
/*    */     }
/*    */     catch (Exception e)
/*    */     {
/* 52 */       System.out.println("Exception = " + e);
/* 53 */       return null;
/*    */     }
/* 55 */     return this.HT;
/*    */   }
/*    */ 
/*    */   public void PrintNodes(Node node)
/*    */   {
/* 60 */     if (!node.getNodeName().equals("#text"))
/* 61 */       this.Node_Name = node.getNodeName();
/* 62 */     if ((node.getNodeValue() != null) && (!node.getNodeValue().trim().equals("")))
/*    */     {
/* 64 */       this.Node_Value = node.getNodeValue().trim();
/* 65 */       this.HT.put(this.Node_Name, this.Node_Value);
/*    */     }
/* 67 */     NodeList childnodes = node.getChildNodes();
/* 68 */     for (int i = 0; i < childnodes.getLength(); i++)
/* 69 */       PrintNodes(childnodes.item(i));
/*    */   }
/*    */ }

/* Location:           /home/anass/Projets/MT/sources/WEB-INF/classes/
 * Qualified Name:     mt.pricing.standard.servlets.xmlParser
 * JD-Core Version:    0.6.0
 */