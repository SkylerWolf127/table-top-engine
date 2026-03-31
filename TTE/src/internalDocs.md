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

#   public static void openErrorWindow(String errorText)
**Parent class:** None

**Overrides:** `actionPerformed`

**Implementations:** None

**Arguments:** `String errorText`

## Local variables

`errorFrame` - Frame for the error window. Title for it is dynamically generated based on the error text that is passed into it.

`errorPanel` - A panel for buttons and elements to be composed onto. 

`errorLabel` - A label text showing the error. Dynamically generated based on the error text that is passed into the method. 

`acknowledgeButton` - A button that makes the window go away.
