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