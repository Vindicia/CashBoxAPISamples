
REM Add all required Axis jars to the ClassPath;
cd ..
SET DIR=%CD%
echo "Executing in: %DIR% ..."
REM SET AXISDIR=%AXIS2_HOME%\lib
SET AXISDIR=%DIR%\..\..\Axis2\axis2-1.6.2\lib

SET CP1=%AXISDIR%\commons-cli-1.2.jar;%AXISDIR%\axis2-adb-1.6.2.jar
SET CP2=%CP1%;%AXISDIR%\axiom-api-1.2.13.jar;%AXISDIR%\axis2-kernel-1.6.2.jar
SET CP3=%CP2%;%AXISDIR%\wsdl4j-1.6.2.jar;%AXISDIR%\*.jar
SET CP4=%CP3%;%AXISDIR%\XmlSchema-1.4.7.jar;%AXISDIR%\axiom-impl-1.2.13.jar
SET CP5=%CP4%;%AXISDIR%\neethi-3.0.2.jar;%AXISDIR%\mail-1.4.jar
SET CP6=%CP5%;%AXISDIR%\axis2-transport-http-1.6.2.jar;%AXISDIR%\axis2-transport-local-1.6.2.jar
SET CP7=%CP6%;%AXISDIR%\commons-httpclient-3.1.jar;%AXISDIR%\httpcore-4.0.jar
SET CP8=%CP7%;%AXISDIR%\commons-codec-1.3.jar;%AXISDIR%\httpcore-4.0.jar

SET SELECT=v1_1
SET BASEDIR=%DIR%\%SELECT%
REM  Now add the Select WSDL generated class files + the local java test class files;
SET CP=%CP8%;%BASEDIR%\bin
REM This is only included if ant was used to build (using build.xml):
REM ;%BASEDIR%\build\classes


REM  In %BASEDIR\Select\src\com\vindicia\soap\v1_1\selecttypes\ReturnCode.java;
REM 
REM  Note; To compile ReturnCode.java, I had to comment out the following line 566;
REM 	if ((enumeration == null) && !((value == null) || (value.equals("")))) {
REM  and replace it with this;
REM 	if ((enumeration == null)) {

javac -version

REM To be compatible with all Java versions forward from 1.5:
SET COMPAT_VER=1.5
SET COMPATIBILITY=-source %COMPAT_VER% -target %COMPAT_VER%

REM  Compile the Select WSDL generated java class files & place into Select\bin directory;
cd %BASEDIR%
javac -cp .;%CP% %COMPATIBILITY% -d bin src\com\vindicia\soap\%SELECT%\selecttypes\*.java src\com\vindicia\soap\%SELECT%\select\*.java

REM  Compile the Select Test files to drive the generated java class files & place into bin directory;
REM cd %BASEDIR%\BillSelect
REM javac -cp .;%CP% %COMPATIBILITY% -d %BASEDIR%\bin *.java

REM Create jar file
cd %BASEDIR%\bin
jar cvf VindiciaSelect_%SELECT%.jar com

REM Generate javadocs
cd %BASEDIR%
javadoc -d docs -sourcepath src com.vindicia.soap.%SELECT%.select com.vindicia.soap.%SELECT%.selecttypes

pause
