#!/bin/bash

OBJ=`xclip -o`
RES=$(echo $OBJ | (shot -i))
echo $RES
notify-send "Date" "$RES" --icon=dialog-information
