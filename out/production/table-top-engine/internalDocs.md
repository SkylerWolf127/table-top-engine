# TTE Internal functional and method documentation
*This is an informal document logging all the functions of methods and what they do* 

Assume all buttons have their respective handlers. Overrides will be stated as necessary. 
# public static void main(String[] args)
**Parent class:** `Main`

**Overrides:** `None`

**Implementations:** `None`

**Arguments:** `String[] args` *Not used under normal circumstances* 

## local variables

`frame` - Main menu of the program

`createSheetButton` - Button for making a new character sheet

`loadSheetButton` - Button for loading an existing sheet

`showSheetButton` - Show a currently loaded sheet

`loadSheet` - Volatile variable that holds a sheet object. Intended for use with `loadSheetButton`





# Routines
## Save routine
Implements the PlayerSheetIO class. Use `savePlayerSheetToFile(Sheet sheet, String filename)`
Pass in a valid `Sheet` object and a `filename` to call it. This will save to the project's local directory or wherever the .JAR file is being ran from.

## Load routine
Implements the PlayerSheetIO class. Use `loadPlayerSheetFromFile(String filename)`
pass in a valid `sheet` object file name. The function is not looking for any particular file name, but as long as you give it a full name + extension, it should find something.
Example, use `playerSheet.dat`. If that file exists on the local directory, `loadPlayerSheetFromFile` will load it into memory.