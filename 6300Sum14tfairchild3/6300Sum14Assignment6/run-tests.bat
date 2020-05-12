copy .\cobertura.ser.golden .\cobertura.ser
java -cp "bin\;.\lib\junit.jar;.\lib\cobertura.jar" org.junit.runner.JUnitCore edu.gatech.replace.AllTests
.\Scripts\cobertura-report.bat --destination replaceCoverage\ .
