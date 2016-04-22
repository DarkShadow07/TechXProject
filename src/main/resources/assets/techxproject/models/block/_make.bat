@echo off
:: Vazkii's JSON creator for blocks
:: Put in your /resources/assets/%modid%/models/block
:: Makes basic block JSON files as well as the acossiated item and simple blockstate
:: Can make multiple blocks at once
::
:: Usage:
:: _make (block name 1) (block name 2) (block name x)
::
:: Change this to your mod's ID
set modid=techxproject

setlocal enabledelayedexpansion

for %%x in (%*) do (
	echo Making %%x.json block
	(
		echo {
		echo 	"parent": "block/cube_all",
		echo 	"textures": {
		echo 		"all": "%modid%:blocks/%%x"
		echo 	}
		echo }
	) > %%x.json

	echo Making %%x.json blockstate
	(
		echo {
		echo 	"forge_marker": 1,
		echo 	"defaults": {
		echo 		"model": "%modid%:%%x"
		echo 	},
		echo 	"variants": {
		echo 		"render":{
	        		"defaults":{
               			"model":"%modid:%%x", "transform":{
	                	"thirdperson":{ "rotation":[ {"x":10}, {"y":-45}, {"z":170} ], "translation":[ 0, 0.09375, -0.171875 ], "scale":[ 0.375, 0.375, 0.375 ] },
	                	"firstperson":{ "rotation":[ {"x":0},  {"y":90},  {"z":0}   ], "translation":[ 0, 0, 0 ], "scale":[ 1, 1, 1 ] },
	                	"gui":{         "rotation":[ {"x":0},  {"y":-90}, {"z":0}   ], "translation":[ 0, 0, 0 ], "scale":[ 1, 1, 1 ] },
	               		"fixed":{       "rotation":[ {"x":0},  {"y":0},   {"z":0}   ], "translation":[ 0, 0, 0 ], "scale":[ 1, 1, 1 ] }
	           	 	}
	        	}
	    	},
			"normal": [{}],
			"inventory": [{}]
		echo 	}
		echo }
	) > ../../blockstates/%%x.json
)