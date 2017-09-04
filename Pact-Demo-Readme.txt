Microservices + Maven + Pact

This project contains a simple demo of using consumer-driven-contract testing to verify the interactions between microservices. 
It leverages Spring Boot for both the provider and consumer services.
Testing is achieved using the pact-jvm project, which is a JVM port of the original pact.

Running the Demo;

1.) Import microservices-pact-maven project to your IDE workspace (Eclipse/STS/JBoss Studio)

2.) Run the build with tests using Maven:
	
	a) Goto the project root folder
	b) D:\Latest\microservices-pact-maven>mvn clean install
	
	Make sure that both consumer and provider build successfully and test case executed;
	
	[INFO] microservices-pact-maven ........................... SUCCESS [  0.967 s]
	[INFO] microservices-pact-consumer ........................ SUCCESS [ 26.995 s]
	[INFO] microservices-pact-provider ........................ SUCCESS [  1.875 s]
	[INFO] ------------------------------------------------------------------------
	[INFO] BUILD SUCCESS
	

2.) This will result in the creation of a "PACT file called "Service_Consumer_DXC-Service_Provider_ATT.json" at 

	a) Goto  microservices-pact-maven/microservices-pact-consumer/target/pacts  folder
	
	b) Open  Service_Consumer_DXC-Service_Provider_ATT.json
	{
	    "provider": {
	        "name": "Service_Provider_ATT"
	    },
	    "consumer": {
	        "name": "Service_Consumer_DXC"
	    },
	    "interactions": [
	        {
	            "description": "A request for Provider Service",
	            "request": {
	                "method": "GET",
	                "path": "/userService"
	            },
	            "response": {
	                "status": 200,
	                "headers": {
	                    "Content-Type": "application/json;charset=UTF-8"
	                },
	                "body": [
	                    {
	                        "value": 42
	                    },
	                    {
	                        "value": 100
	                    }
	                ]
	            }
	        }
	    ],
	    "metadata": {
	        "pact-specification": {
	            "version": "2.0.0"
	        },
	        "pact-jvm": {
	            "version": "3.3.9"
	        }
	    }
	}
	
	
	This is the contract file generated with the help of PACT-MOCK Server 
	By virtue of the fact that these tests pass, we know that the microservices-pact-consumer app interacts appropriately with 
	the contract as defined in ConsumerPortTest.java.	

3.) 	Next, we want to verify that the "microservices-pact-provider" app actually provides the expected contract. 
	In one terminal window, start up the provider app:

	D:\Latest\microservices-pact-maven>
	
		java -jar -Dserver.port=8089 microservices-pact-provider/target/ATT-microservices-pact-provider-1.0.0-SNAPSHOT.jar	
		
	
	Check Tomcat is up and running
		Tomcat started on port(s): 8089 (http)
		Started Application in 12.52 seconds (JVM running

4.) Then, in another terminal window, run the pact verification:

		 D:\Latest\microservices-pact-maven\microservices-pact-consumer>mvn pact:verify
				
				or
	
		D:\Latest\microservices-pact-maven> mvn au.com.dius:pact-jvm-provider-maven_2.11:verify
	
Output:

Loading pact files for provider Service_Provider_ATT from D:\Latest\microservices-pact-maven\microservices-pact-consumer\target\pacts
Found 1 pact files

Verifying a pact between Service_Consumer_DXC and Service_Provider_ATT
  [Using file D:\Latest\microservices-pact-maven\microservices-pact-consumer\target\pacts\Service_Consumer_DXC-Service_Provider_ATT.json]
  A request for Provider Service
    returns a response which
      has status code 200 (OK)
      includes headers
        "Content-Type" with value "application/json;charset=UTF-8" (OK)
      has a matching body (OK)
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time: 10.597 s
[INFO] Finished at: 2017-08-22T11:13:39+05:30
[INFO] Final Memory: 24M/137M
[INFO] ------------------------------------------------------------------------


5.) 	The pact maven plugin provides a�publish�mojo(Maven�plain Old�Java�Object) that can publish all pact files in a directory 
	to a pact broker. To use it, you need to add a publish configuration to the POM that defines the directory where the pact files 
	are and the URL to the pact broker.

	D:\Latest\microservices-pact-maven\microservices-pact-consumer>mvn pact:publish
	
	
6)	To verify the PACT published in PACT broker;

	Here are the access details:

	Host: https://csc.pact.dius.com.au
	Username: 6O1sY1m85a8YRTC8nvkty3ot9IAXO
	Password: VbwPE2nO8WMNCvBWePmkxQcaFwyf5



Hope this simplifies your contract testing journey with Pact.


