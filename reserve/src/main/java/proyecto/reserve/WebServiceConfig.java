package proyecto.reserve;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;
import proyecto.reserve.WebService.ReserveEndPoint;

@EnableWs
@Configuration
public class WebServiceConfig extends WsConfigurerAdapter {
  @SuppressWarnings({ "rawtypes", "unchecked" })
  @Bean
  public ServletRegistrationBean messageDispatcherServlet(ApplicationContext applicationContext) {
    System.out.println("llego1");
    MessageDispatcherServlet servlet = new MessageDispatcherServlet();
    servlet.setApplicationContext(applicationContext);
    servlet.setTransformWsdlLocations(true);
    return new ServletRegistrationBean(servlet, "/ws/*");
  }

  @Bean(name = "reserves")
  public DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema reservesSchema) {
    DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
    wsdl11Definition.setPortTypeName("ReservePort");
    wsdl11Definition.setLocationUri("/ws");
    wsdl11Definition.setTargetNamespace(ReserveEndPoint.NAMESPACE_URI);
    wsdl11Definition.setSchema(reservesSchema);
    return wsdl11Definition;
  }

  @Bean
  public XsdSchema reservesSchema() {
    return new SimpleXsdSchema(new ClassPathResource("reserves.xsd"));
  }
}
