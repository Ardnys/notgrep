# notgrep
_notgrep_ is a command line utility that aims to provide a rudimentary search engine functionality inside a very specific file that represents a basic file system through simple commands written in a command file. 

## Part 1: The program as it is in the project description
That very specific file is assumed to follow the rule below:\
\<document-name\>\
\####\
\<document-body\>\
\####\
\<document-name\>\
...

_notgrep_ supports the following commands which are also supposed to be written in a text with very specific rules. 
| Command | Description |
| --- | --- |
| load <path/to/input/file.txt> | loads the input file to system. querying will be done on this file |
| search \<query-string> | searches the given arguments in the loaded file |
| remove \<document-name> | removes a document from the system. the document **is not** removed from the input file |
| clear list | clears the entries and restarts the system |

The command file shall follow the following rule:\
path/to/output/txt;\
command1;\
command2;\
...

Yes, the search results are written in an output file. Which also has a format like this:

query \<query-string(s)>\
\<document 1, document 2> (search results basically)\
...

Yeah...


## Part 2: The program after my magic touch
As you can tell, there's a lot of peculiar things going on with the thing described above. I shall rectify the situation as soon as I am done with Part 1.

**Some Issues**
- Unintuitive and hard to use.
- Lack of interactivity.
- Command file doesn't make a lot of sense.
- Output file also doesn't make a lot of sense.
- clear list command needs renaming.

To me, this programs _screams_ REPL with an optional command file feature, like scripting languages (like writing `python` starts a REPL and `python <file.py>` interprets the file). There is no loop so it's more like REP but do you see where I am going at?\
I have this image in my mind and it looks nicer in my opinion:

**Usage: As is in the project without an output file**
```console
$ notgrep [OPTIONAL FLAGS] <command_file.txt>
(just prints to stdout)
```
**Usage: - REP (Read-Evaluate-Print) normally there's an L but this doesn't have loops**
```console
$ notgrep
> load ...
> search ...
> remove ...
> clear list
```
OPTIONAL FLAGS\
-h   displays a help page\
-bst changes the data structure to binary search tree\
-ll  changes the data structure to linked list\
-rb  changes the data structure to red-black tree (default)
