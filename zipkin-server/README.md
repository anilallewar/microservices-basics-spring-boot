#Overview

This application provides the **Zipkin Server** that provides UI for the [Zipkin distributed tracing](http://zipkin.io/).

When we enable tracing on the applications, they send the tracing data (timing, components called etc) to the Zipkin server so that we can visualize it.

##Pre-requisites

### Projects that need to be started before
* [config server](/../../blob/master/config-server/README.md) - For pulling the configuration information

### Running the application
* Build the application by running the `./gradlew clean build` gradle command at the "webservice-registry" project root folder	on the terminal.
* If you want to run the application as jar file, then run `java -jar build/libs/basic-zipkin-server-0.0.1.jar` command at the terminal.

## External Configuration
Please refer to [user webservice](/../../blob/master/user-webservice/README.md) for details on how the external configuration works. Note that there is separate configuration file for each Spring application; the application should refer to it's own .yml file for configuration.