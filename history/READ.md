#### Local launch cassandra from docker
`docker pull cassandra`

`docker run -it --rm --name voc-cassandra -p7000:7000 -p7001:7001 -p9042:9042 -p9160:9160 cassandra:latest`

then add cassandra config into application.yml

`
cassandra:
  contactpoints: 127.0.0.1
  port: 9042
  keyspace: voc_keyspace
  basepackages: io.voteofconf.history`

#####UML
First setup [graphviz](https://graphviz.gitlab.io/download/) 
Then use idea Plugin [plantuml](https://plugins.jetbrains.com/plugin/7017-plantuml-integration)

John Schulz:   
>Ideal Cassandra Use Cases
It turns out that Cassandra is really very good for some applications.

>The ideal Cassandra application has the following characteristics:

>Writes exceed reads by a large margin.
Data is rarely updated and when updates are made they are idempotent.
Read Access is by a known primary key.
Data can be partitioned via a key that allows the database to be spread evenly across multiple nodes.
There is no need for joins or aggregates.
Some of my favorite examples of good use cases for Cassandra are:

>Transaction logging: Purchases, test scores, movies watched and movie latest location.
Storing time series data (as long as you do your own aggregates).
Tracking pretty much anything including order status, packages etc.
Storing health tracker data.
Weather service history.
Internet of things status and event history.
Telematics: IOT for cars and trucks.
Email envelopes—not the contents.  