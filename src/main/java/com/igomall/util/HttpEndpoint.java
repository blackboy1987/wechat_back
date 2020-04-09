/**
 * HttpEndpoint类可以作为一个完整的Endpoint实现使用，可以参考此Sample使用自己的Endpoint逻辑。
 * 实现功能：
 * 1：在本地启动一个http服务
 * 2：接收发到/notifications的请求
 * 3：解析并验证发送到/notifications的请求
 *
 * HttpEndpoint类依赖apache的httpcomponents。如果你的项目用maven管理，
 * 请在pom中添加以下依赖：
 * <dependency>
 *   <groupId>org.apache.httpcomponents</groupId>
 *   <artifactId>httpasyncclient</artifactId>
 *   <version>4.0.1</version>
 * </dependency>
 */


package com.igomall.util;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.*;
import org.apache.http.protocol.HttpContext;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * HTTP/1.1 file server,处理发送到/notifications的请求
 */
public final class HttpEndpoint {
    public static Logger logger = Logger.getLogger(HttpEndpoint.class);

    private static String safeGetElementContent(Element element, String tag) {
        NodeList nl = element.getElementsByTagName(tag);
        if (nl != null && nl.getLength() > 0) {
            return nl.item(0).getTextContent();
        } else {
            logger.warn("get " + tag + " from xml fail");
            return "";
        }
    }

    /**
     * parser /notifications message content
     * @param notify, xml element
     */
    private static void paserContent(Element notify) {
        try {
            String topicOwner = safeGetElementContent(notify, "TopicOwner");
            System.out.println("TopicOwner:\t" + topicOwner);
            logger.debug("TopicOwner:\t" + topicOwner);

            String topicName = safeGetElementContent(notify, "TopicName");
            System.out.println("TopicName:\t" + topicName);
            logger.debug("TopicName:\t" + topicName);

            String subscriber = safeGetElementContent(notify, "Subscriber");
            System.out.println("Subscriber:\t" + subscriber);
            logger.debug("Subscriber:\t" + subscriber);

            String subscriptionName = safeGetElementContent(notify, "SubscriptionName");
            System.out.println("SubscriptionName:\t" + subscriptionName);
            logger.debug("SubscriptionName:\t" + subscriptionName);

            String msgid = safeGetElementContent(notify, "MessageId");
            System.out.println("MessageId:\t" + msgid);
            logger.debug("MessageId:\t" + msgid);

            // if PublishMessage with base64 message
            String msg = safeGetElementContent(notify, "Message");
            System.out.println("Message:\t" + new String(Base64.decodeBase64(msg)));
            logger.debug("Message:\t" + new String(Base64.decodeBase64(msg)));

            //if PublishMessage with string message
            //String msg = safeGetElementContent(notify, "Message");
            //System.out.println("Message:\t" + msg);
            //logger.debug("Message:\t" + msg);

            String msgMD5 = safeGetElementContent(notify, "MessageMD5");
            System.out.println("MessageMD5:\t" + msgMD5);
            logger.debug("MessageMD5:\t" + msgMD5);

            String msgPublishTime = safeGetElementContent(notify, "PublishTime");
            Date d = new Date(Long.parseLong(msgPublishTime));
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String strdate = sdf.format(d);
            System.out.println("PublishTime:\t" + strdate);
            logger.debug("MessagePublishTime:\t" + strdate);

            String cert = safeGetElementContent(notify, "SigningCertURL");
            System.out.println("SigningCertURL:\t" + cert);
            logger.debug("SigningCertURL:\t" + cert);


        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            logger.warn(e.getMessage());
        }


    }

    /**
     * check if this request comes from MNS Server
     * @param method, http method
     * @param uri, http uri
     * @param headers, http headers
     * @param cert, cert url
     * @return true if verify pass
     */
    private static Boolean authenticate(String method, String uri, Map<String, String> headers, String cert) {
        String str2sign = getSignStr(method, uri, headers);
        //System.out.println(str2sign);
        String signature = headers.get("Authorization");
        byte[] decodedSign = Base64.decodeBase64(signature);
        //get cert, and verify this request with this cert
        try {
            //String cert = "http://mnstest.oss-cn-hangzhou.aliyuncs.com/x509_public_certificate.pem";
            URL url = new URL(cert);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            DataInputStream in = new DataInputStream(conn.getInputStream());
            CertificateFactory cf = CertificateFactory.getInstance("X.509");

            Certificate c = cf.generateCertificate(in);
            PublicKey pk = c.getPublicKey();

            java.security.Signature signetcheck = java.security.Signature.getInstance("SHA1withRSA");
            signetcheck.initVerify(pk);
            signetcheck.update(str2sign.getBytes());
            Boolean res = signetcheck.verify(decodedSign);
            return res;
        } catch (Exception e) {
            e.printStackTrace();
            logger.warn("authenticate fail, " + e.getMessage());
            return false;
        }
    }

    private static String safeGetHeader(Map<String, String> headers, String name) {
        if (headers.containsKey(name))
            return headers.get(name);
        else
            return "";
    }

    /**
     * build string for sign
     * @param method, http method
     * @param uri, http uri
     * @param headers, http headers
     * @return String fro sign
     */
    private static String getSignStr(String method, String uri, Map<String, String> headers) {
        StringBuilder sb = new StringBuilder();
        sb.append(method);
        sb.append("\n");
        sb.append(safeGetHeader(headers, "Content-md5"));
        sb.append("\n");
        sb.append(safeGetHeader(headers, "Content-Type"));
        sb.append("\n");
        sb.append(safeGetHeader(headers, "Date"));
        sb.append("\n");

        List<String> tmp = new ArrayList<String>();
        for (Map.Entry<String, String> entry : headers.entrySet()) {
            if (entry.getKey().startsWith("x-mns-"))
                tmp.add(entry.getKey() + ":" + entry.getValue());
        }
        Collections.sort(tmp);

        for (String kv : tmp) {
            sb.append(kv);
            sb.append("\n");
        }

        sb.append(uri);
        return sb.toString();
    }

    /**
     * process method for NSHandler
     * @param request, http request
     * @param response, http responst
     * @throws HttpException
     * @throws IOException
     */
    public void handle(
            final HttpServletRequest request,
            final HttpServletResponse response) throws HttpException, IOException {
        String method = request.getMethod().toUpperCase(Locale.ENGLISH);

        if (!method.equals("GET") && !method.equals("HEAD") && !method.equals("POST")) {
            throw new MethodNotSupportedException(method + " method not supported");
        }

        Enumeration<String> headers = request.getHeaderNames();
        Map<String, String> hm = new HashMap<String, String>();
        while (headers.hasMoreElements()){
            String header = headers.nextElement();
            System.out.println(header + ":" + request.getHeader(header));
            hm.put(header, request.getHeader(header));
        }

        String target = request.getRequestURI();
        System.out.println(target);
            //parser xml content
            InputStream content = request.getInputStream();
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            Element notify = null;
            try {
                DocumentBuilder db = dbf.newDocumentBuilder();
                Document document = db.parse(content);
                NodeList nl = document.getElementsByTagName("Notification");
                if (nl == null || nl.getLength() == 0) {
                    System.out.println("xml tag error");
                    logger.warn("xml tag error");
                    response.setStatus(HttpStatus.SC_BAD_REQUEST);
                    return;
                }
                notify = (Element) nl.item(0);
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
                logger.warn("xml parser fail! " + e.getMessage());
                response.setStatus(HttpStatus.SC_BAD_REQUEST);
                return;
            } catch (SAXException e) {
                e.printStackTrace();
                logger.warn("xml parser fail! " + e.getMessage());
                response.setStatus(HttpStatus.SC_BAD_REQUEST);
                return;
            }

            //verify request
            String cert = safeGetElementContent(notify, "SigningCertURL");
            System.out.println("SigningCertURL:\t" + cert);
            logger.debug("SigningCertURL:\t" + cert);
            if (cert.isEmpty()) {
                System.out.println("SigningCertURL empty");
                response.setStatus(HttpStatus.SC_BAD_REQUEST);
                return;
            }
            /*if (!authenticate(method, target, hm, cert)) {
                System.out.println("authenticate fail");
                logger.warn("authenticate fail");
                response.setStatus(HttpStatus.SC_BAD_REQUEST);
                return;
            }*/
            paserContent(notify);
        response.setStatus(HttpStatus.SC_NO_CONTENT);
    }
}

