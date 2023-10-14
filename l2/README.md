
    Basic:
    input.jsp: a page containing a form for uploading a graph in DIMACS format. (Various graph instances can be found at DIMACS challenges, such as graph coloring instances.
    result.jsp a page describing the response that will be delivered to the client, containing graph properties (measures and meterics): order (number of vertices), size (number of edges), number of connected components, the min/max/average degree, diameter, radius, etc. Consider the case in which the client specifies the desired properties. 


    The app contains:
    1) an object-oriented domain model, for example having the classes Input, Output.
    2) a server-side component responsible with the business-logic of the application: determining the graph properties.
    3) a server-side component responsible with controlling the web-flow.
    4) A web filter that will log all requests received by input.jsp.
    5) A web filter that will decorate the response by adding a specific prelude (at the beginning) and a specific coda (at the end) to the generated HTML page. 
    Graph4J lib has been used.

    Extra:
    1) Created a web listener that reads order and size values (default properties) specified as a context init parameter at the application start-up. This default values are stored in an attribute having application scope and are used whenever the request does not contain those properties. 
    2) Used a "hand-made" cookie to store the properties (order and size values). When the user returns to the site (after the current session was invalidated) and presents this cookie, the properties will be set automatically. 
