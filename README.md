Kontonummern
============

According to the German Wikipedia there are 143 different Methods to calculate the check digit in Germany:

https://de.wikipedia.org/wiki/Kontonummer#Deutschland

1. https://www.bundesbank.de/Navigation/DE/Aufgaben/Unbarer_Zahlungsverkehr/Serviceangebot/Pruefzifferberechnung/pruefzifferberechnung.html
2. https://www.bundesbank.de/Redaktion/DE/Downloads/Aufgaben/Unbarer_Zahlungsverkehr/pruefzifferberechnungsmethoden.pdf?__blob=publicationFile

Build
-----

mvn -DcompilerArgument=-Xlint:deprecation clean package

Run
---

java -jar target/Main-0.1.0.jar DE51 XXXX XXXX XXXX XXXX XX

or

java -jar target/Main-0.1.0.jar DE51XXXXXXXXXXXXXXXXXX

Maven
-------

pom.xml was created witht the help of:

mvn help:effective-pom

License
-------

WTFPL LICENSE.md
