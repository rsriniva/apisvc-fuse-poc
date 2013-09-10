# API Manager Services Fuse POC

## Description
This is a POC around Fuse and Camel to demonstrate functionality that allows Camel to sit in between all Web Service 
calls. All Web Service calls will have their URLs transformed and returned to the client.

## Components

### Camel Context
The camel context is described in *camel-context.xml* using the Spring DSL. Currently this is setup as one route that
is listening on *http://address:port/context*.  This is done using the Jetty component. The main thing to notice from 
the URI is the *matchOnUriPrefix*. This means that jetty will grab any HTTP request under the */apimanager* context.

    <camel:from uri="jetty:http://localhost:8282/apimanager?matchOnUriPrefix=true" />

If a WSDL is requested, then that request is forwarded directly to the backend service to get the WSDL. This would be 
the point where any transformations to the WSDL would occur.

    <camel:when>
      <camel:simple>${header.wsdl}</camel:simple>

If the application key needs to be validated, that path is matched with a path defined in *example.properties* which
is included in the context with the line:

    <camel:propertyPlaceholder id="properties" location="example.properties"/>

Using this it is matched against the path that was requested:

    <camel:when>
      <camel:simple>${headers.CamelHttpUri} == ${properties:applicationkeyvalidationservice.path}</camel:simple>

All of the other service requests are just passed through with an *otherwise* statement.

Requests are forwarded to the target by setting the header value *CamelHttpUri* and then calling the *http* component. 
You do not need to have an actual HTTP URL in the endpoint URI. It will use the value from *CamelHttpUri*.

### Tests
There are two test to demonstrate the functionality. These are *WsCamelTest* and *CamelTest*.

#### WsCamelTest
The WsCamelTest makes a call to the Camel route which is then forwarded to the underlying web service.

#### CamelTest
The CamelTest submits a WSDL call through the Camel route. It also makes a direct call to the underlying web service. There is a small issue with Jetty's keep alive that requires the sleep at the end of the test to allow it to shutdown.
