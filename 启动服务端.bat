@echo off
@title ����ð�յ�079V3�� Ⱥ��̳��mxd.592uc.com QQȺ�ţ�542123915
Color 0B
set path=jre7\bin;%SystemRoot%\system32;%SystemRoot%;%SystemRoot%
set JRE_HOME=jre7
set JAVA_HOME=\jre7\bin
set CLASSPATH=.;dist\*
java  -Xms512m -Xmx512m -Xss128k -XX:ReservedCodeCacheSize=128m -Dnet.sf.odinms.wzpath=wz gui.KinMS
pause