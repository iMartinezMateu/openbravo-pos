# openbravopos
An open source Point of Sale software for computers and embedded devices made with Java

## Introduction
Openbravo Java POS is a point of sale application designed for touch screens, supports ticket printers, customer displays and barcode scanners.

This project is entirely based on the original version stored at [Sourceforge]( https://sourceforge.net/projects/openbravopos/). Minor changes has been made in order to compile this project with the latest version of Java. Original license has not been modified in any way.

## How to compile?
Netbeans is the preferred Java IDE to develop Openbravo POS because the Swing components of Openbravo POS have been developed with the visual editor of Netbeans, and in this edition is stored in *.form files that only Netbeans is able to interpret.
You can download Netbeans from [here](http://www.netbeans.org). And there is a quick guide to develop with Netbeans [here](http://www.netbeans.org/kb/60/java/quickstart.html).

1. To create a new Netbeans project with the Openbravo sources, open Netbeans and select _New project..._ to open the new project wizard, select _Java Project with Existing Sources_ and press _Next_.
2. In the following step select the project name and the folder where the Netbeans project files will be stored and press _Next_. Do not select the same folder you checked out the sources.
3. In the following step select the source folders. Add the folders _locales_, _reports_, _src-beans_, _src-data_ and _src-pos_. In this step you can press _Finish_.
4. The next step is to add the libraries needed to build and execute Openbravo POS. Open the _Properties..._ dialog of the project and in the libraries section add all the *.jar files of the folder _libs_.
5. Now you have ready a Netbeans project configured than you can edit, build and test. To execute and debug Openbravo POS you need also to select the main class of the project. Open the _Properties..._ dialog and in the run section Openbravo select `com.openbravo.pos.forms.StartPOS` as the Main Class and press _OK_.

## Documentation
See http://wiki.openbravo.com for more documentation.

## Reporting issues
Issues can be reported via the [Github issue tracker](https://github.com/iMartinezMateu/openbravo-pos/issues). 

Please take the time to review existing issues before submitting your own to prevent duplicates. Incorrect or poorly formed reports are wasteful and are subject to deletion.

## Submitting fixes and improvements
Fixes and improvements are submitted as pull requests via Github. 

