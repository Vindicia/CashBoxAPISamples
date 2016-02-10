SET DIR=%CD%
echo "Executing in: %DIR% ..."

SET JAVA_HOME=G:\Program Files\Java\jdk1.7.0_40
SET AXIS2_HOME=..\..\..\Axis2\axis2-1.6.2

SET AXIS_DIR=%AXIS2_HOME%\bin
SET WSDL_DIR=wsdls

CMD /C %AXIS_DIR%\wsdl2java.bat -uri %WSDL_DIR%\Select.wsdl -u -s -o . -t

pause
