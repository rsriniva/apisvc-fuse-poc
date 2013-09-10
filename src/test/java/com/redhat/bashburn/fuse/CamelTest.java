package com.redhat.bashburn.fuse;

import static org.hamcrest.CoreMatchers.*;

import java.io.IOException;
import java.io.File;
import java.io.FileOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Properties;

import org.apache.camel.test.junit4.CamelSpringTestSupport;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.junit.Test;
import org.junit.BeforeClass;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.redhat.bashburn.taskmanager.ListTasksRequest;
import com.redhat.bashburn.taskmanager.ListTasksResponse;
import com.redhat.bashburn.taskmanager.TasksEndpoint;

public class CamelTest extends CamelSpringTestSupport {
  private static final String URL = "http://localhost:8181/apimanager/taskmanager/webservices/tasks";

  @Test
  public void execute() throws Exception {
    JaxWsProxyFactoryBean proxyFactory = new JaxWsProxyFactoryBean();
    proxyFactory.setServiceClass(TasksEndpoint.class);
    String url = "http://localhost:8181/cxf/taskmanager/webservices/tasks";
    proxyFactory.setAddress(url);
    TasksEndpoint endpoint = (TasksEndpoint)proxyFactory.create();
    ListTasksRequest request = new ListTasksRequest();
    request.setUserId("testuserid");
    ListTasksResponse response = endpoint.listTasks(request);
    assertThat(response, notNullValue());
    org.apache.cxf.BusFactory.setDefaultBus(null);
  }
  @Test
  public void checkForWsdl() throws Exception {
    try {
      String transUrl = "http://localhost:8282/apimanager/taskmanager/webservices/tasks?wsdl";
      URL url = new URL(transUrl);
      HttpURLConnection conn = (HttpURLConnection)url.openConnection();
      assertThat(conn.getResponseCode(), is(HttpURLConnection.HTTP_OK));
      String wsdlString = context.getTypeConverter().convertTo(String.class, conn.getInputStream());
      assertThat(wsdlString, notNullValue());
      org.apache.cxf.BusFactory.setDefaultBus(null);
      // This is a hack to deal with the keepAlive timeout from the Jetty server
      Thread.sleep(5000);
    } catch(IOException e) {
      e.printStackTrace();
      fail(e.getMessage());
    }
  }

  @Override
  protected AbstractApplicationContext createApplicationContext() {
    return new ClassPathXmlApplicationContext("META-INF/spring/camel-context.xml");
  }

}
