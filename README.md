# SimpleKafkaApplication
Created to get familiar with basic Kafka concepts and test Kafka with SpringBoot.

## Modules
There are three modules.
### Producer
Gets data from NBP and sends it to topic `test_topic` for further processing.
Part responsible for getting data from NBP was adapted from [ExchangeRates](https://github.com/PatrykP3/ExchangeRates) project.
Gets data about currencies from one table in specified date range.

Additionally send few simple test records at the start.

Uses Swagger-generated user interface for REST endpoint for data input.
### Processor
Gets data from `test_topic` and divides it into three streams to process each data type separately.
Currency data is "repackaged" so instead of one big array from NBP data is divided into records containing value of one currency in particular day.
### Consumer
Consumes data from topic `test_topic_processed`. Currency data is stored in H2 database. Provides acces to data via REST interface.

Acces to H2 database console:
 * link: [http://localhost:8082/apibase/h2-console](http://localhost:8080/apibase/h2-console)
 * URL: _jdbc:h2:mem:testdb_
 * credentials:
     * username _sa_
     * no password.
## Common parts
Each module exposes various endpoints automatically provided by Spring Actuator and presented by Swagger under link _localhost:<port>//apibase.swagger-ui.html_.
This user interface can be used to gracefully shutdown module.

Port numbers and api prefixes:

module | port | link
------------ | ------------- | -------------
producer | 8080 | [http://localhost:8080/apibase/swagger-ui.html](http://localhost:8080/apibase/swagger-ui.html)
procesor | 8081 | [http://localhost:8081/apibase/swagger-ui.html](http://localhost:8081/apibase/swagger-ui.html)
consumer | 8082 | [http://localhost:8082/apibase/swagger-ui.html](http://localhost:8082/apibase/swagger-ui.html)

Sample call to stop consumer from terminal:
`curl -X POST "http://localhost:8082/apibase/actuator/shutdown"`  

## Preparation
Before running:
* start zookeeper
* start kafka
* create following topics:
    * test_topic
    * test_topic_ttd
    * test_topic_ttsd
    * test_topic_nbprates
    * test_topic_processed

For tests retention of 1000ms was used to allow testing frequent changes.

**TODO**:
* move Processor properties to application.yml
* tests
* windowed aggregation of currency data to calculate mean value on the fly
* port to Spring Cloud or Spring Cloud Integration (TBD)
* Docker
* Ansible

