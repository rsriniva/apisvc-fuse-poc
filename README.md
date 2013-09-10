# API Manager Services Fuse POC

## Description
This is a POC around Fuse and Camel to demonstrate functionality that allows Camel to sit in between all Web Service calls.
All Web Service calls will have their URLs transformed and returned to the client.

## Components

### Camel Context
The camel context is described in *camel-context.xml* using the Spring DSL. Currently this is setup as one route that
is listening on *http://address:port/context*.  This is done using the Jetty component.

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

