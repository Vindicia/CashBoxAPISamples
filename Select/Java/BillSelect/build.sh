
# Add all required Axis jars to the ClassPath:
cd "$( dirname "${BASH_SOURCE[0]}" )"
cd ../..
DIR=`pwd`
echo "Executing in: $DIR ..."
AXISDIR=$DIR/Axis2/axis2-1.6.2/lib
CP1=$AXISDIR/commons-cli-1.2.jar:$DIR/Axis2/bin:$AXISDIR/axis2-adb-1.6.2.jar
CP2=$CP1:$AXISDIR/axiom-api-1.2.13.jar:$AXISDIR/axis2-kernel-1.6.2.jar
CP3=$CP2:$AXISDIR/wsdl4j-1.6.2.jar:$AXISDIR/*.jar
CP4=$CP3:$AXISDIR/XmlSchema-1.4.7.jar:$AXISDIR/axiom-impl-1.2.13.jar
CP5=$CP4:$AXISDIR/neethi-3.0.2.jar:$AXISDIR/mail-1.4.jar
CP6=$CP5:$AXISDIR/axis2-transport-http-1.6.2.jar:$AXISDIR/axis2-transport-local-1.6.2.jar
CP7=$CP6:$AXISDIR/commons-httpclient-3.1.jar:$AXISDIR/httpcore-4.0.jar
CP8=$CP7:$AXISDIR/commons-codec-1.3.jar:$AXISDIR/httpcore-4.0.jar:$DIR/junit-4.11.jar

SELECT=Select_v1_0
BASEDIR=$DIR/Select
# Now add the Select WSDL generated class files + the local java test class files:
CP=$CP8:$BASEDIR/$SELECT/bin:$BASEDIR/bin


# In $BASEDIR/Select/src/com/vindicia/soap/v1_1/selecttypes/ReturnCode.java:
#
# Note: To compile ReturnCode.java, I had to comment out the following line 566:
#	if ((enumeration == null) && !((value == null) || (value.equals("")))) {
# and replace it with this:
#	if ((enumeration == null)) {


# Compile the Select WSDL generated java class files & place into Select/bin directory:
#cd $BASEDIR/$SELECT
#javac -cp .:$CP -d bin src/com/vindicia/soap/v1_1/selecttypes/*.java src/com/vindicia/soap/v1_1/select/*.java

# Compile the Select Test files to drive the generated java class files & place into bin directory:
cd $BASEDIR/BillSelect
javac -cp .:$CP -d $BASEDIR/bin *.java
