# Table Top Engine
## A graphical application built on top of Java to manage character sheets! 

Brought to you by: THE C-Team
*Bringing you "Functional" software since 2024* 

# Running the program
Download the compiled .jar binary from this github repository. If you would like to build it yourself, make sure you have the above requirements met. Table Top Engine expects JRE 21 to be present, but theoretically it could work on any version as old as 8. 

# Setting up your development environment
We have standardized our tool set to use the basic (community version) of JetBrains IntelliJ IDEA. 
Make sure you have OpenJDK 22 or newer installed for your system. 

# Supporting the GUI
The graphics system does not use any fancy technologies or packages. We are using Java Swing for windowing. 
*why swing?*
Swing has been around for YEARS. Yes, it's not nearly as fancy as FX but this ensures the the most compatibility as well as being easier to work with for our architecture.

# Supported Operating Systems
Anything that supports Java 22 and supports a GUI *should* theoretically work. But things can special sometimes. Here's what we tested for.
*  Windows 10 (22H2)
*  Windows 11 (25H2) [unfortunately...]
*  Fedora Linux 43 KDE Plasma (X11 and Wayland)
*  Linux Mint
*  Ubuntu 24.10 LTS
*  macOS 15.7.x

## A note on macOS support
Apple frequently flags unknown software as malicious. This program will be flagged as we are not "verified macOS developers." You may comb through the source code to look for malicious bits, but I can assure you we haven't included anything malicious. Except for you Maeve.
You will need to open System Settings, navigate to "Privacy & Security", and allow the application to run (Scroll to the bottom!) 
If you're running the source code from IntelliJ, you won't see this problem. It will show the error though when you attempt to run a compiled binary. Whether you made it yourself or the one pulled from this repo. 

## A note on Linux support
While Linux is a great platform and we've developed this program on it, we can't test every possbile distribution. If there's a distribution that has some sort of error, please make sure that you have the requisite JRE / JDK installed, a Desktop Environment, and the ability to load .jar applications. Sometimes you made need to invoke the .jar executable from the command line. 
