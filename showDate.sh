#!/bin/bash

OBJ=`xclip -o`
RES=$(echo $OBJ | (timer -i))
echo $RES
notify-send "Date" "$RES" --icon=dialog-information
