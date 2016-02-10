cd "$( dirname "${BASH_SOURCE[0]}" )"
DIR=`pwd`
echo "Executing in: $DIR ..."

#export JAVA_HOME=$(/usr/libexec/java_home)

AXIS_DIR=~/Downloads/Clients/java/axis2-1.6.2/bin
WSDL_DIR=wsdls

set -x verbose #echo on

$AXIS_DIR/wsdl2java.sh -uri $WSDL_DIR/Select.wsdl -u -s -o . -t
