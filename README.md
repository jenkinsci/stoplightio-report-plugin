# Description

Publish report and statistics after Stoplight/Scenario execution through Prism. See https://help.stoplight.io/scenarios/conducting-scenarios-outside-of-stoplight/running-scenarios

This Jenkins plugin is used as "Post Built Action".

# Usage

## Build

    mvn package

## Installation

The plugin can be install with the differents methods described in the Jenkins documentation : https://wiki.jenkins.io/display/JENKINS/Plugins#Plugins-Howtoinstallplugins


# Features

* Graphical representation based on Google Chart 
* Integration in Jenkins menus

## Configuration
The plugin is able to parse either JSON format or standard output of Prism (compliant with prism-2.0.0-alpha.7).
Choose "Jenkins default console" or "File".

![](doc/configuration.png)

## Job Main Page : trend 

![](doc/homepage.png)

## Job Main Page : history

![](doc/reports_history.png)

## Report Build 

![](doc/report_details.png)


# Notes

Thanks to Stoplight.io team.

