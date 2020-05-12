\cp -f cobertura.ser.golden cobertura.ser ; java -cp bin:lib/junit.jar:lib/cobertura.jar org.junit.runner.JUnitCore edu.gatech.replace.AllTests
sh Scripts/cobertura-report.sh --destination replaceCoverage/ .
