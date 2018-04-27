#
# This sample was run using wsdl2php from pear to generate PHP library:
#
# 1. Download & install pear if needed (already installed on the Mac):
#
#	https://pear.php.net/manual/en/installation.getting.php
#
# 2. Download & install wsdl2php-0.2.1-pear.tgz from Sourceforge:
#
#	https://sourceforge.net/projects/wsdl2php/
#
#		sudo pear install wsdl2php-0.2.1-pear.tgz
#
# 3. Generate PHP library using wsdl2php:
#
#		wsdl2php https://soap.vindicia.com/1.1/Select.wsdl
#
# 4. Cleanup generated code to work with Select (Return datatype is PHP keyword):
#
#	mv Select.php{,.orig}; sed 's/class Return /class VindiciaReturn /g' Select.php.orig > Select2.php
#	(or edit generated Select.php: change class Return to class VindiciaReturn).
#
#	(SelectUtil.php overrides 'Return' to 'VindiciaReturn' to match the change above)
#
#	Also fix up constructor to be compatible with PHP 7.0+:
#
#	sed 's/function Select(/function __construct(/g' Select2.php > Select.php
#
# 5. This sample may be found on GitHub at:
#
#	https://github.com/Vindicia/CashBoxAPISamples/tree/master/Select/PHP
#

#
# Note: It may be necessary to first add execute permissions before running:
#	chmod +x generate_PHP.sh
#

cd "$( dirname "${BASH_SOURCE[0]}" )"
DIR=`pwd`
echo "Executing in: $DIR ..."

wsdl2php https://soap.vindicia.com/1.1/Select.wsdl

mv Select.php{,.orig}; sed 's/class Return /class VindiciaReturn /g' Select.php.orig | sed 's/function Select/function __construct/g' > Select.php
diff Select.php{.orig,}

