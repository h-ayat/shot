# shot
Simple tool to convert dates (epoch-time, Jalali, Gregorian)

## Description
Just select a part of text and shot (which is show time) converts and displays it!
![image](https://user-images.githubusercontent.com/4332421/176594297-8dba1479-2314-499e-8806-8afee8b0ead0.png)

Supports epoch time (in seconds or milliseconds) and basic date time format like 2022-06-30 22:40 in gregorian and Jalali Calendar

## Installation
Download latest version from [Releases](https://github.com/h-ayat/shot/releases). You should either put the bin dir in your PATH or create a sym-link 
to the main executable somewhere in your path. e.g:
```bash
cd ~/bin
ln -s /PATH/TO/shot/bin/shot
```
Also copy the `showDate.sh` script in your path. Now you can assign some hot-key to the `showDate.sh` and enjoy.

You can either use the hot-key or  command line like this:
```bash
shot 1656564042540
```

Note that to use the `showDate.sh` script you need `xclip` and `libnotify`
