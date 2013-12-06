/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package addressparser;

/**
 *
 * @author Sam
 */
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AddressParser {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String address = "";
        try {
            address = encodeString(args[0]);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(AddressParser.class.getName()).log(Level.SEVERE, null, ex);
        }

//        This service is based on the free services provided by http://www.gisgraphy.com/
        String result = sendHTTPRequest("http://addressparser.appspot.com/webaddressparser?address=" + address + "&country=" + args[1]);
        System.out.println(result);
    }

    protected static String encodeString(String str)
            throws UnsupportedEncodingException {
        return URLEncoder.encode(str.replace(".", "").replace(",", "").replace(":", ""), "UTF-8");//.replace("+", "%20");  
    }

    protected static String sendHTTPRequest(String url) {
        HttpURLConnection conn = null;
        InputStream in = null;
        BufferedReader rd = null;
        StringBuffer sb = new StringBuffer();
        try {
            URL u = new URL(url);
            conn = (HttpURLConnection) u.openConnection();

            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                // Get the response  
                rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String line;
                while ((line = rd.readLine()) != null) {
                    sb.append(line);
                }
                rd.close();
            }

        } catch (Throwable e) {
            in = null;
        } finally {
            conn = null;
        }
        return sb.toString();
    }

}
