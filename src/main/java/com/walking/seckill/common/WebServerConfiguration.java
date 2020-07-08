package com.walking.seckill.common;

import org.apache.catalina.connector.Connector;
import org.apache.coyote.http11.Http11AprProtocol;
import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.stereotype.Component;

/**
 * @Author: CNwalking
 * @DateTime: 2020/7/8 10:40 下午
 * @Description: 除了application.properties中的tomcat配置，还可以用下面这个类来定制化tomcat
 */
// 当Spring容器内没有TomcatEmbeddedServletContainerFactory时，会加载这个bean的内容
@Component
public class WebServerConfiguration implements WebServerFactoryCustomizer<ConfigurableWebServerFactory> {
    @Override
    public void customize(ConfigurableWebServerFactory factory) {
        // 使用工厂类的接口定制tomcat
        ((TomcatServletWebServerFactory) factory).addConnectorCustomizers(new TomcatConnectorCustomizer() {
            @Override
            public void customize(Connector connector) {
                Http11AprProtocol protocol = (Http11AprProtocol) connector.getProtocolHandler();
                // 连上30秒不发请求就断开
                protocol.setKeepAliveTimeout(30 * 1000);
                // 一个keepAlive发了超过10000个请求就断开
                protocol.setMaxKeepAliveRequests(10000);
            }
        });

    }
}
