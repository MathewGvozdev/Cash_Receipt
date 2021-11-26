						
				        	RUN INSTRUCTION

To compile files from project directory:

	javac -sourcepath ./src -d bin src/main/com/mathewgv/app/CheckRunner.java

To run application:

1st option:
	java -classpath ./bin main/com/mathewgv/app/CheckRunner [args]

2nd option (with jar-file):
    java -jar out/artifacts/Cash_Receipt_jar/Cash_Receipt.jar [args]

Arguments example:

	1-2 2-5 4-4 10-2 13-2 card-0034

Discount cards and products info are in: 
	
	./src/main/resources/

Output check-files are in:

	./src/main/checks/
