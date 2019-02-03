# FDTsFarmWithExp

- Author : FredericDT<i@fdt.onl>

    This is a plugin for minecraft spigot 1.13.2, allowing you to make any block drops experience on its break event.
    
## Commands

### `/fdtsfarmwithexp setExpAmount <amount>`  
    permission: fdtsfarmwithexp.setexpamount
    Set the amount of dropping experience.
### `/fdtsfarmwithexp registerExpBlock`  
    permission: fdtsfarmwithexp.registerexpblock
    Register the block which you looking at as a 'Exp Dropping Block'.
### `/fdtsfarmwithexp unregisterExpBlock`  
    permission: fdtsfarmwithexp.unregisterexpblock
    Unregister the block which you looking at.
## Configuration
config.yml
```yml
ExpAmount: 3 // Dropping exp amount
BlockList: // Blocks dropping exp
- "minecraft:carrots[age=7]"
- "minecraft:potatoes[age=7]"
- "minecraft:wheat[age=7]"
- "minecraft:beetroots[age=3]"
```