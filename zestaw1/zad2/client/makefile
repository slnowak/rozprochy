CC = gcc
TARGETS = client
CFLAGS = -std=c99 -Wall -g
LFLAGS =

all: $(TARGETS)

$(TARGETS):
	@$(CC) -o $@ $@.c $(CFLAGS) $(LFLAGS)

clean:
	@rm -f $(TARGETS)

.PHONY:
	all clean
