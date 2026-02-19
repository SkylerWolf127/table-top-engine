# Table Top Engine
## A graphical application built on top of Java to manage character sheets! 

Brought to you by: THE C-Team
*Bringing you "Functional" since 2024* 



# Setting up your development environment
We have standardized our tool set to use the basic (community version) of JetBrains IntelliJ IDEA. 
Make sure you have OpenJDK 22 (Amazon Corretto) Installed for your system. We switch architecture between Apple M-Series (AARCH64) and general x86_64 though theoretically architecture is independent of this program.

# Running the program
Download the compiled .jar binary from this github repository. If you would like to build it yourself, make sure you have the above requirements met. 



# Supporting the GUI
The graphics system does not use any fancy technologies or packages. We are using Java Swing for windowing. 
*why swing?*
Swing has been around for YEARS. Yes, it's not nearly as fancy as FX but this ensures the the most compatibility as well as being easier to work with for our architecture.

# Supported Operating Systems
Anything that supports Java 22 and Java Swing *should* theoretically work. But things can explode sometimes. Here's what we tested for.
*  Windows 10 (22H2)
*  Windows 11 (25H2) [unfortunately...]
*  Fedora Linux 43 KDE Plasma (X11 and Wayland)
*  Linux Mint
*  macOS 15.7.x

## A note on macOS support
Apple frequently flags unknown software as malicious. This program will be flagged as we are not "verified macOS developers." You may comb through the source code to look for malicious bits, but I can assure you we haven't included anything malicious. Except for you Maeve.
You will need to open System Settings, navigate to "Privacy & Security", and allow the application to run (Scroll to the bottom!) 
If you're running the source code from IntelliJ, you won't see this problem. It will show the error though when you attempt to run a compiled binary. Whether you made it yourself or the one pulled from this repo. 

## A note on Linux support
We (*the C-Team*) are Ardent Linux users. We hate Windows just as much as the next guy. But there's some things we can't account for in the development process. YOUR MILEAGE MAY VARY ON DIFFERENT LINUX DISTRIBUTION. No we didn't test, Ubuntu, no we didn't test Arch, no we didn't test X-Distribution. If you can load a desktop on it and can install OpenJDK 22, you generally should be OK. 
If a Linux distribution is incompatible for whatever reason and you find a fix, please contact us! 
