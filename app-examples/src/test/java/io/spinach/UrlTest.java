package io.spinach;

import org.junit.jupiter.api.Test;

import java.io.UnsupportedEncodingException;
import java.net.*;

/**
 * @author aomsweet
 */
public class UrlTest {

    @Test
    public void url() throws MalformedURLException, UnsupportedEncodingException {
        String path = "httP://my_email%40gmail.com:fewa@127.0.0.1:3232?a=b";

        System.out.println(URLEncoder.encode("my_email%40gmail.com", "utf-8"));

        URL url = new URL(path);
        System.out.println(url.getProtocol());
        System.out.println(URLDecoder.decode(url.getUserInfo() , "utf-8"));
        System.out.println(url.getHost());
        System.out.println(url.getPort());
        System.out.println(url.getDefaultPort());
    }

    @Test
    public void uri() throws MalformedURLException, UnsupportedEncodingException {
        String path = "http://user:pwd@127.0.0.1:1080";
        String path1 = "httP://user:pwd@127.0.0.1:1080";
        String path2 = "socks5://user:pwd@127.0.0.1:1080";
        String path3 = "socks4://user:pwd@127.0.0.1:1080";
        String path4 = "socks4://user:pwd@127.0.0.1";

        URI uri = URI.create(path4);
        System.out.println(uri.getScheme());
        System.out.println(uri.getUserInfo());
        System.out.println(uri.getHost());
        System.out.println(uri.getPort());
    }

    @Test
    public void urlEncoder () throws UnsupportedEncodingException {
        System.out.println(URLEncoder.encode("username:password", "utf-8"));
    }

}
