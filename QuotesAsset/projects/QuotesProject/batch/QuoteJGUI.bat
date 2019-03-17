@ECHO OFF

REM ---------------------------------------------------------------------------
REM Prior to running this script, 
REM ensure that the configuration files have been edited appropriately.
REM application.properties: set JDBC url, user and password
REM log4j.xml: set path to logging file if needed
REM ---------------------------------------------------------------------------

set CP=
set CP_BASE=
set CP_LIB=

set CP_BASE=.
set CP_LIB=.

set CP=%CP_BASE%
set CP=%CP%;%CP_BASE%\quotes-3.1.0.0-RELEASE.jar
REM set CP=%CP%;%CP_BASE%\Quotes.jar
set CP=%CP%;%CP_BASE%\commons-logging-1.1.1.jar
set CP=%CP%;%CP_BASE%\commons-lang3-3.7.jar
set CP=%CP%;%CP_BASE%\log4j-1.2.14.jar

set CP

:RUN_IT

   if "%1" == ""       goto RANDOM
      goto SPECIFIC

:SPECIFIC
	Start java -classpath %CP% com/eric/controller/CommandLineController "-g" "%1"	
	goto END

:RANDOM
	Start java -classpath %CP% com/eric/controller/CommandLineController "-g"	
	goto END

:END


