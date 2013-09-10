package com.redhat.bashburn.fuse.tasks;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

import com.redhat.bashburn.taskmanager.ListTasksRequest;
import com.redhat.bashburn.taskmanager.ListTasksResponse;
import com.redhat.bashburn.taskmanager.TasksEndpoint;

@WebService(targetNamespace = "http://taskmanager.bashburn.redhat.com", name = "TasksEndpoint")
public class TasksEndpointImpl implements TasksEndpoint {

    @WebResult(name = "listTasksResponse", targetNamespace = "http://taskmanager.bashburn.redhat.com", partName = "out")
    @WebMethod(operationName = "ListTasks", action = "http://taskmanager.bashburn.redhat.com/ListTasks")
    public ListTasksResponse listTasks(
        @WebParam(partName = "in", name = "listTasksRequest", targetNamespace = "http://taskmanager.bashburn.redhat.com")
        ListTasksRequest in) {
      return new ListTasksResponse();
    }
}