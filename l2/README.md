
    Basic:
    input.jsp: a page containing a form for uploading a graph in DIMACS format. (coloring instances can be found in the resources directory).
    result.jsp a page describing the response that will be delivered to the client, containing graph properties (measures and meterics):
    order (number of vertices), size (number of edges), number of connected components, the min/max/average degree, diameter, radius, etc. 
    The client can specifies the size and order properties (number of vertices and number of edges) for the graph to be generated, and then
    the graph is analyzed. 


    The app contains:
    1) an object-oriented domain model: Input, Output.
    2) a server-side component responsible with the business-logic of the application: determining the graph properties: CookieService, GraphAnalysisService, 
    including helper classes: DIMACSParser, GraphGenerator.
    3) a server-side component responsible with controlling the web-flow: WebFlowController
    4) A web filter that will log all requests received by input.jsp: LogFilter
    5) A web filter that will decorate the response by adding a specific prelude (at the beginning) and a specific coda (at the end) to the generated HTML page: ResponseDecoratorFilter
    Graph4J lib has been used.

    Extra:
    1) Created a web listener that reads order and size values (default properties) specified as a context init parameter at the application start-up. 
    This default values are stored in an attribute having application scope and are used whenever the request does not contain those properties: WebListenerDefaultParams
    2) Used a "hand-made" cookie to store the properties (order and size values). When the user returns to the site (after the current session was invalidated) and presents this cookie, the properties will be set automatically. 
