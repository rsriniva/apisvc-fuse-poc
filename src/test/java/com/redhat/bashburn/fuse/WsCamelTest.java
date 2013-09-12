package com.redhat.bashburn.fuse;

import static org.hamcrest.CoreMatchers.*;

import java.io.File;
import java.io.FileOutputStream;
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

public class WsCamelTest extends CamelSpringTestSupport {
  private static final String URL = "http://localhost:8282/apimanager/taskmanager/webservices/tasks";

  @Test
  public void executeWebService() throws Exception {
    String url = context.resolvePropertyPlaceholders(URL);
    JaxWsProxyFactoryBean proxyFactory = new JaxWsProxyFactoryBean();
    proxyFactory.setServiceClass(TasksEndpoint.class);
    proxyFactory.setAddress(url);
    TasksEndpoint endpoint = (TasksEndpoint)proxyFactory.create();
    ListTasksRequest request = new ListTasksRequest();
    request.setUserId("testuserid");
    ListTasksResponse response = endpoint.listTasks(request);
    assertThat(response, notNullValue());
  }

  @Override
  protected AbstractApplicationContext createApplicationContext() {
    return new ClassPathXmlApplicationContext("META-INF/spring/camel-context.xml");
  }
	
}