JC = javac
JFLAGS = -g
JSOURCE = ./java-src
JCLASSES = ./java-classes
JFQN = edu.bridgeport.melabid.cs301.life.Game

LISP = clisp
LIST_FLAGS =

default:
	@echo "Please pick from 'java', 'lisp', 'java-compile', 'java-run' options"

java-compile:
	$(JC) $(JFLAGS) -d $(JCLASSES) -cp $(JSOURCE) $(JSOURCE)/`echo "$(JFQN)" | sed 's/\./\//g'`.java

java-run:
	java -cp $(JCLASSES) $(JFQN)

java: java-compile java-run

lisp:
	$(LISP) $(LISP_FLAGS) life.lisp
