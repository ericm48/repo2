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

set CP_BASE=..
set CP_LIB=%CP_BASE%\lib


set CP=%CP_BASE%
set CP=%CP%;%CP_BASE%\build\Release\Quotes.jar;%CP_BASE%\src\main\resources
set CP=%CP%;%CP_LIB%\log4j-1.2.8.jar
set CP=%CP%;%CP_LIB%\logger.jar

set CP


:RUN_IT

   if "%1" == ""       goto RANDOM
      goto SPECIFIC

:SPECIFIC
	Start java -Xmx1536m -classpath %CP% com/eric/controller/CommandLineController "-g" "%1"
	goto END

:RANDOM
	Start java -Xmx1536m -classpath %CP% com/eric/controller/CommandLineController "-g"
	goto END

:END


